package adapter.presenter;

import domain.entity.Guest;
import java.util.List;

/** Presenter yang memformat output buku tamu. */
public class GuestPresenter {

    private String format(Guest g) {
        return String.format("[%d] %s | Tujuan: %s", g.getId(), g.getName(), g.getPurpose());
    }

    private void printList(List<Guest> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Guest g : list) {
            System.out.println(format(g));
        }
    }

    public void showGuests(List<Guest> list) {
        printList(list, "Daftar Tamu:", "- Data tamu belum tersedia!");
    }

    public void showSearchResults(List<Guest> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Tamu tidak ditemukan!");
    }

    public void showRegisterSuccess(Guest g) {
        System.out.printf("Berhasil mendaftarkan tamu: %s%n", format(g));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus tamu.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus tamu dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
