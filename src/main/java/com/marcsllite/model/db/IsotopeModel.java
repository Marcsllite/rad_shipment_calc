package com.marcsllite.model.db;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Isotopes")
@Table(name = "ISOTOPES")
@NamedNativeQuery(name = IsotopeModel.GET_MATCHING_ISOTOPES,
    query = "select i from ISOTOPES where Abbr like :str or Name like :str")
public class IsotopeModel extends BaseModel {
    private static final long serialVersionUID = -4943560854632091343L;
    public static final String GET_MATCHING_ISOTOPES = "IsotopeModel.getMatchingIsotope";

    @EmbeddedId
    private IsotopeModelId isotopeId;

    @ManyToMany(mappedBy = "isotopes")
    private List<ShipmentsModel> shipments;

    public IsotopeModel() {
        this(new IsotopeModelId("Abbreviation", "Abbr"));
    }

    public IsotopeModel(IsotopeModelId isotopeId) {
        setIsotopeId(isotopeId);
    }

    public IsotopeModelId getIsotopeId() {
        return isotopeId;
    }

    public void setIsotopeId(IsotopeModelId isotopeId) {
        this.isotopeId = isotopeId;
    }

    public List<ShipmentsModel> getShipments() {
        return shipments;
    }

    public void setShipments(List<ShipmentsModel> shipments) {
        this.shipments = shipments == null? new ArrayList<>() : shipments;
    }
}
