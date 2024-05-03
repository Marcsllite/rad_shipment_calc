package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Reportable Quantity")
@Table(name = "REPORTABLE_QUANTITY")
public class ReportableQuantityModel extends BaseModel {
    private static final long serialVersionUID = 1479886818838786038L;

    @EmbeddedId
    private NuclideModelId nuclideId;
    @Column(name = "Ci")
    private String curieStr;
    @Column(name = "TBq")
    private String teraBqStr;

    public ReportableQuantityModel() {
        this(new NuclideModelId("XX", "1"),
            RadBigDecimal.NEG_INFINITY_OBJ.toString(),
            RadBigDecimal.NEG_INFINITY_OBJ.toString());
    }

    public ReportableQuantityModel(NuclideModelId nuclideId, String curieStr, String teraBqStr) {
        super(null);
        this.nuclideId = nuclideId;
        this.curieStr = curieStr;
        this.teraBqStr = teraBqStr;
    }

    public NuclideModelId getNuclideId() {
        return nuclideId;
    }

    public void setNuclideId(NuclideModelId nuclideId) {
        this.nuclideId = nuclideId;
    }

    public String getCurieStr() {
        return curieStr;
    }

    public void setCurieStr(String curieStr) {
        this.curieStr = curieStr;
    }

    public RadBigDecimal getCurie() {
        return new RadBigDecimal(curieStr);
    }

    public String getTeraBqStr() {
        return teraBqStr;
    }

    public void setTeraBqStr(String teraBqStr) {
        this.teraBqStr = teraBqStr;
    }

    public RadBigDecimal getTeraBq() {
        return new RadBigDecimal(teraBqStr);
    }

}
