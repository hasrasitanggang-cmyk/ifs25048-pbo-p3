package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ItemUseCase;
import java.util.NoSuchElementException;

/** Tampilan konsol inventaris barang. Hanya interaksi user, tanpa logika bisnis. */
public class ItemView {
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase useCase, ItemPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        try {
            runLoop();
        } catch (NoSuchElementException e) {
            // Input habis (EOF): hentikan aplikasi dengan tenang.
        }
    }

    private void runLoop() {
        boolean running = true;
        while (running) {
            presenter.showItems(useCase.getAllItems());
            printMenu();

            String input = InputUtil.input("Pilih");
            System.out.println();
            switch (input) {
                case "1" -> addItem();
                case "2" -> updateStock();
                case "3" -> searchItem();
                case "4" -> sortItem();
                case "5" -> removeItem();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah Stok");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    private void addItem() {
        System.out.println("[Menambah Barang]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) {
            return;
        }
        String strQuantity = InputUtil.input("Jumlah");

        try {
            int quantity = parseQuantity(strQuantity);
            String category = InputUtil.input("Kategori (x Jika Batal)");
            if (category.equals("x")) {
                return;
            }
            presenter.showAddSuccess(useCase.addItem(name, quantity, category));
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void updateStock() {
        System.out.println("[Mengubah Stok]");
        String strId = InputUtil.input("ID Barang yang diubah (x Jika Batal)");
        if (strId.equals("x")) {
            return;
        }

        try {
            int id = parseId(strId);
            String strQuantity = InputUtil.input("Jumlah Baru (Kosongkan jika tidak ingin mengubah)");
            // Input kosong berarti stok tidak diubah.
            Integer quantity = strQuantity.trim().isEmpty() ? null : parseQuantity(strQuantity);
            useCase.updateStock(id, quantity);
            presenter.showUpdateSuccess();
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showUpdateFailed(e.getId());
        }
    }

    private void searchItem() {
        System.out.println("[Mencari Barang]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchItems(keyword), keyword);
        }
    }

    private void sortItem() {
        System.out.println("[Mengurutkan Barang]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("3. Jumlah (Terkecil -> Terbesar)");
        System.out.println("4. Jumlah (Terbesar -> Terkecil)");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        System.out.println();
        if (input.equals("x")) {
            return;
        }

        try {
            presenter.showSortedItems(useCase.sortItems(mapSortOption(input)));
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void removeItem() {
        System.out.println("[Menghapus Barang]");
        String strId = InputUtil.input("[ID Barang] yang dihapus (x Jika Batal)");
        if (strId.equals("x")) {
            return;
        }

        try {
            int id = parseId(strId);
            useCase.removeItem(id);
            presenter.showRemoveSuccess();
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showRemoveFailed(e.getId());
        }
    }

    private int parseId(String value) throws ValidationException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("ID tidak valid!");
        }
    }

    private int parseQuantity(String value) throws ValidationException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
    }

    private SortOption mapSortOption(String input) throws ValidationException {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            case "3" -> SortOption.QUANTITY_ASC;
            case "4" -> SortOption.QUANTITY_DESC;
            default -> throw new ValidationException("Pilihan tidak valid!");
        };
    }
}