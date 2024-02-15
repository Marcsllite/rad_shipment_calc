package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "DECAY_CONSTANT")
@NamedNativeQueries({
    @NamedNativeQuery(name = DecayConstantModel.GET_DECAY_CONST,
        query = "select Value from DECAY_CONSTANT where Abbr=:abbr")
})
public class DecayConstantModel extends BaseModel {
    private static final long serialVersionUID = 5704920375115196514L;
    public static final String GET_DECAY_CONST = "DecayConstantModel.getDecayConstant";

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
