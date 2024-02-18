package com.marcsllite.dao;

import com.marcsllite.model.db.HalfLifeModel;
import jakarta.persistence.EntityManager;

public class HalfLifeDaoImpl extends AbstractDao<HalfLifeModel, String> {
    public HalfLifeDaoImpl(EntityManager em) {
        super(em);
    }

    public float getHalfLife(String abbr) {
        return findById(abbr).getValue();
    }
}
