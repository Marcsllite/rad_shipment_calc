package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "LIMITS")
public class LimitsModel extends BaseModel {
    private static final long serialVersionUID = -5280835757871497233L;

    @EmbeddedId
    private LimitsModelId limitsId;

    @Column(name = "IA_Limited")
    private float ia_limited;

    @Column(name = "IA_Package")
    private float ia_package;

    @Column(name = "Limited")
    private float limited;

    public LimitsModel() {
        this(new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL),
            -2.0f,
            -2.0f,
            -2.0f);
    }

    public LimitsModel(LimitsModelId limitsId, float ia_limited, float ia_package, float limited) {
        setLimitsId(limitsId);
        setIa_limited(ia_limited);
        setIa_package(ia_package);
        setLimited(limited);
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public float getIa_limited() {
        return ia_limited;
    }

    public void setIa_limited(float ia_limited) {
        this.ia_limited = ia_limited;
    }

    public float getIa_package() {
        return ia_package;
    }

    public void setIa_package(float ia_package) {
        this.ia_package = ia_package;
    }

    public float getLimited() {
        return limited;
    }

    public void setLimited(float limited) {
        this.limited = limited;
    }
}
