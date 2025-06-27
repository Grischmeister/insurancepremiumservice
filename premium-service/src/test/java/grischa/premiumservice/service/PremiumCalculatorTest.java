package grischa.premiumservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PremiumCalculatorTest {

    private PremiumCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new PremiumCalculator();
    }

    @Test
    void testGetKilometerFactor() {
        assertEquals(0.5, calculator.getKilometerFactor(4000));
        assertEquals(1.0, calculator.getKilometerFactor(8000));
        assertEquals(1.5, calculator.getKilometerFactor(15000));
        assertEquals(2.0, calculator.getKilometerFactor(30000));
    }

    @Test
    void testGetCarTypeFactor() {
        assertEquals(0.8, calculator.getCarTypeFactor("KLEINWAGEN"));
        assertEquals(1.0, calculator.getCarTypeFactor("MITTELKLASSE"));
        assertEquals(1.2, calculator.getCarTypeFactor("SUV"));
        assertEquals(1.5, calculator.getCarTypeFactor("SPORTWAGEN"));

        // lower-case input
        assertEquals(0.8, calculator.getCarTypeFactor("kleinwagen"));

        // unknown car type fallback
        assertEquals(1.0, calculator.getCarTypeFactor("LUXUSKLASSE"));
    }

    @Test
    void testGetRegionFactor() {
        assertEquals(1.0, calculator.getRegionFactor("BAYERN"));
        assertEquals(1.3, calculator.getRegionFactor("BERLIN"));
        assertEquals(1.0, calculator.getRegionFactor("rheinland-pfalz")); // lower-case
        assertEquals(1.0, calculator.getRegionFactor("UNKNOWN"));
        assertEquals(1.0, calculator.getRegionFactor("IRGENDEINLAND")); // fallback
    }

    @Test
    void testCalculate() {
        // KLEINWAGEN, 4000 km, BERLIN = 0.5 * 0.8 * 1.3
        double expected1 = 0.5 * 0.8 * 1.3;
        assertEquals(expected1, calculator.calculate(4000, "KLEINWAGEN", "BERLIN"), 0.0001);

        // SUV, 15000 km, NRW = 1.5 * 1.2 * 1.3
        double expected2 = 1.5 * 1.2 * 1.3;
        assertEquals(expected2, calculator.calculate(15000, "SUV", "NORDRHEIN-WESTFALEN"), 0.0001);

        // unknown inputs should default
        double expected3 = 1.0 * 1.0 * 1.0;
        assertEquals(expected3, calculator.calculate(8000, "UNBEKANNT", "IRGENDWO"), 0.0001);
    }
}
