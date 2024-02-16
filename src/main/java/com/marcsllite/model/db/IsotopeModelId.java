package com.marcsllite.model.db;

import java.io.Serializable;
import java.util.Objects;

public class IsotopeModelId implements Serializable {
    private static final long serialVersionUID = 4570361434780825099L;
    private String name;
    private String abbr;

    public IsotopeModelId() {
        this("", "");
    }

    public IsotopeModelId(String name, String abbr) {
        setName(name);
        setAbbr(abbr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        IsotopeModelId temp = (IsotopeModelId) obj;
        if(!Objects.equals(this.name, temp.name)) {
            return false;
        }
        return Objects.equals(this.abbr, temp.abbr);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 17 * hash + (this.abbr != null ? this.abbr.hashCode() : 0);
        return hash;
    }
}
