package adapter.presenter;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import java.util.List;

/** Presenter yang memformat output catatan keuangan. */
public class FinancePresenter {

    private String format(Transaction t) {
        String type = t.getType() == TransactionType.INCOME ? "Pemasukan" : "Pengeluaran";
        return String.format("[%d] %s | Rp %d | [%s]", t.getId(), t.getDescription(), t.getAmount(), type);
    }

    private void printList(List<Transaction> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Transaction t : list) {
            System.out.println(format(t));
        }
    }

    /** Daftar transaksi beserta saldo. */
    public void showTransactions(List<Transaction> list, long balance) {
        printList(list, "Daftar Transaksi:", "- Belum ada transaksi!");
        System.out.printf("Saldo: Rp %d%n", balance);
    }

    public void showSearchResults(List<Transaction> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Transaksi tidak ditemukan!");
    }

    /** Daftar terurut (tanpa saldo). */
    public void showSortedTransactions(List<Transaction> list) {
        printList(list, "Daftar Transaksi (Terurut):", "- Belum ada transaksi!");
    }

    public void showBalance(long balance) {
        System.out.printf("Saldo saat ini: Rp %d%n", balance);
    }

    public void showAddSuccess(Transaction t) {
        System.out.printf("Berhasil menambah transaksi: %s%n", format(t));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus transaksi.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus transaksi dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    /** Pesan error validasi (Jumlah / ID / opsi urut tidak valid, dll). */
    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}