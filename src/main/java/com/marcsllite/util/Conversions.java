package com.marcsllite.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.InvalidParameterException;

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
    public static final int DEFAULT_RAD_SI_INDEX = 14;
    public static final int DEFAULT_RAD_INDEX = 1;
    public static final int DEFAULT_MASS_SI_INDEX = 10;
    public static final int DEFAULT_MASS_INDEX = 0;

    private Conversions() {}

    /*////////////////////////////////////////////////// HELPERS /////////////////////////////////////////////////////*/

    /**
     * Helper function to convert a given value to it's base value using its starting si prefix
     *
     * @param value the value to be converted
     * @param prefixIndex the index of the selected si prefix
     * @return the converted base value
     */
    public static BigDecimal convertToBase(BigDecimal value, int prefixIndex) {
        switch(prefixIndex) {
            case 0:  // user selected yotta
                return Conversions.yottaToBase(value);
            case 1:  // user selected zetta
                return Conversions.zettaToBase(value);
            case 2:  // user selected exa
                return Conversions.exaToBase(value);
            case 3:  // user selected peta
                return Conversions.petaToBase(value);
            case 4:  // user selected tera
                return Conversions.teraToBase(value);
            case 5:  // user selected giga
                return Conversions.gigaToBase(value);
            case 6:  // user selected mega
                return Conversions.megaToBase(value);
            case 7:  // user selected kilo
                return Conversions.kiloToBase(value);
            case 8:  // user selected hecto
                return Conversions.hectoToBase(value);
            case 9:  // user selected deka
                return Conversions.dekaToBase(value);
            case 11:  // user selected deci
                return Conversions.deciToBase(value);
            case 12:  // user selected centi
                return Conversions.centiToBase(value);
            case 13:  // user selected milli
                return Conversions.milliToBase(value);
            case 14:  // user selected micro
                return Conversions.microToBase(value);
            case 15:  // user selected nano
                return Conversions.nanoToBase(value);
            case 16:  // user selected pico
                return Conversions.picoToBase(value);
            case 17:  // user selected femto
                return Conversions.femtoToBase(value);
            case 18:  // user selected atto
                return Conversions.attoToBase(value);
            case 19:  // user selected zepto
                return Conversions.zeptoToBase(value);
            case 20:  // user selected yocto
                return Conversions.yoctoToBase(value);
            default: // user selected base or invalid index
                return value;
        }
    }

    /**
     * Helper function to convert a given value to it's micro value using its starting si prefix
     *
     * @param value the value to be converted
     * @param siPrefix the current si prefix of the given value
     * @return the converted micro value
     */
    public static BigDecimal convertToMicro(BigDecimal value, String siPrefix) {
        if(siPrefix == null || siPrefix.isBlank()) throw new InvalidParameterException("Invalid SI Prefix");

        switch(siPrefix) {
            case "Yotta (Y)": return Conversions.baseToMicro(Conversions.yottaToBase(value));
            case "Zetta (Z)": return Conversions.baseToMicro(Conversions.zettaToBase(value));
            case "Exa (E)": return Conversions.baseToMicro(Conversions.exaToBase(value));
            case "Peta (P)": return Conversions.baseToMicro(Conversions.petaToBase(value));
            case "Tera (T)": return Conversions.baseToMicro(Conversions.teraToBase(value));
            case "Giga (G)": return Conversions.baseToMicro(Conversions.gigaToBase(value));
            case "Mega (M)": return Conversions.baseToMicro(Conversions.megaToBase(value));
            case "Kilo (k)": return Conversions.baseToMicro(Conversions.kiloToBase(value));
            case "Hecto (h)": return Conversions.baseToMicro(Conversions.hectoToBase(value));
            case "Deka (da)": return Conversions.baseToMicro(Conversions.dekaToBase(value));
            case "Deci (d)": return Conversions.baseToMicro(Conversions.deciToBase(value));
            case "Centi (c)": return Conversions.baseToMicro(Conversions.centiToBase(value));
            case "Milli (m)": return Conversions.baseToMicro(Conversions.milliToBase(value));
            case "Nano (n)": return Conversions.baseToMicro(Conversions.nanoToBase(value));
            case "Pico (p)": return Conversions.baseToMicro(Conversions.picoToBase(value));
            case "Femto (f)": return Conversions.baseToMicro(Conversions.femtoToBase(value));
            case "Atto (a)": return Conversions.baseToMicro(Conversions.attoToBase(value));
            case "Zepto (z)": return Conversions.baseToMicro(Conversions.zeptoToBase(value));
            case "Yocto (y)": return Conversions.baseToMicro(Conversions.yoctoToBase(value));
            default: return value;
        }
    }
    /*//////////////////////////////////////////////// CONVERSIONS ///////////////////////////////////////////////////*/
    /**
     * Function to convert the given becquerel to curies
     * 
     * @param bq the becquerel value to be converted
     * @return the converted curie value
     */
    public static BigDecimal bqToCi(BigDecimal bq) { return bq.multiply(BigDecimal.valueOf(2.7d).multiply(BigDecimal.TEN.pow(-11, context), context), context); }

    /**
     * Function to convert the given becquerel to becquerels
     *
     * @param ci the curie value to be converted
     * @return the converted becquerel value
     */
    public static BigDecimal ciToBq(BigDecimal ci) { return ci.multiply(BigDecimal.valueOf(3.7d).multiply(BigDecimal.TEN.pow(10, context), context), context);}

    /**
     * Function to convert the given gray to rads
     *
     * @param gy the gray value to be converted
     * @return the converted rad value
     */
    public static BigDecimal gyToRad(BigDecimal gy) { return gy.multiply(BigDecimal.TEN.pow(2, context), context); }

    /**
     * Function to convert the given rad to grays
     *
     * @param rad the rad value to be converted
     * @return the converted gray value
     */
    public static BigDecimal radToGy(BigDecimal rad) { return rad.multiply(BigDecimal.TEN.pow(-2, context), context); }

    /**
     * Function to convert the given sievert to rems
     *
     * @param sv the sievert value to be converted
     * @return the converted rem value
     */
    public static BigDecimal svToRem(BigDecimal sv) { return sv.multiply(BigDecimal.TEN.pow(2, context), context); }

    /**
     * Function to convert the given rem to sieverts
     *
     * @param rem the rem value to be converted
     * @return the converted sievert value
     */
    public static BigDecimal remToSv(BigDecimal rem) { return rem.multiply(BigDecimal.TEN.pow(-2, context), context); }

    /**
     * Function to convert the given coulomb/kilogram to roentgens
     *
     * @param ckg the coulomb/kilogram value to be converted
     * @return the converted roentgen value
     */
    public static BigDecimal ckgToR(BigDecimal ckg) { return ckg.multiply(BigDecimal.valueOf(3.88d).multiply(BigDecimal.TEN.pow(3, context), context), context); }

    /**
     * Function to convert the given roentgen to coulombs/kilogram
     *
     * @param r the roentgen value to be converted
     * @return the converted coulomb/kilogram value
     */
    public static BigDecimal rToCkg(BigDecimal r) { return r.multiply(BigDecimal.valueOf(2.58d).multiply(BigDecimal.TEN.pow(-4, context), context), context); }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/
    /**
     * Helper function to convert a _BASE unit value to a YOTTA unit value
     * 
     * @param base the value of the _BASE to be converted
     * @return the YOTTA unit value of the given _BASE
     */
    public static BigDecimal baseToYotta(BigDecimal base) { return base.multiply(YOCTO, context); }

    /**
     * Helper function to convert a _BASE unit value to a ZETTA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the ZETTA unit value of the given _BASE
     */
    public static BigDecimal baseToZetta(BigDecimal base) { return base.multiply(ZEPTO, context); }

    /**
     * Helper function to convert a _BASE unit value to a EXA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the EXA unit value of the given _BASE
     */
    public static BigDecimal baseToExa(BigDecimal base) { return base.multiply(ATTO, context); }

    /**
     * Helper function to convert a _BASE unit value to a PETA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the PETA unit value of the given _BASE
     */
    public static BigDecimal baseToPeta(BigDecimal base) { return base.multiply(FEMTO, context); }

    /**
     * Helper function to convert a _BASE unit value to a TERA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the TERA unit value of the given _BASE
     */
    public static BigDecimal baseToTera(BigDecimal base) { return base.multiply(PICO, context); }

    /**
     * Helper function to convert a _BASE unit value to a GIGA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the GIGA unit value of the given _BASE
     */
    public static BigDecimal baseToGiga(BigDecimal base) { return base.multiply(NANO, context); }

    /**
     * Helper function to convert a _BASE unit value to a MEGA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the MEGA unit value of the given _BASE
     */
    public static BigDecimal baseToMega(BigDecimal base) { return base.multiply(MICRO, context); }

    /**
     * Helper function to convert a _BASE unit value to a KILO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the KILO unit value of the given _BASE
     */
    public static BigDecimal baseToKilo(BigDecimal base) { return base.multiply(MILLI, context); }

    /**
     * Helper function to convert a _BASE unit value to a HECTO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the HECTO unit value of the given _BASE
     */
    public static BigDecimal baseToHecto(BigDecimal base) { return base.multiply(CENTI, context); }

    /**
     * Helper function to convert a _BASE unit value to a DEKA unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the DEKA unit value of the given _BASE
     */
    public static BigDecimal baseToDeka(BigDecimal base) { return base.multiply(DECI, context); }

    /**
     * Helper function to convert a _BASE unit value to a DECI unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the DECI unit value of the given _BASE
     */
    public static BigDecimal baseToDeci(BigDecimal base) { return base.multiply(DEKA, context); }

    /**
     * Helper function to convert a _BASE unit value to a CENTI unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the CENTI unit value of the given _BASE
     */
    public static BigDecimal baseToCenti(BigDecimal base) { return base.multiply(HECTO, context); }

    /**
     * Helper function to convert a _BASE unit value to a MILLI unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the MILLI unit value of the given _BASE
     */
    public static BigDecimal baseToMilli(BigDecimal base) { return base.multiply(KILO, context); }

    /**
     * Helper function to convert a _BASE unit value to a MICRO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the MICRO unit value of the given _BASE
     */
    public static BigDecimal baseToMicro(BigDecimal base) { return base.multiply(MEGA, context); }

    /**
     * Helper function to convert a _BASE unit value to a NANO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the NANO unit value of the given _BASE
     */
    public static BigDecimal baseToNano(BigDecimal base) { return base.multiply(GIGA, context); }

    /**
     * Helper function to convert a _BASE unit value to a PICO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the PICO unit value of the given _BASE
     */
    public static BigDecimal baseToPico(BigDecimal base) { return base.multiply(TERA, context); }

    /**
     * Helper function to convert a _BASE unit value to a FEMTO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the FEMTO unit value of the given _BASE
     */
    public static BigDecimal baseToFemto(BigDecimal base) { return base.multiply(PETA, context); }

    /**
     * Helper function to convert a _BASE unit value to a ATTO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the ATTO unit value of the given _BASE
     */
    public static BigDecimal baseToAtto(BigDecimal base) { return base.multiply(EXA, context); }

    /**
     * Helper function to convert a _BASE unit value to a ZEPTO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the ZEPTO unit value of the given _BASE
     */
    public static BigDecimal baseToZepto(BigDecimal base) { return base.multiply(ZETTA, context); }

    /**
     * Helper function to convert a _BASE unit value to a YOCTO unit value
     *
     * @param base the value of the _BASE to be converted
     * @return the YOCTO unit value of the given _BASE
     */
    public static BigDecimal baseToYocto(BigDecimal base) { return base.multiply(YOTTA, context); }

    /**
     * Helper function to convert a YOTTA unit value to a _BASE unit value
     * 
     * @param yotta the YOTTA unit value to be converted
     * @return the _BASE unit value of the given YOTTA
     */
    public static BigDecimal yottaToBase(BigDecimal yotta) { return yotta.multiply(YOTTA, context); }

    /**
     * Helper function to convert a ZETTA unit value to a _BASE unit value
     *
     * @param zetta the ZETTA unit value to be converted
     * @return the _BASE unit value of the given ZETTA
     */
    public static BigDecimal zettaToBase(BigDecimal zetta) { return zetta.multiply(ZETTA, context); }

    /**
     * Helper function to convert a EXA unit value to a _BASE unit value
     *
     * @param exa the EXA unit value to be converted
     * @return the _BASE unit value of the given EXA
     */
    public static BigDecimal exaToBase(BigDecimal exa) { return exa.multiply(EXA, context); }

    /**
     * Helper function to convert a PETA unit value to a _BASE unit value
     *
     * @param peta the PETA unit value to be converted
     * @return the _BASE unit value of the given PETA
     */
    public static BigDecimal petaToBase(BigDecimal peta) { return peta.multiply(PETA, context); }

    /**
     * Helper function to convert a TERA unit value to a _BASE unit value
     *
     * @param tera the TERA unit value to be converted
     * @return the _BASE unit value of the given TERA
     */
    public static BigDecimal teraToBase(BigDecimal tera) { return tera.multiply(TERA, context); }

    /**
     * Helper function to convert a GIGA unit value to a _BASE unit value
     *
     * @param giga the GIGA unit value to be converted
     * @return the _BASE unit value of the given GIGA
     */
    public static BigDecimal gigaToBase(BigDecimal giga) { return giga.multiply(GIGA, context); }

    /**
     * Helper function to convert a MEGA unit value to a _BASE unit value
     *
     * @param mega the MEGA unit value to be converted
     * @return the _BASE unit value of the given MEGA
     */
    public static BigDecimal megaToBase(BigDecimal mega) { return mega.multiply(MEGA, context); }

    /**
     * Helper function to convert a KILO unit value to a _BASE unit value
     *
     * @param kilo the KILO unit value to be converted
     * @return the _BASE unit value of the given KILO
     */
    public static BigDecimal kiloToBase(BigDecimal kilo) { return kilo.multiply(KILO, context); }

    /**
     * Helper function to convert a HECTO unit value to a _BASE unit value
     *
     * @param hecto the HECTO unit value to be converted
     * @return the _BASE unit value of the given HECTO
     */
    public static BigDecimal hectoToBase(BigDecimal hecto) { return hecto.multiply(HECTO, context); }

    /**
     * Helper function to convert a DEKA unit value to a _BASE unit value
     *
     * @param deka the DEKA unit value to be converted
     * @return the _BASE unit value of the given DEKA
     */
    public static BigDecimal dekaToBase(BigDecimal deka) { return deka.multiply(DEKA, context); }

    /**
     * Helper function to convert a DECI unit value to a _BASE unit value
     *
     * @param deci the DECI unit value to be converted
     * @return the _BASE unit value of the given DECI
     */
    public static BigDecimal deciToBase(BigDecimal deci) { return deci.multiply(DECI, context); }

    /**
     * Helper function to convert a CENTI unit value to a _BASE unit value
     *
     * @param centi the CENTI unit value to be converted
     * @return the _BASE unit value of the given CENTI
     */
    public static BigDecimal centiToBase(BigDecimal centi) { return centi.multiply(CENTI, context); }

    /**
     * Helper function to convert a MILLI unit value to a _BASE unit value
     *
     * @param milli the MILLI unit value to be converted
     * @return the _BASE unit value of the given MILLI
     */
    public static BigDecimal milliToBase(BigDecimal milli) { return milli.multiply(MILLI, context); }

    /**
     * Helper function to convert a MICRO unit value to a _BASE unit value
     *
     * @param micro the MICRO unit value to be converted
     * @return the _BASE unit value of the given MICRO
     */
    public static BigDecimal microToBase(BigDecimal micro) { return micro.multiply(MICRO, context); }

    /**
     * Helper function to convert a NANO unit value to a _BASE unit value
     *
     * @param nano the NANO unit value to be converted
     * @return the _BASE unit value of the given NANO
     */
    public static BigDecimal nanoToBase(BigDecimal nano) { return nano.multiply(NANO, context); }

    /**
     * Helper function to convert a PICO unit value to a _BASE unit value
     *
     * @param pico the PICO unit value to be converted
     * @return the _BASE unit value of the given PICO
     */
    public static BigDecimal picoToBase(BigDecimal pico) { return pico.multiply(PICO, context); }

    /**
     * Helper function to convert a FEMTO unit value to a _BASE unit value
     *
     * @param femto the FEMTO unit value to be converted
     * @return the _BASE unit value of the given FEMTO
     */
    public static BigDecimal femtoToBase(BigDecimal femto) { return femto.multiply(FEMTO, context); }

    /**
     * Helper function to convert a ATTO unit value to a _BASE unit value
     *
     * @param atto the ATTO unit value to be converted
     * @return the _BASE unit value of the given ATTO
     */
    public static BigDecimal attoToBase(BigDecimal atto) { return atto.multiply(ATTO, context); }

    /**
     * Helper function to convert a ZEPTO unit value to a _BASE unit value
     *
     * @param zepto the ZEPTO unit value to be converted
     * @return the _BASE unit value of the given ZEPTO
     */
    public static BigDecimal zeptoToBase(BigDecimal zepto) { return zepto.multiply(ZEPTO, context); }

    /**
     * Helper function to convert a YOCTO unit value to a _BASE unit value
     *
     * @param yocto the YOCTO unit value to be converted
     * @return the _BASE unit value of the given YOCTO
     */
    public static BigDecimal yoctoToBase(BigDecimal yocto) { return yocto.multiply(YOCTO, context); }
}
