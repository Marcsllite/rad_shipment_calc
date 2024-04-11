package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Valid Nuclide")
@Table(name = "VALID_NUCLIDE")
public class ValidNuclideModel extends BaseModel {
    private static final long serialVersionUID = -6610061947707574223L;

    @EmbeddedId
    private ValidNuclideModelId isotopeId;

    @Column(name = "Name", nullable = false)
    private String name;

    public ValidNuclideModel() {
        this(new ValidNuclideModelId("XX", "1"));
    }

    public ValidNuclideModel(ValidNuclideModelId isotopeId) {
        setIsotopeId(isotopeId);
        setName("XX");
    }

    public ValidNuclideModelId getIsotopeId() {
        return isotopeId;
    }

    public void setIsotopeId(ValidNuclideModelId isotopeId) {
        this.isotopeId = isotopeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameIsoNotation() {
        return getName() + "-" + getIsotopeId().getMassNumber();
    }

    public String getAbbrIsoNotation() {
        return getIsotopeId().getSymbol() + "-" + getIsotopeId().getMassNumber();
    }
}
