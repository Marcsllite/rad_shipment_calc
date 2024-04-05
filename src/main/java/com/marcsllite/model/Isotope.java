package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Isotope {
    private static final Logger logr = LogManager.getLogger();
    protected PropHandler propHandler;
    protected IsotopeConstants constants;
    private static final SimpleStringProperty name = new SimpleStringProperty();
    private static final SimpleStringProperty abbr = new SimpleStringProperty();
    private Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private static final SimpleFloatProperty mass = new SimpleFloatProperty();
    private MassUnit massUnit;
    private static final SimpleFloatProperty initActivty = new SimpleFloatProperty();
    private RadUnit initActivityUnit;
    private IsoClass isoClass;

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

        public static MassUnit toMass(String value) {
            for (MassUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value)) {
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

    public Isotope(IsotopeModel model) {
        this(model.getIsotopeId());
    }

    public Isotope(IsotopeModelId isoId) {
        this(isoId,
            null,
            null,
            null,
            null);
    }

    public Isotope(IsotopeModelId isoId, MassUnit massUnit, RadUnit initActivityUnit, Nature nature, LimitsModelId limitsId) {
        setIsoId(isoId);
        setMassUnit(massUnit);
        setInitActivityUnit(initActivityUnit);
        setNature(nature);
        setLimitsId(limitsId);
        setIsoClass(IsoClass.TBD);
        try {
            setPropHandler(new PropHandlerFactory().getPropHandler(null));
            setConstants(new IsotopeConstants((float) getPropHandler().getDouble("defaultNum")));
            getConstants().dbInit(getIsoId(), getLimitsId());
        } catch (IOException e) {
            logr.catching(e);
            logr.error("Failed to setup Isotope {}'s constants.", isoId.getName());
        }
        logr.trace("Created new Isotope {}", this::toString);
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public IsotopeConstants getConstants() {
        return constants;
    }

    public void setConstants(IsotopeConstants constants) {
        this.constants = constants;
    }

    public MassUnit getMassUnit() {
        return massUnit;
    }

    public float getMass() {
        return massProperty().floatValue();
    }

    public SimpleFloatProperty massProperty() {
        return mass;
    }

    public void setMass(float mass) {
        massProperty().set(mass);
    }

    public void setMassUnit(MassUnit massUnit) {
        this.massUnit = massUnit == null? Isotope.MassUnit.GRAMS : massUnit;
    }

    public float getInitActivty() {
        return initActivityProperty().floatValue();
    }

    public SimpleFloatProperty initActivityProperty() {
        return initActivty;
    }

    public void setInitActivty(float initActivty) {
        initActivityProperty().set(initActivty);
    }

    public RadUnit getInitActivityUnit() {
        return initActivityUnit;
    }

    public void setInitActivityUnit(RadUnit initActivityUnit) {
        this.initActivityUnit = initActivityUnit == null? RadUnit.CURIE : initActivityUnit;
    }

    public void setIsoId(IsotopeModelId isoId) {
        setName(isoId.getName());
        setAbbr(isoId.getAbbr());
    }

    public IsotopeModelId getIsoId() {
        return new IsotopeModelId(getName(), getAbbr());
    }

    public String getName() {
        return nameProperty().get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        nameProperty().set(name == null? "" : name);
    }

    public String getAbbr() {
        return abbrProperty().get();
    }

    public SimpleStringProperty abbrProperty() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        abbrProperty().set(abbr == null? "" : abbr);
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
            Objects.equals(this.getMass(), temp.getMass()) &&
            Objects.equals(this.getMassUnit(), temp.getMassUnit()) &&
            Objects.equals(this.getInitActivty(), temp.getInitActivty()) &&
            Objects.equals(this.getInitActivityUnit(), temp.getInitActivityUnit()) &&
            Objects.equals(this.getNature(), temp.getNature()) &&
            Objects.equals(this.getLimitsId(), temp.getLimitsId()) &&
            Objects.equals(this.constants, temp.constants);
    }

    @Override
    public int hashCode() {
        int hash = 43;
        hash = 2 * hash + (this.getIsoId() != null ? this.getIsoId().hashCode() : 0);
        hash = 2 * hash + (this.getIsoClass() != null ? this.getIsoClass().hashCode() : 0);
        hash = 2 * hash + (int) this.getMass();
        hash = 2 * hash + (this.getMassUnit() != null ? this.getMassUnit().hashCode() : 0);
        hash = 2 * hash + (int) this.getInitActivty();
        hash = 2 * hash + (this.getInitActivityUnit() != null? this.getInitActivityUnit().hashCode() : 0);
        hash = 2 * hash + (this.getNature() != null ? this.getNature().hashCode() : 0);
        hash = 2 * hash + (this.getLimitsId() != null ? this.getLimitsId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Isotope: { " + getIsoId() +
            "\nClass: " + getIsoClass() +
            "\nMass: " + getMass() + " " + getMassUnit() +
            "\nInitial Activity: " + getInitActivty() + " " + getInitActivityUnit() +
            "\nNature: " + getNature() +
            "\n" + getLimitsId() +
            "\n" + getConstants() + "\n}";
    }
}
