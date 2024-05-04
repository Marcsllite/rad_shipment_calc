package com.marcsllite.dao;

import com.marcsllite.model.db.HalfLifeModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class HalfLifeDaoImpl extends AbstractDao<HalfLifeModel, NuclideModelId> {
    public String getHalfLife(NuclideModelId nuclideId) {
        HalfLifeModel model = findById(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getDecFloatStr();
    }
}
