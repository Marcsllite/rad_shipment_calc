package com.marcsllite.dao;

import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;

import java.util.List;

public class NuclideDaoImpl extends AbstractDao<NuclideModel, NuclideModelId> {
    public List<NuclideModel> getAllNuclides() {
        return findAll();
    }

    public NuclideModel getNuclide(NuclideModelId nuclideId) {
        return findById(nuclideId);
    }

    public String getNuclideNameNotation(NuclideModelId nuclideId) {
        NuclideModel model = getNuclide(nuclideId);
        return model == null? "" : model.getDisplayNameNotation();
    }

    public String getNuclideAbbrNotation(NuclideModelId nuclideId) {
        NuclideModel model = getNuclide(nuclideId);
        return model == null? "" : model.getDisplaySymbolNotation();
    }
}
