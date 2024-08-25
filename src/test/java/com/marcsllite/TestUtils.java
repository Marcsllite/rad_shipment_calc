package com.marcsllite;

import com.marcsllite.model.Nuclide;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.marcsllite.model.db.NuclideModelId.LIFE_SPAN_PATTERN;
import static com.marcsllite.model.db.NuclideModelId.LUNG_ABS_PATTERN;
import static junit.framework.Assert.assertTrue;

public class TestUtils {
    public static class TestNuclide {
        String name;
        String symbol;
        String massNumber;
        Nuclide.LifeSpan lifeSpan;
        Nuclide.LungAbsorption lungAbsorption;

        public TestNuclide(String name, String symbol, String massNumber, Nuclide.LifeSpan lifeSpan, Nuclide.LungAbsorption lungAbsorption) {
            this.name = name;
            this.symbol = symbol;
            this.massNumber = massNumber;
            this.lifeSpan = lifeSpan;
            this.lungAbsorption = lungAbsorption;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getSymbolNotation() {
            return symbol + "-" + massNumber
                .replaceAll(LIFE_SPAN_PATTERN, "")
                .replaceAll(LUNG_ABS_PATTERN, "")
                .trim();
        }

        public String getFullSymbolNotation() {
            return symbol + "-" + massNumber;
        }

        @Override
        public String toString() {
            return "TestNuclide{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", massNumber='" + massNumber + '\'' +
                '}';
        }
    }

    public static final List<TestNuclide> testNuclides = Arrays.asList(
        new TestNuclide("Abbreviation", "Ab", "1", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.NONE),
        new TestNuclide("Annual", "An", "1", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.NONE),
        new TestNuclide("Bofuri", "Bf", "1(short)", Nuclide.LifeSpan.SHORT, Nuclide.LungAbsorption.NONE),
        new TestNuclide("Best", "Bst", "1fast", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.FAST)
    );

    private TestUtils() {}

    public static TestNuclide getRegularNuclide() {
        return testNuclides.stream()
            .filter(nuclide ->
                Nuclide.LifeSpan.REGULAR.equals(nuclide.lifeSpan) &&
                Nuclide.LungAbsorption.NONE.equals(nuclide.lungAbsorption))
            .findFirst()
            .orElse(null);
    }

    public static TestNuclide getLifeSpanNuclide() {
        return testNuclides.stream()
            .filter(nuclide -> !Nuclide.LifeSpan.REGULAR.equals(nuclide.lifeSpan))
            .findFirst()
            .orElse(null);
    }

    public static TestNuclide getLungAbsNuclide() {
        return testNuclides.stream()
            .filter(nuclide -> !Nuclide.LungAbsorption.NONE.equals(nuclide.lungAbsorption))
            .findFirst()
            .orElse(null);
    }

    public static Nuclide createNuclide(String name, String symbol, String massNum) {
        Nuclide nuclide = new Nuclide(-1, name, new NuclideModelId(symbol, massNum));
        nuclide.setInitActivityStr(getRandomRadBigDecimal().toDisplayString());
        nuclide.setInitActivityPrefix(getRandomSIPrefix());
        nuclide.setInitActivityUnit(getRandomRadUnit());
        nuclide.setRefDate(getRandomDate());
        nuclide.setMassStr(getRandomRadBigDecimal().toDisplayString());
        nuclide.setMassPrefix(getRandomSIPrefix());
        nuclide.setMassUnit(getRandomMassUnit());
        nuclide.setNature(getRandomNature());
        nuclide.getLimitsId().setState(getRandomState());
        nuclide.getLimitsId().setForm(getRandomForm());
        nuclide.initConstants();
        return nuclide;
    }

    public static Nuclide createNuclide(TestNuclide nuclide) {
        return createNuclide(nuclide.name, nuclide.symbol, nuclide.massNumber);
    }

    public static Nuclide createNuclide() {
        return createNuclide(getRandomTestNuclide());
    }

    public static double getRandomNumber(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public static TestNuclide getRandomTestNuclide() {
        return testNuclides.get((int) getRandomNumber(0, testNuclides.size()));
    }

    public static RadBigDecimal getRandomRadBigDecimal() {
        return RadBigDecimal.valueOf(getRandomNumber(0, 100));
    }

    public static Conversions.SIPrefix getRandomSIPrefix() {
        Optional<Conversions.SIPrefix> any = Arrays.stream(Conversions.SIPrefix.values()).findAny();
        assertTrue(any.isPresent());
        return any.get();
    }

    public static Conversions.RadUnit getRandomRadUnit() {
        Optional<Conversions.RadUnit> any = Arrays.stream(Conversions.RadUnit.values()).findAny();
        assertTrue(any.isPresent());
        return any.get();
    }

    public static LocalDate getRandomDate() {
        int hundredYears = 100 * 365;
        return LocalDate.ofEpochDay(ThreadLocalRandom
            .current().nextInt(-hundredYears, hundredYears));
    }

    public static Conversions.MassUnit getRandomMassUnit() {
        Optional<Conversions.MassUnit> any = Arrays.stream(Conversions.MassUnit.values()).findAny();
        assertTrue(any.isPresent());
        return any.get();
    }

    public static Nuclide.Nature getRandomNature() {
        Optional<Nuclide.Nature> any = Arrays.stream(Nuclide.Nature.values()).findAny();
        assertTrue(any.isPresent());
        return any.get();
    }

    public static LimitsModelId.State getRandomState() {
        Optional<LimitsModelId.State> any = Arrays.stream(LimitsModelId.State.values()).findAny();
        assertTrue(any.isPresent());
        return any.get();
    }

    public static LimitsModelId.Form getRandomForm() {
        Optional<LimitsModelId.Form> any = Arrays.stream(LimitsModelId.Form.values()).findAny();
        assertTrue(any.isPresent());
        return any.get();
    }
}
