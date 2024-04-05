package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IsotopeModelId implements Serializable {
    private static final long serialVersionUID = 4570361434780825099L;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Abbr", length = 15, nullable = false)
    private String abbr;

    public IsotopeModelId() {
        this("Name", "Abbr");
    }
    public IsotopeModelId(String name, String abbr) {
        setName(name);
        setAbbr(abbr);
    }

    public String getName() {
        return name == null? null : name.trim();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr == null? null : abbr.trim();
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
        return Objects.equals(this.name, temp.name) && Objects.equals(this.abbr, temp.abbr);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 17 * hash + (this.abbr != null ? this.abbr.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "(Name: \"" + getName() + "\", Abbr: \"" + getAbbr() + "\")";
    }
}
