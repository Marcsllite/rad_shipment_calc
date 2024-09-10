package com.marcsllite.util;

import org.codehaus.plexus.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class RadBigDecimal extends BigDecimal {
    public static final String INFINITY_STRING = "Infinity";
    public static final String NEG_INFINITY_STRING = "-Infinity";
    public static final String INFINITY_DISPLAY_STRING = "Unlimited";
    public static final String NEG_INFINITY_DISPLAY_STRING = "No data";
    public static final double INFINITY_DOUBLE = Double.MAX_VALUE;
    public static final double NEG_INFINITY_DOUBLE = -2.0D;
    public static final int DEFAULT_DEC_PRECISION = 2;
    public static final int DEFAULT_SIG_DIGITS = 5;
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final MathContext DEFAULT_CONTEXT = new MathContext(DEFAULT_SIG_DIGITS, DEFAULT_ROUNDING_MODE);
    public static final RadBigDecimal INFINITY_OBJ = RadBigDecimal.valueOf(INFINITY_DOUBLE);
    public static final RadBigDecimal NEG_INFINITY_OBJ = RadBigDecimal.valueOf(NEG_INFINITY_DOUBLE);
    private boolean infinity;
    private boolean negativeInfinity;

    public RadBigDecimal(String val) {
        super(parseString(val), DEFAULT_CONTEXT);
        String str = parseString(val);
        infinity = INFINITY_STRING.equalsIgnoreCase(str) ||
            INFINITY_DISPLAY_STRING.equalsIgnoreCase(str) ||
            Double.parseDouble(str) == INFINITY_DOUBLE;
        negativeInfinity = NEG_INFINITY_STRING.equalsIgnoreCase(str) ||
            NEG_INFINITY_DISPLAY_STRING.equalsIgnoreCase(str) ||
            Double.parseDouble(str) == NEG_INFINITY_DOUBLE;
    }

    public RadBigDecimal(BigDecimal val) { this(val.toString()); }
    
    public RadBigDecimal(BigInteger val) {
        this(new BigDecimal(val, RadBigDecimal.DEFAULT_CONTEXT));
    }

    public static RadBigDecimal valueOf(double val) {
        return new RadBigDecimal(Double.toString(val));
    }

    public static RadBigDecimal valueOf(long val) {
        RadBigDecimal ret = new RadBigDecimal(Long.toString(val));
        ret.setInfinity(val == Long.MAX_VALUE);
        return ret;
    }

    protected static String parseString(String str) {
        if(StringUtils.isBlank(str)) {
            return "0";
        } else if (INFINITY_STRING.equalsIgnoreCase(str) ||
            INFINITY_DISPLAY_STRING.equalsIgnoreCase(str)) {
            return Double.toString(INFINITY_DOUBLE);
        } else if (NEG_INFINITY_STRING.equalsIgnoreCase(str) ||
            NEG_INFINITY_DISPLAY_STRING.equalsIgnoreCase(str)) {
            return Double.toString(NEG_INFINITY_DOUBLE);
        }

        return str;
    }

    @Override
    public RadBigDecimal subtract(BigDecimal subtrahend) {
        RadBigDecimal temp = new RadBigDecimal(subtrahend);
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        } else if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        } else if(temp.isInfinity()) {
            return NEG_INFINITY_OBJ;
        } else if(temp.isNegativeInfinity()) {
            return INFINITY_OBJ;
        }

        return new RadBigDecimal(super.subtract(subtrahend, DEFAULT_CONTEXT));
    }

    @Override
    public RadBigDecimal multiply(BigDecimal multiplicand) {
        RadBigDecimal temp = new RadBigDecimal(multiplicand);
        if(this.isInfinity()) {
            return multiplicand.compareTo(BigDecimal.ZERO) < 0?
                NEG_INFINITY_OBJ : INFINITY_OBJ;
        } else if(this.isNegativeInfinity()) {
            return multiplicand.compareTo(BigDecimal.ZERO) < 0?
                INFINITY_OBJ : NEG_INFINITY_OBJ;
        } else if(temp.isInfinity()) {
            return this.compareTo(BigDecimal.ZERO) < 0?
                NEG_INFINITY_OBJ : INFINITY_OBJ;
        } else if(temp.isNegativeInfinity()) {
            return this.compareTo(BigDecimal.ZERO) < 0?
                INFINITY_OBJ : NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.multiply(multiplicand, DEFAULT_CONTEXT));
    }

    @Override
    public RadBigDecimal divide(BigDecimal divisor) {
        RadBigDecimal temp = new RadBigDecimal(divisor);
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        if(this.isInfinity()) {
            if(temp.isInfinity()) {
                return new RadBigDecimal("1");
            } else if(temp.isNegativeInfinity()) {
                return new RadBigDecimal("-1");
            }

            return divisor.compareTo(BigDecimal.ZERO) < 0?
                NEG_INFINITY_OBJ : INFINITY_OBJ;
        } else if(this.isNegativeInfinity()) {
            if(temp.isNegativeInfinity()) {
                return new RadBigDecimal("1");
            } else if(temp.isInfinity()) {
                return new RadBigDecimal("-1");
            }

            return divisor.compareTo(BigDecimal.ZERO) < 0?
                INFINITY_OBJ : NEG_INFINITY_OBJ;
        } else if(temp.isInfinity() || temp.isNegativeInfinity()) {
            return new RadBigDecimal("0");
        }

        return new RadBigDecimal(super.divide(divisor, DEFAULT_CONTEXT));
    }

    @Override
    public RadBigDecimal divideToIntegralValue(BigDecimal divisor) {
        return RadBigDecimal.valueOf(divide(divisor).intValue());
    }

    @Override
    public RadBigDecimal remainder(BigDecimal divisor) {
        return divide(divisor).subtract(divideToIntegralValue(divisor));
    }

    @Override
    public RadBigDecimal[] divideAndRemainder(BigDecimal divisor) {
        return new RadBigDecimal[]{
            divideToIntegralValue(divisor),
            remainder(divisor)
        };
    }

    public RadBigDecimal sqrt() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.sqrt(DEFAULT_CONTEXT));
    }

    @Override
    public RadBigDecimal pow(int n) {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        return new RadBigDecimal(super.pow(n, DEFAULT_CONTEXT));
    }

    @Override
    public RadBigDecimal abs() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }


        return new RadBigDecimal(super.abs(DEFAULT_CONTEXT));
    }

    @Override
    public RadBigDecimal negate() {
        if(this.isInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return INFINITY_OBJ;
        }

        return new RadBigDecimal(this.toBigInteger().negate());
    }

    @Override
    public RadBigDecimal plus() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        BigDecimal bd = super.plus(DEFAULT_CONTEXT)
            .setScale(DEFAULT_DEC_PRECISION +1, RoundingMode.HALF_UP)
            .setScale(DEFAULT_DEC_PRECISION, RoundingMode.HALF_UP);
        return new RadBigDecimal(bd);
    }

    public RadBigDecimal round() {
        if(this.isInfinity()) {
            return INFINITY_OBJ;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_OBJ;
        }

        BigDecimal bd = super.round(DEFAULT_CONTEXT)
            .setScale(DEFAULT_DEC_PRECISION +1, RoundingMode.HALF_UP)
            .setScale(DEFAULT_DEC_PRECISION, RoundingMode.HALF_UP);
        return new RadBigDecimal(bd);
    }


    @Override
    public int compareTo(BigDecimal val) {
        RadBigDecimal obj = new RadBigDecimal(val);

        if(this.isInfinity()) {
            return obj.isInfinity()? 0 : 1;
        } else if(this.isNegativeInfinity()) {
            return obj.isNegativeInfinity()? 0 : -1;
        }
        return super.compareTo(val);
    }

    @Override
    public boolean equals(Object x) {
        if (!(x instanceof RadBigDecimal obj)) {
            return false;
        }

        if(this.isInfinity()) {
            return obj.isInfinity();
        } else if(this.isNegativeInfinity()) {
            return obj.isNegativeInfinity();
        }
        return super.equals(x);
    }

    @Override
    public int hashCode() {
        if(this.isInfinity()) {
            return INFINITY_STRING.hashCode();
        } else if(this.isNegativeInfinity()) {
            return NEG_INFINITY_STRING.hashCode();
        }
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
        return super.toPlainString();
    }

    public String toDisplayString() {
        if(this.isInfinity()) {
            return INFINITY_DISPLAY_STRING;
        }

        if(this.isNegativeInfinity()) {
            return NEG_INFINITY_DISPLAY_STRING;
        }
        return super.toPlainString();
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
    public short shortValue() {
        if(this.isInfinity()) {
            return (short) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (short) NEG_INFINITY_DOUBLE;
        }

        return super.shortValue();
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
    public byte byteValue() {
        if(this.isInfinity()) {
            return (byte) INFINITY_DOUBLE;
        }

        if(this.isNegativeInfinity()) {
            return (byte) NEG_INFINITY_DOUBLE;
        }

        return super.byteValue();
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
