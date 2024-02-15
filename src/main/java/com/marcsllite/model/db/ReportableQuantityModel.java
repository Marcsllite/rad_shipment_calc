package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "REPORTABLE_QUANTITY")
public class ReportableQuantityModel extends BaseModel {
    private static final long serialVersionUID = 1479886818838786038L;

    @Id
    @Column(name = "Abbr")
    private String abbr;
    @Column(name = "Ci")
    private float curie;
    @Column(name = "TBq")
    private float teraBq;


    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public float getCurie() {
        return curie;
    }

    public void setCurie(float curie) {
        this.curie = curie;
    }

    public float getTeraBq() {
        return teraBq;
    }

    public void setTeraBq(float teraBq) {
        this.teraBq = teraBq;
    }
}
