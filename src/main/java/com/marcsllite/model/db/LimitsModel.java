package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Limits")
@Table(name = "LIMITS")
public class LimitsModel extends BaseModel {
    private static final long serialVersionUID = -5280835757871497233L;

    @EmbeddedId
    private LimitsModelId limitsId;

    @Column(name = "IA_Limited")
    private float iaLimited;

    @Column(name = "IA_Package")
    private float iaPackage;

    @Column(name = "Limited")
    private float limited;

    public LimitsModel() {
        this(new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL),
            -2.0f,
            -2.0f,
            -2.0f);
    }

    public LimitsModel(LimitsModelId limitsId, float iaLimited, float iaPackage, float limited) {
        setLimitsId(limitsId);
        setIaLimited(iaLimited);
        setIaPackage(iaPackage);
        setLimited(limited);
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public float getIaLimited() {
        return iaLimited;
    }

    public void setIaLimited(float iaLimited) {
        this.iaLimited = iaLimited;
    }

    public float getIaPackage() {
        return iaPackage;
    }

    public void setIaPackage(float iaPackage) {
        this.iaPackage = iaPackage;
    }

    public float getLimited() {
        return limited;
    }

    public void setLimited(float limited) {
        this.limited = limited;
    }
}
