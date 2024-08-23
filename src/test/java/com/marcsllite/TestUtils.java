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

import static junit.framework.Assert.assertTrue;

public class TestUtils {
    public static class TestNuclide {
        String name;
        String symbol;
        String massNumber;

        public TestNuclide(String name, String symbol, String massNumber) {
            this.name = name;
            this.symbol = symbol;
            this.massNumber = massNumber;
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
        new TestNuclide("Abbreviation", "Ab", "1"),
        new TestNuclide("Annual", "An", "1"),
        new TestNuclide("Bofuri", "Bf", "1(short)"),
        new TestNuclide("Best", "Bst", "1fast")
    );

    private TestUtils() {}
    
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

    public static TestNuclide getRandomTestNuclide() {
        int min = 0;
        int max = testNuclides.size();
        int randNum = (int) ((Math.random() * (max - min)) + min);
        return testNuclides.get(randNum);
    }

    public static RadBigDecimal getRandomRadBigDecimal() {
        double min = 0;
        double max = 100;
        double randNum = ((Math.random() * (max - min)) + min);
        return RadBigDecimal.valueOf(randNum);
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
