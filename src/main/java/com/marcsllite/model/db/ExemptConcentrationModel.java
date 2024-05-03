package com.marcsllite.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Exempt Concentration")
@Table(name = "EXEMPT_CONCENTRATION")
public class ExemptConcentrationModel extends BaseDataModel {
    private static final long serialVersionUID = 5186004813264658890L;

    public ExemptConcentrationModel() {
        super();
    }

    public ExemptConcentrationModel(NuclideModelId nuclideId, String value) {
        super(nuclideId, value);
    }
}
