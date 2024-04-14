package com.marcsllite.dao;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.RadBigDecimal;

public class LimitsDaoImpl extends AbstractDao<LimitsModel, LimitsModelId> {
    public LimitsModel getLimits(LimitsModelId modelId) {
        return findById(modelId);
    }

    public RadBigDecimal getIALimited(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null? RadBigDecimal.NEG_INFINITY_OBJ : model.getIaLimited();
    }

    public RadBigDecimal getIAPackage(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null? RadBigDecimal.NEG_INFINITY_OBJ : model.getIaPackage();
    }

    public RadBigDecimal getLimited(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null? RadBigDecimal.NEG_INFINITY_OBJ : model.getLimited();
    }
}
