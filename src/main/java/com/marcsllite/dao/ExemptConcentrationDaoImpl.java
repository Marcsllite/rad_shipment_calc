package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptConcentrationModel;

public class ExemptConcentrationDaoImpl extends AbstractDao<ExemptConcentrationModel, String> {
    public float getExemptConcentration(String abbr) {
        ExemptConcentrationModel model = findById(abbr);
        return model == null? -123456789.0f: model.getValue();
    }
}
