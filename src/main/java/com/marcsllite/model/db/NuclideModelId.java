package com.marcsllite.model.db;

import com.marcsllite.util.NuclideUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NuclideModelId implements Serializable {
    @Serial
    private static final long serialVersionUID = 6598716598717598732L;
    public static final String DEFAULT_SYMBOL = "XX";
    public static final int SYMBOL_MAX_LENGTH = 2;
    public static final int MASS_NUMBER_MAX_LENGTH = 15;

    @Column(name = "Symbol", length = SYMBOL_MAX_LENGTH, nullable = false)
    private String symbol;
    @Column(name = "Mass_Number", length = MASS_NUMBER_MAX_LENGTH, nullable = false)
    private String massNumber;

    public NuclideModelId() {
        this(DEFAULT_SYMBOL, "1");
    }
    public NuclideModelId(String symbol, String massNumber) {
        this.symbol = symbol == null? null : symbol.trim();
        this.massNumber = massNumber == null? null : massNumber.trim();
    }

    public String getSymbol() {
        return symbol == null? null : symbol.trim();
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null? null : symbol.trim();
    }

    public String getMassNumber() {
        return massNumber == null? null : massNumber.trim();
    }

    public String getDisplayMassNumber() {
        return massNumber == null ? "" : massNumber.trim()
            .replaceAll(NuclideUtils.LIFE_SPAN_PATTERN, "")
            .replaceAll(NuclideUtils.LUNG_ABS_PATTERN, "");
    }

    public void setMassNumber(String massNumber) {
        this.massNumber = massNumber == null? null : massNumber.trim();
    }

    public String getDisplaySymbolNotation() { return getSymbol() + "-" + getDisplayMassNumber(); }

    public String getFullSymbolNotation() { return getSymbol() + "-" + getMassNumber(); }

    public boolean minimumEquals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        NuclideModelId temp = (NuclideModelId) obj;
        return Objects.equals(this.symbol, temp.symbol) && Objects.equals(this.getDisplayMassNumber(), temp.getDisplayMassNumber());
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
