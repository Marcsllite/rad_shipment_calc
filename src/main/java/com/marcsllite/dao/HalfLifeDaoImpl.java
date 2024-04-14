package com.marcsllite.dao;

import com.marcsllite.model.db.HalfLifeModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class HalfLifeDaoImpl extends AbstractDao<HalfLifeModel, NuclideModelId> {
    public RadBigDecimal getHalfLife(NuclideModelId nuclideId) {
        HalfLifeModel model = findById(nuclideId);
        return model == null? RadBigDecimal.NEG_INFINITY_OBJ : model.getValue();
    }
}
