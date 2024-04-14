package com.marcsllite.model;

import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ReportableQuantity {
    private static final Logger logr = LogManager.getLogger();
    private PropHandler propHandler;
    private RadBigDecimal defaultVal;
    private NuclideModelId nuclideId;
    private final SimpleObjectProperty<RadBigDecimal> curie = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RadBigDecimal> teraBq = new SimpleObjectProperty<>();

    public ReportableQuantity(NuclideModelId nuclideId) {
        this(nuclideId,
            null,
            null);
    }

    public ReportableQuantity(NuclideModelId nuclideId, RadBigDecimal curie, RadBigDecimal teraBq) {
        try {
            setPropHandler(new PropHandlerFactory().getPropHandler(null));
            setDefaultVal(RadBigDecimal.valueOf(getPropHandler().getDouble("defaultNum")));
        } catch (IOException e) {
            logr.catching(e);
            setDefaultVal(RadBigDecimal.NEG_INFINITY_OBJ);
        }

        setNuclideId(nuclideId);
        setCurie(curie == null? defaultVal : curie);
        setTeraBq(teraBq == null? defaultVal : teraBq);
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

    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId;
    }

    public RadBigDecimal getCurie() {
        return curieProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> curieProperty() {
        return curie;
    }

    public void setCurie(RadBigDecimal curie) {
        curieProperty().set(curie);
    }

    public RadBigDecimal getTeraBq() {
        return teraBqProperty().get();
    }

    public SimpleObjectProperty<RadBigDecimal> teraBqProperty() {
        return teraBq;
    }

    public void setTeraBq(RadBigDecimal teraBq) {
        teraBqProperty().set(teraBq);
    }

    @Override
    public String toString() {
        return "Reportable Quantity for " + getNuclideId() + ": " +
             getTeraBq() + " TBq, " + getCurie() + " Ci}";
    }
}
