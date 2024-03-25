package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXEMPT_CONCENTRATION")
public class ExemptConcentrationModel extends BaseDataModel {
    private static final long serialVersionUID = 5186004813264658890L;

    public ExemptConcentrationModel() {
        super();
    }

    public ExemptConcentrationModel(String abbr, float value) {
        super(abbr, value);
        setBasePrefix(Conversions.SIPrefix.BASE);
    }
}
