package com.marcsllite.service;

import com.marcsllite.model.Limits;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import javafx.collections.ObservableList;

public interface DBService {
    int validateDb();

    String getA1(NuclideModelId nuclideId);

    String getA2(NuclideModelId nuclideId);

    String getDecayConstant(NuclideModelId nuclideId);

    String getExemptConcentration(NuclideModelId nuclideId);

    String getExemptLimit(NuclideModelId nuclideId);

    String getHalfLife(NuclideModelId nuclideId);

    int countAllNuclides();

    ObservableList<NuclideModel> getAllNuclideModels();

    ObservableList<Nuclide> getAllNuclides();

    NuclideModel getNuclide(NuclideModelId nuclideId);

    String getNuclideNameNotation(NuclideModelId nuclideId);

    String getNuclideAbbrNotation(NuclideModelId nuclideId);

    Limits getLimits(LimitsModelId modelId);

    String getIALimited(LimitsModelId modelId);

    String getIAPackage(LimitsModelId modelId);

    String getLimited(LimitsModelId modelId);

    ReportableQuantity getReportQuan(NuclideModelId nuclideId);

    String getCiReportQuan(NuclideModelId nuclideId);

    String getTBqReportQuan(NuclideModelId nuclideId);

    Shipment getShipment(Long id);
}
