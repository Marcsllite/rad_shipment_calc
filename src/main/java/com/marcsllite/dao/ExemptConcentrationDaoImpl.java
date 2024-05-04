package com.marcsllite.dao;

import com.marcsllite.model.db.ExemptConcentrationModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;

public class ExemptConcentrationDaoImpl extends AbstractDao<ExemptConcentrationModel, NuclideModelId> {
    public String getExemptConcentration(NuclideModelId nuclideId) {
        ExemptConcentrationModel model = findById(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getDecFloatStr();
    }
}
