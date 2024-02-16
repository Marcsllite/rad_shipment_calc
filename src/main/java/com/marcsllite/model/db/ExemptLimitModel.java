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
    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;
    @Column(name = "Val")
    private float value;

    public ExemptLimitModel(String abbr, float value) {
        this.abbr = abbr;
        this.value = value;
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
