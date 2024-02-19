package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class IsotopeConstants {
    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;
    private final DBService dbService;
    private final float defaultVal;
    private final SimpleFloatProperty a1 = new SimpleFloatProperty();
    private final SimpleFloatProperty a2 = new SimpleFloatProperty();
    private final SimpleFloatProperty decayConstant = new SimpleFloatProperty();
    private final SimpleFloatProperty exemptConcentration = new SimpleFloatProperty();
    private final SimpleFloatProperty exemptLimit = new SimpleFloatProperty();
    private final SimpleFloatProperty halfLife = new SimpleFloatProperty();
    private final SimpleFloatProperty iaLimitedLimit = new SimpleFloatProperty();
    private final SimpleFloatProperty iaPackageLimit = new SimpleFloatProperty();
    private final SimpleFloatProperty limitedLimit = new SimpleFloatProperty();
    private final SimpleFloatProperty curieReportQuan = new SimpleFloatProperty();

    private final SimpleFloatProperty teraBqReportQuan = new SimpleFloatProperty();

    public IsotopeConstants() {
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
    }

    public IsotopeConstants(PropHandler propHandler) {
        this(propHandler, null);
    }

    public IsotopeConstants(PropHandler propHandler, EntityManager em) {
        this.propHandler = propHandler;
        this.dbService = new DBServiceImpl(this.propHandler, em);
        this.defaultVal = (float) this.propHandler.getDouble("defaultNum");
    }

    public void dbInit(IsotopeModelId isoId, LimitsModelId limitsId) {
        try {
            setA1(dbService.getA1(isoId.getAbbr()));
            setA2(dbService.getA2(isoId.getAbbr()));
            setDecayConstant(dbService.getDecayConstant(isoId.getAbbr()));
            setExemptConcentration(dbService.getExemptConcentration(isoId.getAbbr()));
            setExemptLimit(dbService.getExemptLimit(isoId.getAbbr()));
            setHalfLife(dbService.getHalfLife(isoId.getAbbr()));
            setIaLimitedLimit(dbService.getIALimited(limitsId));
            setIaPackageLimit(dbService.getIAPackage(limitsId));
            setLimitedLimit(dbService.getLimited(limitsId));
            setCurieReportQuan(dbService.getCiReportQuan(isoId.getAbbr()));
            setTeraBqReportQuan(dbService.getTBqReportQuan(isoId.getAbbr()));
        } catch (Exception e) {
            logr.fatal("Failed to initialize {} isotope constants from db", isoId.getName());
            logr.catching(Level.FATAL, e);
            if(System.getProperty("keepPlatformOpen") == null) {
                Platform.exit();
            }
        }
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

    public float getCurieReportQuan() {
        return curieReportQuanProperty().get();
    }

    public SimpleFloatProperty curieReportQuanProperty() {
        return curieReportQuan;
    }

    public void setCurieReportQuan(float curieReportQuan) {
        curieReportQuanProperty().set(curieReportQuan);
    }

    public float getTeraBqReportQuan() {
        return teraBqReportQuanProperty().get();
    }

    public SimpleFloatProperty teraBqReportQuanProperty() {
        return teraBqReportQuan;
    }

    public void setTeraBqReportQuan(float teraBqReportQuan) {
        teraBqReportQuanProperty().set(teraBqReportQuan);
    }

    @Override
    public String toString() {
        return "Isotope Constants: {\n\tA1: " + getA1() + " TBq\n" +
            "\tA2: " + getA2() + " TBq\n" +
            "\tDecay Constant: " + getDecayConstant() + " \n" +
            "\tExempt Concentration: " + getExemptConcentration() + " Bq/g\n" +
            "\tExempt Limit: " + getExemptLimit() + " Bq\n" +
            "\tHalfLife: " + getHalfLife() + " days\n" +
            "\tInstruments/Articles Limited Limit: " + getIaLimitedLimit() + " \n" +
            "\tInstruments/Articles Package Limit: " + getIaPackageLimit() + " \n" +
            "\tNormal Limited Limit: " + getLimitedLimit() + " TBq\n" +
            "\tReportable Quantity: " + getCurieReportQuan() + " TBq\n}";
    }
}
