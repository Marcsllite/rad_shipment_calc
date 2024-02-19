package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Isotope {
    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;
    private final IsotopeConstants constants;
    private String name;
    private String abbr;
    private Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
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
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()),
            isoId
        );
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId) {
        this(propHandler,
            isoId,
            Mass.GRAMS);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, Mass mass) {
        this(propHandler,
            isoId,
            mass,
            RadUnit.CURIE);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, Mass mass, RadUnit radUnit) {
        this(propHandler,
            isoId,
            mass,
            radUnit,
            Nature.REGULAR);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, Mass mass, RadUnit radUnit, Nature nature) {
        this(propHandler,
            isoId,
            mass,
            radUnit,
            nature,
            LimitsModelId.State.SOLID);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, Mass mass, RadUnit radUnit, Nature nature, LimitsModelId.State state) {
        this(propHandler,
            isoId,
            mass,
            radUnit,
            nature,
            new LimitsModelId(state, LimitsModelId.Form.NORMAL));
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, Mass mass, RadUnit radUnit, Nature nature, LimitsModelId limitsId) {
        this.propHandler = propHandler;
        this.constants = new IsotopeConstants(this.propHandler);
        constants.dbInit(isoId, limitsId);
        setName(isoId.getName());
        setAbbr(isoId.getAbbr());
        setMass(mass);
        setRadUnit(radUnit);
        setNature(nature);
        setState(limitsId.getState());
        setForm(limitsId.getForm());
        setIsoClass(IsoClass.TBD);
        logr.debug("Created new Isotope " + getAbbr() + " and initialized it with the following data:\n" + constants);
    }

    public IsotopeConstants getConstants() {
        return constants;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public LimitsModelId.State getState() {
        return state;
    }

    public void setState(LimitsModelId.State state) {
        this.state = state;
    }

    public LimitsModelId.Form getForm() {
        return form;
    }

    public void setForm(LimitsModelId.Form form) {
        this.form = form;
    }

    public IsoClass getIsoClass() {
        return isoClass;
    }

    public void setIsoClass(IsoClass isoClass) {
        this.isoClass = isoClass;
    }
}
