package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class NuclideConstants {
    private DBService dbService;
    private static final RadBigDecimal DEFAULT_VAL = RadBigDecimal.NEG_INFINITY_OBJ;
    private final SimpleObjectProperty<RadBigDecimal> a1 = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> a2 = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> decayConstant = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> exemptConcentration = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> exemptLimit = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> halfLife = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> iaLimitedLimit = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> iaPackageLimit = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> limitedLimit = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> curieReportQuan = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> teraBqReportQuan = new SimpleObjectProperty<>();

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
        return a1Property().get();
    }

    public SimpleObjectProperty<RadBigDecimal> a1Property() {
        return a1;
    }

    public void setA1(RadBigDecimal a1) {
        a1Property().set(a1);
    }

    public RadBigDecimal getA2() {
        return a2Property().get();
    }

    public SimpleObjectProperty<RadBigDecimal> a2Property() {
        return a2;
    }

    public void setA2(RadBigDecimal a2) {
        a2Property().set(a2);
    }

    public RadBigDecimal getDecayConstant() {
        return decayConstantProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> decayConstantProperty() {
        return decayConstant;
    }

    public void setDecayConstant(RadBigDecimal decayConstant) {
        decayConstantProperty().set(decayConstant);
    }

    public RadBigDecimal getExemptConcentration() {
        return exemptConcentrationProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> exemptConcentrationProperty() {
        return exemptConcentration;
    }

    public void setExemptConcentration(RadBigDecimal exemptConcentration) {
        exemptConcentrationProperty().set(exemptConcentration);
    }

    public RadBigDecimal getExemptLimit() {
        return exemptLimitProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> exemptLimitProperty() {
        return exemptLimit;
    }

    public void setExemptLimit(RadBigDecimal exemptLimit) {
        exemptLimitProperty().set(exemptLimit);
    }

    public RadBigDecimal getHalfLife() {
        return halfLifeProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> halfLifeProperty() {
        return halfLife;
    }

    public void setHalfLife(RadBigDecimal halfLife) {
        halfLifeProperty().set(halfLife);
    }

    public RadBigDecimal getIaLimitedLimit() {
        return iaLimitedLimitProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> iaLimitedLimitProperty() {
        return iaLimitedLimit;
    }

    public void setIaLimitedLimit(RadBigDecimal iaLimitedLimit) {
        iaLimitedLimitProperty().set(iaLimitedLimit);
    }

    public RadBigDecimal getIaPackageLimit() {
        return iaPackageLimitProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> iaPackageLimitProperty() {
        return iaPackageLimit;
    }

    public void setIaPackageLimit(RadBigDecimal iaPackageLimit) {
        iaPackageLimitProperty().set(iaPackageLimit);
    }

    public RadBigDecimal getLimitedLimit() {
        return limitedLimitProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> limitedLimitProperty() {
        return limitedLimit;
    }

    public void setLimitedLimit(RadBigDecimal limitedLimit) {
        limitedLimitProperty().set(limitedLimit);
    }

    public RadBigDecimal getCurieReportQuan() {
        return curieReportQuanProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> curieReportQuanProperty() {
        return curieReportQuan;
    }

    public void setCurieReportQuan(RadBigDecimal curieReportQuan) {
        curieReportQuanProperty().set(curieReportQuan);
    }

    public RadBigDecimal getTeraBqReportQuan() {
        return teraBqReportQuanProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> teraBqReportQuanProperty() {
        return teraBqReportQuan;
    }

    public void setTeraBqReportQuan(RadBigDecimal teraBqReportQuan) {
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
