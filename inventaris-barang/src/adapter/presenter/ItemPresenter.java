package adapter.presenter;

import domain.entity.Item;
import java.util.List;

/** Presenter yang memformat output inventaris barang. */
public class ItemPresenter {

    private String format(Item i) {
        return String.format("[%d] %s | Stok: %d | Kategori: %s",
                i.getId(), i.getName(), i.getQuantity(), i.getCategory());
    }

    private void printList(List<Item> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Item i : list) {
            System.out.println(format(i));
        }
    }

    public void showItems(List<Item> list) {
        printList(list, "Daftar Barang:", "- Data barang belum tersedia!");
    }

    public void showSearchResults(List<Item> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Barang tidak ditemukan!");
    }

    public void showSortedItems(List<Item> list) {
        printList(list, "Daftar Barang (Terurut):", "- Data barang belum tersedia!");
    }

    public void showAddSuccess(Item i) {
        System.out.printf("Berhasil menambah barang: %s%n", format(i));
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah stok barang.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah stok barang dengan ID: %d.%n", id);
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus barang.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus barang dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
