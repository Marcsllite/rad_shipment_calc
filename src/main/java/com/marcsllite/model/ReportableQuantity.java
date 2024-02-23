package com.marcsllite.model;

import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;

public class ReportableQuantity {
    private final PropHandler propHandler;
    private final float defaultVal;
    private String abbr;
    private final SimpleFloatProperty curie = new SimpleFloatProperty();
    private final SimpleFloatProperty teraBq = new SimpleFloatProperty();

    public ReportableQuantity(String abbr) {
        this(abbr,
            null,
            null);
    }

    public ReportableQuantity(String abbr, Float curie, Float teraBq) {
        this.propHandler = new PropHandler();
        this.defaultVal = (float) getPropHandler().getDouble("defaultNum");

        setAbbr(abbr);
        setCurie(curie == null? defaultVal : curie);
        setTeraBq(teraBq == null? defaultVal : teraBq);
    }

    public PropHandler getPropHandler() {
        return propHandler;
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
