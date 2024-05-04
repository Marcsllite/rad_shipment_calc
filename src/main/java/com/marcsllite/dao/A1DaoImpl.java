package com.marcsllite.dao;

import com.marcsllite.model.db.A1Model;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class A1DaoImpl extends AbstractDao<A1Model, NuclideModelId> {
    public String getA1(NuclideModelId nuclideId) {
        A1Model model = findById(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getDecFloatStr();
    }
}
