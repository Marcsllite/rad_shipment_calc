package com.marcsllite.dao;

import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.model.db.ReportableQuantityModel;
import com.marcsllite.util.RadBigDecimal;

public class ReportableQuantityDaoImpl extends AbstractDao<ReportableQuantityModel, NuclideModelId> {
    public ReportableQuantityModel getReportQuan(NuclideModelId nuclideId) {
        return findById(nuclideId);
    }

    public String getCi(NuclideModelId nuclideId) {
        ReportableQuantityModel model = getReportQuan(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getCurieStr();
    }

    public String getTBq(NuclideModelId nuclideId) {
        ReportableQuantityModel model = getReportQuan(nuclideId);
        return model == null?
            RadBigDecimal.NEG_INFINITY_DISPLAY_STRING : model.getTeraBqStr();
    }
}
