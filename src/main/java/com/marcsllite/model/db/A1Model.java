package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "A_ONE")
@NamedQueries({
    @NamedQuery(name = A1Model.CREATE_TABLE,
        query = "create table if not exists " +
            "A_ONE (Abbr char(15) not null, Value real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + A1Model.A1_CSV_PATH + ")"),
    @NamedQuery(name = A1Model.GET_A1,
        query = "select Value from A_ONE where Abbr=:abbr")
})
public class A1Model extends BaseModel {
    public static final String CREATE_TABLE = "A1Model.createTable";
    public static final String A1_CSV_PATH = "classpath:csv/A1(TBq).csv";
    public static final String GET_A1 = "A1Model.getA1";

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
