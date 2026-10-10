package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;
import java.util.List;

/**
 * Use case logika bisnis catatan keuangan.
 * Tanpa I/O, melempar checked domain exception jika gagal.
 */
public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    /** Menambah transaksi setelah validasi keterangan dan jumlah (> 0). */
    public Transaction addTransaction(String description, long amount, TransactionType type)
            throws ValidationException {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("Keterangan tidak boleh kosong!");
        }
        if (amount <= 0) {
            throw new ValidationException("Jumlah tidak valid!");
        }
        return repository.save(description.trim(), amount, type);
    }

    public void removeTransaction(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Transaksi", id);
        }
    }

    /** Mencari transaksi berdasarkan keterangan (case-insensitive). */
    public List<Transaction> searchTransactions(String keyword) {
        String lower = keyword.toLowerCase();
        return repository.findBy(t -> t.getDescription().toLowerCase().contains(lower));
    }

    public List<Transaction> sortTransactions(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    /** Saldo = total pemasukan - total pengeluaran. */
    public long getBalance() {
        return repository.findAll().stream()
                .mapToLong(t -> t.getType() == TransactionType.INCOME ? t.getAmount() : -t.getAmount())
                .sum();
    }
}
