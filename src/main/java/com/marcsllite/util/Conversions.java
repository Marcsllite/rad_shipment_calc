package com.marcsllite.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class Conversions {
    /* source: REMM(Radiation Emergency Medical Management)
     * https://www.remm.nlm.gov/radmeasurement.htm
     * #################|           SI Units            |      Common Units     |
     * Radioactive      |   becquerel           (Bq)    |   curie       (Ci)    |
     * Absorbed Dose    |   gray                (Gy)    |   rad                 |
     * Dose Equivalent  |   sievert             (Sv)    |   rem                 |
     * Exposure         |   coulomb/kilogram    (C/kg)  |   roentgen    (R)     |
     * ##########################################################################
     *
     * NOTE: Accuracy is set by context
     */
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
        MICRO("Micro (\u00B5)"),
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
                val.replaceAll("^.*\\(", "")
                .replaceAll("\\)$", "");
        }

        public static SIPrefix toSIPrefix(String value) {
            for (SIPrefix enumValue : values()) {
                if (enumValue.getVal().equalsIgnoreCase(value) ||
                    enumValue.getAbbrVal().equalsIgnoreCase(value)) {
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
    
    // Declaring variables
    public static final MathContext context = new MathContext(2);
    private static final BigDecimal YOTTA = BigDecimal.TEN.pow(24, context);   // Y
    private static final BigDecimal ZETTA = BigDecimal.TEN.pow(21, context);   // Z
    private static final BigDecimal EXA = BigDecimal.TEN.pow(18, context);   // E
    private static final BigDecimal PETA = BigDecimal.TEN.pow(15, context);   // P
    private static final BigDecimal TERA = BigDecimal.TEN.pow(12, context);   // T
    private static final BigDecimal GIGA = BigDecimal.TEN.pow(9, context);    // G
    private static final BigDecimal MEGA = BigDecimal.TEN.pow(6, context);    // M
    private static final BigDecimal KILO = BigDecimal.TEN.pow(3, context);    // k
    private static final BigDecimal HECTO = BigDecimal.TEN.pow(2, context);    // h
    private static final BigDecimal DEKA = BigDecimal.TEN.pow(1, context);    // da
    private static final BigDecimal DECI = BigDecimal.TEN.pow(-1, context);   // d
    private static final BigDecimal CENTI = BigDecimal.TEN.pow(-2, context);   // c
    private static final BigDecimal MILLI = BigDecimal.TEN.pow(-3, context);   // m
    private static final BigDecimal MICRO = BigDecimal.TEN.pow(-6, context);   // MICRO
    private static final BigDecimal NANO = BigDecimal.TEN.pow(-9, context);   // n
    private static final BigDecimal PICO = BigDecimal.TEN.pow(-12, context);  // p
    private static final BigDecimal FEMTO = BigDecimal.TEN.pow(-15, context);  // f
    private static final BigDecimal ATTO = BigDecimal.TEN.pow(-18, context);  // a
    private static final BigDecimal ZEPTO = BigDecimal.TEN.pow(-21, context);  // z
    private static final BigDecimal YOCTO = BigDecimal.TEN.pow(-24, context);  // y
    private static final String INVALID_VALUE = "Invalid Value";
    private Conversions() {}

    /*////////////////////////////////////////////////// HELPERS /////////////////////////////////////////////////////*/

    /**
     * Helper function to convert a given value to it's base value using its starting SI prefix
     *
     * @param value the value to be converted
     * @param prefix the starting SI prefix
     * @return the converted base value
     */
    public static BigDecimal convertToBase(BigDecimal value, SIPrefix prefix) throws InvalidParameterException {
        if(value == null) throw new InvalidParameterException(INVALID_VALUE);
        if(prefix == null) throw new InvalidParameterException("Invalid SI Prefix");
        switch(prefix) {
            case YOTTA: return value.multiply(YOTTA, context);
            case ZETTA: return value.multiply(ZETTA, context);
            case EXA: return value.multiply(EXA, context);
            case PETA: return value.multiply(PETA, context);
            case TERA: return value.multiply(TERA, context);
            case GIGA: return value.multiply(GIGA, context);
            case MEGA: return value.multiply(MEGA, context);
            case KILO: return value.multiply(KILO, context);
            case HECTO: return value.multiply(HECTO, context);
            case DEKA: return value.multiply(DEKA, context);
            case DECI: return value.multiply(DECI, context);
            case CENTI: return value.multiply(CENTI, context);
            case MILLI: return value.multiply(MILLI, context);
            case MICRO: return value.multiply(MICRO, context);
            case NANO: return value.multiply(NANO, context);
            case PICO: return value.multiply(PICO, context);
            case FEMTO: return value.multiply(FEMTO, context);
            case ATTO: return value.multiply(ATTO, context);
            case ZEPTO: return value.multiply(ZEPTO, context);
            case YOCTO: return value.multiply(YOCTO, context);
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
    public static BigDecimal convertToPrefix(BigDecimal value, SIPrefix start, SIPrefix end) throws InvalidParameterException {
        if(value == null) throw new InvalidParameterException(INVALID_VALUE);
        if(start == null) throw new InvalidParameterException("Invalid starting SI Prefix");
        if(end == null) throw new InvalidParameterException("Invalid ending SI Prefix");
        
        BigDecimal baseValue = convertToBase(value, start);
        switch(end) {
            case YOTTA: return baseValue.multiply(YOCTO, context);
            case ZETTA: return baseValue.multiply(ZEPTO, context);
            case EXA: return baseValue.multiply(ATTO, context);
            case PETA: return baseValue.multiply(FEMTO, context);
            case TERA: return baseValue.multiply(PICO, context);
            case GIGA: return baseValue.multiply(NANO, context);
            case MEGA: return baseValue.multiply(MICRO, context);
            case KILO: return baseValue.multiply(MILLI, context);
            case HECTO: return baseValue.multiply(CENTI, context);
            case DEKA: return baseValue.multiply(DECI, context);
            case DECI: return baseValue.multiply(DEKA, context);
            case CENTI: return baseValue.multiply(HECTO, context);
            case MILLI: return baseValue.multiply(KILO, context);
            case MICRO: return baseValue.multiply(MEGA, context);
            case NANO: return baseValue.multiply(GIGA, context);
            case PICO: return baseValue.multiply(TERA, context);
            case FEMTO: return baseValue.multiply(PETA, context);
            case ATTO: return baseValue.multiply(EXA, context);
            case ZEPTO: return baseValue.multiply(ZETTA, context);
            case YOCTO: return baseValue.multiply(YOTTA, context);
            default: return baseValue;
        }
    }
    
    /*//////////////////////////////////////////////// CONVERSIONS ///////////////////////////////////////////////////*/
    /**
     * Function to convert the given becquerel to curies
     * 
     * @param bq the becquerel value to be converted
     * @return the converted curie value
     */
    public static BigDecimal bqToCi(BigDecimal bq) throws InvalidParameterException {
        if(bq == null) throw new InvalidParameterException(INVALID_VALUE);
        return bq.multiply(BigDecimal.valueOf(2.7d).multiply(BigDecimal.TEN.pow(-11, context), context), context);
    }

    /**
     * Function to convert the given becquerel to becquerels
     *
     * @param ci the curie value to be converted
     * @return the converted becquerel value
     */
    public static BigDecimal ciToBq(BigDecimal ci) throws InvalidParameterException {
        if(ci == null) throw new InvalidParameterException(INVALID_VALUE);
        return ci.multiply(BigDecimal.valueOf(3.7d).multiply(BigDecimal.TEN.pow(10, context), context), context);
    }

    /**
     * Function to convert the given gray to rads
     *
     * @param gy the gray value to be converted
     * @return the converted rad value
     */
    public static BigDecimal gyToRad(BigDecimal gy) throws InvalidParameterException {
        if(gy == null) throw new InvalidParameterException(INVALID_VALUE);
        return gy.multiply(BigDecimal.TEN.pow(2, context), context);
    }

    /**
     * Function to convert the given rad to grays
     *
     * @param rad the rad value to be converted
     * @return the converted gray value
     */
    public static BigDecimal radToGy(BigDecimal rad) throws InvalidParameterException {
        if(rad == null) throw new InvalidParameterException(INVALID_VALUE);
        return rad.multiply(BigDecimal.TEN.pow(-2, context), context);
    }

    /**
     * Function to convert the given sievert to rems
     *
     * @param sv the sievert value to be converted
     * @return the converted rem value
     */
    public static BigDecimal svToRem(BigDecimal sv) throws InvalidParameterException {
        if(sv == null) throw new InvalidParameterException(INVALID_VALUE);
        return sv.multiply(BigDecimal.TEN.pow(2, context), context);
    }

    /**
     * Function to convert the given rem to sieverts
     *
     * @param rem the rem value to be converted
     * @return the converted sievert value
     */
    public static BigDecimal remToSv(BigDecimal rem) throws InvalidParameterException {
        if(rem == null) throw new InvalidParameterException(INVALID_VALUE);
        return rem.multiply(BigDecimal.TEN.pow(-2, context), context);
    }

    /**
     * Function to convert the given coulomb/kilogram to roentgens
     *
     * @param ckg the coulomb/kilogram value to be converted
     * @return the converted roentgen value
     */
    public static BigDecimal ckgToR(BigDecimal ckg) throws InvalidParameterException {
        if(ckg == null) throw new InvalidParameterException(INVALID_VALUE);
        return ckg.multiply(BigDecimal.valueOf(3.88d).multiply(BigDecimal.TEN.pow(3, context), context), context);
    }

    /**
     * Function to convert the given roentgen to coulombs/kilogram
     *
     * @param r the roentgen value to be converted
     * @return the converted coulomb/kilogram value
     */
    public static BigDecimal rToCkg(BigDecimal r) throws InvalidParameterException {
        if(r == null) throw new InvalidParameterException(INVALID_VALUE);
        return r.multiply(BigDecimal.valueOf(2.58d).multiply(BigDecimal.TEN.pow(-4, context), context), context);
    }
}
