package domain.repository;

import domain.entity.Activity;
import domain.entity.Day;

/** Port spesifik Activity yang memperluas generic {@link IRepository}. */
public interface IActivityRepository extends IRepository<Activity, Integer> {
    /** Menyimpan kegiatan baru; ID dibuat oleh repository. */
    Activity save(String title, Day day, String time);
}