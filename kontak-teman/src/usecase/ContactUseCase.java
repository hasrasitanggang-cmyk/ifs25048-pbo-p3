package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;
import java.util.List;

/** Use case kontak teman. Tanpa I/O, melempar checked domain exception jika gagal. */
public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Contact addContact(String name, String phone, String email) throws ValidationException {
        validateName(name);
        return repository.save(name.trim(), phone == null ? "" : phone.trim(), email == null ? "" : email.trim());
    }

    /**
     * Mengubah kontak secara parsial.
     * Parameter {@code null} berarti field tersebut tidak diubah.
     */
    public void updateContact(int id, String name, String phone, String email)
            throws EntityNotFoundException, ValidationException {
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kontak", id));

        if (name != null) {
            validateName(name);
        }

        if (name != null) {
            contact.changeName(name.trim());
        }
        if (phone != null) {
            contact.changePhone(phone.trim());
        }
        if (email != null) {
            contact.changeEmail(email.trim());
        }
        repository.update(contact);
    }

    public void removeContact(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Kontak", id);
        }
    }

    /** Mencari kontak berdasarkan nama (case-insensitive). */
    public List<Contact> searchContacts(String keyword) {
        String lower = keyword.toLowerCase();
        return repository.findBy(c -> c.getName().toLowerCase().contains(lower));
    }

    public List<Contact> sortContacts(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama kontak tidak boleh kosong!");
        }
    }
}