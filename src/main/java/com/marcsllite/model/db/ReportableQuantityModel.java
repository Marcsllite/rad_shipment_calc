package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Reportable Quantity")
@Table(name = "REPORTABLE_QUANTITY")
public class ReportableQuantityModel extends BaseModel {
    private static final long serialVersionUID = 1479886818838786038L;

    @EmbeddedId
    private NuclideModelId nuclideId;
    @Column(name = "Ci")
    private String curieStr;
    @Transient
    private RadBigDecimal curie;
    @Column(name = "TBq")
    private String teraBqStr;
    @Transient
    private RadBigDecimal teraBq;

    public ReportableQuantityModel() {
        this(new NuclideModelId("XX", "1"),
            RadBigDecimal.NEG_INFINITY_OBJ.toString(),
            RadBigDecimal.NEG_INFINITY_OBJ.toString());
    }

    public ReportableQuantityModel(NuclideModelId nuclideId, String curieStr, String teraBqStr) {
        setNuclideId(nuclideId);
        setCurieStr(curieStr);
        setTeraBqStr(teraBqStr);
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
        this.curie = new RadBigDecimal(curieStr);
    }

    public RadBigDecimal getCurie() {
        return curie;
    }

    public void setCurie(RadBigDecimal curie) {
        this.curie = curie;
        this.curieStr = curie.toString();
    }

    public String getTeraBqStr() {
        return teraBqStr;
    }

    public void setTeraBqStr(String teraBqStr) {
        this.teraBqStr = teraBqStr;
        this.teraBq = new RadBigDecimal(teraBqStr);
    }

    public RadBigDecimal getTeraBq() {
        return teraBq;
    }

    public void setTeraBq(RadBigDecimal teraBq) {
        this.teraBq = teraBq;
        this.teraBqStr = teraBq.toString();
    }
}
