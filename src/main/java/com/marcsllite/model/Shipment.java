package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ShipmentsModel;
import com.marcsllite.util.Conversions;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shipment {
    private long id;
    private SimpleObjectProperty<LocalDate> refDate;
    private SimpleFloatProperty mass;
    private Conversions.SIPrefix massPrefix;
    private Isotope.MassUnit massUnit;
    private Isotope.Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private SimpleListProperty<Isotope> isotopes;

    public Shipment() {
        this(-1L,
            null,
            -1F,
            null,
            null,
            null,
            null);
    }

    public Shipment(ShipmentsModel model) {
        this(model.getId(),
            model.getRefDate(),
            model.getMass(),
            model.getMassUnit(),
            model.getNature(),
            model.getState(),
            model.getForm());
        setIsotopes(model.getIsotopes()
                .stream()
                .map(Isotope::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    public Shipment(long id, LocalDate refDate, float mass, Isotope.MassUnit massUnit, Isotope.Nature nature, LimitsModelId.State state, LimitsModelId.Form form) {
        setId(id);
        setRefDate(refDate);
        setMass(mass);
        setMassPrefix(Conversions.SIPrefix.BASE);
        setMassUnit(massUnit);
        setNature(nature);
        setState(state);
        setForm(form);
        setIsotopes(FXCollections.observableArrayList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getRefDate() {
        return refDateProperty() == null? LocalDate.now() : refDateProperty().get();
    }

    public SimpleObjectProperty<LocalDate> refDateProperty() {
        return refDate;
    }

    public void setRefDate(LocalDate refDate) {
        LocalDate val = refDate == null? LocalDate.now() : refDate;

        if(refDateProperty() == null) {
            this.refDate = new SimpleObjectProperty<>(val);
        } else {
            refDateProperty().set(val);
        }
    }

    public float getMass() {
        return massProperty() == null? -1F : massProperty().get();
    }

    public void setMass(float mass) {
        if(massProperty() == null) {
            this.mass = new SimpleFloatProperty(mass);
        } else {
            massProperty().set(mass);
        }
    }

    public SimpleFloatProperty massProperty() {
        return mass;
    }

    public Conversions.SIPrefix getMassPrefix() {
        return massPrefix;
    }

    public void setMassPrefix(Conversions.SIPrefix massPrefix) {
        this.massPrefix = massPrefix;
    }

    public Isotope.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(Isotope.MassUnit massUnit) {
        this.massUnit = massUnit == null? Isotope.MassUnit.GRAMS : massUnit;
    }

    public Isotope.Nature getNature() {
        return nature;
    }

    public void setNature(Isotope.Nature nature) {
        this.nature = nature == null? Isotope.Nature.REGULAR: nature;
    }

    public LimitsModelId.State getState() {
        return state;
    }

    public void setState(LimitsModelId.State state) {
        this.state = state == null? LimitsModelId.State.SOLID : state;
    }

    public LimitsModelId.Form getForm() {
        return form;
    }

    public void setForm(LimitsModelId.Form form) {
        this.form = form == null? LimitsModelId.Form.NORMAL : form;
    }

    public ObservableList<Isotope> getIsotopes() {
        return isotopesProperty() == null? FXCollections.observableArrayList() : isotopesProperty().get();
    }

    public SimpleListProperty<Isotope> isotopesProperty() {
        return isotopes;
    }

    public void setIsotopes(ObservableList<Isotope> isotopes) {
        if(isotopesProperty() == null) {
            this.isotopes = new SimpleListProperty<>(isotopes == null? FXCollections.observableArrayList() : isotopes);
        } else {
            isotopesProperty().addAll(isotopes == null? FXCollections.observableArrayList() : isotopes);
        }
    }

    public void addAll(List<Isotope> list) {
        getIsotopes().addAll(list);
    }

    public void add(Isotope isotope) {
        getIsotopes().add(isotope);
    }

    public void remove(List<Isotope> isotopes) {
        getIsotopes().removeAll(isotopes);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Shipment temp = (Shipment) obj;
        return Objects.equals(this.getId(), temp.getId()) &&
            Objects.equals(this.getRefDate(), temp.getRefDate()) &&
            Objects.equals(this.getMass(), temp.getMass()) &&
            Objects.equals(this.getMassUnit(), temp.getMassUnit()) &&
            Objects.equals(this.getNature(), temp.getNature()) &&
            Objects.equals(this.getState(), temp.getState()) &&
            Objects.equals(this.getForm(), temp.getForm()) &&
            Objects.equals(this.getIsotopes(), temp.getIsotopes());
    }

    @Override
    public int hashCode() {
        int hash = 57;
        hash = 7 * hash + (int) this.getId();
        hash = 7 * hash + (this.getRefDate() != null ? this.getRefDate().hashCode() : 0);
        hash = 7 * hash + (int) this.getMass();
        hash = 7 * hash + (this.getMassUnit() != null ? this.getMassUnit().hashCode() : 0);
        hash = 7 * hash + (this.getNature() != null ? this.getNature().hashCode() : 0);
        hash = 7 * hash + (this.getState() != null ? this.getState().hashCode() : 0);
        hash = 7 * hash + (this.getForm() != null ? this.getForm().hashCode() : 0);
        hash = 7 * hash + (this.getIsotopes() != null? this.getIsotopes().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Shipment: { Id: " + getId() +
            "\nReference Date: " + getRefDate() +
            "\nMass: " + getMass() + " " + getMassPrefix() + getMassUnit() +
            "\nNature: " + getNature() +
            "\nState: " + getState() +
            "\nForm: " + getForm() +
            "\nIsotopes: " + getIsotopes() + "\n}";
    }
}
