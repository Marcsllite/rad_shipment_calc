package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "HALF_LIFE")
@NamedQueries(
    @NamedQuery(name = HalfLifeModel.CREATE_TABLE,
        query = "create table if not exists " +
            "HALF_LIFE (Abbr char(15) not null, Value real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + HalfLifeModel.HALF_LIFE_CSV_PATH + ")")
)
public class HalfLifeModel extends BaseModel {
    public static final String CREATE_TABLE = "HalfLifeModel.createTable";
    public static final String HALF_LIFE_CSV_PATH = "classpath:csv/Half_Life(days).csv";

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
