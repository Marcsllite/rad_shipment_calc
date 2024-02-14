package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "DECAY_CONSTANT")
@NamedQueries({
    @NamedQuery(name = DecayConstantModel.CREATE_TABLE,
        query = "create table if not exists " +
            "DECAY_CONSTANT (Abbr char(15) not null, Value real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + DecayConstantModel.DECAY_CONST_CSV_PATH + ")"),
    @NamedQuery(name = DecayConstantModel.GET_DECAY_CONST,
        query = "select Value from DECAY_CONSTANT where Abbr=:abbr")
})
public class DecayConstantModel extends BaseModel {
    public static final String CREATE_TABLE = "DecayConstantModel.createTable";
    public static final String DECAY_CONST_CSV_PATH = "classpath:csv/Decay_Constant.csv";
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
