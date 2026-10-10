package usecase;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IGuestRepository;
import java.util.List;

/** Use case buku tamu. Tanpa I/O, melempar checked domain exception jika gagal. */
public class GuestUseCase {
    private final IGuestRepository repository;

    public GuestUseCase(IGuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> getAllGuests() {
        return repository.findAll();
    }

    public Guest registerGuest(String name, String purpose) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama tamu tidak boleh kosong!");
        }
        if (purpose == null || purpose.trim().isEmpty()) {
            throw new ValidationException("Tujuan kunjungan tidak boleh kosong!");
        }
        return repository.save(name.trim(), purpose.trim());
    }

    /** Mencari tamu berdasarkan nama (case-insensitive). */
    public List<Guest> searchGuests(String keyword) {
        String lower = keyword.toLowerCase();
        return repository.findBy(g -> g.getName().toLowerCase().contains(lower));
    }

    public void removeGuest(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Tamu", id);
        }
    }
}
