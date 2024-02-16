package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;

import java.util.ResourceBundle;

public class Limits {
    private final PropHandler propHandler;
    private final float defaultVal;
    private LimitsModel.State state;
    private LimitsModel.Form form;
    private final SimpleFloatProperty ia_limited = new SimpleFloatProperty();
    private final SimpleFloatProperty ia_package = new SimpleFloatProperty();
    private final SimpleFloatProperty limited = new SimpleFloatProperty();

    public Limits() {
        this(
            (PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory())
        );
    }

    public Limits(PropHandler propHandler) {
        this(propHandler,
            LimitsModel.State.SOLID,
            LimitsModel.Form.NORMAL,
            null,
            null,
            null
        );
    }

    public Limits(PropHandler propHandler, LimitsModel.State state, LimitsModel.Form form, Float ia_limited, Float ia_package, Float limited) {
        this.propHandler = propHandler;
        this.defaultVal = (float) propHandler.getDouble("defaultNum");
        setState(state);
        setForm(form);
        setIa_limited(ia_limited == null? defaultVal : ia_limited);
        setIa_package(ia_package == null? defaultVal : ia_package);
        setLimited(limited == null? defaultVal : limited);
    }

    public float getDefaultVal() {
        return defaultVal;
    }

    public LimitsModel.State getState() {
        return state;
    }

    public void setState(LimitsModel.State state) {
        this.state = state;
    }

    public LimitsModel.Form getForm() {
        return form;
    }

    public void setForm(LimitsModel.Form form) {
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
