package com.marcsllite.dao;

import com.marcsllite.model.db.HalfLifeModel;

public class HalfLifeDaoImpl extends AbstractDao<HalfLifeModel, String> {
    public float getHalfLife(String abbr) {
        HalfLifeModel model = findById(abbr);
        return model == null? -123456789.0f: model.getValue();
    }
}
