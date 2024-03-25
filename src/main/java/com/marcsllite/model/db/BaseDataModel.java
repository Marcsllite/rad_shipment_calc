package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseDataModel extends BaseModel {
    private static final long serialVersionUID = 6194462219788554210L;

    @Id
    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;
    @Column(name = "Val")
    private float value;

    BaseDataModel() {
        this("Abbr", -2.0f);
    }

    BaseDataModel(String abbr, float value) {
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
