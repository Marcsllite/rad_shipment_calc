package com.marcsllite.dao;

import com.marcsllite.model.db.DecayConstantModel;
import jakarta.persistence.EntityManager;

public class DecayConstantDaoImpl extends AbstractDao<DecayConstantModel, String> {
    public DecayConstantDaoImpl(EntityManager em) {
        super(em);
    }

    public float getDecayConstant(String abbr) {
        return findById(abbr).getValue();
    }
}
