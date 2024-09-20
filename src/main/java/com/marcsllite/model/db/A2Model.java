package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

/**
 * The maximum activity of Class 7 (radioactive) material
 * permitted in a Type A package other than:
 * <ul>
 *     <li>special form material</li>
 *     <li>Low Specific Activity (LSA) material</li>
 *     <li>Surface Contaminated Object (SCO)</li>
 * </ul>
 */
@Entity(name = "A2")
@Table(name = "A_TWO")
public class A2Model extends BaseDataModel {
    @Serial
    private static final long serialVersionUID = -5895460983437367212L;

    public A2Model() {
        super();
    }

    public A2Model(NuclideModelId nuclideId, String value) {
        super(nuclideId, value, Conversions.SIPrefix.TERA);
    }
}
