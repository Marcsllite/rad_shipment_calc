package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import com.marcsllite.util.NuclideUtils;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Nuclide")
@Table(name = "NUCLIDE")
public class NuclideModel extends BaseModel {
    @Serial
    private static final long serialVersionUID = -6610061947707574223L;

    @Column(name = "Atomic_Number", nullable = false)
    private int atomicNumber;
    @EmbeddedId
    private NuclideModelId nuclideId;
    @Column(name = "Name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "nuclides")
    private List<ShipmentModel> shipments;
    @Transient
    private Nuclide.LifeSpan lifeSpan;
    @Transient
    private Nuclide.LungAbsorption lungAbsorption;

    public NuclideModel() {
        this(1, "XX", new NuclideModelId("XX", "1"));
    }

    public NuclideModel(int atomicNumber, String name, NuclideModelId nuclideId) {
        super(null);
        this.atomicNumber = atomicNumber;
        this.name = name;
        this.nuclideId = nuclideId;
        this.lifeSpan = NuclideUtils.parseLifeSpanFromMassNumber(this.nuclideId == null? "" : this.nuclideId.getMassNumber());
        this.lungAbsorption = NuclideUtils.parseLungAbsFromMassNumber(this.nuclideId == null? "" : this.nuclideId.getMassNumber());
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId;
        lifeSpan = NuclideUtils.parseLifeSpanFromMassNumber(this.nuclideId == null? "" : this.nuclideId.getMassNumber());
        lungAbsorption = NuclideUtils.parseLungAbsFromMassNumber(this.nuclideId == null? "" : this.nuclideId.getMassNumber());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShipmentModel> getShipments() {
        return shipments;
    }

    public void setShipments(List<ShipmentModel> shipments) {
        this.shipments = shipments == null? new ArrayList<>() : shipments;
    }

    public Nuclide.LifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(Nuclide.LifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public Nuclide.LungAbsorption getLungAbsorption() {
        return lungAbsorption;
    }

    public void setLungAbsorption(Nuclide.LungAbsorption lungAbsorption) {
        this.lungAbsorption = lungAbsorption;
    }

    public String getDisplayNameNotation() {
        return getName() + "-" + getNuclideId().getDisplayMassNumber();
    }

    public String getFullNameNotation() {
        return getName() + "-" + getNuclideId().getMassNumber();
    }

    public String getDisplaySymbolNotation() {
        return getNuclideId().getDisplaySymbolNotation();
    }

    public String getFullSymbolNotation() {
        return getNuclideId().getFullSymbolNotation();
    }
}
