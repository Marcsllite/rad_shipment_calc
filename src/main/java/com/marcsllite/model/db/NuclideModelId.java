package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class NuclideModelId implements Serializable {
    private static final long serialVersionUID = 6598716598717598732L;
    @Transient
    public static final String LUNG_ABS_PATTERN = "(slow|medium|fast)$";
    @Transient
    public static final String LIFE_SPAN_PATTERN = "\\((.*)\\)$";

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

    public String getDisplayMassNumber() {
        return massNumber == null ? null : massNumber
            .replaceAll(LIFE_SPAN_PATTERN, "")
            .replaceAll(LUNG_ABS_PATTERN, "");
    }

    public void setMassNumber(String massNumber) {
        this.massNumber = massNumber;
    }

    public String getSymbolNotation() { return symbol + "-" + massNumber; }

    public String getDisplaySymbolNotation() { return symbol + "-" + getDisplayMassNumber(); }

    public Nuclide.LifeSpan parseLifeSpanFromMassNumber() {
        Pattern lifeSpanPattern = Pattern.compile(LIFE_SPAN_PATTERN);
        Matcher lifeSpanMatch = lifeSpanPattern.matcher(getMassNumber());
        Nuclide.LifeSpan ret = Nuclide.LifeSpan.REGULAR;
        if(lifeSpanMatch.find()) {
            ret = Nuclide.LifeSpan.toLifeSpan(lifeSpanMatch.group(1));
        }
        return ret;
    }

    public Nuclide.LungAbsorption parseLungAbsFromMassNumber() {
        Pattern lungAbsPattern = Pattern.compile(LUNG_ABS_PATTERN);
        Matcher lungAbsMatch = lungAbsPattern.matcher(getMassNumber());
        Nuclide.LungAbsorption ret = Nuclide.LungAbsorption.NONE;
        if(lungAbsMatch.find()) {
            ret = Nuclide.LungAbsorption.toLungAbsorption(lungAbsMatch.group(0));
        }
        return ret;
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
