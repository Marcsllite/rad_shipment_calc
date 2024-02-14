package com.marcsllite.model;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class IsotopeConstants {
    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;
    final float defaultVal;
    // A1 (TBq) of isotope
    final SimpleFloatProperty a1;
    // A2 (TBq) of isotope
    final SimpleFloatProperty a2;
    // Decay Constant (1 / halflife(days)) of isotope
    final SimpleFloatProperty decayConstant;
    // Exempt Concentration (Bq/gram) of isotope
    final SimpleFloatProperty exemptConcentration;
    // Exempt Limit (Bq)of isotope
    final SimpleFloatProperty exemptLimit;
    // Halflife (days) of isotope
    final SimpleFloatProperty halfLife;
    // Instruments/Articles multiplier (see 173.425_Table 4) of isotope
    final SimpleFloatProperty iaLimitedMultiplier;
    // Licensing Limit (microCi) of isotope
    final SimpleFloatProperty licenseLimit;
    // limited Limit (TBq) of isotope
    final SimpleFloatProperty limitedLimit;
    // Reportable Quantity (Ci) of isotope
    final SimpleFloatProperty reportableQuan;

    public IsotopeConstants() {
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
    }

    public IsotopeConstants(PropHandler propHandler) {
        this.propHandler = propHandler;
        defaultVal = (float) propHandler.getDouble("defaultNum");

        a1 = new SimpleFloatProperty(defaultVal);
        a2 = new SimpleFloatProperty(defaultVal);
        decayConstant = new SimpleFloatProperty(defaultVal);
        exemptConcentration = new SimpleFloatProperty(defaultVal);
        exemptLimit = new SimpleFloatProperty(defaultVal);
        halfLife = new SimpleFloatProperty(defaultVal);
        iaLimitedMultiplier = new SimpleFloatProperty(defaultVal);
        licenseLimit = new SimpleFloatProperty(defaultVal);
        limitedLimit = new SimpleFloatProperty(defaultVal);
        reportableQuan = new SimpleFloatProperty(defaultVal);
    }

}
