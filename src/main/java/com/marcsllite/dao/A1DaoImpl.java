package com.marcsllite.dao;

import com.marcsllite.model.db.A1Model;

public class A1DaoImpl extends AbstractDao<A1Model, String> {
    public float getA1(String abbr) {
        return findById(abbr).getValue();
    }
}
