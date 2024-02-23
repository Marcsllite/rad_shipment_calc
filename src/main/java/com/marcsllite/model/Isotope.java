package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Isotope {
    private static final Logger logr = LogManager.getLogger();
    protected PropHandler propHandler;
    protected IsotopeConstants constants;
    private IsotopeModelId isoId;
    private Nature nature;
    private LimitsModelId limitsId;
    private Mass mass;
    private RadUnit radUnit;
    private IsoClass isoClass;

    public enum Mass {
        GRAMS("grams"),
        LITERS("liters");

        private final String val;

        Mass(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(Mass.values())
                .map(Mass::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
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

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(RadUnit.values())
                .map(RadUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
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

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(IsoClass.values())
                .map(IsoClass::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
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

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(Nature.values())
                .map(Nature::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
    }

    public Isotope(IsotopeModelId isoId) {
        this(isoId,
            Mass.GRAMS);
    }

    public Isotope(IsotopeModelId isoId, Mass mass) {
        this(isoId,
            mass,
            RadUnit.CURIE);
    }

    public Isotope(IsotopeModelId isoId, Mass mass, RadUnit radUnit) {
        this(isoId,
            mass,
            radUnit,
            Nature.REGULAR);
    }

    public Isotope(IsotopeModelId isoId, Mass mass, RadUnit radUnit, Nature nature) {
        this(isoId,
            mass,
            radUnit,
            nature,
            LimitsModelId.State.SOLID);
    }

    public Isotope(IsotopeModelId isoId, Mass mass, RadUnit radUnit, Nature nature, LimitsModelId.State state) {
        this(isoId,
            mass,
            radUnit,
            nature,
            new LimitsModelId(state, LimitsModelId.Form.NORMAL));
    }

    public Isotope(IsotopeModelId isoId, Mass mass, RadUnit radUnit, Nature nature, LimitsModelId limitsId) {
        setPropHandler(new PropHandler());
        constants = new IsotopeConstants((float) getPropHandler().getDouble("defaultNum"));
        constants.dbInit(isoId, limitsId);
        setIsoId(isoId);
        setMass(mass);
        setRadUnit(radUnit);
        setNature(nature);
        setLimitsId(limitsId);
        setIsoClass(IsoClass.TBD);
        logr.debug("Created new Isotope " + getIsoId().getAbbr() + " and initialized it with the following data:\n" + getConstants());
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

    public Mass getMass() {
        return mass;
    }

    public void setMass(Mass mass) {
        this.mass = mass;
    }

    public RadUnit getRadUnit() {
        return radUnit;
    }

    public void setRadUnit(RadUnit radUnit) {
        this.radUnit = radUnit;
    }

    public void setIsoId(IsotopeModelId isoId) {
        this.isoId = isoId;
    }

    public IsotopeModelId getIsoId() {
        return isoId;
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
        this.limitsId = limitsId;
    }

    public IsoClass getIsoClass() {
        return isoClass;
    }

    public void setIsoClass(IsoClass isoClass) {
        this.isoClass = isoClass;
    }
}
