package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "REPORTABLE_QUANTITY")
@NamedQueries(
    @NamedQuery(name = ReportableQuantityModel.CREATE_TABLE,
        query = "create table if not exists " +
            "REPORTABLE_QUANTITY (Abbr char(15) not null, Ci real, TBq real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + ReportableQuantityModel.REPORTABLE_QTY_CSV_PATH + ")")
)
public class ReportableQuantityModel extends BaseModel {
    public static final String CREATE_TABLE = "ReportableQuantityModel.createTable";
    public static final String REPORTABLE_QTY_CSV_PATH = "classpath:csv/Reportable_Quantities.csv";

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
