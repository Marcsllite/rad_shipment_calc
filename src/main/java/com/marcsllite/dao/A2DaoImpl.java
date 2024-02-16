package com.marcsllite.dao;

import com.marcsllite.model.db.A2Model;

public class A2DaoImpl extends AbstractDao<A2Model, String> {
    public float getA2(String abbr) {
        return findById(abbr).getValue();
    }
}
