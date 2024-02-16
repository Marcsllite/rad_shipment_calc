package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Id
    @Column(name = "Name", nullable = false)
    private String name;
    @Id
    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;

    public IsotopeModel(String name, String abbr) {
        this.abbr = abbr;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

}
