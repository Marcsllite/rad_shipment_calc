package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.NuclideUtils;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Nuclide {
    public static final String DEFAULT_NAME = "XX";

    private DBService dbService;
    private NuclideConstants constants;

    private final SimpleIntegerProperty atomicNumber;
    private final SimpleStringProperty name;
    private NuclideModelId nuclideId;
    private final SimpleStringProperty massNumber;
    private final SimpleStringProperty nameSymbol;
    private final SimpleStringProperty fullNameNotation;
    private final SimpleStringProperty displayNameNotation;
    private final SimpleStringProperty fullSymbolNotation;
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
        this((Nuclide) null);
    }

    public Nuclide(Nuclide nuclide) {
        dbService = new DBServiceImpl();
        constants = new NuclideConstants();
        nuclideId = new NuclideModelId();
        limitsId = new LimitsModelId();

        if(nuclide == null) {
            atomicNumber = new SimpleIntegerProperty(0);
            name = new SimpleStringProperty(DEFAULT_NAME);
            nature = Nature.REGULAR;
            refDate = new SimpleObjectProperty<>(LocalDate.now());
            nuclideClass = NuclideClass.TBD;
            lifeSpan = LifeSpan.REGULAR;
            lungAbsorption = LungAbsorption.NONE;
            massPrefix = Conversions.SIPrefix.BASE;
            massUnit = Conversions.MassUnit.GRAMS;
            mass = new SimpleStringProperty(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
            initActivityPrefix = Conversions.SIPrefix.BASE;
            initActivityUnit = Conversions.RadUnit.CURIE;
            initActivity = new SimpleStringProperty(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
        } else {
            atomicNumber = new SimpleIntegerProperty(nuclide.getAtomicNumber());
            name = new SimpleStringProperty(nuclide.getName());
            if (nuclide.getNuclideId() != null) {
                nuclideId.setSymbol(nuclide.getNuclideId().getSymbol());
                nuclideId.setMassNumber(nuclide.getNuclideId().getMassNumber());
            }
            nature = nuclide.getNature();
            if (nuclide.getLimitsId() != null) {
                limitsId.setState(nuclide.getLimitsId().getState());
                limitsId.setForm(nuclide.getLimitsId().getForm());
            }
            refDate = new SimpleObjectProperty<>(nuclide.getRefDate());
            nuclideClass = nuclide.getNuclideClass();
            lifeSpan = nuclide.getLifeSpan();
            lungAbsorption = nuclide.getLungAbsorption();
            massPrefix = nuclide.getMassPrefix();
            massUnit = nuclide.getMassUnit();
            mass = new SimpleStringProperty(nuclide.getMassStr());
            initActivityPrefix = nuclide.getInitActivityPrefix();
            initActivityUnit = nuclide.getInitActivityUnit();
            initActivity = new SimpleStringProperty(nuclide.getInitActivityStr());
        }

        massNumber = new SimpleStringProperty(nuclideId.getMassNumber());
        nameSymbol = new SimpleStringProperty(name.getValue() + " (" + nuclideId.getSymbol() + ")");
        fullNameNotation = new SimpleStringProperty(name.getValue() + "-" + massNumber.getValue());
        displayNameNotation = new SimpleStringProperty(name.getValue() + "-" + nuclideId.getDisplayMassNumber());
        fullSymbolNotation = new SimpleStringProperty(nuclideId.getFullSymbolNotation());
        displaySymbolNotation = new SimpleStringProperty(nuclideId.getDisplaySymbolNotation());
        displayMass = new SimpleStringProperty(mass.getValue());
        displayInitActivity = new SimpleStringProperty(initActivity.getValue());
    }

    public Nuclide(NuclideModel model) {
        this(model.getAtomicNumber(), model.getName(), model.getNuclideId());
    }

    public Nuclide(int atomicNumber, String name, NuclideModelId nuclideId) {
        this();

        setAtomicNumber(atomicNumber);
        setName(name);
        setNuclideId(nuclideId);
        setLifeSpan(NuclideUtils.parseLifeSpanFromMassNumber(getNuclideId().getMassNumber()));
        setLungAbsorption(NuclideUtils.parseLungAbsFromMassNumber(getNuclideId().getMassNumber()));
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
        nameProperty().set(StringUtils.isBlank(name)? "" : name);
        setNameSymbol();
        setFullNameNotation();
        setDisplayNameNotation();
        setFullSymbolNotation();
        setDisplaySymbolNotation();
    }

    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId == null ? new NuclideModelId() : nuclideId;
        setMassNumber();
        setNameSymbol();
        setFullNameNotation();
        setDisplayNameNotation();
        setFullSymbolNotation();
        setDisplaySymbolNotation();
    }

    public SimpleStringProperty massNumberProperty() {
        return massNumber;
    }

    public String getMassNumber() {
        return massNumber.get();
    }

    public void setMassNumber() {
        if(nuclideId != null) {
            massNumber.set(nuclideId.getMassNumber());
        } else {
            massNumber.set("N/A");
        }
    }

    public SimpleStringProperty nameSymbolProperty() {
        return nameSymbol;
    }

    public String getNameSymbol() {
        return nameSymbolProperty().get();
    }

    public void setNameSymbol() {
        String str = "";
        if(nameProperty() != null) {
            str += getName();
        }
        if(getNuclideId() != null) {
            str += " (" + getNuclideId().getSymbol() + ")";
        }
        nameSymbolProperty().set(str);
    }

    public SimpleStringProperty fullNameNotationProperty() {
        return fullNameNotation;
    }

    public String getFullNameNotation() {
        return fullNameNotationProperty().get();
    }

    public void setFullNameNotation() {
        String str = getName();
        if(getNuclideId() != null) {
            str += "-" + getNuclideId().getMassNumber();
        }
        fullNameNotationProperty().set(str);
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

    public SimpleStringProperty fullSymbolNotationProperty() {
        return fullSymbolNotation;
    }

    public String getFullSymbolNotation() {
        return fullSymbolNotationProperty().get();
    }

    public void setFullSymbolNotation() {
        String str = "";
        if(getNuclideId() != null) {
            str = getNuclideId().getFullSymbolNotation();
        }
        fullSymbolNotationProperty().set(str);
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
        massProperty().set(StringUtils.isBlank(massStr)?
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
        String str = getMass().toDisplayString();
        if(!str.equals(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING)) {
            str += " ";
            if(getMassPrefix()!= null) {
                str += getMassPrefix().getAbbrVal();
            }

            if(getMassUnit()!= null) {
                str += getMassUnit().getAbbrVal();
            }
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
        initActivityProperty().set(StringUtils.isBlank(initActivityStr)?
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
        String str = getInitActivity().toDisplayString();
        if(!str.equals(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING)) {
            str += " ";
            if(getInitActivityPrefix()!= null) {
                str += getInitActivityPrefix().getAbbrVal();
            }

            if(getInitActivityUnit()!= null) {
                str += getInitActivityUnit().getAbbrVal();
            }
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
            Objects.equals(this.lifeSpan, temp.getLifeSpan()) &&
            Objects.equals(this.lungAbsorption, temp.getLungAbsorption()) &&
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
        return "Nuclide: {\n" + getNuclideId() +
            "\n Class: " + getNuclideClass() +
            "\n Ref Date: " + getRefDate() +
            "\n Mass: " + getDisplayMass() +
            "\n Initial Activity: " + getDisplayInitActivity() +
            "\n Nature: " + getNature() +
            "\n LifeSpan: " + getLifeSpan() +
            "\n Lung Absorption: " + getLungAbsorption() +
            "\n " + getLimitsId() +
            "\n " + getConstants() + "\n}";
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
        REGULAR("N/A");

        private final String val;

        LifeSpan(String val) {
            this.val = val;
        }

        public String getVal() {
            return val + ("N/A".equals(val) ? "" : " Lived");
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
        NONE("N/A");

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
