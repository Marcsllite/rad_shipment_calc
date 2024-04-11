package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ValidNuclideModelId implements Serializable {
    private static final long serialVersionUID = 6598716598717598732L;
    @Column(name = "Symbol", length = 2, nullable = false)
    private String symbol;
    @Column(name = "Mass_Number", length = 15, nullable = false)
    private String massNumber;
    @Transient
    private String isoNotation;

    public ValidNuclideModelId() {
        this("XX", "1");
    }
    public ValidNuclideModelId(String symbol, String massNumber) {
        setSymbol(symbol);
        setMassNumber(massNumber);
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
        ValidNuclideModelId temp = (ValidNuclideModelId) obj;
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
