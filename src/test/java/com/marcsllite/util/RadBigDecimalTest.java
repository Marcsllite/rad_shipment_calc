package com.marcsllite.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

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
        BigDecimal exp = new BigDecimal(124);
        RadBigDecimal actual = new RadBigDecimal(exp);
        assertEquals(exp.toString(), actual.toString());
    }

    @Test
    void testConstructor_BigInteger() {
        BigInteger exp = new BigInteger("13546");
        RadBigDecimal actual = new RadBigDecimal(exp);
        assertEquals(exp.toString(), actual.toString());
    }

    @Test
    void testConstructor_String() {
        int exp = 45;
        RadBigDecimal actual = new RadBigDecimal(Integer.toString(exp));
        assertEquals(exp, actual.intValue());
    }
}