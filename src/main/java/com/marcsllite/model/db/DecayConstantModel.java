package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "DECAY_CONSTANT")
public class DecayConstantModel extends BaseDataModel {
    private static final long serialVersionUID = 5704920375115196514L;

    public DecayConstantModel() {
        super();
    }

    public DecayConstantModel(String abbr, float value) {
        super(abbr, value);
        setBasePrefix(Conversions.SIPrefix.BASE);
    }
}
