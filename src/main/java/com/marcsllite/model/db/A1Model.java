package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "A_ONE")
public class A1Model extends BaseDataModel {
    private static final long serialVersionUID = 1600283366978070629L;

    public A1Model() {
        super();
    }

    public A1Model(String abbr, float value) {
        super(abbr, value);
        setBasePrefix(Conversions.SIPrefix.TERA);
    }
}
