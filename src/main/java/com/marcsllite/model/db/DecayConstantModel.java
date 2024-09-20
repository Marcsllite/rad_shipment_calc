package com.marcsllite.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity(name = "Decay Constant")
@Table(name = "DECAY_CONSTANT")
public class DecayConstantModel extends BaseDataModel {
    @Serial
    private static final long serialVersionUID = 5704920375115196514L;

    public DecayConstantModel() {
        super();
    }

    public DecayConstantModel(NuclideModelId nuclideId, String value) {
        super(nuclideId, value);
    }
}
