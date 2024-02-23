package com.marcsllite.dao;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;

public class LimitsDaoImpl extends AbstractDao<LimitsModel, LimitsModelId> {
    public LimitsModel getAllLimits(LimitsModelId modelId) {
        return findById(modelId);
    }

    public float getIALimited(LimitsModelId modelId) {
        return getAllLimits(modelId).getIa_limited();
    }

    public float getIAPackage(LimitsModelId modelId) {
        return getAllLimits(modelId).getIa_package();
    }

    public float getLimited(LimitsModelId modelId) {
        return getAllLimits(modelId).getLimited();
    }
}
