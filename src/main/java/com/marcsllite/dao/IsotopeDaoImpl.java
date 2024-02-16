package com.marcsllite.dao;

import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;

import java.util.List;

@SuppressWarnings("unchecked")
public class IsotopeDaoImpl extends AbstractDao<IsotopeModel, IsotopeModelId> {
    public List<IsotopeModel> getMatchingIsotopes(String substr) {
        return (List<IsotopeModel>) getEntityManager()
            .createNamedQuery(IsotopeModel.GET_MATCHING_ISOTOPES)
            .setParameter("substr", substr)
            .getResultList();
    }

    public IsotopeModel getIsotope(IsotopeModelId modelId) {
        return findById(modelId);
    }

    public String getIsotopeName(IsotopeModelId modelId) {
        return getIsotope(modelId).getName();
    }

    public String getIsotopeAbbr(IsotopeModelId modelId) {
        return getIsotope(modelId).getAbbr();
    }
}
