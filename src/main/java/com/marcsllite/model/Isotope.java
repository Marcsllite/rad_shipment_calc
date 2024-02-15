package com.marcsllite.model;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class Isotope {
    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;
    private final IsotopeConstants constants;
    private MassUnit massUnit;

    public enum MassUnit {
        GRAMS("grams"),
        LITERS("liters");

        public final String val;

        MassUnit(String val) {
            this.val = val;
        }
    }
    public Isotope() {
        this(
            (PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory())
        );
    }

    public Isotope(PropHandler propHandler) {
        this.propHandler = propHandler;
        this.constants = new IsotopeConstants(this.propHandler);
        setMassUnit(MassUnit.GRAMS);
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
}
