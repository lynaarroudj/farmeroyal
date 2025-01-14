package Services;

import exception.TypeProductionInvalidException;
import java.util.Map;

public class Productiontemps{
    private static final Map<String, Integer> PRODUCTION_MAP = Map.of(
            "blé", 60,
            "salade", 120,
            "carotte", 90,
            "pomme de terre", 180
    );

    public static int getTempsProduction(String typeProduction) throws TypeProductionInvalidException {
        if (!PRODUCTION_MAP.containsKey(typeProduction.toLowerCase())) {
            throw new TypeProductionInvalidException("Type de production invalide : " + typeProduction);
        }
        return PRODUCTION_MAP.get(typeProduction.toLowerCase());
    }
}
