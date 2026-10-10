package domain.entity;

/** Entity tamu pada buku tamu digital. */
public class Guest {
    /** ID unik tamu, tidak boleh diubah setelah dibuat. */
    private final int id;

    private final String name;
    private final String purpose;

    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }
}
