package grischa.premiumservice.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PremiumCalculator {

    // Kilometer-Faktoren
    public double getKilometerFactor(int km) {
        if (km <= 5000) return 0.5;
        else if (km <= 10000) return 1.0;
        else if (km <= 20000) return 1.5;
        else return 2.0;
    }

    // Fahrzeugtyp-Faktoren – Beispielwerte
    private final Map<String, Double> carTypeFactors = Map.of(
            "KLEINWAGEN", 0.8,
            "MITTELKLASSE", 1.0,
            "SUV", 1.2,
            "SPORTWAGEN", 1.5
    );

    public double getCarTypeFactor(String carType) {
        return carTypeFactors.getOrDefault(carType.toUpperCase(), 1.0);
    }

    private final Map<String, Double> regionFactors = Map.ofEntries(
            Map.entry("BADEN-WÜRTTEMBERG", 1.1),
            Map.entry("BAYERN", 1.0),
            Map.entry("BERLIN", 1.3),
            Map.entry("BRANDENBURG", 1.2),
            Map.entry("BREMEN", 1.4),
            Map.entry("HAMBURG", 1.4),
            Map.entry("HESSEN", 1.1),
            Map.entry("MECKLENBURG-VORPOMMERN", 1.0),
            Map.entry("NIEDERSACHSEN", 1.1),
            Map.entry("NORDRHEIN-WESTFALEN", 1.3),
            Map.entry("RHEINLAND-PFALZ", 1.0),
            Map.entry("SAARLAND", 1.0),
            Map.entry("SACHSEN", 1.0),
            Map.entry("SACHSEN-ANHALT", 1.1),
            Map.entry("SCHLESWIG-HOLSTEIN", 1.0),
            Map.entry("THÜRINGEN", 1.0),
            Map.entry("UNKNOWN", 1.0) // Default
    );

    public double getRegionFactor(String bundesland) {
        return regionFactors.getOrDefault(bundesland.toUpperCase(), 1.0);
    }

    public double calculate(int kilometers, String carType, String bundesland) {
        return getKilometerFactor(kilometers)
                * getCarTypeFactor(carType)
                * getRegionFactor(bundesland);
    }
}
