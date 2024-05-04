package com.marcsllite.dao;

import com.marcsllite.model.db.DecayConstantModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class DecayConstantDaoImpl extends AbstractDao<DecayConstantModel, NuclideModelId> {
    public String getDecayConstant(NuclideModelId nuclideId) {
        DecayConstantModel model= findById(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getDecFloatStr();
    }
}
