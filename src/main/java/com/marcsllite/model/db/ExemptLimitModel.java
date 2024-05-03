package com.marcsllite.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Exempt Limit")
@Table(name = "EXEMPT_LIMIT")
public class ExemptLimitModel extends BaseDataModel {
    private static final long serialVersionUID = -5142275333078025319L;

    public ExemptLimitModel() {
        super();
    }

    public ExemptLimitModel(NuclideModelId nuclideId, String value) {
        super(nuclideId, value);
    }
}
