package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseDataModel extends BaseModel {
    private static final long serialVersionUID = 6194462219788554210L;

    @EmbeddedId
    private NuclideModelId nuclideId;
    @Column(name = "Val")
    private String decFloatStr;

    BaseDataModel() {
        this(new NuclideModelId("XX", "1"),
            RadBigDecimal.NEG_INFINITY_OBJ.toString(),
            Conversions.SIPrefix.BASE);
    }

    BaseDataModel(NuclideModelId nuclideId, String decFloatStr) {
        this(nuclideId, decFloatStr, Conversions.SIPrefix.BASE);
    }

    BaseDataModel(NuclideModelId nuclideId, String decFloatStr, Conversions.SIPrefix basePrefix) {
        super(basePrefix);
        this.nuclideId = nuclideId;
        this.decFloatStr = decFloatStr;
    }

    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId;
    }

    public RadBigDecimal getValue() {
        return new RadBigDecimal(decFloatStr);
    }

    public String getDecFloatStr() {
        return decFloatStr;
    }

    public void setDecFloatStr(String decFloatStr) {
        this.decFloatStr = decFloatStr;
    }
}
