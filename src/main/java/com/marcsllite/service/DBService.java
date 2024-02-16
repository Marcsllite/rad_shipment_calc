package com.marcsllite.service;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.Limits;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;

import java.util.List;

public interface DBService {
    float getA1(String abbr);

    float getA2(String abbr);

    float getDecayConstant(String abbr);

    float getExemptConcentration(String abbr);

    float getExemptLimit(String abbr);

    float getHalfLife(String abbr);

    List<Isotope> getMatchingIsotopes(String substr);

    Isotope getIsotope(IsotopeModelId modelId);

    String getIsotopeName(IsotopeModelId modelId);

    String getIsotopeAbbr(IsotopeModelId modelId);

    Limits getAllLimits(LimitsModelId modelId);

    float getIALimited(LimitsModelId modelId);

    float getIAPackage(LimitsModelId modelId);

    float getLimited(LimitsModelId modelId);

    ReportableQuantity getReportQuan(String abbr);

    float getCi(String abbr);

    float getTBq(String abbr);
}
