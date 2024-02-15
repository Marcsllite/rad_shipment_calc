package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "A_ONE")
@NamedNativeQueries({
    @NamedNativeQuery(name = A1Model.GET_A1,
        query = "select Value from A_ONE where Abbr=:abbr")
})
public class A1Model extends BaseModel {
    private static final long serialVersionUID = 1600283366978070629L;
    public static final String GET_A1 = "A1Model.getA1";

    @Id
    @Column(name = "Abbr")
    private String abbr;
    @Column(name = "Value")
    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
