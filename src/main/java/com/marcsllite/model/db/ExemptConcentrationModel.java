package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_CONCENTRATION")
public class ExemptConcentrationModel extends BaseModel {
    private static final long serialVersionUID = 5186004813264658890L;

    @Id
    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;
    @Column(name = "Val")
    private float value;

    public ExemptConcentrationModel() {
        this("Abbr", -2.0f);
    }

    public ExemptConcentrationModel(String abbr, float value) {
        setAbbr(abbr);
        setValue(value);
        setBasePrefix(Conversions.SIPrefix.BASE);
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
