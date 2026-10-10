package domain.repository;

import domain.entity.Contact;

/** Port spesifik Contact yang memperluas generic {@link IRepository}. */
public interface IContactRepository extends IRepository<Contact, Integer> {
    /** Menyimpan kontak baru; ID dibuat oleh repository. */
    Contact save(String name, String phone, String email);
}
