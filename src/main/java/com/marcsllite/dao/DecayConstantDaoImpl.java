package com.marcsllite.dao;

import com.marcsllite.model.db.DecayConstantModel;

public class DecayConstantDaoImpl extends AbstractDao<DecayConstantModel, String> {
    public float getDecayConstant(String abbr) {
        DecayConstantModel model= findById(abbr);
        return model == null? -123456789.0f: model.getValue();
    }
}
