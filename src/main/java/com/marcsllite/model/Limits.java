package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Limits {
    private static final Logger logr = LogManager.getLogger();
    private PropHandler propHandler;
    private RadBigDecimal defaultVal;
    private LimitsModelId limitsId;
    private final SimpleObjectProperty<RadBigDecimal> iaLimited = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> iaPackage = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> limited = new SimpleObjectProperty<>();

    public Limits() {
        this(new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL));
    }

    public Limits(LimitsModelId limitsId) {
        this(limitsId,
            null,
            null,
            null
        );
    }

    public Limits(LimitsModelId limitsId, RadBigDecimal iaLimited, RadBigDecimal iaPackage, RadBigDecimal limited) {
        try {
            setPropHandler(new PropHandlerFactory().getPropHandler(null));
            setDefaultVal(RadBigDecimal.valueOf(getPropHandler().getDouble("defaultNum")));
        } catch (IOException e) {
            logr.catching(e);
            setDefaultVal(RadBigDecimal.NEG_INFINITY_OBJ);
        }
        setLimitsId(limitsId);
        setIaLimited(iaLimited == null? defaultVal : iaLimited);
        setIaPackage(iaPackage == null? defaultVal : iaPackage);
        setLimited(limited == null? defaultVal : limited);
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public RadBigDecimal getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(RadBigDecimal defaultVal) {
        this.defaultVal = defaultVal;
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public RadBigDecimal getIaLimited() {
        return iaLimitedProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> iaLimitedProperty() {
        return iaLimited;
    }

    public void setIaLimited(RadBigDecimal iaLimited) {
        iaLimitedProperty().set(iaLimited);
    }

    public RadBigDecimal getIaPackage() {
        return iaPackageProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> iaPackageProperty() {
        return iaPackage;
    }

    public void setIaPackage(RadBigDecimal iaPackage) {
        iaPackageProperty().set(iaPackage);
    }

    public RadBigDecimal getLimited() {
        return limitedProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> limitedProperty() {
        return limited;
    }

    public void setLimited(RadBigDecimal limited) {
        limitedProperty().set(limited);
    }

    @Override
    public String toString() {
        return "Limits for " + getLimitsId() +
            ": {\n\tInstruments/Articles Limited Limit: " + getIaLimited() +
            "\n\tInstruments/Articles Package Limit: " + getIaPackage() +
            "\n\tNormal Limited Limit: " + getLimited() + "TBq\n}";
    }
}
