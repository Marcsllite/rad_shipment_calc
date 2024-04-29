package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
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

@Entity(name = "Shipment")
@Table(name = "SHIPMENT")
public class ShipmentModel extends BaseModel {
    private static final long serialVersionUID = 1895641912158690468L;

    public ShipmentModel() {
        super();
        setBasePrefix(Conversions.SIPrefix.BASE);
    }

    @Id
    @Column(name = "Id")
    private long id;

    @Column(name = "Reference_Date")
    private LocalDate refDate;

    @Column(name = "Mass")
    private String mass;

    @Column(name = "Mass_Unit", nullable = false)
    @Convert(converter = MassUnitAttrConverter.class)
    private Conversions.MassUnit massUnit;

    @Column(name = "Nature", nullable = false)
    @Convert(converter = NatureAttrConverter.class)
    private Nuclide.Nature nature;

    @Column(name = "State", nullable = false)
    @Convert(converter = StateAttrConverter.class)
    private LimitsModelId.State state;

    @Column(name = "Form", nullable = false)
    @Convert(converter = FormAttrConverter.class)
    private LimitsModelId.Form form;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "SHIPMENT_NUCLIDE",
        joinColumns = {@JoinColumn(name = "Shipment_Id")},
        inverseJoinColumns = {
            @JoinColumn(name = "Nuclide_Symbol", referencedColumnName = "Symbol"),
            @JoinColumn(name = "Mass_Number", referencedColumnName = "Mass_Number")
        }
    )
    private List<NuclideModel> nuclides = new ArrayList<>();

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

    public RadBigDecimal getMass() {
        return new RadBigDecimal(mass);
    }

    public String getMassStr() {
        return mass;
    }

    public void setMassStr(String mass) {
        this.mass = mass;
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

    public List<NuclideModel> getNuclides() {
        return nuclides;
    }

    public void setNuclides(List<NuclideModel> nuclides) {
        this.nuclides = nuclides == null? new ArrayList<>() : nuclides;
    }
}
