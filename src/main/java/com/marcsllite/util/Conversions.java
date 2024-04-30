package com.marcsllite.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class Conversions {
    private static final RadBigDecimal YOTTA = new RadBigDecimal(BigDecimal.TEN).pow(24);   // Y
    private static final RadBigDecimal ZETTA = new RadBigDecimal(BigDecimal.TEN).pow(21);   // Z
    private static final RadBigDecimal EXA = new RadBigDecimal(BigDecimal.TEN).pow(18);   // E
    private static final RadBigDecimal PETA = new RadBigDecimal(BigDecimal.TEN).pow(15);   // P
    private static final RadBigDecimal TERA = new RadBigDecimal(BigDecimal.TEN).pow(12);   // T
    private static final RadBigDecimal GIGA = new RadBigDecimal(BigDecimal.TEN).pow(9);    // G
    private static final RadBigDecimal MEGA = new RadBigDecimal(BigDecimal.TEN).pow(6);    // M
    private static final RadBigDecimal KILO = new RadBigDecimal(BigDecimal.TEN).pow(3);    // k
    private static final RadBigDecimal HECTO = new RadBigDecimal(BigDecimal.TEN).pow(2);    // h
    private static final RadBigDecimal DEKA = new RadBigDecimal(BigDecimal.TEN).pow(1);    // da
    private static final RadBigDecimal DECI = new RadBigDecimal(BigDecimal.TEN).pow(-1);   // d
    private static final RadBigDecimal CENTI = new RadBigDecimal(BigDecimal.TEN).pow(-2);   // c
    private static final RadBigDecimal MILLI = new RadBigDecimal(BigDecimal.TEN).pow(-3);   // m
    private static final RadBigDecimal MICRO = new RadBigDecimal(BigDecimal.TEN).pow(-6);   // MICRO
    private static final RadBigDecimal NANO = new RadBigDecimal(BigDecimal.TEN).pow(-9);   // n
    private static final RadBigDecimal PICO = new RadBigDecimal(BigDecimal.TEN).pow(-12);  // p
    private static final RadBigDecimal FEMTO = new RadBigDecimal(BigDecimal.TEN).pow(-15);  // f
    private static final RadBigDecimal ATTO = new RadBigDecimal(BigDecimal.TEN).pow(-18);  // a
    private static final RadBigDecimal ZEPTO = new RadBigDecimal(BigDecimal.TEN).pow(-21);  // z
    private static final RadBigDecimal YOCTO = new RadBigDecimal(BigDecimal.TEN).pow(-24);  // y
    private static final String INVALID_VALUE = "Invalid Value";
    private static final String ENUM_FIRST_PART_REGEX = "^.*\\(";
    private static final String ENUM_LAST_PART_REGEX = "\\)$";
    private Conversions() {}

    /*////////////////////////////////////////////////// HELPERS /////////////////////////////////////////////////////*/

    /**
     * Helper function to convert a given value to it's base value using its starting SI prefix
     *
     * @param value the value to be converted
     * @param prefix the starting SI prefix
     * @return the converted base value
     */
    public static RadBigDecimal convertToBase(RadBigDecimal value, SIPrefix prefix) throws InvalidParameterException {
        if(value == null) throw new InvalidParameterException(INVALID_VALUE);
        if(prefix == null) throw new InvalidParameterException("Invalid SI Prefix");

        if(value.isInfinity() || value.isNegativeInfinity()) {
            return value;
        }
        
        switch(prefix) {
            case YOTTA: return value.multiply(YOTTA);
            case ZETTA: return value.multiply(ZETTA);
            case EXA: return value.multiply(EXA);
            case PETA: return value.multiply(PETA);
            case TERA: return value.multiply(TERA);
            case GIGA: return value.multiply(GIGA);
            case MEGA: return value.multiply(MEGA);
            case KILO: return value.multiply(KILO);
            case HECTO: return value.multiply(HECTO);
            case DEKA: return value.multiply(DEKA);
            case DECI: return value.multiply(DECI);
            case CENTI: return value.multiply(CENTI);
            case MILLI: return value.multiply(MILLI);
            case MICRO: return value.multiply(MICRO);
            case NANO: return value.multiply(NANO);
            case PICO: return value.multiply(PICO);
            case FEMTO: return value.multiply(FEMTO);
            case ATTO: return value.multiply(ATTO);
            case ZEPTO: return value.multiply(ZEPTO);
            case YOCTO: return value.multiply(YOCTO);
            default: return value;
        }
    }

    /**
     * Helper function to convert a given value from a starting SIPrefix
     * to an ending SI prefix
     *
     * @param value the value to be converted
     * @param start the starting SI prefix
     * @param end the ending SI prefix
     * @return the value converted from the start prefix to the end prefix
     */
    public static RadBigDecimal convertToPrefix(RadBigDecimal value, SIPrefix start, SIPrefix end) throws InvalidParameterException {
        if(isNotValidValue(value)) throw new InvalidParameterException(INVALID_VALUE);
        if(start == null) throw new InvalidParameterException("Invalid starting SI Prefix");
        if(end == null) throw new InvalidParameterException("Invalid ending SI Prefix");
        
        RadBigDecimal baseValue = convertToBase(value, start);
        switch(end) {
            case YOTTA: return baseValue.multiply(YOCTO);
            case ZETTA: return baseValue.multiply(ZEPTO);
            case EXA: return baseValue.multiply(ATTO);
            case PETA: return baseValue.multiply(FEMTO);
            case TERA: return baseValue.multiply(PICO);
            case GIGA: return baseValue.multiply(NANO);
            case MEGA: return baseValue.multiply(MICRO);
            case KILO: return baseValue.multiply(MILLI);
            case HECTO: return baseValue.multiply(CENTI);
            case DEKA: return baseValue.multiply(DECI);
            case DECI: return baseValue.multiply(DEKA);
            case CENTI: return baseValue.multiply(HECTO);
            case MILLI: return baseValue.multiply(KILO);
            case MICRO: return baseValue.multiply(MEGA);
            case NANO: return baseValue.multiply(GIGA);
            case PICO: return baseValue.multiply(TERA);
            case FEMTO: return baseValue.multiply(PETA);
            case ATTO: return baseValue.multiply(EXA);
            case ZEPTO: return baseValue.multiply(ZETTA);
            case YOCTO: return baseValue.multiply(YOTTA);
            default: return baseValue;
        }
    }

    private static boolean isNotValidValue(RadBigDecimal value) {
        return value == null || value.isInfinity() || value.isNegativeInfinity();
    }

    /*//////////////////////////////////////////////// CONVERSIONS ///////////////////////////////////////////////////*/
    /**
     * Function to convert the given becquerel to curies
     * 
     * @param bq the becquerel value to be converted
     * @return the converted curie value
     */
    public static RadBigDecimal bqToCi(RadBigDecimal bq) throws InvalidParameterException {
        if(isNotValidValue(bq)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }
        
        return bq.multiply(RadBigDecimal.valueOf(2.7d).multiply(new RadBigDecimal(BigDecimal.TEN).pow(-11)));
    }

    /**
     * Function to convert the given becquerel to becquerels
     *
     * @param ci the curie value to be converted
     * @return the converted becquerel value
     */
    public static RadBigDecimal ciToBq(RadBigDecimal ci) throws InvalidParameterException {
        if(isNotValidValue(ci)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }

        return ci.multiply(RadBigDecimal.valueOf(3.7d).multiply(new RadBigDecimal(BigDecimal.TEN).pow(10)));
    }

    /**
     * Function to convert the given gray to rads
     *
     * @param gy the gray value to be converted
     * @return the converted rad value
     */
    public static RadBigDecimal gyToRad(RadBigDecimal gy) throws InvalidParameterException {
        if(isNotValidValue(gy)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }
        
        return gy.multiply(new RadBigDecimal(BigDecimal.TEN).pow(2));
    }

    /**
     * Function to convert the given rad to grays
     *
     * @param rad the rad value to be converted
     * @return the converted gray value
     */
    public static RadBigDecimal radToGy(RadBigDecimal rad) throws InvalidParameterException {
        if(isNotValidValue(rad)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }
        
        return rad.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-2));
    }

    /**
     * Function to convert the given sievert to rems
     *
     * @param sv the sievert value to be converted
     * @return the converted rem value
     */
    public static RadBigDecimal svToRem(RadBigDecimal sv) throws InvalidParameterException {
        if(isNotValidValue(sv)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }
        
        return sv.multiply(new RadBigDecimal(BigDecimal.TEN).pow(2));
    }

    /**
     * Function to convert the given rem to sieverts
     *
     * @param rem the rem value to be converted
     * @return the converted sievert value
     */
    public static RadBigDecimal remToSv(RadBigDecimal rem) throws InvalidParameterException {
        if(isNotValidValue(rem)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }
        
        return rem.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-2));
    }

    /**
     * Function to convert the given coulomb/kilogram to roentgens
     *
     * @param ckg the coulomb/kilogram value to be converted
     * @return the converted roentgen value
     */
    public static RadBigDecimal ckgToR(RadBigDecimal ckg) throws InvalidParameterException {
        if(isNotValidValue(ckg)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }

        return ckg.multiply(RadBigDecimal.valueOf(3.88d).multiply(new RadBigDecimal(BigDecimal.TEN).pow(3)));
    }

    /**
     * Function to convert the given roentgen to coulombs/kilogram
     *
     * @param r the roentgen value to be converted
     * @return the converted coulomb/kilogram value
     */
    public static RadBigDecimal rToCkg(RadBigDecimal r) throws InvalidParameterException {
        if(isNotValidValue(r)) {
            throw new InvalidParameterException(INVALID_VALUE);
        }
        
        return r.multiply(RadBigDecimal.valueOf(2.58d).multiply(new RadBigDecimal(BigDecimal.TEN).pow(-4)));
    }

    public enum MassUnit {
        GRAMS("grams (g)"),
        LITERS("liters (l)");

        private final String val;

        MassUnit(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() {
            return val.replaceAll(ENUM_FIRST_PART_REGEX, "")
                    .replaceAll(ENUM_LAST_PART_REGEX, "");
        }

        public static MassUnit toMass(String value) {
            for (MassUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(MassUnit.values())
                .map(MassUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    /* REMM(Radiation Emergency Medical Management)
     * source: https://www.remm.hhs.gov/radmeasurement.htm
     * #################|           SI Units            |             Common Units            |
     * Absorbed Dose    |   gray                (Gy)    |   radiation absorbed dose     (rad) |
     * Dose Equivalent  |   sievert             (Sv)    |   roentgen equivalent, man    (rem) |
     */
    public enum DoseUnit {
        GRAY("gray (gy)"),
        RAD("sievert (Sv)"),
        SIEVERT("radiation absorbed dose (rad)"),
        REM("roentgen equivalent, man (rem)");

        private final String val;

        DoseUnit(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() {
            return val.replaceAll(ENUM_FIRST_PART_REGEX, "")
                    .replaceAll(ENUM_LAST_PART_REGEX, "");
        }

        public static DoseUnit toDoseUnit(String value) {
            for (DoseUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equals(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(DoseUnit.values())
                .map(DoseUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    /*
     * REMM(Radiation Emergency Medical Management)
     * source: https://www.remm.hhs.gov/radmeasurement.htm
     * #########|           SI Unit            |      Common Unit     |
     * Exposure |   coulomb/kilogram    (C/kg) |   roentgen      (R)  |
     */
    public enum ExposureUnit {
        COULOMB_KILOGRAM("coulomb/kilogram (C/kg)"),
        ROENTGEN("roentgen (R)");

        private final String val;

        ExposureUnit(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() {
            return val.replaceAll(ENUM_FIRST_PART_REGEX, "")
                .replaceAll(ENUM_LAST_PART_REGEX, "");
        }

        public static ExposureUnit toExposureUnit(String value) {
            for (ExposureUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(DoseUnit.values())
                .map(DoseUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    /*
     * REMM(Radiation Emergency Medical Management)
     * source: https://remm.hhs.gov/radmeasurement.htm
     * ############|           SI Unit          |    Common Unit     |
     * Radioactive |   becquerel           (Bq) |   curie       (Ci) |
     */
    public enum RadUnit {
        BECQUEREL("Becquerel (Bq)"),
        CURIE("Curie (Ci)");

        private final String val;

        RadUnit(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() {
            return val.replaceAll(ENUM_FIRST_PART_REGEX, "")
                    .replaceAll(ENUM_LAST_PART_REGEX, "");
        }

        public static RadUnit toRadUnit(String value) {
            for (RadUnit enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(RadUnit.values())
                .map(RadUnit::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        @Override
        public String toString() {
            return getVal();
        }
    }

    public enum SIPrefix {
        YOTTA("Yotta (Y)"),
        ZETTA("Zetta (Z)"),
        EXA("Exa (E)"),
        PETA("Peta (P)"),
        TERA("Tera (T)"),
        GIGA("Giga (G)"),
        MEGA("Mega (M)"),
        KILO("Kilo (k)"),
        HECTO("Hecto (h)"),
        DEKA("Deka (da)"),
        BASE("----"),
        DECI("Deci (d)"),
        CENTI("Centi (c)"),
        MILLI("Milli (m)"),
        MICRO("Micro (µ)"),
        NANO("Nano (n)"),
        PICO("Pico (p)"),
        FEMTO("Femto (f)"),
        ATTO("Atto (a)"),
        ZEPTO("Zepto (z)"),
        YOCTO("Yocto (y)");

        private final String val;

        SIPrefix(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public String getAbbrVal() {
            return val.equals("----")? "" :
                val.replaceAll(ENUM_FIRST_PART_REGEX, "")
                    .replaceAll(ENUM_LAST_PART_REGEX, "");
        }

        public static SIPrefix toSIPrefix(String value) {
            for (SIPrefix enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equals(value)) {
                    return enumValue;
                }
            }
            return null;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(SIPrefix.values())
                .map(SIPrefix::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
    }
}
