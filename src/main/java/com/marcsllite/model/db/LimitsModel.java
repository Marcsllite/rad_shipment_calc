package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Limits")
@Table(name = "LIMITS")
public class LimitsModel extends BaseModel {
    private static final long serialVersionUID = -5280835757871497233L;

    @EmbeddedId
    private LimitsModelId limitsId;

    @Column(name = "IA_Limited")
    private String iaLimitedStr;
    @Transient
    private RadBigDecimal iaLimited;

    @Column(name = "IA_Package")
    private String iaPackageStr;
    @Transient
    private RadBigDecimal iaPackage;

    @Column(name = "Limited")
    private String limitedStr;
    @Transient
    private RadBigDecimal limited;

    public LimitsModel() {
        this(new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL),
            RadBigDecimal.NEG_INFINITY_OBJ.toString(),
            RadBigDecimal.NEG_INFINITY_OBJ.toString(),
            RadBigDecimal.NEG_INFINITY_OBJ.toString());
    }

    public LimitsModel(LimitsModelId limitsId, String iaLimitedStr, String iaPackageStr, String limitedStr) {
        setLimitsId(limitsId);
        setIaLimitedStr(iaLimitedStr);
        setIaPackageStr(iaPackageStr);
        setLimitedStr(limitedStr);
    }

    public LimitsModelId getLimitsId() {
        return limitsId;
    }

    public void setLimitsId(LimitsModelId limitsId) {
        this.limitsId = limitsId;
    }

    public String getIaLimitedStr() {
        return iaLimitedStr;
    }

    public void setIaLimitedStr(String iaLimitedStr) {
        this.iaLimitedStr = iaLimitedStr;
        this.iaLimited = new RadBigDecimal(iaLimitedStr);
    }

    public RadBigDecimal getIaLimited() {
        return iaLimited;
    }

    public void setIaLimited(RadBigDecimal iaLimited) {
        this.iaLimited = iaLimited;
        this.iaLimitedStr = iaLimited.toString();
    }

    public String getIaPackageStr() {
        return iaPackageStr;
    }

    public void setIaPackageStr(String iaPackageStr) {
        this.iaPackageStr = iaPackageStr;
        this.iaPackage = new RadBigDecimal(iaPackageStr);
    }

    public RadBigDecimal getIaPackage() {
        return iaPackage;
    }

    public void setIaPackage(RadBigDecimal iaPackage) {
        this.iaPackage = iaPackage;
        this.iaPackageStr = iaPackage.toString();
    }

    public String getLimitedStr() {
        return limitedStr;
    }

    public void setLimitedStr(String limitedStr) {
        this.limitedStr = limitedStr;
        this.limited = new RadBigDecimal(limitedStr);
    }

    public RadBigDecimal getLimited() {
        return limited;
    }

    public void setLimited(RadBigDecimal limited) {
        this.limited = limited;
        this.limitedStr = limited.toString();
    }
}
