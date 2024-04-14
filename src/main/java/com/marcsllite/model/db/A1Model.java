package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * The maximum activity of special form Class 7 (radioactive) material
 * permitted in a Type A package.
 */
@Entity(name = "A1")
@Table(name = "A_ONE")
public class A1Model extends BaseDataModel {
    private static final long serialVersionUID = 1600283366978070629L;

    public A1Model() {
        super();
    }

    public A1Model(NuclideModelId nuclideId, RadBigDecimal value) {
        super(nuclideId, value.toString());
        setBasePrefix(Conversions.SIPrefix.TERA);
    }
}
