package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan transaksi.
 * Setiap opsi membawa comparator-nya sendiri.
 */
public enum SortOption {
    /** Jumlah terbesar ke terkecil. */
    LARGEST_AMOUNT(Comparator.comparingLong(Transaction::getAmount).reversed()),

    /** Jumlah terkecil ke terbesar. */
    SMALLEST_AMOUNT(Comparator.comparingLong(Transaction::getAmount)),

    /** Pemasukan ditampilkan lebih dulu (urutan enum INCOME < EXPENSE). */
    INCOME_FIRST(Comparator.comparing(Transaction::getType)),

    /** Pengeluaran ditampilkan lebih dulu. */
    EXPENSE_FIRST(Comparator.comparing(Transaction::getType).reversed());

    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transaction> comparator() {
        return comparator;
    }
}
