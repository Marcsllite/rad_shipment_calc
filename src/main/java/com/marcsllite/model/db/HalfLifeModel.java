package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "HalfLife")
@Table(name = "HALF_LIFE")
public class HalfLifeModel extends BaseDataModel {
    private static final long serialVersionUID = -3082962071180959635L;

    public HalfLifeModel() {
        super();
    }

    public HalfLifeModel(NuclideModelId nuclideId, RadBigDecimal value) {
        super(nuclideId, value.toString());
    }
}
