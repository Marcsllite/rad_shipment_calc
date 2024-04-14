package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Exempt Concentration")
@Table(name = "EXEMPT_CONCENTRATION")
public class ExemptConcentrationModel extends BaseDataModel {
    private static final long serialVersionUID = 5186004813264658890L;

    public ExemptConcentrationModel() {
        super();
    }

    public ExemptConcentrationModel(NuclideModelId nuclideId, RadBigDecimal value) {
        super(nuclideId, value.toString());
        setBasePrefix(Conversions.SIPrefix.BASE);
    }
}
