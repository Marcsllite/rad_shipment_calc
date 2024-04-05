package com.marcsllite.model.db;

import com.marcsllite.model.Isotope;
import com.marcsllite.util.Conversions;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Shipments")
@Table(name = "SHIPMENTS")
public class ShipmentsModel extends BaseModel {
    private static final long serialVersionUID = 1895641912158690468L;

    public ShipmentsModel() {
        super();
        setBasePrefix(Conversions.SIPrefix.BASE);
    }

    @Id
    @Column(name = "Id")
    private long id;

    @Column(name = "Reference_Date")
    private LocalDate refDate;

    @Column(name = "MassUnit")
    private float mass;

    @Column(name = "Mass_Unit", nullable = false)
    @Convert(converter = MassUnitAttrConverter.class)
    private Isotope.MassUnit massUnit;

    @Column(name = "Nature", nullable = false)
    @Convert(converter = NatureAttrConverter.class)
    private Isotope.Nature nature;

    @Column(name = "State", nullable = false)
    @Convert(converter = StateAttrConverter.class)
    private LimitsModelId.State state;

    @Column(name = "Form", nullable = false)
    @Convert(converter = FormAttrConverter.class)
    private LimitsModelId.Form form;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "SHIPMENT_ISOTOPES",
        joinColumns = {@JoinColumn(name = "Shipment_Id")},
        inverseJoinColumns = {
            @JoinColumn(name = "Isotope_Name", referencedColumnName = "name"),
            @JoinColumn(name = "Isotope_Abbr", referencedColumnName = "abbr")
        }
    )
    private List<IsotopeModel> isotopes = new ArrayList<>();

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

    public List<IsotopeModel> getIsotopes() {
        return isotopes;
    }

    public void setIsotopes(List<IsotopeModel> isotopes) {
        this.isotopes = isotopes == null? new ArrayList<>() : isotopes;
    }
}
