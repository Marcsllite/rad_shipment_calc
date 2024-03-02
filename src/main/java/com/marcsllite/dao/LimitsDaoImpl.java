package com.marcsllite.dao;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;

public class LimitsDaoImpl extends AbstractDao<LimitsModel, LimitsModelId> {
    public LimitsModel getLimits(LimitsModelId modelId) {
        return findById(modelId);
    }

    public float getIALimited(LimitsModelId modelId) {
        return getLimits(modelId).getIaLimited();
    }

    public float getIAPackage(LimitsModelId modelId) {
        return getLimits(modelId).getIaPackage();
    }

    public float getLimited(LimitsModelId modelId) {
        return getLimits(modelId).getLimited();
    }
}
