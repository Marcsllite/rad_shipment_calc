package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class Isotope {
    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;
    private final IsotopeConstants constants;
    private String name;
    private String abbr;
    private Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private MassUnit massUnit;
    private IsoClass isoClass;


    public enum MassUnit {
        GRAMS("grams"),
        LITERS("liters");

        public final String val;

        MassUnit(String val) {
            this.val = val;
        }
    }

    public enum IsoClass {
        EXEMPT("Exempt"),
        EXCEPTED("Excepted"),
        TYPE_A("Type A"),
        TYPE_B("Type B"),
        TYPE_B_HIGHWAY("Type B: Highway Route Control"),
        TBD("To Be Determined");

        public final String val;

        IsoClass(String val) {
            this.val = val;
        }
    }

    public enum Nature {
        REGULAR("Regular"),
        INSTRUMENT("Instrument"),
        ARTICLE("Article");

        public final String val;

        Nature(String val) {
            this.val = val;
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
            MassUnit.GRAMS);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, MassUnit massUnit) {
        this(propHandler,
            isoId,
            massUnit,
            Nature.REGULAR);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, MassUnit massUnit, Nature nature) {
        this(propHandler,
            isoId,
            massUnit,
            nature,
            LimitsModelId.State.SOLID);
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, MassUnit massUnit, Nature nature, LimitsModelId.State state) {
        this(propHandler,
            isoId,
            massUnit,
            nature,
            new LimitsModelId(state, LimitsModelId.Form.NORMAL));
    }

    public Isotope(PropHandler propHandler, IsotopeModelId isoId, MassUnit massUnit, Nature nature, LimitsModelId limitsId) {
        this.propHandler = propHandler;
        this.constants = new IsotopeConstants(this.propHandler);
        constants.dbInit(isoId, limitsId);
        setName(isoId.getName());
        setAbbr(isoId.getAbbr());
        setMassUnit(massUnit);
        setNature(nature);
        setState(limitsId.getState());
        setForm(limitsId.getForm());
        setIsoClass(IsoClass.TBD);
        logr.debug("Created new Isotope " + getAbbr() + " and initialized it with the following data:\n" + constants);
    }

    public IsotopeConstants getConstants() {
        return constants;
    }

    public MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(MassUnit massUnit) {
        this.massUnit = massUnit;
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
