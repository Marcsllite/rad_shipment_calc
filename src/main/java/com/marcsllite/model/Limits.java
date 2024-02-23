package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;

public class Limits {
    private PropHandler propHandler;
    private final float defaultVal;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private final SimpleFloatProperty ia_limited = new SimpleFloatProperty();
    private final SimpleFloatProperty ia_package = new SimpleFloatProperty();
    private final SimpleFloatProperty limited = new SimpleFloatProperty();

    public Limits() {
        this(new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL),
            null,
            null,
            null
        );
    }

    public Limits(LimitsModelId limitsId) {
        this(limitsId,
            null,
            null,
            null
        );
    }

    public Limits(LimitsModelId limitsId, Float ia_limited, Float ia_package, Float limited) {
        this.propHandler = new PropHandler();
        this.defaultVal = (float) getPropHandler().getDouble("defaultNum");
        setState(limitsId.getState());
        setForm(limitsId.getForm());
        setIa_limited(ia_limited == null? defaultVal : ia_limited);
        setIa_package(ia_package == null? defaultVal : ia_package);
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

    public LimitsModelId.State getState() {
        return state;
    }

    public void setState(LimitsModelId.State state) {
        this.state = state;
    }

    public LimitsModelId.Form getForm() {
        return form;
    }

    public void setForm(LimitsModelId.Form form) {
        this.form = form;
    }

    public float getIa_limited() {
        return ia_limitedProperty().get();
    }

    public SimpleFloatProperty ia_limitedProperty() {
        return ia_limited;
    }

    public void setIa_limited(float ia_limited) {
        ia_limitedProperty().set(ia_limited);
    }

    public float getIa_package() {
        return ia_packageProperty().get();
    }

    public SimpleFloatProperty ia_packageProperty() {
        return ia_package;
    }

    public void setIa_package(float ia_package) {
        ia_packageProperty().set(ia_package);
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
}
