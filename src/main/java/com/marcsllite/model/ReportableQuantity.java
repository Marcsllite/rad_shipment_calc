package com.marcsllite.model;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;

import java.util.ResourceBundle;

public class ReportableQuantity {
    private final float defaultVal;
    private String abbr;
    private final SimpleFloatProperty curie = new SimpleFloatProperty();
    private final SimpleFloatProperty teraBq = new SimpleFloatProperty();

    public ReportableQuantity(String abbr) {
        this(
            (PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()),
            abbr
        );
    }

    public ReportableQuantity(PropHandler propHandler, String abbr) {
        this(propHandler,
            abbr,
            null,
            null);
    }

    public ReportableQuantity(PropHandler propHandler, String abbr, Float curie, Float teraBq) {
        this.defaultVal = (float) propHandler.getDouble("defaultNum");

        setAbbr(abbr);
        setCurie(curie == null? defaultVal : curie);
        setTeraBq(teraBq == null? defaultVal : teraBq);
    }

    public float getDefaultVal() {
        return defaultVal;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public float getCurie() {
        return curieProperty().get();
    }

    public SimpleFloatProperty curieProperty() {
        return curie;
    }

    public void setCurie(float curie) {
        curieProperty().set(curie);
    }

    public float getTeraBq() {
        return teraBqProperty().get();
    }

    public SimpleFloatProperty teraBqProperty() {
        return teraBq;
    }

    public void setTeraBq(float teraBq) {
        teraBqProperty().set(teraBq);
    }
}
