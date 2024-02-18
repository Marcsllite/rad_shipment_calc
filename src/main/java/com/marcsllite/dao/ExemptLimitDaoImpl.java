package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptLimitModel;
import jakarta.persistence.EntityManager;

public class ExemptLimitDaoImpl extends AbstractDao<ExemptLimitModel, String> {
    public ExemptLimitDaoImpl(EntityManager em) {
        super(em);
    }

    public float getExemptLimit(String abbr) {
        return findById(abbr).getValue();
    }
}
