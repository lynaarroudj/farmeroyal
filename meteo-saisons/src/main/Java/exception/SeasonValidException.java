package exception;

import java.util.Set;

public class SeasonValidException extends RuntimeException {

    private static final Set<String> VALID_SEASONS = Set.of("hiver", "automne", "printemps", "ete");

    public SeasonValidException(String seasonName) {
        super("Invalid season name: " + seasonName + ". Valid options are: hiver, automne, printemps, ete.");
    }

    public static void validateSeasonName(String seasonName) {
        if (!VALID_SEASONS.contains(seasonName.toLowerCase())) {
            throw new SeasonValidException(seasonName);
        }
    }
}
