package com.marcsllite;

import com.marcsllite.model.Nuclide;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import javafx.scene.control.TableView;
import org.codehaus.plexus.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.marcsllite.util.NuclideUtils.LIFE_SPAN_PATTERN;
import static com.marcsllite.util.NuclideUtils.LUNG_ABS_PATTERN;
import static junit.framework.Assert.assertTrue;

public class TestUtils {
    public static class TestNuclide {
        int atomicNumber;
        String name;
        String symbol;
        String massNumber;
        Nuclide.LifeSpan lifeSpan;
        Nuclide.LungAbsorption lungAbsorption;

        public TestNuclide(int atomicNumber, String name, String symbol, String massNumber, Nuclide.LifeSpan lifeSpan, Nuclide.LungAbsorption lungAbsorption) {
            this.atomicNumber = atomicNumber;
            this.name = name;
            this.symbol = symbol;
            this.massNumber = massNumber;
            this.lifeSpan = lifeSpan;
            this.lungAbsorption = lungAbsorption;
        }

        public int getAtomicNumber() {
            return atomicNumber;
        }

        public String getName() {
            return StringUtils.isNotBlank(name) ? name : name.trim();
        }

        public String getSymbol() {
            return StringUtils.isNotBlank(symbol) ? symbol : symbol.trim();
        }

        public String getMassNumber() {
            return StringUtils.isBlank(massNumber)? massNumber : massNumber.trim();
        }

        public String getDisplayMassNumber() {
            return getMassNumber()
                .replaceAll(LIFE_SPAN_PATTERN, "")
                .replaceAll(LUNG_ABS_PATTERN, "");
        }

        public Nuclide.LifeSpan getLifeSpan() {
            return lifeSpan;
        }

        public Nuclide.LungAbsorption getLungAbsorption() {
            return lungAbsorption;
        }

        public String getDisplayNameNotation() {
            return getName() + "-" + getDisplayMassNumber();
        }

        public String getFullNameNotation() {
            return getName() + "-" + getMassNumber();
        }

        public String getDisplaySymbolNotation() {
            return getSymbol() + "-" + getDisplayMassNumber();
        }

        public String getFullSymbolNotation() {
            return getSymbol() + "-" + getMassNumber();
        }

        @Override
        public String toString() {
            return "TestNuclide{" +
                "atomicNumber='" + getAtomicNumber() + '\'' +
                ", name='" + getName() + '\'' +
                ", symbol='" + getSymbol() + '\'' +
                ", massNumber='" + getMassNumber() + '\'' +
                ", lifeSpan='" + getLifeSpan() + '\'' +
                ", lungAbsorption='" + getLungAbsorption() + '\'' +
                '}';
        }
    }

    public static final List<TestNuclide> testNuclides = Arrays.asList(
        new TestNuclide(1, "Abbreviation", "Ab", "1", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.NONE),
        new TestNuclide(2, "Annual", "An", "1", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.NONE),
        new TestNuclide(3, "Bofuri", "Bf", "1", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.NONE),
        new TestNuclide(3, "Bofuri", "Bf", "1(short)", Nuclide.LifeSpan.SHORT, Nuclide.LungAbsorption.NONE),
        new TestNuclide(3, "Bofuri", "Bf", "1(long)", Nuclide.LifeSpan.LONG, Nuclide.LungAbsorption.NONE),
        new TestNuclide(4, "Best", "Bs", "1", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.NONE),
        new TestNuclide(4, "Best", "Bs", "1slow", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.SLOW),
        new TestNuclide(4, "Best", "Bs", "1medium", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.MEDIUM),
        new TestNuclide(4, "Best", "Bs", "1fast", Nuclide.LifeSpan.REGULAR, Nuclide.LungAbsorption.FAST)
    );

    private TestUtils() {}

    public static int getTestNuclideSize() {
        return testNuclides.size();
    }

    public static int subStringTestNuclide(String str) {
        return (int) testNuclides.stream()
            .filter(nuclide ->
                nuclide.getDisplayNameNotation().toLowerCase().contains(str.toLowerCase()) ||
                nuclide.getDisplaySymbolNotation().toLowerCase().contains(str.toLowerCase()))
            .count();
    }

    public static int equalsTestNuclide(String str) {
        String searchStr = str.trim()
            .replaceAll(LIFE_SPAN_PATTERN, "")
            .replaceAll(LUNG_ABS_PATTERN, "");
        return (int) testNuclides.stream()
            .filter(nuclide ->
                nuclide.getDisplayNameNotation().equalsIgnoreCase(searchStr) ||
                    nuclide.getDisplaySymbolNotation().equalsIgnoreCase(searchStr))
            .count();
    }

    public static TestNuclide getRegularNuclide() {
        return testNuclides.stream()
            .filter(nuclide -> Nuclide.LifeSpan.REGULAR.equals(nuclide.lifeSpan) &&
                Nuclide.LungAbsorption.NONE.equals(nuclide.lungAbsorption))
            .findAny()
            .orElse(null);
    }

    public static TestNuclide getLifeSpanNuclide() {
        return testNuclides.stream()
            .filter(nuclide -> !Nuclide.LifeSpan.REGULAR.equals(nuclide.lifeSpan))
            .findAny()
            .orElse(null);
    }

    public static TestNuclide getLungAbsNuclide() {
        return testNuclides.stream()
            .filter(nuclide -> !Nuclide.LungAbsorption.NONE.equals(nuclide.lungAbsorption))
            .findAny()
            .orElse(null);
    }

    public static Nuclide createNuclide(int atomicNumber, String name, String symbol, String massNum) {
        Nuclide nuclide = new Nuclide(atomicNumber, name, new NuclideModelId(symbol, massNum));
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
        System.out.println("Created nuclide: " + nuclide);
        System.out.flush();
        return nuclide;
    }

    public static Nuclide createNuclide(TestNuclide nuclide) {
        return createNuclide(nuclide.getAtomicNumber(), nuclide.getName(), nuclide.getSymbol(), nuclide.getMassNumber());
    }

    public static Nuclide createNuclide() {
        return createNuclide(getRandomTestNuclide());
    }

    public static double getRandomNumber(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public static int getRandomRow(TableView<?> tableView) {
        return (int) TestUtils.getRandomNumber(0, tableView.getItems().size()-1);
    }

    public static TestNuclide getRandomTestNuclide() {
        return testNuclides.stream().findAny().orElse(null);
    }

    public static RadBigDecimal getRandomRadBigDecimal() {
        return RadBigDecimal.valueOf(getRandomNumber(50, 100));
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
