package domain.repository;

import domain.entity.Item;

/** Port spesifik Item yang memperluas generic {@link IRepository}. */
public interface IItemRepository extends IRepository<Item, Integer> {
    /** Menyimpan barang baru; ID dibuat oleh repository. */
    Item save(String name, int quantity, String category);
}
