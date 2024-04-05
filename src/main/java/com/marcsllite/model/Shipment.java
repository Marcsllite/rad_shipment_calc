package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ShipmentsModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shipment {
    private long id;
    private LocalDate refDate;
    private float mass;
    private Isotope.MassUnit massUnit;
    private Isotope.Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private List<Isotope> isotopes;

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
                .collect(Collectors.toList()));
    }

    public Shipment(long id, LocalDate refDate, float mass, Isotope.MassUnit massUnit, Isotope.Nature nature, LimitsModelId.State state, LimitsModelId.Form form) {
        setId(id);
        setRefDate(refDate);
        setMass(mass);
        setMassUnit(massUnit);
        setNature(nature);
        setState(state);
        setForm(form);
        setIsotopes(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getRefDate() {
        return refDate;
    }

    public void setRefDate(LocalDate refDate) {
        this.refDate = refDate == null? LocalDate.now() : refDate;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
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

    public List<Isotope> getIsotopes() {
        return isotopes;
    }

    public void setIsotopes(List<Isotope> isotopes) {
        this.isotopes = isotopes == null? new ArrayList<>() : isotopes;
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
            "\nMass: " + getMass() + " " + getMassUnit() +
            "\nNature: " + getNature() +
            "\nState: " + getState() +
            "\nForm: " + getForm() +
            "\nIsotopes: " + getIsotopes() + "\n}";
    }
}
