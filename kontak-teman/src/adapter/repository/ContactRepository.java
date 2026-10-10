package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;

/** Implementasi in-memory IContactRepository berbasis {@link InMemoryRepository}. */
public class ContactRepository extends InMemoryRepository<Contact, Integer>
        implements IContactRepository {
    private int idCounter = 0;

    public ContactRepository() {
        super(Contact::getId);
    }

    @Override
    public Contact save(String name, String phone, String email) {
        return save(new Contact(++idCounter, name, phone, email));
    }
}
