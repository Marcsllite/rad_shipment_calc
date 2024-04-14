package com.marcsllite.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class RadBigDecimal extends BigDecimal {
    private static final String INFINITY_STRING = "Infinity";
    private static final String NEG_INFINITY_STRING = "-Infinity";
    private static final double INFINITY_DOUBLE = Double.MAX_VALUE;
    private static final double NEG_INFINITY_DOUBLE = -2.0D;
    public static final RadBigDecimal INFINITY_OBJ = RadBigDecimal.valueOf(INFINITY_DOUBLE);
    public static final RadBigDecimal NEG_INFINITY_OBJ = RadBigDecimal.valueOf(NEG_INFINITY_DOUBLE);
    private boolean infinity;
    private boolean negativeInfinity;
    public static final int DEFAULT_PRECISION = 2;
    public static final RoundingMode defaultRoundingMode = RoundingMode.HALF_EVEN;
    public static final MathContext defaultContext = new MathContext(DEFAULT_PRECISION, defaultRoundingMode);

    public RadBigDecimal(String val) {
        super(parseString(val));
        setInfinity(INFINITY_STRING.equalsIgnoreCase(val));
        setNegativeInfinity(NEG_INFINITY_STRING.equalsIgnoreCase(val));
    }

    public RadBigDecimal(BigDecimal val) { this(val.toString()); }
    
    public RadBigDecimal(BigInteger val) {
        this(val.toString());
    }

    public static RadBigDecimal valueOf(double val) {
        return new RadBigDecimal(Double.toString(val));
    }

    public static RadBigDecimal valueOf(long val) {
        return new RadBigDecimal(Long.toString(val));
    }

    protected static String parseString(String str) {
        if(str == null) {
            return "0";
        } else if (INFINITY_STRING.equalsIgnoreCase(str)) {
            return INFINITY_OBJ.toString();
        } else if (NEG_INFINITY_STRING.equalsIgnoreCase(str)) {
            return NEG_INFINITY_OBJ.toString();
        }

        return str;
    }

    public static RadBigDecimal[] toRadBigDecimal(BigDecimal[] val) {
        RadBigDecimal[] ret = new RadBigDecimal[val.length];
        for (int i = 0; i < val.length; i++) {
            ret[i] = new RadBigDecimal(val[i]);
        }
        return ret;
    }

    @Override
    public RadBigDecimal subtract(BigDecimal subtrahend) {
        RadBigDecimal temp = (RadBigDecimal) subtrahend;
        if(temp.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(temp.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.subtract(subtrahend, defaultContext));
    }

    @Override
    public RadBigDecimal multiply(BigDecimal multiplicand) {
        RadBigDecimal temp = (RadBigDecimal) multiplicand;
        if(temp.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(temp.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.multiply(multiplicand, defaultContext));
    }

    @Override
    public RadBigDecimal divide(BigDecimal divisor) {
        RadBigDecimal temp = (RadBigDecimal) divisor;
        if(temp.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(temp.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return ((RadBigDecimal) divisor).isInfinity()?
            INFINITY_OBJ :
            new RadBigDecimal(super.divide(divisor, defaultContext));
    }

    @Override
    public RadBigDecimal divideToIntegralValue(BigDecimal divisor) {
        RadBigDecimal temp = (RadBigDecimal) divisor;
        if(temp.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(temp.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return ((RadBigDecimal) divisor).isInfinity()?
            INFINITY_OBJ :
            new RadBigDecimal(super.divideToIntegralValue(divisor, defaultContext));
    }

    @Override
    public RadBigDecimal remainder(BigDecimal divisor) {
        RadBigDecimal temp = (RadBigDecimal) divisor;
        if(temp.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(temp.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return ((RadBigDecimal) divisor).isInfinity()?
            INFINITY_OBJ :
            new RadBigDecimal(super.remainder(divisor, defaultContext));
    }

    @Override
    public RadBigDecimal[] divideAndRemainder(BigDecimal divisor) {
        RadBigDecimal temp = (RadBigDecimal) divisor;
        if(temp.isInfinity()) {
            return new RadBigDecimal[]{INFINITY_OBJ, RadBigDecimal.valueOf(0)};
        }

        if(temp.isNegativeInfinity()) {
            return new RadBigDecimal[]{NEG_INFINITY_OBJ, RadBigDecimal.valueOf(0)};
        }

        return RadBigDecimal.toRadBigDecimal(super.divideAndRemainder(divisor, defaultContext));
    }

    public RadBigDecimal sqrt() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.sqrt(defaultContext));
    }

    @Override
    public RadBigDecimal pow(int n) {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.pow(n, defaultContext));
    }

    @Override
    public RadBigDecimal abs() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }


        return new RadBigDecimal(super.abs(defaultContext));
    }

    @Override
    public RadBigDecimal negate() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.negate(defaultContext));
    }

    @Override
    public RadBigDecimal plus() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.plus(defaultContext));
    }

    public RadBigDecimal round() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.round(defaultContext));
    }


    @Override
    public int compareTo(BigDecimal val) {
        if(val == INFINITY_OBJ) {
            return this.isInfinity()? 0 : -1;
        }

        if(val == NEG_INFINITY_OBJ) {
            return (this == NEG_INFINITY_OBJ)? 0 : 1;
        }

        return super.compareTo(val);
    }

    @Override
    public boolean equals(Object x) {
        if (!(x instanceof RadBigDecimal)) {
            return false;
        }
        RadBigDecimal obj = (RadBigDecimal) x;

        if(this.isInfinity()) {
            return obj.isInfinity();
        } else if(this.isNegativeInfinity()) {
            return obj.isNegativeInfinity();
        }
        return super.equals(x);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        if(this.isInfinity()) {
            return INFINITY_STRING;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_STRING;
        }
        return super.toString();
    }

    @Override
    public BigInteger toBigInteger() {
        if(this.isInfinity()) {
            return BigInteger.valueOf((long) INFINITY_DOUBLE);
        }

        if(this.isNegativeInfinity()) {
            return BigInteger.valueOf((long) NEG_INFINITY_DOUBLE);
        }

        return super.toBigInteger();
    }


    @Override
    public BigInteger toBigIntegerExact() {
        if(this.isInfinity()) {
            return BigInteger.valueOf((long) INFINITY_DOUBLE);
        }

        if(this.isNegativeInfinity()) {
            return BigInteger.valueOf((long) NEG_INFINITY_DOUBLE);
        }

        return super.toBigIntegerExact();
    }

    @Override
    public long longValue() {
        if(this.isInfinity()) {
            return (long) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (long) NEG_INFINITY_DOUBLE;
        }

        return super.longValue();
    }

    @Override
    public long longValueExact() {
        if(this.isInfinity()) {
            return (long) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (long) NEG_INFINITY_DOUBLE;
        }

        return super.longValueExact();
    }

    @Override
    public int intValue() {
        if(this.isInfinity()) {
            return (int) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (int) NEG_INFINITY_DOUBLE;
        }

        return super.intValue();
    }

    @Override
    public int intValueExact() {
        if(this.isInfinity()) {
            return (int) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (int) NEG_INFINITY_DOUBLE;
        }

        return super.intValueExact();
    }

    @Override
    public short shortValueExact() {
        if(this.isInfinity()) {
            return (short) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (short) NEG_INFINITY_DOUBLE;
        }

        return super.shortValueExact();
    }

    @Override
    public byte byteValueExact() {
        if(this.isInfinity()) {
            return (byte) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (byte) NEG_INFINITY_DOUBLE;
        }

        return super.byteValueExact();
    }

    @Override
    public float floatValue() {
        if(this.isInfinity()) {
            return (float) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (float) NEG_INFINITY_DOUBLE;
        }

        return super.floatValue();
    }

    @Override
    public double doubleValue() {
        if(this.isInfinity()) {
            return INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_DOUBLE;
        }

        return super.doubleValue();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            new BigDecimal(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean isInfinity() {
        return infinity;
    }

    public void setInfinity(boolean infinity) {
        this.infinity = infinity;
    }

    public boolean isNegativeInfinity() {
        return negativeInfinity;
    }

    public void setNegativeInfinity(boolean negativeInfinity) {
        this.negativeInfinity = negativeInfinity;
    }
}
