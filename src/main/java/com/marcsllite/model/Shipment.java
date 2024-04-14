package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ShipmentModel;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shipment {
    private long id;
    private SimpleObjectProperty<LocalDate> refDate;
    private SimpleObjectProperty<RadBigDecimal> mass;
    private Conversions.SIPrefix massPrefix;
    private Conversions.MassUnit massUnit;
    private Nuclide.Nature nature;
    private LimitsModelId.State state;
    private LimitsModelId.Form form;
    private SimpleListProperty<Nuclide> nuclides;

    public Shipment() {
        this(-1L,
            null,
            RadBigDecimal.NEG_INFINITY_OBJ,
            null,
            null,
            null,
            null);
    }

    public Shipment(ShipmentModel model) {
        this(model.getId(),
            model.getRefDate(),
            model.getMass(),
            model.getMassUnit(),
            model.getNature(),
            model.getState(),
            model.getForm());
        setNuclides(model.getNuclides()
                .stream()
                .map(Nuclide::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    public Shipment(long id, LocalDate refDate, RadBigDecimal mass, Conversions.MassUnit massUnit, Nuclide.Nature nature, LimitsModelId.State state, LimitsModelId.Form form) {
        setId(id);
        setRefDate(refDate);
        setMass(mass);
        setMassPrefix(Conversions.SIPrefix.BASE);
        setMassUnit(massUnit);
        setNature(nature);
        setState(state);
        setForm(form);
        setNuclides(FXCollections.observableArrayList());
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

    public RadBigDecimal getMass() {
        return massProperty() == null? RadBigDecimal.NEG_INFINITY_OBJ : massProperty().get();
    }

    public void setMass(RadBigDecimal mass) {
        if(massProperty() == null) {
            this.mass = new SimpleObjectProperty<>(mass);
        } else {
            massProperty().set(mass);
        }
    }

    public SimpleObjectProperty<RadBigDecimal> massProperty() {
        return mass;
    }

    public Conversions.SIPrefix getMassPrefix() {
        return massPrefix;
    }

    public void setMassPrefix(Conversions.SIPrefix massPrefix) {
        this.massPrefix = massPrefix;
    }

    public Conversions.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(Conversions.MassUnit massUnit) {
        this.massUnit = massUnit == null? Conversions.MassUnit.GRAMS : massUnit;
    }

    public Nuclide.Nature getNature() {
        return nature;
    }

    public void setNature(Nuclide.Nature nature) {
        this.nature = nature == null? Nuclide.Nature.REGULAR: nature;
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

    public ObservableList<Nuclide> getNuclides() {
        return nuclidesProperty() == null? FXCollections.observableArrayList() : nuclidesProperty().get();
    }

    public SimpleListProperty<Nuclide> nuclidesProperty() {
        return nuclides;
    }

    public void setNuclides(ObservableList<Nuclide> nuclides) {
        if(nuclidesProperty() == null) {
            this.nuclides = new SimpleListProperty<>(nuclides == null? FXCollections.observableArrayList() : nuclides);
        } else {
            nuclidesProperty().addAll(nuclides == null? FXCollections.observableArrayList() : nuclides);
        }
    }

    public void addAll(List<Nuclide> list) {
        getNuclides().addAll(list);
    }

    public void add(Nuclide isotope) {
        getNuclides().add(isotope);
    }

    public void remove(List<Nuclide> isotopes) {
        getNuclides().removeAll(isotopes);
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
            Objects.equals(this.getNuclides(), temp.getNuclides());
    }

    @Override
    public int hashCode() {
        int hash = 57;
        hash = 7 * hash + (int) this.getId();
        hash = 7 * hash + (this.getRefDate() != null ? this.getRefDate().hashCode() : 0);
        hash = 7 * hash + this.getMass().intValue();
        hash = 7 * hash + (this.getMassUnit() != null ? this.getMassUnit().hashCode() : 0);
        hash = 7 * hash + (this.getNature() != null ? this.getNature().hashCode() : 0);
        hash = 7 * hash + (this.getState() != null ? this.getState().hashCode() : 0);
        hash = 7 * hash + (this.getForm() != null ? this.getForm().hashCode() : 0);
        hash = 7 * hash + (this.getNuclides() != null? this.getNuclides().hashCode() : 0);
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
            "\nNuclides: " + getNuclides() + "\n}";
    }

    public enum Label {
        // Source: https://remm.hhs.gov/NRC_for-educators_11.pdf
        WHITE_1("White 1",
            "Surface: Does not exceed 0.5 millirem/hour, TI: N/A"),
        YELLOW_1("Yellow 1",
            "Surface: Does not exceed 50 millirems/hour, TI: Does not exceed 1 millirem/hour"),
        YELLOW_2("Yellow 2",
            "Surface: Exceeds 50 millirems/hour, TI: Exceeds 1 millirem/hour");

        private final String val;
        private final String info;

        Label(String val, String info) {
            this.val = val;
            this.info = info;
        }

        public String getVal() {
            return val;
        }

        public String getInfo() { return info; }

        public static Shipment.Label toIsoClass(String value) {
            for (Shipment.Label enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(Shipment.Label.values())
                .map(Shipment.Label::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }
}
