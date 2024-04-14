package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Nuclide {
    private static final Logger logr = LogManager.getLogger();
    private DBService dbService;
    private PropHandler propHandler;
    private NuclideConstants constants;
    private Nature nature;
    private SimpleStringProperty fullName;
    private SimpleStringProperty name;
    private SimpleStringProperty symbol;
    private SimpleStringProperty nameNotation;
    private SimpleStringProperty abbrNotation;
    private SimpleStringProperty massNumber;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private SimpleStringProperty strMass;
    private SimpleObjectProperty<RadBigDecimal> mass;
    private Conversions.SIPrefix massPrefix;
    private Conversions.MassUnit massUnit;
    private SimpleStringProperty strInitActivity;
    private SimpleObjectProperty<RadBigDecimal> initActivity;
    private Conversions.SIPrefix initActivityPrefix;
    private Conversions.RadUnit initActivityUnit;
    private NuclideClass nuclideClass;
    private SimpleObjectProperty<LocalDate> refDate;
    private LifeSpan lifeSpan;
    private LungAbsorption lungAbsorption;
    
    public Nuclide(NuclideModel model) {
        this(model.getName(), model.getNuclideId());
    }

    public Nuclide(String name, NuclideModelId nuclideId) {
        this(name,
            nuclideId,
            null,
            null,
            null,
            null,
            LocalDate.now());
    }

    public Nuclide(String name, NuclideModelId nuclideId, Conversions.MassUnit massUnit, Conversions.RadUnit initActivityUnit, Nature nature, LimitsModelId limitsId, LocalDate refDate) {
        setDbService(new DBServiceImpl());
        setName(name);
        setNuclideId(nuclideId);
        setFullName();
        setNameNotation();
        setAbbrNotation();
        setMassPrefix(Conversions.SIPrefix.BASE);
        setMassUnit(massUnit);
        setStrMass();
        setInitActivityPrefix(Conversions.SIPrefix.BASE);
        setInitActivityUnit(initActivityUnit);
        setStrInitActivity();
        setNature(nature);
        setLimitsId(limitsId);
        setNuclideClass(NuclideClass.TBD);
        setPropHandler(null);
        setRefDate(refDate);
        setLifeSpan(LifeSpan.REGULAR);
        setLungAbsorption(LungAbsorption.NONE);

        logr.trace("Created new Nuclide {}", this::toString);
    }

    public Nuclide initConstants() {
        getConstants().setDbService(getDbService());
        getConstants().dbInit(getNuclideId(), getLimitsId());
        return this;
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        try {
            this.propHandler = propHandler == null?
                new PropHandlerFactory().getPropHandler(null):
            propHandler;
        } catch (IOException e) {
            logr.catching(e);
            logr.error("Failed to set PropHandler for isotope {}", this::getNuclideId);
        }
    }

    public NuclideConstants getConstants() {
        if(constants == null) {
            setConstants(null);
        }
        return constants;
    }

    public void setConstants(NuclideConstants constants) {
        this.constants = constants == null? new NuclideConstants() : constants;
    }

    public String getStrMass() {
        return strMassProperty() == null ?
            getMass() + " " + getMassPrefix().getAbbrVal() + getMassUnit().getAbbrVal() : strMassProperty().get();
    }

    public SimpleStringProperty strMassProperty() {
        return strMass;
    }

    public void setStrMass() {
        String str = getMass() + " " + getMassPrefix().getAbbrVal() + getMassUnit().getAbbrVal();
        if(strMassProperty() == null) {
            this.strMass = new SimpleStringProperty(str);
        } else {
            strMassProperty().set(str);
        }
    }

    public RadBigDecimal getMass() {
        return massProperty() == null? RadBigDecimal.NEG_INFINITY_OBJ : massProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> massProperty() {
        return mass;
    }

    public void setMass(RadBigDecimal mass) {
        if(massProperty() == null) {
            this.mass = new SimpleObjectProperty<>(mass);
        } else {
            massProperty().set(mass);
        }
    }

    public Conversions.SIPrefix getMassPrefix() {
        return massPrefix == null? Conversions.SIPrefix.BASE : massPrefix;
    }

    public void setMassPrefix(Conversions.SIPrefix massPrefix) {
        this.massPrefix = massPrefix;
    }

    public Conversions.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(Conversions.MassUnit massUnit) {
        this.massUnit = massUnit == null? Conversions.MassUnit.GRAMS : massUnit;
    }

    public String getStrInitActivity() {
        return strInitActivityProperty() == null?
            getInitActivity() + " " + getInitActivityPrefix().getAbbrVal() + getInitActivityUnit() :
            strInitActivityProperty().get();
    }

    public SimpleStringProperty strInitActivityProperty() {
        return strInitActivity;
    }

    public void setStrInitActivity() {
        String str = getInitActivity() + " " + getInitActivityPrefix().getAbbrVal() + getInitActivityUnit();
        if(strInitActivityProperty() == null) {
            this.strInitActivity = new SimpleStringProperty(str);
        } else {
            strInitActivityProperty().set(str);
        }
    }

    public RadBigDecimal getInitActivity() {
        return initActivityProperty() == null? RadBigDecimal.NEG_INFINITY_OBJ : initActivityProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> initActivityProperty() {
        return initActivity;
    }

    public void setInitActivity(RadBigDecimal initActivity) {
        if(initActivityProperty() == null) {
            this.initActivity = new SimpleObjectProperty<>(initActivity);
        } else {
            initActivityProperty().set(initActivity);
        }
    }

    public Conversions.SIPrefix getInitActivityPrefix() {
        return initActivityPrefix;
    }

    public void setInitActivityPrefix(Conversions.SIPrefix initActivityPrefix) {
        this.initActivityPrefix = initActivityPrefix;
    }

    public Conversions.RadUnit getInitActivityUnit() {
        return initActivityUnit;
    }

    public void setInitActivityUnit(Conversions.RadUnit initActivityUnit) {
        this.initActivityUnit = initActivityUnit == null? Conversions.RadUnit.CURIE : initActivityUnit;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        if(nuclideId == null) {
            setSymbol("");
            setMassNumber("");
        } else {
            setSymbol(nuclideId.getSymbol());
            setMassNumber(nuclideId.getMassNumber());
        }
    }

    public NuclideModelId getNuclideId() {
        return new NuclideModelId(getSymbol(), getMassNumber());
    }

    public String getNameNotation() {
        return nameNotationProperty() == null?
            getName() + "-" + getMassNumber()
            : nameNotationProperty().get();
    }

    public SimpleStringProperty nameNotationProperty() {
        return nameNotation;
    }

    public void setNameNotation() {
        String str = getName() + "-" + getMassNumber();
        if(strInitActivityProperty() == null) {
            this.nameNotation = new SimpleStringProperty(str);
        } else {
            nameNotationProperty().set(str);
        }
    }

    public String getAbbrNotation() {
        return abbrNotationProperty() == null?
            getSymbol() + "-" + getMassNumber()
            :abbrNotationProperty().get();
    }

    public SimpleStringProperty abbrNotationProperty() {
        return abbrNotation;
    }

    public void setAbbrNotation() {
        String str = getSymbol() + "-" + getMassNumber();
        if(strInitActivityProperty() == null) {
            this.nameNotation = new SimpleStringProperty(str);
        } else {
            nameNotationProperty().set(str);
        }
    }

    public String getSymbol() {
        return symbolProperty() == null? "" : symbolProperty().get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        String val = symbol == null? "" : symbol;
        if(symbolProperty() == null) {
            this.symbol = new SimpleStringProperty(val);
        } else {
            symbolProperty().set(val);
        }
    }

    public String getMassNumber() {
        return massNumberProperty() == null? "" : massNumberProperty().get();
    }

    public SimpleStringProperty massNumberProperty() {
        return massNumber;
    }

    public void setMassNumber(String massNumber) {
        String val = massNumber == null? "" : massNumber;
        if(massNumberProperty() == null) {
            this.massNumber = new SimpleStringProperty(val);
        } else {
            massNumberProperty().set(val);
        }
    }

    public String getFullName() {
        return fullNameProperty() == null?
            getName() + " (" + getSymbol() + ")":
            fullNameProperty().get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName() {
        String str = getName() + " (" + getSymbol() + ")";
        if(fullNameProperty() == null) {
            this.fullName = new SimpleStringProperty(str);
        } else {
            fullNameProperty().set(str);
        }
    }

    public String getName() {
        return nameProperty() == null? "" : nameProperty().get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        String val = name == null? "" : name;
        if(nameProperty() == null) {
            this.name = new SimpleStringProperty(val);
        } else {
            nameProperty().set(val);
        }
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature == null? Nuclide.Nature.REGULAR: nature;
    }

    public LimitsModelId getLimitsId() {
        return new LimitsModelId(getState(), getForm());
    }

    public void setLimitsId(LimitsModelId limitsId) {
        if(limitsId == null) {
            limitsId = new LimitsModelId();
        }
        setState(limitsId.getState());
        setForm(limitsId.getForm());
    }

    public LimitsModelId.State getState() {
        return state;
    }

    public void setState(LimitsModelId.State state) {
        this.state = state == null? LimitsModelId.State.SOLID : state;
    }

    public LimitsModelId.Form getForm() {
        return form;
    }

    public void setForm(LimitsModelId.Form form) {
        this.form = form == null? LimitsModelId.Form.NORMAL : form;
    }

    public NuclideClass getNuclideClass() {
        return nuclideClass;
    }

    public void setNuclideClass(NuclideClass nuclideClass) {
        this.nuclideClass = nuclideClass == null? NuclideClass.TBD : nuclideClass;
    }

    public LocalDate getRefDate() {
        return refDateProperty() == null? LocalDate.now() : refDateProperty().get();
    }

    public SimpleObjectProperty<LocalDate> refDateProperty() {
        return refDate;
    }

    public void setRefDate(LocalDate refDate) {
        LocalDate val = refDate == null? LocalDate.now() : refDate;

        if(refDateProperty() == null) {
            this.refDate = new SimpleObjectProperty<>(val);
        } else {
            refDateProperty().set(val);
        }
    }

    public LifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(LifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan == null? LifeSpan.REGULAR : lifeSpan;
    }

    public LungAbsorption getLungAbsorption() {
        return lungAbsorption;
    }

    public void setLungAbsorption(LungAbsorption lungAbsorption) {
        this.lungAbsorption = lungAbsorption == null? LungAbsorption.NONE : lungAbsorption;
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
            Objects.equals(this.getStrMass(), temp.getStrMass()) &&
            Objects.equals(this.getStrInitActivity(), temp.getStrInitActivity()) &&
            Objects.equals(this.getNature(), temp.getNature()) &&
            Objects.equals(this.getLimitsId(), temp.getLimitsId());
    }

    @Override
    public int hashCode() {
        int hash = 43;
        hash = 2 * hash + (this.getNuclideId() != null ? this.getNuclideId().hashCode() : 0);
        hash = 2 * hash + (this.getNuclideClass() != null ? this.getNuclideClass().hashCode() : 0);
        hash = 2 * hash + (this.getRefDate() != null ? this.getRefDate().hashCode() : 0);
        hash = 2 * hash + (this.getStrMass().hashCode());
        hash = 2 * hash + (this.getStrInitActivity().hashCode());
        hash = 2 * hash + (this.getNature() != null ? this.getNature().hashCode() : 0);
        hash = 2 * hash + (this.getLimitsId() != null ? this.getLimitsId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Nuclide: { " + getNuclideId() +
            "\nClass: " + getNuclideClass() +
            "\nRef Date: " + getRefDate() +
            "\nMass: " + getStrMass() +
            "\nInitial Activity: " + getStrInitActivity() +
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
            "The amount of material is less than 0.002 \u00B5curies per gram."),
        EXCEPTED("Limited Quantity",
            "The amount is greater than 0.002 \u00B5curies per gram but does not exceed\n" +
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
            return Objects.equals(val, "") ? "" : val + " Lived";
        }

        public String getAbbrVal() { return val.equals("")? "" :
            "(" + val.toLowerCase() + ")";}

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
                .map(LifeSpan::getAbbrVal)
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

        public String getAbbrVal() { return val.equals("")? "" :
            val.substring(0,1).toLowerCase();}

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
