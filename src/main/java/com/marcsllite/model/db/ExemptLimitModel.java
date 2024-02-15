package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_LIMIT")
public class ExemptLimitModel extends BaseModel {
    private static final long serialVersionUID = -5142275333078025319L;

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
