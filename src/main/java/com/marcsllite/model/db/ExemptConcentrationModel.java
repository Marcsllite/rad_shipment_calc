package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_CONCENTRATION")
@NamedQueries({
    @NamedQuery(name = ExemptConcentrationModel.CREATE_TABLE,
        query = "create table if not exists " +
            "EXEMPT_CONCENTRATION (Abbr char(15) not null, Value real, version bigint default 0, primary key (Abbr)) " +
            "as select * from csvread(" + ExemptConcentrationModel.EXEMPT_CON_CSV_PATH + ")"),
    @NamedQuery(name = ExemptConcentrationModel.GET_EXEMPT_CON,
        query = "select Value from EXEMPT_CONCENTRATION where Abbr=:abbr")
})
public class ExemptConcentrationModel extends BaseModel {
    private static final long serialVersionUID = 5186004813264658890L;
    public static final String CREATE_TABLE = "ExemptConcentrationModel.createTable";
    public static final String EXEMPT_CON_CSV_PATH = "classpath:csv/Exempt_concentration(Bq-g).csv";
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
