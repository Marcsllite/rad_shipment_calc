package com.marcsllite.model;

import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import javafx.beans.property.SimpleFloatProperty;

import java.util.Objects;

public class IsotopeConstants {
    private DBService dbService;
    private static final float defaultVal = -2.0F;
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
        setDbService(new DBServiceImpl());

        setA1(IsotopeConstants.getDefaultVal());
        setA2(IsotopeConstants.getDefaultVal());
        setDecayConstant(IsotopeConstants.getDefaultVal());
        setExemptConcentration(IsotopeConstants.getDefaultVal());
        setExemptLimit(IsotopeConstants.getDefaultVal());
        setHalfLife(IsotopeConstants.getDefaultVal());
        setIaLimitedLimit(IsotopeConstants.getDefaultVal());
        setIaPackageLimit(IsotopeConstants.getDefaultVal());
        setLimitedLimit(IsotopeConstants.getDefaultVal());
        setCurieReportQuan(IsotopeConstants.getDefaultVal());
        setTeraBqReportQuan(IsotopeConstants.getDefaultVal());
    }

    public void dbInit(IsotopeModelId isoId, LimitsModelId limitsId) {
        setA1(getDbService().getA1(isoId.getAbbr()));
        setA2(getDbService().getA2(isoId.getAbbr()));
        setDecayConstant(getDbService().getDecayConstant(isoId.getAbbr()));
        setExemptConcentration(getDbService().getExemptConcentration(isoId.getAbbr()));
        setExemptLimit(getDbService().getExemptLimit(isoId.getAbbr()));
        setHalfLife(getDbService().getHalfLife(isoId.getAbbr()));
        setIaLimitedLimit(getDbService().getIALimited(limitsId));
        setIaPackageLimit(getDbService().getIAPackage(limitsId));
        setLimitedLimit(getDbService().getLimited(limitsId));
        setCurieReportQuan(getDbService().getCiReportQuan(isoId.getAbbr()));
        setTeraBqReportQuan(getDbService().getTBqReportQuan(isoId.getAbbr()));
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public static float getDefaultVal() {
        return IsotopeConstants.defaultVal;
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
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        IsotopeConstants temp = (IsotopeConstants) obj;
        return Objects.equals(this.getA1(), temp.getA1()) &&
            Objects.equals(this.getA2(), temp.getA2()) &&
            Objects.equals(this.getDecayConstant(), temp.getDecayConstant()) &&
            Objects.equals(this.getExemptConcentration(), temp.getExemptConcentration()) &&
            Objects.equals(this.getExemptLimit(), temp.getExemptLimit()) &&
            Objects.equals(this.getHalfLife(), temp.getHalfLife()) &&
            Objects.equals(this.getIaLimitedLimit(), temp.getIaLimitedLimit()) &&
            Objects.equals(this.getIaPackageLimit(), temp.getIaPackageLimit()) &&
            Objects.equals(this.getLimitedLimit(), temp.getLimitedLimit()) &&
            Objects.equals(this.getTeraBqReportQuan(), temp.getTeraBqReportQuan());
    }

    @Override
    public int hashCode() {
        int hash = 79;
        hash = 37 * hash + (int) this.getA1();
        hash = 37 * hash + (int) this.getA2();
        hash = 37 * hash + (int) this.getDecayConstant();
        hash = 37 * hash + (int) this.getExemptConcentration();
        hash = 37 * hash + (int) this.getExemptLimit();
        hash = 37 * hash + (int) this.getHalfLife();
        hash = 37 * hash + (int) this.getIaLimitedLimit();
        hash = 37 * hash + (int) this.getIaPackageLimit();
        hash = 37 * hash + (int) this.getLimitedLimit();
        hash = 37 * hash + (int) this.getTeraBqReportQuan();
        return hash;
    }


    @Override
    public String toString() {
        return "Isotope Constants: {\n\tA1: " + getA1() + " TBq" +
            "\n\tA2: " + getA2() + " TBq" +
            "\n\tDecay Constant: " + getDecayConstant() +
            "\n\tExempt Concentration: " + getExemptConcentration() + " Bq/g" +
            "\n\tExempt Limit: " + getExemptLimit() + " Bq" +
            "\n\tHalfLife: " + getHalfLife() + " days" +
            "\n\tInstruments/Articles Limited Limit: " + getIaLimitedLimit() +
            "\n\tInstruments/Articles Package Limit: " + getIaPackageLimit() +
            "\n\tNormal Limited Limit: " + getLimitedLimit() +
            "\n\tReportable Quantity: " + getTeraBqReportQuan() + " TBq\n}";
    }
}
