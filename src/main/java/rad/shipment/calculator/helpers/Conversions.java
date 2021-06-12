package rad.shipment.calculator.helpers;



import java.util.logging.Logger;

/* source: REMM(Radiation Emergency Medical Management) https://www.remm.nlm.gov/radmeasurement.htm
 * #################|           SI Units            |      Common Units     |
 * Radioactivity    |   becquerel           (Bq)    |   curie       (Ci)    |
 * Absorbed Dose    |   gray                (Gy)    |   rad                 |
 * Dose Equivalent  |   sievert             (Sv)    |   rem                 |
 * Exposure         |   coulomb/kilogram    (C/kg)  |   roentgen    (R)     |
 * ##########################################################################
 */

public class Conversions {
    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(CommandExecutor.class.getName());  // getting logger
    private static final int decimalBase = 10;
    private static final float yotta = (float)Math.pow(decimalBase, 24);   // Y
    private static final float zetta = (float)Math.pow(decimalBase, 21);   // Z
    private static final float exa   = (float)Math.pow(decimalBase, 18);   // E
    private static final float peta  = (float)Math.pow(decimalBase, 15);   // P
    private static final float tera  = (float)Math.pow(decimalBase, 12);   // T
    private static final float giga  = (float)Math.pow(decimalBase, 9);    // G
    private static final float mega  = (float)Math.pow(decimalBase, 6);    // M
    private static final float kilo  = (float)Math.pow(decimalBase, 3);    // k
    private static final float hecto = (float)Math.pow(decimalBase, 2);    // h
    private static final float deka  = (float)Math.pow(decimalBase, 1);    // da
    private static final float deci  = (float)Math.pow(decimalBase, -1);   // d
    private static final float centi = (float)Math.pow(decimalBase, -2);   // c
    private static final float milli = (float)Math.pow(decimalBase, -3);   // m
    private static final float micro = (float)Math.pow(decimalBase, -6);   // micro
    private static final float nano  = (float)Math.pow(decimalBase, -9);   // n
    private static final float pico  = (float)Math.pow(decimalBase, -12);  // p
    private static final float femto = (float)Math.pow(decimalBase, -15);  // f
    private static final float atto  = (float)Math.pow(decimalBase, -18);  // a
    private static final float zepto = (float)Math.pow(decimalBase, -21);  // z
    private static final float yocto = (float)Math.pow(decimalBase, -24);  // y
    
    /*//////////////////////////////////////////////// CONVERSIONS ///////////////////////////////////////////////////*/
    /**
     * Function to convert the given becquerel to curies
     * 
     * @param bq the becquerel value to be converted
     * @return the converted curie value
     */
    public static float BqToCi(float bq) { return bq * (float)(2.7 * (float)Math.pow(decimalBase, -11)); }

    /**
     * Function to convert the given becquerel to becquerels
     *
     * @param ci the curie value to be converted
     * @return the converted becquerel value
     */
    public static float CiToBq(float ci) { return ci * (float)(3.7 * (float)Math.pow(decimalBase, decimalBase));}

    /**
     * Function to convert the given gray to rads
     *
     * @param gy the gray value to be converted
     * @return the converted rad value
     */
    public static float GyToRad(float gy) { return gy * (float)Math.pow(decimalBase, 2); }

    /**
     * Function to convert the given rad to grays
     *
     * @param rad the rad value to be converted
     * @return the converted gray value
     */
    public static float RadToGy(float rad) { return rad * (float)Math.pow(decimalBase, -2); }

    /**
     * Function to convert the given sievert to rems
     *
     * @param sv the sievert value to be converted
     * @return the converted rem value
     */
    public static float SvToRem(float sv) { return sv * (float)Math.pow(decimalBase, 2); }

    /**
     * Function to convert the given rem to sieverts
     *
     * @param rem the rem value to be converted
     * @return the converted sievert value
     */
    public static float RemToSv(float rem) { return rem * (float)Math.pow(decimalBase, -2); }

    /**
     * Function to convert the given coulomb/kilogram to roentgens
     *
     * @param c_kg the coulomb/kilogram value to be converted
     * @return the converted roentgen value
     */
    public static float C_kgToR(float c_kg) { return c_kg * (float)(3.88 * (float)Math.pow(decimalBase, 3)); }

    /**
     * Function to convert the given roentgen to coulombs/kilogram
     *
     * @param R the roentgen value to be converted
     * @return the converted coulomb/kilogram value
     */
    public static float RToC_kg(float R) { return R * (float)(2.58 * (float)Math.pow(decimalBase, -4)); }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/
    /**
     * Helper function to convert a decimalBase unit value to a yotta unit value
     * 
     * @param base the value of the decimalBase to be converted
     * @return the yotta unit value of the given decimalBase
     */
    public static float baseToYotta(float base) { return base * yocto; }

    /**
     * Helper function to convert a decimalBase unit value to a zetta unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the zetta unit value of the given decimalBase
     */
    public static float baseToZetta(float base) { return base * zepto; }

    /**
     * Helper function to convert a decimalBase unit value to a exa unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the exa unit value of the given decimalBase
     */
    public static float baseToExa(float base) { return base * atto; }

    /**
     * Helper function to convert a decimalBase unit value to a peta unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the peta unit value of the given decimalBase
     */
    public static float baseToPeta(float base) { return base * femto; }

    /**
     * Helper function to convert a decimalBase unit value to a tera unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the tera unit value of the given decimalBase
     */
    public static float baseToTera(float base) { return base * pico; }

    /**
     * Helper function to convert a decimalBase unit value to a giga unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the giga unit value of the given decimalBase
     */
    public static float baseToGiga(float base) { return base * nano; }

    /**
     * Helper function to convert a decimalBase unit value to a mega unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the mega unit value of the given decimalBase
     */
    public static float baseToMega(float base) { return base * micro; }

    /**
     * Helper function to convert a decimalBase unit value to a kilo unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the kilo unit value of the given decimalBase
     */
    public static float baseToKilo(float base) { return base * milli; }

    /**
     * Helper function to convert a decimalBase unit value to a hecto unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the hecto unit value of the given decimalBase
     */
    public static float baseToHecto(float base) { return base * centi; }

    /**
     * Helper function to convert a decimalBase unit value to a deka unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the deka unit value of the given decimalBase
     */
    public static float baseToDeka(float base) { return base * deci; }

    /**
     * Helper function to convert a decimalBase unit value to a deci unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the deci unit value of the given decimalBase
     */
    public static float baseToDeci(float base) { return base * deka; }

    /**
     * Helper function to convert a decimalBase unit value to a centi unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the centi unit value of the given decimalBase
     */
    public static float baseToCenti(float base) { return base * hecto; }

    /**
     * Helper function to convert a decimalBase unit value to a milli unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the milli unit value of the given decimalBase
     */
    public static float baseToMilli(float base) { return base * kilo; }

    /**
     * Helper function to convert a decimalBase unit value to a micro unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the micro unit value of the given decimalBase
     */
    public static float baseToMicro(float base) { return base * mega; }

    /**
     * Helper function to convert a decimalBase unit value to a nano unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the nano unit value of the given decimalBase
     */
    public static float baseToNano(float base) { return base * giga; }

    /**
     * Helper function to convert a decimalBase unit value to a pico unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the pico unit value of the given decimalBase
     */
    public static float baseToPico(float base) { return base * tera; }

    /**
     * Helper function to convert a decimalBase unit value to a femto unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the femto unit value of the given decimalBase
     */
    public static float baseToFemto(float base) { return base * peta; }

    /**
     * Helper function to convert a decimalBase unit value to a atto unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the atto unit value of the given decimalBase
     */
    public static float baseToAtto(float base) { return base * exa; }

    /**
     * Helper function to convert a decimalBase unit value to a zepto unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the zepto unit value of the given decimalBase
     */
    public static float baseToZepto(float base) { return base * zetta; }

    /**
     * Helper function to convert a decimalBase unit value to a yocto unit value
     *
     * @param base the value of the decimalBase to be converted
     * @return the yocto unit value of the given decimalBase
     */
    public static float baseToYocto(float base) { return base * yotta; }

    /**
     * Helper function to convert a yotta unit value to a decimalBase unit value
     * 
     * @param yotta the yotta unit value to be converted
     * @return the decimalBase unit value of the given yotta
     */
    public static float YottaToBase(float yotta) { return yotta * Conversions.yotta; }

    /**
     * Helper function to convert a zetta unit value to a decimalBase unit value
     *
     * @param zetta the zetta unit value to be converted
     * @return the decimalBase unit value of the given zetta
     */
    public static float ZettaToBase(float zetta) { return zetta * Conversions.zetta; }

    /**
     * Helper function to convert a exa unit value to a decimalBase unit value
     *
     * @param exa the exa unit value to be converted
     * @return the decimalBase unit value of the given exa
     */
    public static float ExaToBase(float exa) { return exa * Conversions.exa; }

    /**
     * Helper function to convert a peta unit value to a decimalBase unit value
     *
     * @param peta the peta unit value to be converted
     * @return the decimalBase unit value of the given peta
     */
    public static float PetaToBase(float peta) { return peta * Conversions.peta; }

    /**
     * Helper function to convert a tera unit value to a decimalBase unit value
     *
     * @param tera the tera unit value to be converted
     * @return the decimalBase unit value of the given tera
     */
    public static float TeraToBase(float tera) { return tera * Conversions.tera; }

    /**
     * Helper function to convert a giga unit value to a decimalBase unit value
     *
     * @param giga the giga unit value to be converted
     * @return the decimalBase unit value of the given giga
     */
    public static float GigaToBase(float giga) { return giga * Conversions.giga; }

    /**
     * Helper function to convert a mega unit value to a decimalBase unit value
     *
     * @param mega the mega unit value to be converted
     * @return the decimalBase unit value of the given mega
     */
    public static float MegaToBase(float mega) { return mega * Conversions.mega; }

    /**
     * Helper function to convert a kilo unit value to a decimalBase unit value
     *
     * @param kilo the kilo unit value to be converted
     * @return the decimalBase unit value of the given kilo
     */
    public static float KiloToBase(float kilo) { return kilo * Conversions.kilo; }

    /**
     * Helper function to convert a hecto unit value to a decimalBase unit value
     *
     * @param hecto the hecto unit value to be converted
     * @return the decimalBase unit value of the given hecto
     */
    public static float HectoToBase(float hecto) { return hecto * Conversions.hecto; }

    /**
     * Helper function to convert a deka unit value to a decimalBase unit value
     *
     * @param deka the deka unit value to be converted
     * @return the decimalBase unit value of the given deka
     */
    public static float DekaToBase(float deka) { return deka * Conversions.deka; }

    /**
     * Helper function to convert a deci unit value to a decimalBase unit value
     *
     * @param deci the deci unit value to be converted
     * @return the decimalBase unit value of the given deci
     */
    public static float DeciToBase(float deci) { return deci * Conversions.deci; }

    /**
     * Helper function to convert a centi unit value to a decimalBase unit value
     *
     * @param centi the centi unit value to be converted
     * @return the decimalBase unit value of the given centi
     */
    public static float CentiToBase(float centi) { return centi * Conversions.centi; }

    /**
     * Helper function to convert a milli unit value to a decimalBase unit value
     *
     * @param milli the milli unit value to be converted
     * @return the decimalBase unit value of the given milli
     */
    public static float MilliToBase(float milli) { return milli * Conversions.milli; }

    /**
     * Helper function to convert a micro unit value to a decimalBase unit value
     *
     * @param micro the micro unit value to be converted
     * @return the decimalBase unit value of the given micro
     */
    public static float MicroToBase(float micro) { return micro * Conversions.micro; }

    /**
     * Helper function to convert a nano unit value to a decimalBase unit value
     *
     * @param nano the nano unit value to be converted
     * @return the decimalBase unit value of the given nano
     */
    public static float NanoToBase(float nano) { return nano * Conversions.nano; }

    /**
     * Helper function to convert a pico unit value to a decimalBase unit value
     *
     * @param pico the pico unit value to be converted
     * @return the decimalBase unit value of the given pico
     */
    public static float PicoToBase(float pico) { return pico * Conversions.pico; }

    /**
     * Helper function to convert a femto unit value to a decimalBase unit value
     *
     * @param femto the femto unit value to be converted
     * @return the decimalBase unit value of the given femto
     */
    public static float FemtoToBase(float femto) { return femto * Conversions.femto; }

    /**
     * Helper function to convert a atto unit value to a decimalBase unit value
     *
     * @param atto the atto unit value to be converted
     * @return the decimalBase unit value of the given atto
     */
    public static float AttoToBase(float atto) { return atto * Conversions.atto; }

    /**
     * Helper function to convert a zepto unit value to a decimalBase unit value
     *
     * @param zepto the zepto unit value to be converted
     * @return the decimalBase unit value of the given zepto
     */
    public static float ZeptoToBase(float zepto) { return zepto * Conversions.zepto; }

    /**
     * Helper function to convert a yocto unit value to a decimalBase unit value
     *
     * @param yocto the yocto unit value to be converted
     * @return the decimalBase unit value of the given yocto
     */
    public static float YoctoToBase(float yocto) { return yocto * Conversions.yocto; }
}
