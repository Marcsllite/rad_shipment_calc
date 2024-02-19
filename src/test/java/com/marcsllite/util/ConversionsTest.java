package com.marcsllite.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversionsTest {
    final static BigDecimal toBeConverted = BigDecimal.valueOf(7.5d);
    final static MathContext mc = Conversions.context;

    @Test
    @DisplayName("Convert 7.5 {0} to micro = {1}")
    void testConvertToMicroException() {
        String message = "Invalid SI Prefix";
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Conversions.convertToMicro(toBeConverted, null)
        );
        assertTrue(exception.getMessage().contains(message));
    }

    @ParameterizedTest(name = "Convert 7.5{0} to micro = {1}")
    @MethodSource("convertToMicro_data")
    void testConvertToMicro(Conversions.SIPrefix prefix, BigDecimal expected) {
        assertEquals(expected, Conversions.convertToMicro(toBeConverted, prefix));
    }

    private static Object[] convertToMicro_data() {
        return new Object[] {
            new Object[] {Conversions.SIPrefix.YOTTA, toBeConverted.multiply(BigDecimal.TEN.pow(30, mc), mc)},
            new Object[] {Conversions.SIPrefix.ZETTA, toBeConverted.multiply(BigDecimal.TEN.pow(27, mc), mc)},
            new Object[] {Conversions.SIPrefix.EXA, toBeConverted.multiply(BigDecimal.TEN.pow(24, mc), mc)},
            new Object[] {Conversions.SIPrefix.PETA, toBeConverted.multiply(BigDecimal.TEN.pow(21, mc), mc)},
            new Object[] {Conversions.SIPrefix.TERA, toBeConverted.multiply(BigDecimal.TEN.pow(18, mc), mc)},
            new Object[] {Conversions.SIPrefix.GIGA, toBeConverted.multiply(BigDecimal.TEN.pow(15, mc), mc)},
            new Object[] {Conversions.SIPrefix.MEGA, toBeConverted.multiply(BigDecimal.TEN.pow(12, mc), mc)},
            new Object[] {Conversions.SIPrefix.KILO, toBeConverted.multiply(BigDecimal.TEN.pow(9, mc), mc)},
            new Object[] {Conversions.SIPrefix.HECTO, toBeConverted.multiply(BigDecimal.TEN.pow(8, mc), mc)},
            new Object[] {Conversions.SIPrefix.DEKA, toBeConverted.multiply(BigDecimal.TEN.pow(7, mc), mc)},
            new Object[] {Conversions.SIPrefix.DECI, toBeConverted.multiply(BigDecimal.TEN.pow(5, mc), mc)},
            new Object[] {Conversions.SIPrefix.CENTI, toBeConverted.multiply(BigDecimal.TEN.pow(4, mc), mc)},
            new Object[] {Conversions.SIPrefix.MILLI, toBeConverted.multiply(BigDecimal.TEN.pow(3, mc), mc)},
            new Object[] {Conversions.SIPrefix.MICRO, toBeConverted},
            new Object[] {Conversions.SIPrefix.NANO, toBeConverted.multiply(BigDecimal.TEN.pow(-3, mc), mc)},
            new Object[] {Conversions.SIPrefix.PICO, toBeConverted.multiply(BigDecimal.TEN.pow(-6, mc), mc)},
            new Object[] {Conversions.SIPrefix.FEMTO, toBeConverted.multiply(BigDecimal.TEN.pow(-9, mc), mc)},
            new Object[] {Conversions.SIPrefix.ATTO, toBeConverted.multiply(BigDecimal.TEN.pow(-12, mc), mc)},
            new Object[] {Conversions.SIPrefix.ZEPTO, toBeConverted.multiply(BigDecimal.TEN.pow(-15, mc), mc)},
            new Object[] {Conversions.SIPrefix.YOCTO, toBeConverted.multiply(BigDecimal.TEN.pow(-18, mc), mc)}
        };
    }

    @Test
    @DisplayName("Convert 7.5 {0} to base = {1}")
    void testConvertToBaseException() {
        String message = "Invalid SI Prefix";
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Conversions.convertToBase(toBeConverted, null)
        );
        assertTrue(exception.getMessage().contains(message));
    }
    
    @ParameterizedTest(name = "Convert {0} {1} to base = {3}")
    @MethodSource("convertToBase_data")
    void testConvertToBase(BigDecimal value, Conversions.SIPrefix prefix, BigDecimal expected) {
        assertEquals(expected, Conversions.convertToBase(value, prefix));
    }

    private static Object[] convertToBase_data() {
        return new Object[] {
            new Object[] {toBeConverted, Conversions.SIPrefix.YOTTA, toBeConverted.multiply(BigDecimal.TEN.pow(24, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.ZETTA, toBeConverted.multiply(BigDecimal.TEN.pow(21, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.EXA, toBeConverted.multiply(BigDecimal.TEN.pow(18, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.PETA, toBeConverted.multiply(BigDecimal.TEN.pow(15), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.TERA, toBeConverted.multiply(BigDecimal.TEN.pow(12), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.GIGA, toBeConverted.multiply(BigDecimal.TEN.pow(9), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.MEGA, toBeConverted.multiply(BigDecimal.TEN.pow(6), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.KILO, toBeConverted.multiply(BigDecimal.TEN.pow(3), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.HECTO, toBeConverted.multiply(BigDecimal.TEN.pow(2), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.DEKA, toBeConverted.multiply(BigDecimal.TEN.pow(1), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.BASE, toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.DECI, toBeConverted.multiply(BigDecimal.TEN.pow(-1, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.CENTI, toBeConverted.multiply(BigDecimal.TEN.pow(-2, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.MILLI, toBeConverted.multiply(BigDecimal.TEN.pow(-3, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.MICRO, toBeConverted.multiply(BigDecimal.TEN.pow(-6, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.NANO, toBeConverted.multiply(BigDecimal.TEN.pow(-9, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.PICO, toBeConverted.multiply(BigDecimal.TEN.pow(-12, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.FEMTO, toBeConverted.multiply(BigDecimal.TEN.pow(-15, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.ATTO, toBeConverted.multiply(BigDecimal.TEN.pow(-18, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.ZEPTO, toBeConverted.multiply(BigDecimal.TEN.pow(-21, mc), mc)},
            new Object[] {toBeConverted, Conversions.SIPrefix.YOCTO, toBeConverted.multiply(BigDecimal.TEN.pow(-24, mc), mc)}
        };
    }

    @Test
    void testBqToCi() {
        BigDecimal expected = BigDecimal.valueOf(2.7).multiply(BigDecimal.TEN.pow(-11, mc), mc);
        BigDecimal actual = Conversions.bqToCi(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testCiToBq() {
        BigDecimal expected = BigDecimal.valueOf(3.7).multiply(BigDecimal.TEN.pow(10, mc), mc);
        BigDecimal actual = Conversions.ciToBq(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testGyToRad() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.gyToRad(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testRadToGy() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.radToGy(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testSvToRem() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.svToRem(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testRemToSv() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.remToSv(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testCkgToR() {
        BigDecimal expected = BigDecimal.valueOf(3.88).multiply(BigDecimal.TEN.pow(3, mc), mc);
        BigDecimal actual = Conversions.ckgToR(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testRToCkg() {
        BigDecimal expected = BigDecimal.valueOf(2.58).multiply(BigDecimal.TEN.pow(-4, mc), mc);
        BigDecimal actual = Conversions.rToCkg(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToYotta() {
        BigDecimal expected = BigDecimal.TEN.pow(-24, mc);
        BigDecimal actual = Conversions.baseToYotta(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToZetta() {
        BigDecimal expected = BigDecimal.TEN.pow(-21, mc);
        BigDecimal actual = Conversions.baseToZetta(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToExa() {
        BigDecimal expected = BigDecimal.TEN.pow(-18, mc);
        BigDecimal actual = Conversions.baseToExa(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToPeta() {
        BigDecimal expected = BigDecimal.TEN.pow(-15, mc);
        BigDecimal actual = Conversions.baseToPeta(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToTera() {
        BigDecimal expected = BigDecimal.TEN.pow(-12, mc);
        BigDecimal actual = Conversions.baseToTera(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToGiga() {
        BigDecimal expected = BigDecimal.TEN.pow(-9, mc);
        BigDecimal actual = Conversions.baseToGiga(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToMega() {
        BigDecimal expected = BigDecimal.TEN.pow(-6, mc);
        BigDecimal actual = Conversions.baseToMega(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToKilo() {
        BigDecimal expected = BigDecimal.TEN.pow(-3, mc);
        BigDecimal actual = Conversions.baseToKilo(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToHecto() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.baseToHecto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToDeka() {
        BigDecimal expected = BigDecimal.TEN.pow(-1, mc);
        BigDecimal actual = Conversions.baseToDeka(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToDeci() {
        BigDecimal expected = BigDecimal.TEN.pow(1, mc);
        BigDecimal actual = Conversions.baseToDeci(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToCenti() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.baseToCenti(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToMilli() {
        BigDecimal expected = BigDecimal.TEN.pow(3, mc);
        BigDecimal actual = Conversions.baseToMilli(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToMicro() {
        BigDecimal expected = BigDecimal.TEN.pow(6, mc);
        BigDecimal actual = Conversions.baseToMicro(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToNano() {
        BigDecimal expected = BigDecimal.TEN.pow(9, mc);
        BigDecimal actual = Conversions.baseToNano(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToPico() {
        BigDecimal expected = BigDecimal.TEN.pow(12, mc);
        BigDecimal actual = Conversions.baseToPico(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToFemto() {
        BigDecimal expected = BigDecimal.TEN.pow(15, mc);
        BigDecimal actual = Conversions.baseToFemto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToAtto() {
        BigDecimal expected = BigDecimal.TEN.pow(18, mc);
        BigDecimal actual = Conversions.baseToAtto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToZepto() {
        BigDecimal expected = BigDecimal.TEN.pow(21, mc);
        BigDecimal actual = Conversions.baseToZepto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testBaseToYocto() {
        BigDecimal expected = BigDecimal.TEN.pow(24, mc);
        BigDecimal actual = Conversions.baseToYocto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testYottaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(24, mc);
        BigDecimal actual = Conversions.yottaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testZettaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(21, mc);
        BigDecimal actual = Conversions.zettaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testExaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(18, mc);
        BigDecimal actual = Conversions.exaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testPetaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(15, mc);
        BigDecimal actual = Conversions.petaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testTeraToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(12, mc);
        BigDecimal actual = Conversions.teraToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testGigaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(9, mc);
        BigDecimal actual = Conversions.gigaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testMegaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(6, mc);
        BigDecimal actual = Conversions.megaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testKiloToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(3, mc);
        BigDecimal actual = Conversions.kiloToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testHectoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.hectoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testDekaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(1, mc);
        BigDecimal actual = Conversions.dekaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testDeciToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-1, mc);
        BigDecimal actual = Conversions.deciToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testCentiToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.centiToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testMilliToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-3, mc);
        BigDecimal actual = Conversions.milliToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testMicroToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-6, mc);
        BigDecimal actual = Conversions.microToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testNanoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-9, mc);
        BigDecimal actual = Conversions.nanoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testPicoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-12, mc);
        BigDecimal actual = Conversions.picoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testFemtoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-15, mc);
        BigDecimal actual = Conversions.femtoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testAttoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-18, mc);
        BigDecimal actual = Conversions.attoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testZeptoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-21, mc);
        BigDecimal actual = Conversions.zeptoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    void testYoctoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-24, mc);
        BigDecimal actual = Conversions.yoctoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }
}
