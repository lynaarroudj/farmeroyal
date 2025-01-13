package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM");

    public static LocalDate parseWithDefaultYear(String date) {
        try {
            // Validation du format "dd/MM"
            String[] parts = date.split("/");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Le format de la date est invalide. Format attendu : dd/MM");
            }

            // Extraction des valeurs
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = LocalDate.now().getYear(); // Utilise l'année actuelle

            // Construction de la date
            return LocalDate.of(year, month, day);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Erreur de parsing de la date. Format attendu : dd/MM", e);
        }
    }
}


