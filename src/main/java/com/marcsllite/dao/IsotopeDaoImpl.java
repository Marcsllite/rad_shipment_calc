package com.marcsllite.dao;

import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;

import java.util.List;

public class IsotopeDaoImpl extends AbstractDao<IsotopeModel, IsotopeModelId> {
    public List<IsotopeModel> getAllIsotopes() {
        return findAll();
    }

    public IsotopeModel getIsotope(IsotopeModelId isoId) {
        return findById(isoId);
    }

    public String getIsotopeName(IsotopeModelId isoId) {
        IsotopeModel model = getIsotope(isoId);
        return model == null? "" : model.getIsotopeId().getName();
    }

    public String getIsotopeAbbr(IsotopeModelId isoId) {
        IsotopeModel model = getIsotope(isoId);
        return model == null? "" : model.getIsotopeId().getAbbr();
    }
}
