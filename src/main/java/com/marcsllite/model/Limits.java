package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleStringProperty;

public class Limits {
    private LimitsModelId limitsId;
    private final SimpleStringProperty iaLimitedStr = new SimpleStringProperty();
    private final SimpleStringProperty iaPackageStr = new SimpleStringProperty();
    private final SimpleStringProperty limitedStr = new SimpleStringProperty();

    public Limits() {
        this(new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL),
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING,
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING,
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
    }

    public Limits(LimitsModelId limitsId) {
        this(limitsId,
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING,
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING,
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING
        );
    }

    public Limits(LimitsModelId limitsId, String iaLimitedStr, String iaPackageStr, String limitedStr) {
        this.limitsId = limitsId;
        this.iaLimitedStr.set(iaLimitedStr);
        this.iaPackageStr.set(iaPackageStr);
        this.limitedStr.set(limitedStr);
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public RadBigDecimal getIaLimited() {
        return new RadBigDecimal(getIaLimitedStr());
    }

    public SimpleStringProperty iaLimitedStrProperty() {
        return iaLimitedStr;
    }

    public String getIaLimitedStr() {
        return iaLimitedStrProperty().get();
    }

    public void setIaLimitedStr(String iaLimitedStr) {
        iaLimitedStrProperty().set(iaLimitedStr);
    }

    public RadBigDecimal getIaPackage() {
        return new RadBigDecimal(getIaPackageStr());
    }

    public SimpleStringProperty iaPackageStrProperty() {
        return iaPackageStr;
    }

    public String getIaPackageStr() {
        return iaPackageStrProperty().get();
    }

    public void setIaPackageStr(String iaPackageStr) {
        iaPackageStrProperty().set(iaPackageStr);
    }

    public RadBigDecimal getLimited() {
        return new RadBigDecimal(getLimitedStr());
    }

    public SimpleStringProperty limitedStrProperty() {
        return limitedStr;
    }

    public String getLimitedStr() {
        return limitedStrProperty().get();
    }

    public void setLimitedStr(String limitedStr) {
        limitedStrProperty().set(limitedStr);
    }

    @Override
    public String toString() {
        return "Limits for " + getLimitsId() +
            ": {\n\tInstruments/Articles Limited Limit: " + getIaLimitedStr() +
            "\n\tInstruments/Articles Package Limit: " + getIaPackageStr() +
            "\n\tNormal Limited Limit: " + getLimitedStr() + "TBq\n}";
    }
}
