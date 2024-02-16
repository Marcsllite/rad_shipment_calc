package com.marcsllite.dao;

import com.marcsllite.model.db.DecayConstantModel;

public class DecayConstantDaoImpl extends AbstractDao<DecayConstantModel, String> {
    public float getDecayConstant(String abbr) {
        return findById(abbr).getValue();
    }
}
