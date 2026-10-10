package domain.entity;

import domain.exception.ValidationException;

/** Hari dalam seminggu. Urutan enum dipakai untuk pengurutan kegiatan berdasarkan hari. */
public enum Day {
    SENIN("Senin"),
    SELASA("Selasa"),
    RABU("Rabu"),
    KAMIS("Kamis"),
    JUMAT("Jumat"),
    SABTU("Sabtu"),
    MINGGU("Minggu");

    private final String label;

    Day(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /** Mengonversi teks (case-insensitive) menjadi Day. */
    public static Day parse(String value) throws ValidationException {
        for (Day day : values()) {
            if (day.label.equalsIgnoreCase(value.trim())) {
                return day;
            }
        }
        throw new ValidationException("Hari tidak valid!");
    }
}
