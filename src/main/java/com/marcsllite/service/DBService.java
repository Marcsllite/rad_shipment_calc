package com.marcsllite.service;

import com.marcsllite.model.Limits;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;
import javafx.collections.ObservableList;

public interface DBService {
    int validateDb();

    RadBigDecimal getA1(NuclideModelId nuclideId);

    RadBigDecimal getA2(NuclideModelId nuclideId);

    RadBigDecimal getDecayConstant(NuclideModelId nuclideId);

    RadBigDecimal getExemptConcentration(NuclideModelId nuclideId);

    RadBigDecimal getExemptLimit(NuclideModelId nuclideId);

    RadBigDecimal getHalfLife(NuclideModelId nuclideId);

    int countAllNuclides();

    ObservableList<NuclideModel> getAllNuclideModels();

    ObservableList<Nuclide> getAllNuclides();

    NuclideModel getNuclide(NuclideModelId nuclideId);

    String getNuclideNameNotation(NuclideModelId nuclideId);

    String getNuclideAbbrNotation(NuclideModelId nuclideId);

    Limits getLimits(LimitsModelId modelId);

    RadBigDecimal getIALimited(LimitsModelId modelId);

    RadBigDecimal getIAPackage(LimitsModelId modelId);

    RadBigDecimal getLimited(LimitsModelId modelId);

    ReportableQuantity getReportQuan(NuclideModelId nuclideId);

    RadBigDecimal getCiReportQuan(NuclideModelId nuclideId);

    RadBigDecimal getTBqReportQuan(NuclideModelId nuclideId);

    Shipment getShipment(Long id);
}
