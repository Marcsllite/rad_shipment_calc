package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
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
    }

    @Id
    @Column(name = "Id")
    private long id;

    @Column(name = "Reference_Date")
    private LocalDate refDate;

    @Column(name = "Mass_Prefix", nullable = false)
    @Convert(converter = SIPrefixAttrConverter.class)
    private Conversions.SIPrefix massPrefix;

    @Column(name = "Mass_Unit", nullable = false)
    @Convert(converter = MassUnitAttrConverter.class)
    private Conversions.MassUnit massUnit;

    @Column(name = "Mass")
    private String mass;

    @Column(name = "Nature", nullable = false)
    @Convert(converter = NatureAttrConverter.class)
    private Nuclide.Nature nature;

    @EmbeddedId
    private LimitsModelId limitsId;
    
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

    public RadBigDecimal getMass() {
        return new RadBigDecimal(mass);
    }

    public String getMassStr() {
        return mass;
    }

    public void setMassStr(String mass) {
        this.mass = mass;
    }

    public Nuclide.Nature getNature() {
        return nature;
    }

    public void setNature(Nuclide.Nature nature) {
        this.nature = nature == null? Nuclide.Nature.REGULAR: nature;
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public List<NuclideModel> getNuclides() {
        return nuclides;
    }

    public void setNuclides(List<NuclideModel> nuclides) {
        this.nuclides = nuclides == null? new ArrayList<>() : nuclides;
    }
}
