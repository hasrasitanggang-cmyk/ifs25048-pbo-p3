package domain.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;

/**
 * Port spesifik Transaction yang memperluas generic {@link IRepository}.
 * Repository bertanggung jawab membuat ID unik saat save.
 */
public interface ITransactionRepository extends IRepository<Transaction, Integer> {
    Transaction save(String description, long amount, TransactionType type);
}
