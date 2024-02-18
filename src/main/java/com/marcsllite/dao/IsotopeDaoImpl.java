package com.marcsllite.dao;

import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import jakarta.persistence.EntityManager;

import java.util.List;

@SuppressWarnings("unchecked")
public class IsotopeDaoImpl extends AbstractDao<IsotopeModel, IsotopeModelId> {
    public IsotopeDaoImpl(EntityManager em) {
        super(em);
    }

    public List<IsotopeModel> getMatchingIsotopes(String substr) {
        return (List<IsotopeModel>) getEntityManager()
            .createNamedQuery(IsotopeModel.GET_MATCHING_ISOTOPES)
            .setParameter("substr", substr)
            .getResultList();
    }

    public IsotopeModel getIsotope(IsotopeModelId isoId) {
        return findById(isoId);
    }

    public String getIsotopeName(IsotopeModelId isoId) {
        return getIsotope(isoId).getIsotopeId().getName();
    }

    public String getIsotopeAbbr(IsotopeModelId isoId) {
        return getIsotope(isoId).getIsotopeId().getAbbr();
    }
}
