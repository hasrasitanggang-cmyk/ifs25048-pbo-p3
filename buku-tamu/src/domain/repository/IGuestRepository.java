package domain.repository;

import domain.entity.Guest;

/** Port spesifik Guest yang memperluas generic {@link IRepository}. */
public interface IGuestRepository extends IRepository<Guest, Integer> {
    /** Menyimpan tamu baru; ID dibuat oleh repository. */
    Guest save(String name, String purpose);
}
