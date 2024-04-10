package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;
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

public class Isotope {
    private static final Logger logr = LogManager.getLogger();
    private DBService dbService;
    private PropHandler propHandler;
    private IsotopeConstants constants;
    private SimpleStringProperty name;
    private SimpleStringProperty abbr;
    private Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private SimpleStringProperty strMass;
    private SimpleFloatProperty mass;
    private Conversions.SIPrefix massPrefix;
    private MassUnit massUnit;
    private SimpleStringProperty strInitActivity;
    private SimpleFloatProperty initActivity;
    private Conversions.SIPrefix initActivityPrefix;
    private RadUnit initActivityUnit;
    private IsoClass isoClass;
    private SimpleObjectProperty<LocalDate> refDate;
    private LifeSpan lifeSpan;
    private LungAbsorption lungAbsorption;

    public enum MassUnit {
        GRAMS("grams"),
        LITERS("liters");

        private final String val;

        MassUnit(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() { return getVal().substring(0,1).toLowerCase(); }

        public static MassUnit toMass(String value) {
            for (MassUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(MassUnit.values())
                .map(MassUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    public enum RadUnit {
        BECQUEREL("Bq"),
        CURIE("Ci");

        private final String val;

        RadUnit(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static RadUnit toRadUnit(String value) {
            for (RadUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }
        
        public static ObservableList<String> getFxValues() {
            return Arrays.stream(RadUnit.values())
                .map(RadUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    public enum IsoClass {
        EXEMPT("Exempt"),
        EXCEPTED("Excepted"),
        TYPE_A("Type A"),
        TYPE_B("Type B"),
        TYPE_B_HIGHWAY("Type B: Highway Route Control"),
        TBD("To Be Determined");

        private final String val;

        IsoClass(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static IsoClass toIsoClass(String value) {
            for (IsoClass enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }
        
        public static ObservableList<String> getFxValues() {
            return Arrays.stream(IsoClass.values())
                .map(IsoClass::getVal)
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

    public Isotope(IsotopeModel model) {
        this(model.getIsotopeId());
    }

    public Isotope(IsotopeModelId isoId) {
        this(isoId,
            null,
            null,
            null,
            null,
            LocalDate.now());
    }

    public Isotope(IsotopeModelId isoId, MassUnit massUnit, RadUnit initActivityUnit, Nature nature, LimitsModelId limitsId, LocalDate refDate) {
        setDbService(new DBServiceImpl());
        setIsoId(isoId);
        setMassPrefix(Conversions.SIPrefix.BASE);
        setMassUnit(massUnit);
        setStrMass();
        setInitActivityPrefix(Conversions.SIPrefix.BASE);
        setInitActivityUnit(initActivityUnit);
        setStrInitActivity();
        setNature(nature);
        setLimitsId(limitsId);
        setIsoClass(IsoClass.TBD);
        setPropHandler(null);
        setRefDate(refDate);
        setLifeSpan(LifeSpan.REGULAR);
        setLungAbsorption(LungAbsorption.NONE);

        logr.trace("Created new Isotope {}", this::toString);
    }

    public Isotope initConstants() {
        getConstants().setDbService(getDbService());
        getConstants().dbInit(getIsoId(), getLimitsId());
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
            logr.error("Failed to set PropHandler for isotope {}", this::getIsoId);
        }
    }

    public IsotopeConstants getConstants() {
        if(constants == null) {
            setConstants(null);
        }
        return constants;
    }

    public void setConstants(IsotopeConstants constants) {
        this.constants = constants == null? new IsotopeConstants() : constants;
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

    public float getMass() {
        return massProperty() == null? -1F : massProperty().floatValue();
    }

    public SimpleFloatProperty massProperty() {
        return mass;
    }

    public void setMass(float mass) {
        if(massProperty() == null) {
            this.mass = new SimpleFloatProperty(mass);
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

    public MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(MassUnit massUnit) {
        this.massUnit = massUnit == null? Isotope.MassUnit.GRAMS : massUnit;
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

    public float getInitActivity() {
        return initActivityProperty() == null? -1F : initActivityProperty().floatValue();
    }

    public SimpleFloatProperty initActivityProperty() {
        return initActivity;
    }

    public void setInitActivity(float initActivity) {
        if(initActivityProperty() == null) {
            this.initActivity = new SimpleFloatProperty(initActivity);
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

    public RadUnit getInitActivityUnit() {
        return initActivityUnit;
    }

    public void setInitActivityUnit(RadUnit initActivityUnit) {
        this.initActivityUnit = initActivityUnit == null? RadUnit.CURIE : initActivityUnit;
    }

    public void setIsoId(IsotopeModelId isoId) {
        if(isoId == null) {
            setName("");
            setAbbr("");
        } else {
            setName(isoId.getName());
            setAbbr(isoId.getAbbr());
        }
    }

    public IsotopeModelId getIsoId() {
        return new IsotopeModelId(getName(), getAbbr());
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

    public String getAbbr() {
        return abbrProperty() == null? "" : abbrProperty().get();
    }

    public SimpleStringProperty abbrProperty() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        String val = abbr == null? "" : abbr;
        if(abbrProperty() == null) {
            this.abbr = new SimpleStringProperty(val);
        } else {
            abbrProperty().set(val);
        }
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature == null? Isotope.Nature.REGULAR: nature;
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

    public IsoClass getIsoClass() {
        return isoClass;
    }

    public void setIsoClass(IsoClass isoClass) {
        this.isoClass = isoClass == null? IsoClass.TBD : isoClass;
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
        Isotope temp = (Isotope) obj;
        return Objects.equals(this.getIsoId(), temp.getIsoId()) &&
            Objects.equals(this.getIsoClass(), temp.getIsoClass()) &&
            Objects.equals(this.getStrMass(), temp.getStrMass()) &&
            Objects.equals(this.getStrInitActivity(), temp.getStrInitActivity()) &&
            Objects.equals(this.getNature(), temp.getNature()) &&
            Objects.equals(this.getLimitsId(), temp.getLimitsId());
    }

    @Override
    public int hashCode() {
        int hash = 43;
        hash = 2 * hash + (this.getIsoId() != null ? this.getIsoId().hashCode() : 0);
        hash = 2 * hash + (this.getIsoClass() != null ? this.getIsoClass().hashCode() : 0);
        hash = 2 * hash + (this.getRefDate() != null ? this.getRefDate().hashCode() : 0);
        hash = 2 * hash + (this.getStrMass().hashCode());
        hash = 2 * hash + (this.getStrInitActivity().hashCode());
        hash = 2 * hash + (this.getNature() != null ? this.getNature().hashCode() : 0);
        hash = 2 * hash + (this.getLimitsId() != null ? this.getLimitsId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Isotope: { " + getIsoId() +
            "\nClass: " + getIsoClass() +
            "\nRef Date: " + getRefDate() +
            "\nMass: " + getStrMass() +
            "\nInitial Activity: " + getStrInitActivity() +
            "\nNature: " + getNature() +
            "\n" + getLimitsId() +
            "\n" + getConstants() + "\n}";
    }
}
