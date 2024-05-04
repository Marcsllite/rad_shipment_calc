package com.marcsllite.dao;

import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.RadBigDecimal;

public class LimitsDaoImpl extends AbstractDao<LimitsModel, LimitsModelId> {
    public LimitsModel getLimits(LimitsModelId modelId) {
        return findById(modelId);
    }

    public String getIALimited(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getIaLimitedStr();
    }

    public String getIAPackage(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getIaPackageStr();
    }

    public String getLimited(LimitsModelId modelId) {
        LimitsModel model = getLimits(modelId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getLimitedStr();
    }
}
