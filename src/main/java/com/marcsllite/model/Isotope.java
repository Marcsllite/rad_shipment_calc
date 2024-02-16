package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;

import java.util.ResourceBundle;

public class Isotope {
    private final PropHandler propHandler;
    private final IsotopeConstants constants;
    private String name;
    private String abbr;
    private Nature nature;
    private LimitsModel.State state;
    private LimitsModel.Form form;
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
    public Isotope(String name, String abbr) {
        this(
            (PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()),
            name,
            abbr
        );
    }

    public Isotope(PropHandler propHandler, String name, String abbr) {
        this(propHandler,
            name,
            abbr,
            MassUnit.GRAMS);
    }

    public Isotope(PropHandler propHandler, String name, String abbr, MassUnit massUnit) {
        this(propHandler,
            name,
            abbr,
            massUnit,
            Nature.REGULAR);
    }

    public Isotope(PropHandler propHandler, String name, String abbr, MassUnit massUnit, Nature nature) {
        this(propHandler,
            name,
            abbr,
            massUnit,
            nature,
            LimitsModel.State.SOLID,
            LimitsModel.Form.NORMAL);
    }

    public Isotope(PropHandler propHandler, String name, String abbr, MassUnit massUnit, Nature nature, LimitsModel.State state) {
        this(propHandler,
            name,
            abbr,
            massUnit,
            nature,
            state,
            LimitsModel.Form.NORMAL);
    }

    public Isotope(PropHandler propHandler, String name, String abbr, MassUnit massUnit, Nature nature, LimitsModel.State state, LimitsModel.Form form) {
        this.propHandler = propHandler;
        this.constants = new IsotopeConstants(this.propHandler);
        setName(name);
        setAbbr(abbr);
        setMassUnit(massUnit);
        setNature(nature);
        setState(state);
        setForm(form);
        setIsoClass(IsoClass.TBD);
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

    public LimitsModel.State getState() {
        return state;
    }

    public void setState(LimitsModel.State state) {
        this.state = state;
    }

    public LimitsModel.Form getForm() {
        return form;
    }

    public void setForm(LimitsModel.Form form) {
        this.form = form;
    }

    public IsoClass getIsoClass() {
        return isoClass;
    }

    public void setIsoClass(IsoClass isoClass) {
        this.isoClass = isoClass;
    }
}
