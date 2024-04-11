package com.marcsllite.service;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.Limits;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ValidNuclideModelId;
import com.marcsllite.model.db.ValidNuclideModel;
import javafx.collections.ObservableList;

import java.util.List;

public interface DBService {
    int validateDb();

    float getA1(String abbr);

    float getA2(String abbr);

    float getDecayConstant(String abbr);

    float getExemptConcentration(String abbr);

    float getExemptLimit(String abbr);

    float getHalfLife(String abbr);

    int countAllIsotopes();

    ObservableList<IsotopeModel> getAllIsotopeModels();

    ObservableList<Isotope> getAllIsotopes();

    Isotope getIsotope(IsotopeModelId modelId);

    String getIsotopeName(IsotopeModelId modelId);

    String getIsotopeAbbr(IsotopeModelId modelId);

    List<ValidNuclideModel> getAllValidIsotopes();

    ValidNuclideModel getValidIsotope(ValidNuclideModelId isoId);

    String getValidIsotopeNameNotation(ValidNuclideModelId isoId);

    String getValidIsotopeAbbrNotation(ValidNuclideModelId isoId);

    Limits getLimits(LimitsModelId modelId);

    float getIALimited(LimitsModelId modelId);

    float getIAPackage(LimitsModelId modelId);

    float getLimited(LimitsModelId modelId);

    ReportableQuantity getReportQuan(String abbr);

    float getCiReportQuan(String abbr);

    float getTBqReportQuan(String abbr);

    Shipment getShipment(Long id);
}
