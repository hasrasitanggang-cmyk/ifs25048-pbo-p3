package usecase;

import domain.entity.Activity;
import domain.entity.Day;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;
import java.util.List;

/** Use case jadwal kegiatan. Tanpa I/O, melempar checked domain exception jika gagal. */
public class ActivityUseCase {
    private static final String TIME_PATTERN = "([01]\\d|2[0-3]):[0-5]\\d";

    private final IActivityRepository repository;

    public ActivityUseCase(IActivityRepository repository) {
        this.repository = repository;
    }

    public List<Activity> getAllActivities() {
        return repository.findAll();
    }

    public Activity addActivity(String title, String day, String time) throws ValidationException {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Judul kegiatan tidak boleh kosong!");
        }
        Day parsedDay = Day.parse(day);
        validateTime(time);
        return repository.save(title.trim(), parsedDay, time.trim());
    }

    /**
     * Mengubah kegiatan secara parsial.
     * Parameter {@code null} berarti field tersebut tidak diubah.
     */
    public void updateActivity(int id, String title, String day, String time)
            throws EntityNotFoundException, ValidationException {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kegiatan", id));

        // Validasi semua field dulu agar tidak ada perubahan setengah jalan
        if (title != null && title.trim().isEmpty()) {
            throw new ValidationException("Judul kegiatan tidak boleh kosong!");
        }
        Day newDay = day == null ? null : Day.parse(day);
        if (time != null) {
            validateTime(time);
        }

        if (title != null) {
            activity.changeTitle(title.trim());
        }
        if (newDay != null) {
            activity.changeDay(newDay);
        }
        if (time != null) {
            activity.changeTime(time.trim());
        }
        repository.update(activity);
    }

    public void removeActivity(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Kegiatan", id);
        }
    }

    /** Mencari kegiatan berdasarkan judul (case-insensitive). */
    public List<Activity> searchActivities(String keyword) {
        String lower = keyword.toLowerCase();
        return repository.findBy(a -> a.getTitle().toLowerCase().contains(lower));
    }

    public List<Activity> sortActivities(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private void validateTime(String time) throws ValidationException {
        if (time == null || !time.trim().matches(TIME_PATTERN)) {
            throw new ValidationException("Waktu tidak valid (gunakan format HH:mm)!");
        }
    }
}
