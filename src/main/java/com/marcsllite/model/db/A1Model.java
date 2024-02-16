package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "A_ONE")
public class A1Model extends BaseModel {
    private static final long serialVersionUID = 1600283366978070629L;

    @Id
    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;
    @Column(name = "Val")
    private float value;

    public A1Model(String abbr, float value) {
        this.abbr = abbr;
        this.value = value;
        setBasePrefix(Conversions.SIPrefix.TERA);
    }

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
