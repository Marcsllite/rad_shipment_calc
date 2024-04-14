package com.marcsllite.dao;

import com.marcsllite.model.db.A2Model;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class A2DaoImpl extends AbstractDao<A2Model, NuclideModelId> {
    public RadBigDecimal getA2(NuclideModelId nuclideId) {
        A2Model model = findById(nuclideId);
        return model == null? RadBigDecimal.NEG_INFINITY_OBJ : model.getValue();
    }
}
