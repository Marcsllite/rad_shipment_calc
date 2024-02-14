package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_LIMIT")
@NamedQueries(
    @NamedQuery(name = ExemptLimitModel.CREATE_TABLE,
        query = "create table if not exists " +
            "EXEMPT_LIMIT (Abbr char(15) not null, Value real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + ExemptLimitModel.EXEMPT_LIM_CSV_PATH + ")")
)
public class ExemptLimitModel extends BaseModel {
    public static final String CREATE_TABLE = "ExemptLimitModel.createTable";
    public static final String EXEMPT_LIM_CSV_PATH = "classpath:csv/Exempt_limit(Bq).csv";

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
