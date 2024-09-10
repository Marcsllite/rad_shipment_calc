package com.marcsllite.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class Conversions {
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

        if(prefix == SIPrefix.BASE || value.isInfinity() || value.isNegativeInfinity()) {
            return value;
        }

        return value.multiply(getSIMultiplyingFactor(prefix, false));
    }

    /**
     * Helper function to get the multiplying factor for the given prefix,
     * or the multiplying factor for the inverse of the provided prefix
     * @param prefix    the prefix to get the multiplying factor for
     * @param negate    if true, gets the multiplying factor for the inverse of the prefix provided
     * @return a RadBigDecimal with the multiplying factor of the provided prefix, or it's inverse
     * @throws InvalidParameterException if prefix is null
     */
    public static RadBigDecimal getSIMultiplyingFactor(SIPrefix prefix, boolean negate) throws InvalidParameterException {
        if(prefix == null) throw new InvalidParameterException(INVALID_VALUE);
        return new RadBigDecimal(BigDecimal.TEN).pow(negate? -prefix.getSiVal() : prefix.getSiVal());
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
        verifyRadBigDecimal(value);
        if(start == null) throw new InvalidParameterException("Invalid starting SI Prefix");
        if(end == null) throw new InvalidParameterException("Invalid ending SI Prefix");

        RadBigDecimal baseValue = convertToBase(value, start);

        if(end == SIPrefix.BASE) {
            return baseValue;
        }

        return baseValue.multiply(getSIMultiplyingFactor(end, true));
    }

    private static void verifyRadBigDecimal(RadBigDecimal value) {
        if(value == null) throw new InvalidParameterException(INVALID_VALUE);
    }

    /*//////////////////////////////////////////////// CONVERSIONS ///////////////////////////////////////////////////*/
    /**
     * Function to convert the given becquerel to curies
     * 
     * @param bq the becquerel value to be converted
     * @return the converted curie value
     */
    public static RadBigDecimal bqToCi(RadBigDecimal bq) throws InvalidParameterException {
        verifyRadBigDecimal(bq);
        
        return bq.multiply(RadBigDecimal.valueOf(2.7d).multiply(new RadBigDecimal(BigDecimal.TEN).pow(-11)));
    }

    /**
     * Function to convert the given becquerel to becquerels
     *
     * @param ci the curie value to be converted
     * @return the converted becquerel value
     */
    public static RadBigDecimal ciToBq(RadBigDecimal ci) throws InvalidParameterException {
        verifyRadBigDecimal(ci);

        return ci.multiply(RadBigDecimal.valueOf(3.7d).multiply(new RadBigDecimal(BigDecimal.TEN).pow(10)));
    }

    /**
     * Function to convert the given gray to rads
     *
     * @param gy the gray value to be converted
     * @return the converted rad value
     */
    public static RadBigDecimal gyToRad(RadBigDecimal gy) throws InvalidParameterException {
        verifyRadBigDecimal(gy);
        return gy.multiply(getSIMultiplyingFactor(SIPrefix.HECTO, false));
    }

    /**
     * Function to convert the given rad to grays
     *
     * @param rad the rad value to be converted
     * @return the converted gray value
     */
    public static RadBigDecimal radToGy(RadBigDecimal rad) throws InvalidParameterException {
        verifyRadBigDecimal(rad);
        return rad.multiply(getSIMultiplyingFactor(SIPrefix.CENTI, false));
    }

    /**
     * Function to convert the given sievert to rems
     *
     * @param sv the sievert value to be converted
     * @return the converted rem value
     */
    public static RadBigDecimal svToRem(RadBigDecimal sv) throws InvalidParameterException {
        return gyToRad(sv);
    }

    /**
     * Function to convert the given rem to sieverts
     *
     * @param rem the rem value to be converted
     * @return the converted sievert value
     */
    public static RadBigDecimal remToSv(RadBigDecimal rem) throws InvalidParameterException {
        return radToGy(rem);
    }

    /**
     * Function to convert the given coulomb/kilogram to roentgens
     *
     * @param ckg the coulomb/kilogram value to be converted
     * @return the converted roentgen value
     */
    public static RadBigDecimal ckgToR(RadBigDecimal ckg) throws InvalidParameterException {
        verifyRadBigDecimal(ckg);

        return ckg.multiply(RadBigDecimal.valueOf(3.88d).multiply(getSIMultiplyingFactor(SIPrefix.KILO, false)));
    }

    /**
     * Function to convert the given roentgen to coulombs/kilogram
     *
     * @param r the roentgen value to be converted
     * @return the converted coulomb/kilogram value
     */
    public static RadBigDecimal rToCkg(RadBigDecimal r) throws InvalidParameterException {
        verifyRadBigDecimal(r);
        
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
        YOTTA("Yotta (Y)", 24),
        ZETTA("Zetta (Z)", 21),
        EXA("Exa (E)", 18),
        PETA("Peta (P)", 15),
        TERA("Tera (T)", 12),
        GIGA("Giga (G)", 9),
        MEGA("Mega (M)", 6),
        KILO("Kilo (k)", 3),
        HECTO("Hecto (h)", 2),
        DEKA("Deka (da)", 1),
        BASE("----", 0),
        DECI("Deci (d)", -1),
        CENTI("Centi (c)", -2),
        MILLI("Milli (m)", -3),
        MICRO("Micro (µ)", -6),
        NANO("Nano (n)", -9),
        PICO("Pico (p)", -12),
        FEMTO("Femto (f)", -15),
        ATTO("Atto (a)", -18),
        ZEPTO("Zepto (z)", -21),
        YOCTO("Yocto (y)", -24);

        private final String val;
        private final int siVal;

        SIPrefix(String val, int siVal) {
            this.val = val;
            this.siVal = siVal;
        }

        public String getVal() { return val; }

        public int getSiVal() { return siVal; }

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
