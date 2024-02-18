package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HALF_LIFE")
public class HalfLifeModel extends BaseModel {
    private static final long serialVersionUID = -3082962071180959635L;

    @Id
    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;
    @Column(name = "Val")
    private float value;

    public HalfLifeModel() {
        this("Abbr", -2.0f);
    }

    public HalfLifeModel(String abbr, float value) {
        setAbbr(abbr);
        setValue(value);
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
