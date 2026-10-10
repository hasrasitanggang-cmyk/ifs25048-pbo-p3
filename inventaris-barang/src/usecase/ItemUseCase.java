package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IItemRepository;
import java.util.List;

/** Use case inventaris barang. Tanpa I/O, melempar checked domain exception jika gagal. */
public class ItemUseCase {
    private final IItemRepository repository;

    public ItemUseCase(IItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public Item addItem(String name, int quantity, String category) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama barang tidak boleh kosong!");
        }
        validateQuantity(quantity);
        if (category == null || category.trim().isEmpty()) {
            throw new ValidationException("Kategori tidak boleh kosong!");
        }
        return repository.save(name.trim(), quantity, category.trim());
    }

    /** Mengubah stok barang; stok harus lebih dari 0. */
    public void updateStock(int id, int quantity) throws EntityNotFoundException, ValidationException {
        Item item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Barang", id));
        validateQuantity(quantity);
        item.changeQuantity(quantity);
        repository.update(item);
    }

    public void removeItem(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Barang", id);
        }
    }

    /** Mencari barang berdasarkan nama (case-insensitive). */
    public List<Item> searchItems(String keyword) {
        String lower = keyword.toLowerCase();
        return repository.findBy(i -> i.getName().toLowerCase().contains(lower));
    }

    public List<Item> sortItems(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private void validateQuantity(int quantity) throws ValidationException {
        if (quantity <= 0) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
    }
}
