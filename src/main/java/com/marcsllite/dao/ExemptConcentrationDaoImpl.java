package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptConcentrationModel;

public class ExemptConcentrationDaoImpl extends AbstractDao<ExemptConcentrationModel, String> {
    public float getExemptConcentration(String abbr) {
        return findById(abbr).getValue();
    }
}
