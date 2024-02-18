package com.marcsllite.dao;

import com.marcsllite.model.db.A2Model;
import jakarta.persistence.EntityManager;

public class A2DaoImpl extends AbstractDao<A2Model, String> {
    public A2DaoImpl(EntityManager em) {
        super(em);
    }

    public float getA2(String abbr) {
        return findById(abbr).getValue();
    }
}
