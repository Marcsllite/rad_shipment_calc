package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "A_TWO")
@NamedQueries({
    @NamedQuery(name = A2Model.CREATE_TABLE,
        query = "create table if not exists " +
            "A_TWO (Abbr char(15) not null, Value real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + A2Model.A2_CSV_PATH + ")"),
    @NamedQuery(name = A2Model.GET_A2,
        query = "select Value from A_TWO where Abbr=:abbr")
})
public class A2Model extends BaseModel {
    private static final long serialVersionUID = -5895460983437367212L;
    public static final String CREATE_TABLE = "A2Model.createTable";
    public static final String A2_CSV_PATH = "classpath:csv/A2(TBq).csv";
    public static final String GET_A2 = "A2Model.getA2";

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
