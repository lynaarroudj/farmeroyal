package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM");

    public static LocalDate parseWithDefaultYear(String date) {
        try {
            // Extraire jour et mois avec le formatteur "dd/MM"
            String[] parts = date.split("/");
            if (parts.length != 2) {
                throw new DateTimeParseException("Invalid date format", date, 0);
            }

            int dayOfMonth = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = LocalDate.now().getYear(); // Année actuelle

            // Créer une date complète avec jour, mois et année actuelle
            return LocalDate.of(year, month, dayOfMonth);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: dd/MM", e);
        }
    }
}

