package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "A_TWO")
@NamedNativeQueries({
    @NamedNativeQuery(name = A2Model.GET_A2,
        query = "select Value from A_TWO where Abbr=:abbr")
})
public class A2Model extends BaseModel {
    private static final long serialVersionUID = -5895460983437367212L;
    public static final String GET_A2 = "A2Model.getA2";

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
