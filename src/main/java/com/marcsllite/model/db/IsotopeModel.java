package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "ISOTOPES")
@NamedQueries(
    @NamedQuery(name = IsotopeModel.CREATE_TABLE,
        query = "create table if not exists " +
            "ISOTOPES (Name varchar(255) not null, Abbr char(15) not null, version bigint default 0, primary key (Name, Abbr)) " +
            "as select * from csvread(" + IsotopeModel.ISOTOPES_CSV_PATH + ")")
)
public class IsotopeModel extends BaseModel {
    private static final long serialVersionUID = -4943560854632091343L;
    public static final String CREATE_TABLE = "IsotopeModel.createTable";
    public static final String ISOTOPES_CSV_PATH = "classpath:csv/ValidIsotopes.csv";

    public enum Nature {
        REGULAR("Regular"),
        INSTRUMENT("Instrument"),
        ARTICLE("Article");

        public final String val;

        Nature(String val) {
            this.val = val;
        }
    }
    @Id
    @Column(name = "Name")
    private String name;
    @Id
    @Column(name = "Abbr")
    private String abbr;
    private Nature nature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }
}
