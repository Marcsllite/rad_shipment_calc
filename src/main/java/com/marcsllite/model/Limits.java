package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Limits {
    private static final Logger logr = LogManager.getLogger();
    private PropHandler propHandler;
    private float defaultVal;
    private LimitsModelId limitsId;
    private final SimpleFloatProperty iaLimited = new SimpleFloatProperty();
    private final SimpleFloatProperty iaPackage = new SimpleFloatProperty();
    private final SimpleFloatProperty limited = new SimpleFloatProperty();

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

    public Limits(LimitsModelId limitsId, Float iaLimited, Float iaPackage, Float limited) {
        try {
            setPropHandler(new PropHandlerFactory().getPropHandler(null));
            setDefaultVal((float) getPropHandler().getDouble("defaultNum"));
        } catch (IOException e) {
            logr.catching(e);
            setDefaultVal(-2.0f);
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

    public float getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(float defaultVal) {
        this.defaultVal = defaultVal;
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public float getIaLimited() {
        return iaLimitedProperty().get();
    }

    public SimpleFloatProperty iaLimitedProperty() {
        return iaLimited;
    }

    public void setIaLimited(float iaLimited) {
        iaLimitedProperty().set(iaLimited);
    }

    public float getIaPackage() {
        return iaPackageProperty().get();
    }

    public SimpleFloatProperty iaPackageProperty() {
        return iaPackage;
    }

    public void setIaPackage(float iaPackage) {
        iaPackageProperty().set(iaPackage);
    }

    public float getLimited() {
        return limitedProperty().get();
    }

    public SimpleFloatProperty limitedProperty() {
        return limited;
    }

    public void setLimited(float limited) {
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
