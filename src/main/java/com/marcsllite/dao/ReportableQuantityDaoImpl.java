package com.marcsllite.dao;

import com.marcsllite.model.db.ReportableQuantityModel;

public class ReportableQuantityDaoImpl extends AbstractDao<ReportableQuantityModel, String> {
    public ReportableQuantityModel getReportQuan(String abbr) {
        return findById(abbr);
    }

    public float getCi(String abbr) {
        return getReportQuan(abbr).getCurie();
    }

    public float getTBq(String abbr) {
        return getReportQuan(abbr).getTeraBq();
    }
}
