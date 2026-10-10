package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

/** Presenter yang memformat output kontak teman. */
public class ContactPresenter {

    private String format(Contact c) {
        return String.format("[%d] %s | Telp: %s | Email: %s",
                c.getId(), c.getName(), c.getPhone(), c.getEmail());
    }

    private void printList(List<Contact> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Contact c : list) {
            System.out.println(format(c));
        }
    }

    public void showContacts(List<Contact> list) {
        printList(list, "Daftar Kontak:", "- Data kontak belum tersedia!");
    }

    public void showSearchResults(List<Contact> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Kontak tidak ditemukan!");
    }

    public void showSortedContacts(List<Contact> list) {
        printList(list, "Daftar Kontak (Terurut):", "- Data kontak belum tersedia!");
    }

    public void showAddSuccess(Contact c) {
        System.out.printf("Berhasil menambah kontak: %s%n", format(c));
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah kontak.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah kontak dengan ID: %d.%n", id);
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus kontak.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus kontak dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
