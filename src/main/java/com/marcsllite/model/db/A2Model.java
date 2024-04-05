package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "A2")
@Table(name = "A_TWO")
public class A2Model extends BaseDataModel {
    private static final long serialVersionUID = -5895460983437367212L;

    public A2Model() {
        super();
    }

    public A2Model(String abbr, float value) {
        super(abbr, value);
        setBasePrefix(Conversions.SIPrefix.TERA);
    }
}
