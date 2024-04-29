package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
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
    private String iaLimitedStr;

    @Column(name = "IA_Package")
    private String iaPackageStr;

    @Column(name = "Limited")
    private String limitedStr;

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
    }

    public RadBigDecimal getIaLimited() {
        return new RadBigDecimal(iaLimitedStr);
    }

    public String getIaPackageStr() {
        return iaPackageStr;
    }

    public void setIaPackageStr(String iaPackageStr) {
        this.iaPackageStr = iaPackageStr;
    }

    public RadBigDecimal getIaPackage() {
        return new RadBigDecimal(iaPackageStr);
    }

    public String getLimitedStr() {
        return limitedStr;
    }

    public void setLimitedStr(String limitedStr) {
        this.limitedStr = limitedStr;
    }

    public RadBigDecimal getLimited() {
        return new RadBigDecimal(limitedStr);
    }

}
