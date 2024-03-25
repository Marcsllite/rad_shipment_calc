package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_LIMIT")
public class ExemptLimitModel extends BaseDataModel {
    private static final long serialVersionUID = -5142275333078025319L;

    public ExemptLimitModel() {
        super();
    }

    public ExemptLimitModel(String abbr, float value) {
        super(abbr, value);
        setBasePrefix(Conversions.SIPrefix.BASE);
    }
}
