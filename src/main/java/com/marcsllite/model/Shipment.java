package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ShipmentModel;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shipment {
    private long id;
    private final SimpleObjectProperty<LocalDate> refDate;
    private final SimpleStringProperty massStr;
    private Conversions.SIPrefix massPrefix;
    private Conversions.MassUnit massUnit;
    private Nuclide.Nature nature;
    private LimitsModelId limitsId;
    private final SimpleListProperty<Nuclide> nuclides;

    public Shipment() {
        id = -1L;
        refDate = new SimpleObjectProperty<>(LocalDate.now());
        massPrefix = Conversions.SIPrefix.BASE;
        massUnit = Conversions.MassUnit.GRAMS;
        massStr = new SimpleStringProperty(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
        nature = Nuclide.Nature.REGULAR;
        limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);
        nuclides = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public Shipment(ShipmentModel model) {
        this(model.getId(),
            model.getRefDate(),
            model.getMassPrefix(),
            model.getMassUnit(),
            model.getMassStr(),
            model.getNature(),
            model.getLimitsId());

        setNuclides(model.getNuclides()
                .stream()
                .map(Nuclide::new)
                .toList());
    }

    public Shipment(long id, LocalDate refDate,
                    Conversions.SIPrefix massPrefix, Conversions.MassUnit massUnit, String massStr,
                    Nuclide.Nature nature, LimitsModelId limitsId) {
        this();

        setId(id);
        setRefDate(refDate);
        setMassStr(massStr);
        setMassPrefix(massPrefix);
        setMassUnit(massUnit);
        setNature(nature);
        setLimitsId(limitsId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SimpleObjectProperty<LocalDate> refDateProperty() {
        return refDate;
    }

    public LocalDate getRefDate() {
        return refDateProperty().get();
    }

    public void setRefDate(LocalDate refDate) {
        refDateProperty().set(refDate == null? LocalDate.now() : refDate);
    }

    public RadBigDecimal getMass() {
        return new RadBigDecimal(getMassStr());
    }

    public SimpleStringProperty massStrProperty() {
        return massStr;
    }

    public String getMassStr() {
        return massStrProperty().get();
    }

    public void setMassStr(String massStr) {
        massStrProperty().set(massStr);
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
        this.nature = nature == null? Nuclide.Nature.REGULAR : nature;
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public SimpleListProperty<Nuclide> nuclidesProperty() {
        return nuclides;
    }

    public ObservableList<Nuclide> getNuclides() {
        return nuclidesProperty().get();
    }

    public void setNuclides(List<Nuclide> nuclides) {
        if (nuclides == null) {
            nuclidesProperty().clear();
        } else {
            nuclidesProperty().setAll(nuclides);
        }
    }

    public void add(Nuclide isotope) {
        nuclidesProperty().add(isotope);
    }

    public void remove(List<Nuclide> isotopes) {
        nuclidesProperty().removeAll(isotopes);
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
            Objects.equals(this.getLimitsId(), temp.getLimitsId()) &&
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
        hash = 7 * hash + (this.getLimitsId() != null ? this.getLimitsId().hashCode() : 0);
        hash = 7 * hash + (this.getNuclides() != null? this.getNuclides().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Shipment: { Id: " + getId() +
            "\nReference Date: " + getRefDate() +
            "\nMass: " + getMass() + " " + getMassPrefix() + getMassUnit() +
            "\nNature: " + getNature() +
            "\nLimits Id: " + getLimitsId() +
            "\nNuclides: " + getNuclides() + "\n}";
    }

    public enum Label {
        // Source: https://remm.hhs.gov/NRC_for-educators_11.pdf
        WHITE_1("White 1",
            "Surface: Does not exceed 0.5 milli rem/hour, TI: N/A"),
        YELLOW_1("Yellow 1",
            "Surface: Does not exceed 50 milli rems/hour, TI: Does not exceed 1 milli rem/hour"),
        YELLOW_2("Yellow 2",
            "Surface: Exceeds 50 milli rems/hour, TI: Exceeds 1 milli rem/hour");

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

        public static Shipment.Label toLabel(String value) {
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
