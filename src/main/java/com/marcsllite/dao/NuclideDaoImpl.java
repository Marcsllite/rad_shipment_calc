package com.marcsllite.dao;

import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import jakarta.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import java.util.List;

public class NuclideDaoImpl extends AbstractDao<NuclideModel, NuclideModelId> {
    private static final Logger logr = LogManager.getLogger(NuclideDaoImpl.class);

    public List<NuclideModel> getAllNuclides() {
        return findAll();
    }

    public NuclideModel getNuclide(NuclideModelId nuclideId) {
        return findById(nuclideId);
    }

    public String getNuclideSymbol(String name, String massNum) {
        try {
            String result = getEntityManager().createNamedQuery(NuclideModel.FIND_SYMBOL_BY_NAME_AND_MASS_NUM, String.class)
                .setParameter("name", name)
                .setParameter("massNumber", massNum)
                .getSingleResult();
            return StringUtils.isBlank(result) ? "" : result;
        } catch (NoResultException e) {
            logr.debug("No result found for name: {}; massNumber: {}", name, massNum);
            logr.catching(e);
        }
        return "";
    }

    public String getNuclideName(NuclideModelId nuclideId) {
        NuclideModel model = getNuclide(nuclideId);
        return model == null? "" : model.getName();
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
