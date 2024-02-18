package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptConcentrationModel;
import jakarta.persistence.EntityManager;

public class ExemptConcentrationDaoImpl extends AbstractDao<ExemptConcentrationModel, String> {
    public ExemptConcentrationDaoImpl(EntityManager em) {
        super(em);
    }

    public float getExemptConcentration(String abbr) {
        return findById(abbr).getValue();
    }
}
