package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Nuclide {
    private DBService dbService;
    private NuclideConstants constants;

    private final SimpleIntegerProperty atomicNumber;
    private final SimpleStringProperty name;
    private NuclideModelId nuclideId;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty nameNotation;
    private final SimpleStringProperty displayNameNotation;
    private final SimpleStringProperty symbolNotation;
    private final SimpleStringProperty displaySymbolNotation;

    private Nature nature;
    private LimitsModelId limitsId;
    private final SimpleObjectProperty<LocalDate> refDate;

    private NuclideClass nuclideClass;
    private LifeSpan lifeSpan;
    private LungAbsorption lungAbsorption;

    private Conversions.SIPrefix massPrefix;
    private Conversions.MassUnit massUnit;
    private final SimpleStringProperty mass;
    private final SimpleStringProperty displayMass;

    private Conversions.SIPrefix initActivityPrefix;
    private Conversions.RadUnit initActivityUnit;
    private final SimpleStringProperty initActivity;
    private final SimpleStringProperty displayInitActivity;
    
    public Nuclide() {
        dbService = new DBServiceImpl();
        constants = new NuclideConstants();

        atomicNumber = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("XX");
        nuclideId = new NuclideModelId();
        fullName = new SimpleStringProperty(name + " (" + nuclideId.getSymbol() + ")");
        nameNotation = new SimpleStringProperty(name + "-" + nuclideId.getMassNumber());
        displayNameNotation = new SimpleStringProperty(name + "-" + nuclideId.getDisplayMassNumber());
        symbolNotation = new SimpleStringProperty(nuclideId.getSymbolNotation());
        displaySymbolNotation = new SimpleStringProperty(nuclideId.getDisplaySymbolNotation());

        nature = Nature.REGULAR;
        limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);
        refDate = new SimpleObjectProperty<>(LocalDate.now());

        nuclideClass = NuclideClass.TBD;
        lifeSpan = LifeSpan.REGULAR;
        lungAbsorption = LungAbsorption.NONE;

        massPrefix = Conversions.SIPrefix.BASE;
        massUnit = Conversions.MassUnit.GRAMS;
        mass = new SimpleStringProperty(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
        displayMass = new SimpleStringProperty(mass + " " +
            massPrefix.getAbbrVal() + massUnit.getAbbrVal());

        initActivityPrefix = Conversions.SIPrefix.BASE;
        initActivityUnit = Conversions.RadUnit.CURIE;
        initActivity = new SimpleStringProperty(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
        displayInitActivity = new SimpleStringProperty(initActivity + " " +
            initActivityPrefix.getAbbrVal() + initActivityUnit.getAbbrVal());
    }

    public Nuclide(NuclideModel model) {
        this(model.getAtomicNumber(), model.getName(), model.getNuclideId());
    }

    public Nuclide(int atomicNumber, String name, NuclideModelId nuclideId) {
        this();

        setAtomicNumber(atomicNumber);
        setName(name);
        setNuclideId(nuclideId);
    }

    public Nuclide(Conversions.SIPrefix massPrefix, Conversions.MassUnit massUnit, String massStr,
                   Conversions.SIPrefix initActivityPrefix, Conversions.RadUnit initActivityUnit, String initActivityStr) {
        this();

        setMassPrefix(massPrefix);
        setMassUnit(massUnit);
        setMassStr(massStr);

        setInitActivityPrefix(initActivityPrefix);
        setInitActivityUnit(initActivityUnit);
        setInitActivityStr(initActivityStr);
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public NuclideConstants getConstants() {
        return constants;
    }

    public void setConstants(NuclideConstants constants) {
        this.constants = constants == null? new NuclideConstants() : constants;
    }

    public Nuclide initConstants() {
        getConstants().setDbService(getDbService());
        getConstants().dbInit(getNuclideId(), getLimitsId());
        return this;
    }

    public SimpleIntegerProperty atomicNumberProperty() {
        return atomicNumber;
    }

    public int getAtomicNumber() {
        return atomicNumber.get();
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber.set(atomicNumber);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String name) {
        nameProperty().set(name == null? "" : name);
        setFullName();
        setNameNotation();
        setDisplayNameNotation();
        setSymbolNotation();
        setDisplaySymbolNotation();
    }

    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId == null ? new NuclideModelId() : nuclideId;
        setFullName();
        setNameNotation();
        setDisplayNameNotation();
        setSymbolNotation();
        setDisplaySymbolNotation();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public String getFullName() {
        return fullNameProperty().get();
    }

    public void setFullName() {
        String str = "";
        if(nameProperty() != null) {
            str += getName();
        }
        if(getNuclideId() != null) {
            str += " (" + getNuclideId().getSymbol() + ")";
        }
        fullNameProperty().set(str);
    }

    public SimpleStringProperty nameNotationProperty() {
        return nameNotation;
    }

    public String getNameNotation() {
        return nameNotationProperty().get();
    }

    public void setNameNotation() {
        String str = getName();
        if(getNuclideId() != null) {
            str += "-" + getNuclideId().getMassNumber();
        }
        nameNotationProperty().set(str);
    }

    public SimpleStringProperty displayNameNotationProperty() {
        return displayNameNotation;
    }

    public String getDisplayNameNotation() {
        return displayNameNotationProperty().get();
    }

    public void setDisplayNameNotation() {
        String str = getName();
        if(getNuclideId() != null) {
            str += "-" + getNuclideId().getDisplayMassNumber();
        }
        displayNameNotationProperty().set(str);
    }

    public SimpleStringProperty symbolNotationProperty() {
        return symbolNotation;
    }

    public String getSymbolNotation() {
        return symbolNotationProperty().get();
    }

    public void setSymbolNotation() {
        String str = "";
        if(getNuclideId() != null) {
            str = getNuclideId().getSymbolNotation();
        }
        symbolNotationProperty().set(str);
    }

    public SimpleStringProperty displaySymbolNotationProperty() {
        return displaySymbolNotation;
    }

    public String getDisplaySymbolNotation() {
        return displaySymbolNotationProperty().get();
    }

    public void setDisplaySymbolNotation() {
        String str = "";
        if(getNuclideId() != null) {
            str = getNuclideId().getDisplaySymbolNotation();
        }
        displaySymbolNotationProperty().set(str);
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId == null ? new LimitsModelId() : limitsId;
    }

    public SimpleObjectProperty<LocalDate> refDateProperty() {
        return refDate;
    }

    public LocalDate getRefDate() {
        return refDateProperty().get();
    }

    public void setRefDate(LocalDate refDate) {
        refDateProperty().set(refDate == null? LocalDate.now() : refDate);
    }

    public NuclideClass getNuclideClass() {
        return nuclideClass;
    }

    public void setNuclideClass(NuclideClass nuclideClass) {
        this.nuclideClass = nuclideClass;
    }

    public LifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(LifeSpan lifeSpan) {
        this.lifeSpan =  lifeSpan;
    }

    public LungAbsorption getLungAbsorption() {
        return lungAbsorption;
    }

    public void setLungAbsorption(LungAbsorption lungAbsorption) {
        this.lungAbsorption =  lungAbsorption;
    }

    public Conversions.SIPrefix getMassPrefix() {
        return massPrefix;
    }

    public void setMassPrefix(Conversions.SIPrefix massPrefix) {
        this.massPrefix = massPrefix == null? Conversions.SIPrefix.BASE : massPrefix;
        setDisplayMass();
    }

    public Conversions.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(Conversions.MassUnit massUnit) {
        this.massUnit = massUnit == null? Conversions.MassUnit.GRAMS : massUnit;
        setDisplayMass();
    }

    public RadBigDecimal getMass() {
        return new RadBigDecimal(getMassStr());
    }

    public SimpleStringProperty massProperty() {
        return mass;
    }

    public String getMassStr() {
        return massProperty().get();
    }

    public void setMassStr(String massStr) {
        massProperty().set(massStr == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : massStr);
        setDisplayMass();
    }

    public SimpleStringProperty displayMassProperty() {
        return displayMass;
    }

    public String getDisplayMass() {
        return displayMassProperty().get();
    }

    public void setDisplayMass() {
        String str = getMass() + " ";
        if(getMassPrefix()!= null) {
            str += getMassPrefix().getAbbrVal();
        }

        if(getMassUnit()!= null) {
            str += getMassUnit().getAbbrVal();
        }
        displayMassProperty().set(str);
    }

    public Conversions.SIPrefix getInitActivityPrefix() {
        return initActivityPrefix;
    }

    public void setInitActivityPrefix(Conversions.SIPrefix initActivityPrefix) {
        this.initActivityPrefix = initActivityPrefix == null? Conversions.SIPrefix.BASE : initActivityPrefix;
        setDisplayInitActivity();
    }

    public Conversions.RadUnit getInitActivityUnit() {
        return initActivityUnit;
    }

    public void setInitActivityUnit(Conversions.RadUnit initActivityUnit) {
        this.initActivityUnit = initActivityUnit == null? Conversions.RadUnit.CURIE : initActivityUnit;
        setDisplayInitActivity();
    }

    public RadBigDecimal getInitActivity() {
        return new RadBigDecimal(getInitActivityStr());
    }

    public SimpleStringProperty initActivityProperty() {
        return initActivity;
    }

    public String getInitActivityStr() {
        return initActivityProperty().get();
    }

    public void setInitActivityStr(String initActivityStr) {
        initActivityProperty().set(initActivityStr == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : initActivityStr);
        setDisplayInitActivity();
    }

    public SimpleStringProperty displayInitActivityProperty() {
        return displayInitActivity;
    }

    public String getDisplayInitActivity() {
        return displayInitActivityProperty().get();
    }

    public void setDisplayInitActivity() {
        String str = getInitActivity() + " ";
        if(getInitActivityPrefix()!= null) {
            str += getInitActivityPrefix().getAbbrVal();
        }

        if(getInitActivityUnit()!= null) {
            str += getInitActivityUnit().getAbbrVal();
        }
        displayInitActivityProperty().set(str);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Nuclide temp = (Nuclide) obj;
        return Objects.equals(this.getNuclideId(), temp.getNuclideId()) &&
            Objects.equals(this.getNuclideClass(), temp.getNuclideClass()) &&
            Objects.equals(this.getDisplayMass(), temp.getDisplayMass()) &&
            Objects.equals(this.getDisplayInitActivity(), temp.getDisplayInitActivity()) &&
            Objects.equals(this.getNature(), temp.getNature()) &&
            Objects.equals(this.getLimitsId(), temp.getLimitsId());
    }

    @Override
    public int hashCode() {
        int hash = 43;
        hash = 2 * hash + (this.getNuclideId() != null ? this.getNuclideId().hashCode() : 0);
        hash = 2 * hash + (this.getNuclideClass() != null ? this.getNuclideClass().hashCode() : 0);
        hash = 2 * hash + (this.getRefDate() != null ? this.getRefDate().hashCode() : 0);
        hash = 2 * hash + (this.getDisplayMass().hashCode());
        hash = 2 * hash + (this.getDisplayInitActivity().hashCode());
        hash = 2 * hash + (this.getNature() != null ? this.getNature().hashCode() : 0);
        hash = 2 * hash + (this.getLimitsId() != null ? this.getLimitsId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Nuclide: { " + getNuclideId() +
            "\nClass: " + getNuclideClass() +
            "\nRef Date: " + getRefDate() +
            "\nMass: " + getDisplayMass() +
            "\nInitial Activity: " + getDisplayInitActivity() +
            "\nNature: " + getNature() +
            "\nLifeSpan: " + getLifeSpan() +
            "\nLung Absorption: " + getLungAbsorption() +
            "\n" + getLimitsId() +
            "\n" + getConstants() + "\n}";
    }

    public enum NuclideClass {
        // Source: https://remm.hhs.gov/NRC_for-educators_11.pdf
        TBD("To Be Determined", ""),
        EXEMPT("Non-Radioactive",
            "The amount of material is less than 0.002 µ curies per gram."),
        EXCEPTED("Limited Quantity",
            "The amount is greater than 0.002 µ curies per gram but does not exceed\n" +
                "one thousandth of the A1 or A2 value (depending on the form)."),
        TYPE_A("Type A Quantity",
            "The amount is less than or equal to the A1 or A2 value (depending on the\n" +
                "form) but greater than one thousandth of the value."),
        TYPE_B("Type B Quantity",
            "The amount is greater than the A1 or A2 value (depending on the form) but\n" +
                "less than or equal to 3000 times these values"),
        TYPE_B_HIGHWAY("Type B: Highway Route Controlled Quantity",
            "The amount is greater than 3000 times the A1 or A2 value (depending on\n" +
                "the form) but less than 27,000 curies.");

        private final String val;
        private final String info;

        NuclideClass(String val, String info) {
            this.val = val;
            this.info = info;
        }

        public String getVal() {
            return val;
        }

        public String getInfo() { return info; }

        public static NuclideClass toNuclideClass(String value) {
            for (NuclideClass enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(NuclideClass.values())
                .map(NuclideClass::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    public enum Nature {
        REGULAR("Regular"),
        INSTRUMENT("Instrument"),
        ARTICLE("Article");

        private final String val;

        Nature(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static Nature toNature(String value) {
            for (Nature enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(Nature.values())
                .map(Nature::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    public enum LifeSpan {
        SHORT("Short"),
        LONG("Long"),
        REGULAR("");

        private final String val;

        LifeSpan(String val) {
            this.val = val;
        }

        public String getVal() {
            return val.isEmpty() ? "" : val + " Lived";
        }

        public String getAbbrVal() { return val.isEmpty() ? "" : val.toLowerCase();}

        public static LifeSpan toLifeSpan(String value) {
            for (LifeSpan enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(LifeSpan.values())
                .map(LifeSpan::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    public enum LungAbsorption {
        SLOW("Slow Lung Absorption"),
        MEDIUM("Medium Lung Absorption"),
        FAST("Fast Lung Absorption"),
        NONE("");

        private final String val;

        LungAbsorption(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() { return val.isEmpty() ? "" :
            val.substring(0,val.indexOf(' ')).toLowerCase();}

        public static LungAbsorption toLungAbsorption(String value) {
            for (LungAbsorption enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(LungAbsorption.values())
                .map(LungAbsorption::getAbbrVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

}
