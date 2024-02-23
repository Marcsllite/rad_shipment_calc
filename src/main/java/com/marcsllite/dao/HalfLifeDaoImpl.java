package com.marcsllite.dao;

import com.marcsllite.model.db.HalfLifeModel;

public class HalfLifeDaoImpl extends AbstractDao<HalfLifeModel, String> {
    public float getHalfLife(String abbr) {
        return findById(abbr).getValue();
    }
}
