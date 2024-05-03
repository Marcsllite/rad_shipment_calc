package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NuclideModelId implements Serializable {
    private static final long serialVersionUID = 6598716598717598732L;

    @Column(name = "Symbol", length = 2, nullable = false)
    private String symbol;
    @Column(name = "Mass_Number", length = 15, nullable = false)
    private String massNumber;

    public NuclideModelId() {
        this("XX", "1");
    }
    public NuclideModelId(String symbol, String massNumber) {
        this.symbol = symbol;
        this.massNumber = massNumber;
    }

    public String getSymbol() {
        return symbol == null? null : symbol.trim();
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMassNumber() {
        return massNumber == null? null : massNumber.trim();
    }

    public void setMassNumber(String massNumber) {
        this.massNumber = massNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        NuclideModelId temp = (NuclideModelId) obj;
        return Objects.equals(this.symbol, temp.symbol) && Objects.equals(this.massNumber, temp.massNumber);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 17 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
        hash = 17 * hash + (this.massNumber != null ? this.massNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getSymbol() + "-" + getMassNumber();
    }
}
