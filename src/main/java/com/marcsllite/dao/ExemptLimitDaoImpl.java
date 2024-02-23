package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptLimitModel;

public class ExemptLimitDaoImpl extends AbstractDao<ExemptLimitModel, String> {
    public float getExemptLimit(String abbr) {
        return findById(abbr).getValue();
    }
}
