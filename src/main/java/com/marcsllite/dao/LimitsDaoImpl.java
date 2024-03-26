package com.marcsllite.dao;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;

public class LimitsDaoImpl extends AbstractDao<LimitsModel, LimitsModelId> {
    public LimitsModel getLimits(LimitsModelId modelId) {
        return findById(modelId);
    }

    public float getIALimited(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null? -123456789.0f : model.getIaLimited();
    }

    public float getIAPackage(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null? -123456789.0f : model.getIaPackage();
    }

    public float getLimited(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null? -123456789.0f : model.getLimited();
    }
}
