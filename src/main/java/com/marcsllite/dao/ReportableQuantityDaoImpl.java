package com.marcsllite.dao;

import com.marcsllite.model.db.ReportableQuantityModel;

public class ReportableQuantityDaoImpl extends AbstractDao<ReportableQuantityModel, String> {
    public ReportableQuantityModel getReportQuan(String abbr) {
        return findById(abbr);
    }

    public float getCi(String abbr) {
        ReportableQuantityModel model = getReportQuan(abbr);
        return model == null? -123456789.0f : model.getCurie();
    }

    public float getTBq(String abbr) {
        ReportableQuantityModel model = getReportQuan(abbr);
        return model == null? -123456789.0f : model.getTeraBq();
    }
}
