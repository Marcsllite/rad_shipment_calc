package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class NuclideConstants {
    private DBService dbService;
    private static final RadBigDecimal DEFAULT_VAL = RadBigDecimal.NEG_INFINITY_OBJ;
    private final SimpleStringProperty a1 = new SimpleStringProperty();
    private final SimpleStringProperty a2 = new SimpleStringProperty();
    private final SimpleStringProperty decayConstant = new SimpleStringProperty();
    private final SimpleStringProperty exemptConcentration = new SimpleStringProperty();
    private final SimpleStringProperty exemptLimit = new SimpleStringProperty();
    private final SimpleStringProperty halfLife = new SimpleStringProperty();
    private final SimpleStringProperty iaLimitedLimit = new SimpleStringProperty();
    private final SimpleStringProperty iaPackageLimit = new SimpleStringProperty();
    private final SimpleStringProperty limitedLimit = new SimpleStringProperty();
    private final SimpleStringProperty curieReportQuan = new SimpleStringProperty();
    private final SimpleStringProperty teraBqReportQuan = new SimpleStringProperty();

    public NuclideConstants() {
        setDbService(new DBServiceImpl());

        setA1(NuclideConstants.getDefaultVal());
        setA2(NuclideConstants.getDefaultVal());
        setDecayConstant(NuclideConstants.getDefaultVal());
        setExemptConcentration(NuclideConstants.getDefaultVal());
        setExemptLimit(NuclideConstants.getDefaultVal());
        setHalfLife(NuclideConstants.getDefaultVal());
        setIaLimitedLimit(NuclideConstants.getDefaultVal());
        setIaPackageLimit(NuclideConstants.getDefaultVal());
        setLimitedLimit(NuclideConstants.getDefaultVal());
        setCurieReportQuan(NuclideConstants.getDefaultVal());
        setTeraBqReportQuan(NuclideConstants.getDefaultVal());
    }

    public void dbInit(NuclideModelId nuclideId, LimitsModelId limitsId) {
        setA1(getDbService().getA1(nuclideId));
        setA2(getDbService().getA2(nuclideId));
        setDecayConstant(getDbService().getDecayConstant(nuclideId));
        setExemptConcentration(getDbService().getExemptConcentration(nuclideId));
        setExemptLimit(getDbService().getExemptLimit(nuclideId));
        setHalfLife(getDbService().getHalfLife(nuclideId));
        setIaLimitedLimit(getDbService().getIALimited(limitsId));
        setIaPackageLimit(getDbService().getIAPackage(limitsId));
        setLimitedLimit(getDbService().getLimited(limitsId));
        setCurieReportQuan(getDbService().getCiReportQuan(nuclideId));
        setTeraBqReportQuan(getDbService().getTBqReportQuan(nuclideId));
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public static RadBigDecimal getDefaultVal() {
        return NuclideConstants.DEFAULT_VAL;
    }

    public RadBigDecimal getA1() {
        return new RadBigDecimal(a1Property().get());
    }

    public SimpleStringProperty a1Property() {
        return a1;
    }

    public void setA1(RadBigDecimal a1) {
        a1Property().set(a1.toDisplayString());
    }

    public RadBigDecimal getA2() {
        return new RadBigDecimal(a2Property().get());
    }

    public SimpleStringProperty a2Property() {
        return a2;
    }

    public void setA2(RadBigDecimal a2) {
        a2Property().set(a2.toDisplayString());
    }

    public RadBigDecimal getDecayConstant() {
        return new RadBigDecimal(decayConstantProperty().get());
    }

    public SimpleStringProperty decayConstantProperty() {
        return decayConstant;
    }

    public void setDecayConstant(RadBigDecimal decayConstant) {
        decayConstantProperty().set(decayConstant.toDisplayString());
    }

    public RadBigDecimal getExemptConcentration() {
        return new RadBigDecimal(exemptConcentrationProperty().get());
    }

    public SimpleStringProperty exemptConcentrationProperty() {
        return exemptConcentration;
    }

    public void setExemptConcentration(RadBigDecimal exemptConcentration) {
        exemptConcentrationProperty().set(exemptConcentration.toDisplayString());
    }

    public RadBigDecimal getExemptLimit() {
        return new RadBigDecimal(exemptLimitProperty().get());
    }

    public SimpleStringProperty exemptLimitProperty() {
        return exemptLimit;
    }

    public void setExemptLimit(RadBigDecimal exemptLimit) {
        exemptLimitProperty().set(exemptLimit.toDisplayString());
    }

    public RadBigDecimal getHalfLife() {
        return new RadBigDecimal(halfLifeProperty().get());
    }

    public SimpleStringProperty halfLifeProperty() {
        return halfLife;
    }

    public void setHalfLife(RadBigDecimal halfLife) {
        halfLifeProperty().set(halfLife.toDisplayString());
    }

    public RadBigDecimal getIaLimitedLimit() {
        return new RadBigDecimal(iaLimitedLimitProperty().get());
    }

    public SimpleStringProperty iaLimitedLimitProperty() {
        return iaLimitedLimit;
    }

    public void setIaLimitedLimit(RadBigDecimal iaLimitedLimit) {
        iaLimitedLimitProperty().set(iaLimitedLimit.toDisplayString());
    }

    public RadBigDecimal getIaPackageLimit() {
        return new RadBigDecimal(iaPackageLimitProperty().get());
    }

    public SimpleStringProperty iaPackageLimitProperty() {
        return iaPackageLimit;
    }

    public void setIaPackageLimit(RadBigDecimal iaPackageLimit) {
        iaPackageLimitProperty().set(iaPackageLimit.toDisplayString());
    }

    public RadBigDecimal getLimitedLimit() {
        return new RadBigDecimal(limitedLimitProperty().get());
    }

    public SimpleStringProperty limitedLimitProperty() {
        return limitedLimit;
    }

    public void setLimitedLimit(RadBigDecimal limitedLimit) {
        limitedLimitProperty().set(limitedLimit.toDisplayString());
    }

    public RadBigDecimal getCurieReportQuan() {
        return new RadBigDecimal(curieReportQuanProperty().get());
    }

    public SimpleStringProperty curieReportQuanProperty() {
        return curieReportQuan;
    }

    public void setCurieReportQuan(RadBigDecimal curieReportQuan) {
        curieReportQuanProperty().set(curieReportQuan.toDisplayString());
    }

    public RadBigDecimal getTeraBqReportQuan() {
        return new RadBigDecimal(teraBqReportQuanProperty().get());
    }

    public SimpleStringProperty teraBqReportQuanProperty() {
        return teraBqReportQuan;
    }

    public void setTeraBqReportQuan(RadBigDecimal teraBqReportQuan) {
        teraBqReportQuanProperty().set(teraBqReportQuan.toDisplayString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        NuclideConstants temp = (NuclideConstants) obj;
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
        hash = 37 * hash + this.getA1().intValue();
        hash = 37 * hash + this.getA2().intValue();
        hash = 37 * hash + this.getDecayConstant().intValue();
        hash = 37 * hash + this.getExemptConcentration().intValue();
        hash = 37 * hash + this.getExemptLimit().intValue();
        hash = 37 * hash + this.getHalfLife().intValue();
        hash = 37 * hash + this.getIaLimitedLimit().intValue();
        hash = 37 * hash + this.getIaPackageLimit().intValue();
        hash = 37 * hash + this.getLimitedLimit().intValue();
        hash = 37 * hash + this.getTeraBqReportQuan().intValue();
        return hash;
    }


    @Override
    public String toString() {
        return "Nuclide Constants: {\n\tA1: " + getA1() + " TBq" +
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
