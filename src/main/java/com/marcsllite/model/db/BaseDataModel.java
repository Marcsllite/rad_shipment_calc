package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class BaseDataModel extends BaseModel {
    private static final long serialVersionUID = 6194462219788554210L;

    @EmbeddedId
    private NuclideModelId nuclideId;
    @Column(name = "Val")
    private String decFloatStr;
    @Transient
    private RadBigDecimal value;

    BaseDataModel() {
        this(new NuclideModelId("XX", "1"),
            RadBigDecimal.NEG_INFINITY_OBJ.toString());
    }

    BaseDataModel(NuclideModelId nuclideId, String decFloatStr) {
        setNuclideId(nuclideId);
        setDecFloatStr(decFloatStr);
    }
    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId;
    }

    public RadBigDecimal getValue() {
        return value;
    }

    public void setValue(RadBigDecimal value) {
        this.value = value;
        this.decFloatStr = value.toString();
    }

    public String getDecFloatStr() {
        return decFloatStr;
    }

    public void setDecFloatStr(String decFloatStr) {
        this.decFloatStr = decFloatStr;
        this.value = new RadBigDecimal(decFloatStr);
    }
}
