package com.marcsllite.model;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleFloatProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ReportableQuantity {
    private static final Logger logr = LogManager.getLogger();
    private PropHandler propHandler;
    private float defaultVal;
    private String abbr;
    private final SimpleFloatProperty curie = new SimpleFloatProperty();
    private final SimpleFloatProperty teraBq = new SimpleFloatProperty();

    public ReportableQuantity(String abbr) {
        this(abbr,
            null,
            null);
    }

    public ReportableQuantity(String abbr, Float curie, Float teraBq) {
        try {
            setPropHandler(new PropHandlerFactory().getPropHandler(null));
            setDefaultVal((float) getPropHandler().getDouble("defaultNum"));
        } catch (IOException e) {
            logr.catching(e);
            setDefaultVal(-2.0f);
        }

        setAbbr(abbr);
        setCurie(curie == null? defaultVal : curie);
        setTeraBq(teraBq == null? defaultVal : teraBq);
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

    @Override
    public String toString() {
        return "Reportable Quantity for " + getAbbr() + ": " +
             getTeraBq() + " TBq, " + getCurie() + " Ci}";
    }
}
