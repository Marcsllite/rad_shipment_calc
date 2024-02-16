package com.marcsllite.model;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;

import java.util.ResourceBundle;

public class IsotopeConstants {
    private final PropHandler propHandler;
    private final float defaultVal;
    private final SimpleFloatProperty a1;
    private final SimpleFloatProperty a2;
    private final SimpleFloatProperty decayConstant;
    private final SimpleFloatProperty exemptConcentration;
    private final SimpleFloatProperty exemptLimit;
    private final SimpleFloatProperty halfLife;
    private final SimpleFloatProperty iaLimitedLimit;
    private final SimpleFloatProperty iaPackageLimit;
    private final SimpleFloatProperty limitedLimit;
    private final SimpleFloatProperty reportableQuan;

    public IsotopeConstants() {
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
    }

    public IsotopeConstants(PropHandler propHandler) {
       this(propHandler,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
    }

    public IsotopeConstants(PropHandler propHandler,
                            Float a1,
                            Float a2,
                            Float decayConstant,
                            Float exemptConcentration,
                            Float exemptLimit,
                            Float halfLife,
                            Float iaLimitedLimit,
                            Float iaPackageLimit,
                            Float limitedLimit,
                            Float reportableQuan) {
        this.propHandler = propHandler;
        this.defaultVal = (float) this.propHandler.getDouble("defaultNum");

        this.a1 = new SimpleFloatProperty(a1 == null? defaultVal : a1);
        this.a2 = new SimpleFloatProperty(a2 == null? defaultVal : a2);
        this.decayConstant = new SimpleFloatProperty(decayConstant == null? defaultVal : decayConstant);
        this.exemptConcentration = new SimpleFloatProperty(exemptConcentration == null? defaultVal : exemptConcentration);
        this.exemptLimit = new SimpleFloatProperty(exemptLimit == null? defaultVal : exemptLimit);
        this.halfLife = new SimpleFloatProperty(halfLife == null? defaultVal : halfLife);
        this.iaLimitedLimit = new SimpleFloatProperty(iaLimitedLimit == null? defaultVal : iaLimitedLimit);
        this.iaPackageLimit = new SimpleFloatProperty(iaPackageLimit == null? defaultVal : iaPackageLimit);
        this.limitedLimit = new SimpleFloatProperty(limitedLimit == null? defaultVal : limitedLimit);
        this.reportableQuan = new SimpleFloatProperty(reportableQuan == null? defaultVal : reportableQuan);
    }

    public float getDefaultVal() {
        return defaultVal;
    }

    public float getA1() {
        return a1Property().get();
    }

    public SimpleFloatProperty a1Property() {
        return a1;
    }

    public void setA1(float a1) {
        a1Property().set(a1);
    }

    public float getA2() {
        return a2Property().get();
    }

    public SimpleFloatProperty a2Property() {
        return a2;
    }

    public void setA2(float a2) {
        a2Property().set(a2);
    }

    public float getDecayConstant() {
        return decayConstantProperty().get();
    }

    public SimpleFloatProperty decayConstantProperty() {
        return decayConstant;
    }

    public void setDecayConstant(float decayConstant) {
        decayConstantProperty().set(decayConstant);
    }

    public float getExemptConcentration() {
        return exemptConcentrationProperty().get();
    }

    public SimpleFloatProperty exemptConcentrationProperty() {
        return exemptConcentration;
    }

    public void setExemptConcentration(float exemptConcentration) {
        exemptConcentrationProperty().set(exemptConcentration);
    }

    public float getExemptLimit() {
        return exemptLimitProperty().get();
    }

    public SimpleFloatProperty exemptLimitProperty() {
        return exemptLimit;
    }

    public void setExemptLimit(float exemptLimit) {
        exemptLimitProperty().set(exemptLimit);
    }

    public float getHalfLife() {
        return halfLifeProperty().get();
    }

    public SimpleFloatProperty halfLifeProperty() {
        return halfLife;
    }

    public void setHalfLife(float halfLife) {
        halfLifeProperty().set(halfLife);
    }

    public float getIaLimitedLimit() {
        return iaLimitedLimitProperty().get();
    }

    public SimpleFloatProperty iaLimitedLimitProperty() {
        return iaLimitedLimit;
    }

    public void setIaLimitedLimit(float iaLimitedLimit) {
        iaLimitedLimitProperty().set(iaLimitedLimit);
    }

    public float getIaPackageLimit() {
        return iaPackageLimitProperty().get();
    }

    public SimpleFloatProperty iaPackageLimitProperty() {
        return iaPackageLimit;
    }

    public void setIaPackageLimit(float iaPackageLimit) {
        iaPackageLimitProperty().set(iaPackageLimit);
    }

    public float getLimitedLimit() {
        return limitedLimitProperty().get();
    }

    public SimpleFloatProperty limitedLimitProperty() {
        return limitedLimit;
    }

    public void setLimitedLimit(float limitedLimit) {
        limitedLimitProperty().set(limitedLimit);
    }

    public float getReportableQuan() {
        return reportableQuanProperty().get();
    }

    public SimpleFloatProperty reportableQuanProperty() {
        return reportableQuan;
    }

    public void setReportableQuan(float reportableQuan) {
        reportableQuanProperty().set(reportableQuan);
    }
}
