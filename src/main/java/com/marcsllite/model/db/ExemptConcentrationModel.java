package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_CONCENTRATION")
@NamedNativeQueries({
    @NamedNativeQuery(name = ExemptConcentrationModel.GET_EXEMPT_CON,
        query = "select Value from EXEMPT_CONCENTRATION where Abbr=:abbr")
})
public class ExemptConcentrationModel extends BaseModel {
    private static final long serialVersionUID = 5186004813264658890L;
    public static final String GET_EXEMPT_CON = "ExemptConcentrationModel.getExemptConcentration";

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
