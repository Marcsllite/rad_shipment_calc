package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptLimitModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class ExemptLimitDaoImpl extends AbstractDao<ExemptLimitModel, NuclideModelId> {
    public String getExemptLimit(NuclideModelId nuclideId) {
        ExemptLimitModel model = findById(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getDecFloatStr();
    }
}
