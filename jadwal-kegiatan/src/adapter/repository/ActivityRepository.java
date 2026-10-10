package adapter.repository;

import domain.entity.Activity;
import domain.entity.Day;
import domain.repository.IActivityRepository;

/** Implementasi in-memory IActivityRepository berbasis {@link InMemoryRepository}. */
public class ActivityRepository extends InMemoryRepository<Activity, Integer>
        implements IActivityRepository {
    private int idCounter = 0;

    public ActivityRepository() {
        super(Activity::getId);
    }

    @Override
    public Activity save(String title, Day day, String time) {
        return save(new Activity(++idCounter, title, day, time));
    }
}
