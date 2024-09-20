package com.marcsllite.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity(name = "HalfLife")
@Table(name = "HALF_LIFE")
public class HalfLifeModel extends BaseDataModel {
    @Serial
    private static final long serialVersionUID = -3082962071180959635L;

    public HalfLifeModel() {
        super();
    }

    public HalfLifeModel(NuclideModelId nuclideId, String value) {
        super(nuclideId, value, null);
    }
}
