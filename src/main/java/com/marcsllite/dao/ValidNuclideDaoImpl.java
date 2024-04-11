package com.marcsllite.dao;

import com.marcsllite.model.db.ValidNuclideModelId;
import com.marcsllite.model.db.ValidNuclideModel;

import java.util.List;

public class ValidNuclideDaoImpl extends AbstractDao<ValidNuclideModel, ValidNuclideModelId> {
    public List<ValidNuclideModel> getAllValidIsotopes() {
        return findAll();
    }

    public ValidNuclideModel getValidIsotope(ValidNuclideModelId isoId) {
        return findById(isoId);
    }

    public String getValidIsotopeNameNotation(ValidNuclideModelId isoId) {
        ValidNuclideModel model = getValidIsotope(isoId);
        return model == null? "" : model.getNameIsoNotation();
    }

    public String getValidIsotopeAbbrNotation(ValidNuclideModelId isoId) {
        ValidNuclideModel model = getValidIsotope(isoId);
        return model == null? "" : model.getAbbrIsoNotation();
    }
}
