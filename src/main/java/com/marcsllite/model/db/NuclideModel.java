package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Nuclide")
@Table(name = "NUCLIDE")
public class NuclideModel extends BaseModel {
    private static final long serialVersionUID = -6610061947707574223L;

    @Column(name = "Atomic_Number", nullable = false)
    private int atomicNumber;
    @EmbeddedId
    private NuclideModelId nuclideId;
    @Column(name = "Name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "nuclides")
    private List<ShipmentModel> shipments;

    public NuclideModel() {
        this(1, "XX", new NuclideModelId("XX", "1"));
    }

    public NuclideModel(int atomicNumber, String name, NuclideModelId nuclideId) {
        super(null);
        this.atomicNumber = atomicNumber;
        this.name = name;
        this.nuclideId = nuclideId;
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

    public String getNameNotation() {
        return getName() + "-" + getNuclideId().getMassNumber();
    }

    public String getSymbolNotation() {
        return getNuclideId().getSymbolNotation();
    }
}
