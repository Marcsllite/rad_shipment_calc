package com.marcsllite.model.db;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "ISOTOPES")
@NamedNativeQueries({
    @NamedNativeQuery(name = IsotopeModel.GET_MATCHING_ISOTOPES,
        query = "select i from ISOTOPES where Abbr like :substr or Name like :substr")
})
public class IsotopeModel extends BaseModel {
    private static final long serialVersionUID = -4943560854632091343L;
    public static final String GET_MATCHING_ISOTOPES = "IsotopeModel.getMatchingIsotope";

    @EmbeddedId
    private IsotopeModelId isotopeId;

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
}
