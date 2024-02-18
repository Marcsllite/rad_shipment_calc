package com.marcsllite.dao;

import com.marcsllite.model.db.A1Model;
import jakarta.persistence.EntityManager;

public class A1DaoImpl extends AbstractDao<A1Model, String> {
    public A1DaoImpl(EntityManager em) {
        super(em);
    }

    public float getA1(String abbr) {
        return findById(abbr).getValue();
    }
}
