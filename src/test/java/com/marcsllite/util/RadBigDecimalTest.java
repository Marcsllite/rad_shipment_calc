package com.marcsllite.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"EqualsWithItself", "AssertBetweenInconvertibleTypes"})
class RadBigDecimalTest {
    @Test
    void testConstructor_Null() {
        RadBigDecimal actual = new RadBigDecimal((String) null);
        assertEquals(0, actual.intValue());
    }

    @Test
    void testConstructor_Infinity() {
        String inf = "Infinity";
        RadBigDecimal actual = new RadBigDecimal(inf);
        assertEquals(RadBigDecimal.INFINITY_OBJ, actual);
        assertTrue(actual.isInfinity());
        assertEquals(inf, actual.toString());
    }

    @Test
    void testConstructor_NegInfinity() {
        String negativeInf = "-Infinity";
        RadBigDecimal actual = new RadBigDecimal(negativeInf);
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, actual);
        assertTrue(actual.isNegativeInfinity());
        assertEquals(negativeInf, actual.toString());
    }

    @Test
    void testConstructor_BigDecimal() {
        BigDecimal exp = new BigDecimal(124, RadBigDecimal.DEFAULT_CONTEXT);
        RadBigDecimal actual = new RadBigDecimal(exp);
        assertEquals(exp.toString(), actual.toString());
    }

    @Test
    void testConstructor_BigInteger() {
        BigInteger val = new BigInteger("13546");
        BigDecimal exp = new BigDecimal(val, RadBigDecimal.DEFAULT_CONTEXT);
        RadBigDecimal actual = new RadBigDecimal(val);
        assertEquals(exp.toString(), actual.toString());
    }

    @Test
    void testConstructor_String() {
        int exp = 45;
        RadBigDecimal actual = new RadBigDecimal(Integer.toString(exp));
        assertEquals(exp, actual.intValue());
    }

    @Test
    void testValueOf_Long() {
        long exp = Long.MAX_VALUE;
        String expStr = RadBigDecimal.INFINITY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        expStr = RadBigDecimal.INFINITY_DISPLAY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        exp = (long) RadBigDecimal.NEG_INFINITY_DOUBLE;
        expStr = RadBigDecimal.NEG_INFINITY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        expStr = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        exp = 5L;
        expStr = Long.toString(exp);
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));
    }

    @Test
    void testValueOf_Double() {
        double exp = RadBigDecimal.INFINITY_DOUBLE;
        String expStr = RadBigDecimal.INFINITY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        expStr = RadBigDecimal.INFINITY_DISPLAY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        exp = RadBigDecimal.NEG_INFINITY_DOUBLE;
        expStr = RadBigDecimal.NEG_INFINITY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        expStr = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));

        exp = 5D;
        expStr = Double.toString(exp);
        assertEquals(new RadBigDecimal(expStr), RadBigDecimal.valueOf(exp));
    }

    @Test
    void testToBigInteger() {
        BigInteger exp = BigInteger.valueOf((long) RadBigDecimal.INFINITY_DOUBLE);
        assertEquals(exp, RadBigDecimal.INFINITY_OBJ.toBigInteger());

        exp = BigInteger.valueOf((long) RadBigDecimal.NEG_INFINITY_DOUBLE);
        assertEquals(exp, RadBigDecimal.NEG_INFINITY_OBJ.toBigInteger());

        BigDecimal expBD = new BigDecimal("746", RadBigDecimal.DEFAULT_CONTEXT);
        assertEquals(expBD.toBigInteger(), new RadBigDecimal(expBD).toBigInteger());
    }

    @Test
    void testToBigIntegerExact() {
        BigInteger exp = BigInteger.valueOf((long) RadBigDecimal.INFINITY_DOUBLE);
        assertEquals(exp, RadBigDecimal.INFINITY_OBJ.toBigIntegerExact());

        exp = BigInteger.valueOf((long) RadBigDecimal.NEG_INFINITY_DOUBLE);
        assertEquals(exp, RadBigDecimal.NEG_INFINITY_OBJ.toBigIntegerExact());

        BigDecimal expBD = new BigDecimal("746", RadBigDecimal.DEFAULT_CONTEXT);
        assertEquals(expBD.toBigIntegerExact(), new RadBigDecimal(expBD).toBigIntegerExact());
    }

    @Test
    void testLongValue() {
        long exp = 5L;
        RadBigDecimal rbd = new RadBigDecimal(Long.toString(exp));
        assertEquals((long) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.longValue());
        assertEquals((long) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.longValue());
        assertEquals(exp, rbd.longValue());

        assertEquals((long) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.longValueExact());
        assertEquals((long) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.longValueExact());
        assertEquals(exp, rbd.longValueExact());
    }

    @Test
    void testIntValue() {
        int exp = 5;
        RadBigDecimal rbd = new RadBigDecimal(Integer.toString(exp));
        assertEquals((int) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.intValue());
        assertEquals((int) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.intValue());
        assertEquals(exp, rbd.intValue());

        assertEquals((int) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.intValueExact());
        assertEquals((int) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.intValueExact());
        assertEquals(exp, rbd.intValueExact());
    }

    @Test
    void testShortValue() {
        short exp = 5;
        RadBigDecimal rbd = new RadBigDecimal(Short.toString(exp));
        assertEquals((short) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.shortValue());
        assertEquals((short) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.shortValue());
        assertEquals(exp, rbd.shortValue());

        assertEquals((short) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.shortValueExact());
        assertEquals((short) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.shortValueExact());
        assertEquals(exp, rbd.shortValueExact());
    }

    @Test
    void testByteValue() {
        BigDecimal bd = new BigDecimal("10", RadBigDecimal.DEFAULT_CONTEXT);
        assertEquals((byte) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.byteValue());
        assertEquals((byte) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.byteValue());
        assertEquals(bd.byteValue(), new RadBigDecimal(bd).byteValue());
        
        assertEquals((byte) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.byteValueExact());
        assertEquals((byte) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.byteValueExact());
        assertEquals(bd.byteValueExact(), new RadBigDecimal(bd).byteValueExact());
    }

    @Test
    void testFloatValue() {
        float exp = 5;
        RadBigDecimal rbd = new RadBigDecimal(Float.toString(exp));
        assertEquals((float) RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.floatValue());
        assertEquals((float) RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.floatValue());
        assertEquals(exp, rbd.floatValue());
    }

    @Test
    void testDoubleValue() {
        double exp = 5L;
        RadBigDecimal rbd = new RadBigDecimal(Double.toString(exp));
        assertEquals(RadBigDecimal.INFINITY_DOUBLE, RadBigDecimal.INFINITY_OBJ.doubleValue());
        assertEquals(RadBigDecimal.NEG_INFINITY_DOUBLE, RadBigDecimal.NEG_INFINITY_OBJ.doubleValue());
        assertEquals(exp, rbd.doubleValue());
    }

    @Test
    void testToString() {
        String exp = "5";
        RadBigDecimal rbd = new RadBigDecimal(exp);
        assertEquals(RadBigDecimal.INFINITY_STRING, RadBigDecimal.INFINITY_OBJ.toString());
        assertEquals(RadBigDecimal.NEG_INFINITY_STRING, RadBigDecimal.NEG_INFINITY_OBJ.toString());
        assertEquals(exp, rbd.toString());
    }

    @Test
    void testToDisplayString() {
        String exp = "5";
        RadBigDecimal rbd = new RadBigDecimal(exp);
        assertEquals(RadBigDecimal.INFINITY_DISPLAY_STRING, RadBigDecimal.INFINITY_OBJ.toDisplayString());
        assertEquals(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING, RadBigDecimal.NEG_INFINITY_OBJ.toDisplayString());
        assertEquals(exp, rbd.toDisplayString());
    }

    @Test
    void testSubtract() {
        RadBigDecimal rbd1 = new RadBigDecimal("16");
        RadBigDecimal rbd2 = new RadBigDecimal("5");
        RadBigDecimal exp = new RadBigDecimal("11");
        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.subtract(rbd1));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.subtract(rbd1));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, rbd1.subtract(RadBigDecimal.INFINITY_OBJ));
        assertEquals(RadBigDecimal.INFINITY_OBJ, rbd1.subtract(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(exp, rbd1.subtract(rbd2));
        assertEquals(exp.negate(), rbd2.subtract(rbd1));
    }

    @Test
    void testMultiply() {
        RadBigDecimal negVal = new RadBigDecimal("-5");
        RadBigDecimal rbd1 = new RadBigDecimal("3");
        RadBigDecimal rbd2 = new RadBigDecimal("5");
        RadBigDecimal exp = new RadBigDecimal("15");
        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.multiply(rbd1));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.multiply(negVal));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.multiply(rbd1));
        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.multiply(negVal));
        assertEquals(RadBigDecimal.INFINITY_OBJ, rbd1.multiply(RadBigDecimal.INFINITY_OBJ));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, negVal.multiply(RadBigDecimal.INFINITY_OBJ));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, rbd1.multiply(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(RadBigDecimal.INFINITY_OBJ, negVal.multiply(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(exp, rbd1.multiply(rbd2));
        assertEquals(exp.negate(), rbd1.multiply(negVal));
    }

    @Test
    void testDivide_Exception() {
        String msg = "Cannot divide by zero";
        RadBigDecimal rbd = new RadBigDecimal("6");
        RadBigDecimal zeroVal = new RadBigDecimal("0");
        ArithmeticException ae = assertThrows(ArithmeticException.class, () -> rbd.divide(zeroVal));
        assertEquals(msg, ae.getMessage());
    }

    @Test
    void testDivide() {
        RadBigDecimal negVal = new RadBigDecimal("-5");
        RadBigDecimal rbd1 = new RadBigDecimal("10");
        RadBigDecimal rbd2 = new RadBigDecimal("5");
        RadBigDecimal exp1 = new RadBigDecimal("2");
        RadBigDecimal exp2 = new RadBigDecimal("0.5");

        assertEquals(RadBigDecimal.valueOf(1), RadBigDecimal.INFINITY_OBJ.divide(RadBigDecimal.INFINITY_OBJ));
        assertEquals(RadBigDecimal.valueOf(-1), RadBigDecimal.INFINITY_OBJ.divide(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.divide(negVal));
        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.divide(rbd1));

        assertEquals(RadBigDecimal.valueOf(-1), RadBigDecimal.NEG_INFINITY_OBJ.divide(RadBigDecimal.INFINITY_OBJ));
        assertEquals(RadBigDecimal.valueOf(1), RadBigDecimal.NEG_INFINITY_OBJ.divide(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.divide(negVal));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.divide(rbd1));

        assertEquals(RadBigDecimal.valueOf(0), rbd1.divide(RadBigDecimal.INFINITY_OBJ));
        assertEquals(RadBigDecimal.valueOf(0), rbd1.divide(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(exp1, rbd1.divide(rbd2));
        assertEquals(exp2, rbd2.divide(rbd1));
    }

    @Test
    void testDivideToIntegralValue() {
        RadBigDecimal rbd1 = new RadBigDecimal("10");
        RadBigDecimal rbd2 = new RadBigDecimal("5");
        RadBigDecimal exp1 = new RadBigDecimal("2");
        RadBigDecimal exp2 = new RadBigDecimal("0");
        assertEquals(exp1, rbd1.divideToIntegralValue(rbd2));
        assertEquals(exp2, rbd2.divideToIntegralValue(rbd1));
    }

    @Test
    void testRemainder() {
        RadBigDecimal rbd1 = new RadBigDecimal("10");
        RadBigDecimal rbd2 = new RadBigDecimal("5");
        RadBigDecimal exp1 = new RadBigDecimal("0");
        RadBigDecimal exp2 = new RadBigDecimal("0.5");
        assertEquals(exp1, rbd1.remainder(rbd2));
        assertEquals(exp2, rbd2.remainder(rbd1));
    }

    @Test
    void testDivideAndRemainder() {
        RadBigDecimal rbd1 = new RadBigDecimal("10");
        RadBigDecimal rbd2 = new RadBigDecimal("5");

        RadBigDecimal exp1Int = new RadBigDecimal("2");
        RadBigDecimal exp1Remain = new RadBigDecimal("0");
        RadBigDecimal[] exp1 = new RadBigDecimal[]{exp1Int, exp1Remain};

        RadBigDecimal exp2Int = new RadBigDecimal("0");
        RadBigDecimal exp2Remain = new RadBigDecimal("0.5");
        RadBigDecimal[] exp2 = new RadBigDecimal[]{exp2Int, exp2Remain};

        assertEquals(exp1[0], rbd1.divideAndRemainder(rbd2)[0]);
        assertEquals(exp1[1], rbd1.divideAndRemainder(rbd2)[1]);
        assertEquals(exp2[0], rbd2.divideAndRemainder(rbd1)[0]);
        assertEquals(exp2[1], rbd2.divideAndRemainder(rbd1)[1]);
    }

    @Test
    void testSqrt() {
        RadBigDecimal rbd1 = new RadBigDecimal("16");
        RadBigDecimal exp = new RadBigDecimal("4");

        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.sqrt());
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.sqrt());
        assertEquals(exp, rbd1.sqrt());
    }

    @Test
    void testPow() {
        int n = 2;
        RadBigDecimal rbd1 = new RadBigDecimal("4");
        RadBigDecimal exp = new RadBigDecimal("16");

        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.pow(n));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.pow(n));
        assertEquals(exp, rbd1.pow(n));
    }

    @Test
    void testAbs() {
        RadBigDecimal rbd1 = new RadBigDecimal("-16");
        RadBigDecimal exp = new RadBigDecimal("16");

        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.abs());
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.abs());
        assertEquals(exp, rbd1.abs());
    }

    @Test
    void testNegate() {
        RadBigDecimal rbd1 = new RadBigDecimal("12");
        RadBigDecimal exp = new RadBigDecimal("-12");

        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.negate());
        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.negate());
        assertEquals(exp, rbd1.negate());
    }

    @Test
    void testPlus() {
        RadBigDecimal rbd1 = new RadBigDecimal("12.448435");
        RadBigDecimal exp = new RadBigDecimal("12");

        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.plus());
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.plus());
        assertEquals(exp, rbd1.plus());
    }

    @Test
    void testRound() {
        RadBigDecimal rbd1 = new RadBigDecimal("12.448435");
        RadBigDecimal exp = new RadBigDecimal("12");

        assertEquals(RadBigDecimal.INFINITY_OBJ, RadBigDecimal.INFINITY_OBJ.round());
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, RadBigDecimal.NEG_INFINITY_OBJ.round());
        assertEquals(exp, rbd1.round());
    }

    @Test
    void testCompareTo() {
        BigDecimal bd = new BigDecimal("1", RadBigDecimal.DEFAULT_CONTEXT);
        BigDecimal bdInf = new BigDecimal(RadBigDecimal.INFINITY_DOUBLE);
        BigDecimal bdNegInf = new BigDecimal(RadBigDecimal.NEG_INFINITY_DOUBLE);
        RadBigDecimal rbd1 = new RadBigDecimal("2");
        RadBigDecimal rbd2 = new RadBigDecimal("4");

        assertEquals(0, RadBigDecimal.INFINITY_OBJ.compareTo(RadBigDecimal.INFINITY_OBJ));
        assertEquals(1, RadBigDecimal.INFINITY_OBJ.compareTo(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(0, RadBigDecimal.INFINITY_OBJ.compareTo(bdInf));
        assertEquals(1, RadBigDecimal.INFINITY_OBJ.compareTo(bdNegInf));
        assertEquals(1, RadBigDecimal.INFINITY_OBJ.compareTo(bd));
        assertEquals(1, RadBigDecimal.INFINITY_OBJ.compareTo(rbd1));
        assertEquals(1, RadBigDecimal.INFINITY_OBJ.compareTo(rbd2));

        assertEquals(-1, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(RadBigDecimal.INFINITY_OBJ));
        assertEquals(0, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(-1, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(bdInf));
        assertEquals(0, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(bdNegInf));
        assertEquals(-1, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(bd));
        assertEquals(-1, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(rbd1));
        assertEquals(-1, RadBigDecimal.NEG_INFINITY_OBJ.compareTo(rbd2));

        assertEquals(-1, rbd1.compareTo(RadBigDecimal.INFINITY_OBJ));
        assertEquals(1, rbd1.compareTo(RadBigDecimal.NEG_INFINITY_OBJ));
        assertEquals(-1, rbd1.compareTo(bdInf));
        assertEquals(1, rbd1.compareTo(bdNegInf));
        assertEquals(1, rbd1.compareTo(bd));
        assertEquals(0, rbd1.compareTo(rbd1));
        assertEquals(-1, rbd1.compareTo(rbd2));
    }

    @Test
    void testEquals() {
        RadBigDecimal rbd = new RadBigDecimal("21");
        assertNotEquals(1L, rbd);

        RadBigDecimal rbd2 = new RadBigDecimal("21");
        RadBigDecimal rbd3 = new RadBigDecimal("5");
        assertEquals(rbd2, rbd2);
        assertEquals(rbd2.hashCode(), rbd2.hashCode());
        assertNotEquals(rbd2, rbd3);
        assertNotEquals(rbd2.hashCode(), rbd3.hashCode());

        assertNotEquals(RadBigDecimal.INFINITY_OBJ, rbd);
        assertNotEquals(RadBigDecimal.INFINITY_OBJ.hashCode(), rbd.hashCode());
        assertEquals(RadBigDecimal.INFINITY_OBJ, new RadBigDecimal("Infinity"));
        assertEquals(RadBigDecimal.INFINITY_OBJ.hashCode(), new RadBigDecimal("Infinity").hashCode());

        assertNotEquals(RadBigDecimal.NEG_INFINITY_OBJ, rbd);
        assertNotEquals(RadBigDecimal.NEG_INFINITY_OBJ.hashCode(), rbd.hashCode());
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, new RadBigDecimal("-Infinity"));
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ.hashCode(), new RadBigDecimal("-Infinity").hashCode());
    }
}