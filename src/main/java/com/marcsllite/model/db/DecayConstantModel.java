package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Decay Constant")
@Table(name = "DECAY_CONSTANT")
public class DecayConstantModel extends BaseDataModel {
    private static final long serialVersionUID = 5704920375115196514L;

    public DecayConstantModel() {
        super();
    }

    public DecayConstantModel(NuclideModelId nuclideId, RadBigDecimal value) {
        super(nuclideId, value.toString());
        setBasePrefix(Conversions.SIPrefix.BASE);
    }
}
