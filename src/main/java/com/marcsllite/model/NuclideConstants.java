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
    private static final String DEFAULT_VAL_STR = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;
    private final SimpleStringProperty a1 = new SimpleStringProperty();
    private final SimpleStringProperty a2 = new SimpleStringProperty();
    private final SimpleStringProperty decayConstant = new SimpleStringProperty();
    private final SimpleStringProperty exemptConcentration = new SimpleStringProperty();
    private final SimpleStringProperty exemptLimit = new SimpleStringProperty();
    private final SimpleStringProperty halfLife = new SimpleStringProperty();
    private final SimpleStringProperty displayHalfLife = new SimpleStringProperty();
    private static final String HALF_LIFE_UNIT = " days";
    private final SimpleStringProperty iaLimitedLimit = new SimpleStringProperty();
    private final SimpleStringProperty iaPackageLimit = new SimpleStringProperty();
    private final SimpleStringProperty limitedLimit = new SimpleStringProperty();
    private final SimpleStringProperty curieReportQuan = new SimpleStringProperty();
    private final SimpleStringProperty teraBqReportQuan = new SimpleStringProperty();
    private boolean isInit = false;

    public NuclideConstants() {
        setDbService(new DBServiceImpl());

        setA1Str(NuclideConstants.DEFAULT_VAL_STR);
        setA2Str(NuclideConstants.DEFAULT_VAL_STR);
        setDecayConstantStr(NuclideConstants.DEFAULT_VAL_STR);
        setExemptConcentrationStr(NuclideConstants.DEFAULT_VAL_STR);
        setExemptLimitStr(NuclideConstants.DEFAULT_VAL_STR);
        setHalfLifeStr(NuclideConstants.DEFAULT_VAL_STR);
        setIaLimitedLimitStr(NuclideConstants.DEFAULT_VAL_STR);
        setIaPackageLimitStr(NuclideConstants.DEFAULT_VAL_STR);
        setLimitedLimitStr(NuclideConstants.DEFAULT_VAL_STR);
        setCurieReportQuanStr(NuclideConstants.DEFAULT_VAL_STR);
        setTeraBqReportQuanStr(NuclideConstants.DEFAULT_VAL_STR);
    }

    public void dbInit(NuclideModelId nuclideId, LimitsModelId limitsId) {
        if(!isInit) {
            setA1Str(getDbService().getA1(nuclideId));
            setA2Str(getDbService().getA2(nuclideId));
            setDecayConstantStr(getDbService().getDecayConstant(nuclideId));
            setExemptConcentrationStr(getDbService().getExemptConcentration(nuclideId));
            setExemptLimitStr(getDbService().getExemptLimit(nuclideId));
            setHalfLifeStr(getDbService().getHalfLife(nuclideId));
            setIaLimitedLimitStr(getDbService().getIALimited(limitsId));
            setIaPackageLimitStr(getDbService().getIAPackage(limitsId));
            setLimitedLimitStr(getDbService().getLimited(limitsId));
            setCurieReportQuanStr(getDbService().getCiReportQuan(nuclideId));
            setTeraBqReportQuanStr(getDbService().getTBqReportQuan(nuclideId));
            setInit(true);
        }
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public RadBigDecimal getA1() {
        return new RadBigDecimal(getA1Str());
    }

    public SimpleStringProperty a1Property() {
        return a1;
    }

    public String getA1Str() {
        return a1Property().get();
    }

    public void setA1Str(String a1Str) {
        a1Property().set(a1Str);
    }

    public RadBigDecimal getA2() {
        return new RadBigDecimal(getA2Str());
    }

    public SimpleStringProperty a2Property() {
        return a2;
    }

    public String getA2Str() {
        return a2Property().get();
    }

    public void setA2Str(String a2Str) {
        a2Property().set(a2Str);
    }

    public RadBigDecimal getDecayConstant() {
        return new RadBigDecimal(getDecayConstantStr());
    }

    public SimpleStringProperty decayConstantProperty() {
        return decayConstant;
    }

    public String getDecayConstantStr() {
        return decayConstantProperty().get();
    }

    public void setDecayConstantStr(String decayConstantStr) {
        decayConstantProperty().set(decayConstantStr);
    }

    public RadBigDecimal getExemptConcentration() {
        return new RadBigDecimal(getExemptConcentrationStr());
    }

    public SimpleStringProperty exemptConcentrationProperty() {
        return exemptConcentration;
    }

    public String getExemptConcentrationStr() {
        return exemptConcentrationProperty().get();
    }

    public void setExemptConcentrationStr(String exemptConcentrationStr) {
        exemptConcentrationProperty().set(exemptConcentrationStr);
    }

    public RadBigDecimal getExemptLimit() {
        return new RadBigDecimal(getExemptLimitStr());
    }

    public SimpleStringProperty exemptLimitProperty() {
        return exemptLimit;
    }

    public String getExemptLimitStr() {
        return exemptLimitProperty().get();
    }

    public void setExemptLimitStr(String exemptLimitStr) {
        exemptLimitProperty().set(exemptLimitStr);
    }

    public RadBigDecimal getHalfLife() {
        return new RadBigDecimal(getHalfLifeStr());
    }

    public SimpleStringProperty halfLifeProperty() {
        return halfLife;
    }

    public String getHalfLifeStr() {
        return halfLifeProperty().get();
    }

    public void setHalfLifeStr(String halfLifeStr) {
        halfLifeProperty().set(halfLifeStr);
        setDisplayHalfLife();
    }

    public SimpleStringProperty displayHalfLifeProperty() {
        return displayHalfLife;
    }

    public void setDisplayHalfLife() {
        if(DEFAULT_VAL_STR.equals(getHalfLifeStr())) {
            displayHalfLifeProperty().set(getHalfLifeStr());
        } else {
            displayHalfLifeProperty().set(getHalfLifeStr() + HALF_LIFE_UNIT);
        }
    }

    public RadBigDecimal getIaLimitedLimit() {
        return new RadBigDecimal(getIaLimitedLimitStr());
    }

    public SimpleStringProperty iaLimitedLimitProperty() {
        return iaLimitedLimit;
    }

    public String getIaLimitedLimitStr() {
        return iaLimitedLimitProperty().get();
    }

    public void setIaLimitedLimitStr(String iaLimitedLimitStr) {
        iaLimitedLimitProperty().set(iaLimitedLimitStr);
    }

    public RadBigDecimal getIaPackageLimit() {
        return new RadBigDecimal(getIaPackageLimitStr());
    }

    public SimpleStringProperty iaPackageLimitProperty() {
        return iaPackageLimit;
    }

    public String getIaPackageLimitStr() {
        return iaPackageLimitProperty().get();
    }

    public void setIaPackageLimitStr(String iaPackageLimitStr) {
        iaPackageLimitProperty().set(iaPackageLimitStr);
    }

    public RadBigDecimal getLimitedLimit() {
        return new RadBigDecimal(getLimitedLimitStr());
    }

    public SimpleStringProperty limitedLimitProperty() {
        return limitedLimit;
    }

    public String getLimitedLimitStr() {
        return limitedLimitProperty().get();
    }

    public void setLimitedLimitStr(String limitedLimitStr) {
        limitedLimitProperty().set(limitedLimitStr);
    }

    public RadBigDecimal getCurieReportQuan() {
        return new RadBigDecimal(getCurieReportQuanStr());
    }

    public SimpleStringProperty curieReportQuanProperty() {
        return curieReportQuan;
    }

    public String getCurieReportQuanStr() {
        return curieReportQuanProperty().get();
    }


    public void setCurieReportQuanStr(String curieReportQuanStr) {
        curieReportQuanProperty().set(curieReportQuanStr);
    }

    public RadBigDecimal getTeraBqReportQuan() {
        return new RadBigDecimal(getTeraBqReportQuanStr());
    }

    public SimpleStringProperty teraBqReportQuanProperty() {
        return teraBqReportQuan;
    }

    public String getTeraBqReportQuanStr() {
        return teraBqReportQuanProperty().get();
    }

    public void setTeraBqReportQuanStr(String teraBqReportQuan) {
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
        return "Nuclide Constants: {\n\tA1: " + getA1().toDisplayString() + " TBq" +
            "\n\tA2: " + getA2().toDisplayString() + " TBq" +
            "\n\tDecay Constant: " + getDecayConstant().toDisplayString() +
            "\n\tExempt Concentration: " + getExemptConcentration().toDisplayString() + " Bq/g" +
            "\n\tExempt Limit: " + getExemptLimit().toDisplayString() + " Bq" +
            "\n\tHalfLife: " + displayHalfLifeProperty().toString() +
            "\n\tInstruments/Articles Limited Limit: " + getIaLimitedLimit().toDisplayString() +
            "\n\tInstruments/Articles Package Limit: " + getIaPackageLimit().toDisplayString() +
            "\n\tNormal Limited Limit: " + getLimitedLimit().toDisplayString() +
            "\n\tReportable Quantity: " + getTeraBqReportQuan().toDisplayString() + " TBq\n}";
    }
}
