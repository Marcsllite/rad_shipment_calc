package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptLimitModel;

public class ExemptLimitDaoImpl extends AbstractDao<ExemptLimitModel, String> {
    public float getExemptLimit(String abbr) {
        ExemptLimitModel model = findById(abbr);
        return model == null? -123456789.0f: model.getValue();
    }
}
