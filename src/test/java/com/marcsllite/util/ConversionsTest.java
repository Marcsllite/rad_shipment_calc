package com.marcsllite.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.InvalidParameterException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ConversionsTest {
    BigDecimal toBeConverted = BigDecimal.valueOf(7.5d);
    MathContext mc = Conversions.context;

    @Parameters(method = "convertToMicroException_data")
    public void testConvertToMicroExceptionChecker(String siPrefix, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Conversions.convertToMicro(toBeConverted, siPrefix)
        );
        assertTrue(exception.getMessage().contains(message));
    }

    private Object[] convertToMicroException_data() {
        String message = "Invalid SI Prefix";

        return new Object[] {
            new Object[] {null, message},
            new Object[] {" ", message},
            new Object[] {"", message}
        };
    }

    @Test
    @Parameters(method = "convertToMicro_data")
    public void testConvertToMicroChecker(String siPrefix, BigDecimal expected) {
        assertEquals(expected, Conversions.convertToMicro(toBeConverted, siPrefix));
    }

    private Object[] convertToMicro_data() {
        return new Object[] {
            new Object[] {"Yotta (Y)", toBeConverted.multiply(BigDecimal.TEN.pow(30, mc), mc)},
            new Object[] {"Zetta (Z)", toBeConverted.multiply(BigDecimal.TEN.pow(27, mc), mc)},
            new Object[] {"Exa (E)", toBeConverted.multiply(BigDecimal.TEN.pow(24, mc), mc)},
            new Object[] {"Peta (P)", toBeConverted.multiply(BigDecimal.TEN.pow(21, mc), mc)},
            new Object[] {"Tera (T)", toBeConverted.multiply(BigDecimal.TEN.pow(18, mc), mc)},
            new Object[] {"Giga (G)", toBeConverted.multiply(BigDecimal.TEN.pow(15, mc), mc)},
            new Object[] {"Mega (M)", toBeConverted.multiply(BigDecimal.TEN.pow(12, mc), mc)},
            new Object[] {"Kilo (k)", toBeConverted.multiply(BigDecimal.TEN.pow(9, mc), mc)},
            new Object[] {"Hecto (h)", toBeConverted.multiply(BigDecimal.TEN.pow(8, mc), mc)},
            new Object[] {"Deka (da)", toBeConverted.multiply(BigDecimal.TEN.pow(7, mc), mc)},
            new Object[] {"Deci (d)", toBeConverted.multiply(BigDecimal.TEN.pow(5, mc), mc)},
            new Object[] {"Centi (c)", toBeConverted.multiply(BigDecimal.TEN.pow(4, mc), mc)},
            new Object[] {"Milli (m)", toBeConverted.multiply(BigDecimal.TEN.pow(3, mc), mc)},
            new Object[] {"Nano (n)", toBeConverted.multiply(BigDecimal.TEN.pow(-3, mc), mc)},
            new Object[] {"Pico (p)", toBeConverted.multiply(BigDecimal.TEN.pow(-6, mc), mc)},
            new Object[] {"Femto (f)", toBeConverted.multiply(BigDecimal.TEN.pow(-9, mc), mc)},
            new Object[] {"Atto (a)", toBeConverted.multiply(BigDecimal.TEN.pow(-12, mc), mc)},
            new Object[] {"Zepto (z)", toBeConverted.multiply(BigDecimal.TEN.pow(-15, mc), mc)},
            new Object[] {"Yocto (y)", toBeConverted.multiply(BigDecimal.TEN.pow(-18, mc), mc)},
            new Object[] {"fakePrefix", toBeConverted}
        };
    }

    @Test
    @Parameters(method = "convertToBase_data")
    public void convertToBaseChecker(BigDecimal value, int prefixIndex, BigDecimal expected) {
        assertEquals(expected, Conversions.convertToBase(value, prefixIndex));
    }

    private Object[] convertToBase_data() {
        return new Object[] {
            new Object[] {toBeConverted, -1, toBeConverted},
            new Object[] {toBeConverted, 0, toBeConverted.multiply(BigDecimal.TEN.pow(24, mc), mc)},
            new Object[] {toBeConverted, 1, toBeConverted.multiply(BigDecimal.TEN.pow(21, mc), mc)},
            new Object[] {toBeConverted, 2, toBeConverted.multiply(BigDecimal.TEN.pow(18, mc), mc)},
            new Object[] {toBeConverted, 3, toBeConverted.multiply(BigDecimal.TEN.pow(15), mc)},
            new Object[] {toBeConverted, 4, toBeConverted.multiply(BigDecimal.TEN.pow(12), mc)},
            new Object[] {toBeConverted, 5, toBeConverted.multiply(BigDecimal.TEN.pow(9), mc)},
            new Object[] {toBeConverted, 6, toBeConverted.multiply(BigDecimal.TEN.pow(6), mc)},
            new Object[] {toBeConverted, 7, toBeConverted.multiply(BigDecimal.TEN.pow(3), mc)},
            new Object[] {toBeConverted, 8, toBeConverted.multiply(BigDecimal.TEN.pow(2), mc)},
            new Object[] {toBeConverted, 9, toBeConverted.multiply(BigDecimal.TEN.pow(1), mc)},
            new Object[] {toBeConverted, 10, toBeConverted},
            new Object[] {toBeConverted, 11, toBeConverted.multiply(BigDecimal.TEN.pow(-1, mc), mc)},
            new Object[] {toBeConverted, 12, toBeConverted.multiply(BigDecimal.TEN.pow(-2, mc), mc)},
            new Object[] {toBeConverted, 13, toBeConverted.multiply(BigDecimal.TEN.pow(-3, mc), mc)},
            new Object[] {toBeConverted, 14, toBeConverted.multiply(BigDecimal.TEN.pow(-6, mc), mc)},
            new Object[] {toBeConverted, 15, toBeConverted.multiply(BigDecimal.TEN.pow(-9, mc), mc)},
            new Object[] {toBeConverted, 16, toBeConverted.multiply(BigDecimal.TEN.pow(-12, mc), mc)},
            new Object[] {toBeConverted, 17, toBeConverted.multiply(BigDecimal.TEN.pow(-15, mc), mc)},
            new Object[] {toBeConverted, 18, toBeConverted.multiply(BigDecimal.TEN.pow(-18, mc), mc)},
            new Object[] {toBeConverted, 19, toBeConverted.multiply(BigDecimal.TEN.pow(-21, mc), mc)},
            new Object[] {toBeConverted, 20, toBeConverted.multiply(BigDecimal.TEN.pow(-24, mc), mc)}
        };
    }

    @Test
    public void testGetSiPrefixes() {
        List<String> expected = Util.getList("siPrefixes");

        assumeNotNull(expected);
        assumeFalse(expected.isEmpty());

        assertEquals(expected, Conversions.getSiPrefixes());
    }

    @Test
    public void testGetRadioactiveUnits() {
        List<String> expected = Util.getList("radioactiveUnits");

        assumeNotNull(expected);
        assumeFalse(expected.isEmpty());

        assertEquals(expected, Conversions.getRadioactiveUnits());
    }

    @Test
    public void testGetMassUnits() {
        List<String> expected = Util.getList("massUnits");

        assumeNotNull(expected);
        assumeFalse(expected.isEmpty());

        assertEquals(expected, Conversions.getMassUnits());
    }

    @Test
    public void testBqToCi() {
        BigDecimal expected = BigDecimal.valueOf(2.7).multiply(BigDecimal.TEN.pow(-11, mc), mc);
        BigDecimal actual = Conversions.bqToCi(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testCiToBq() {
        BigDecimal expected = BigDecimal.valueOf(3.7).multiply(BigDecimal.TEN.pow(10, mc), mc);
        BigDecimal actual = Conversions.ciToBq(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testGyToRad() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.gyToRad(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testRadToGy() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.radToGy(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testSvToRem() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.svToRem(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testRemToSv() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.remToSv(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testCkgToR() {
        BigDecimal expected = BigDecimal.valueOf(3.88).multiply(BigDecimal.TEN.pow(3, mc), mc);
        BigDecimal actual = Conversions.ckgToR(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testRToCkg() {
        BigDecimal expected = BigDecimal.valueOf(2.58).multiply(BigDecimal.TEN.pow(-4, mc), mc);
        BigDecimal actual = Conversions.rToCkg(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToYotta() {
        BigDecimal expected = BigDecimal.TEN.pow(-24, mc);
        BigDecimal actual = Conversions.baseToYotta(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToZetta() {
        BigDecimal expected = BigDecimal.TEN.pow(-21, mc);
        BigDecimal actual = Conversions.baseToZetta(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToExa() {
        BigDecimal expected = BigDecimal.TEN.pow(-18, mc);
        BigDecimal actual = Conversions.baseToExa(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToPeta() {
        BigDecimal expected = BigDecimal.TEN.pow(-15, mc);
        BigDecimal actual = Conversions.baseToPeta(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToTera() {
        BigDecimal expected = BigDecimal.TEN.pow(-12, mc);
        BigDecimal actual = Conversions.baseToTera(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToGiga() {
        BigDecimal expected = BigDecimal.TEN.pow(-9, mc);
        BigDecimal actual = Conversions.baseToGiga(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToMega() {
        BigDecimal expected = BigDecimal.TEN.pow(-6, mc);
        BigDecimal actual = Conversions.baseToMega(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToKilo() {
        BigDecimal expected = BigDecimal.TEN.pow(-3, mc);
        BigDecimal actual = Conversions.baseToKilo(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToHecto() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.baseToHecto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToDeka() {
        BigDecimal expected = BigDecimal.TEN.pow(-1, mc);
        BigDecimal actual = Conversions.baseToDeka(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToDeci() {
        BigDecimal expected = BigDecimal.TEN.pow(1, mc);
        BigDecimal actual = Conversions.baseToDeci(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToCenti() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.baseToCenti(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToMilli() {
        BigDecimal expected = BigDecimal.TEN.pow(3, mc);
        BigDecimal actual = Conversions.baseToMilli(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToMicro() {
        BigDecimal expected = BigDecimal.TEN.pow(6, mc);
        BigDecimal actual = Conversions.baseToMicro(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToNano() {
        BigDecimal expected = BigDecimal.TEN.pow(9, mc);
        BigDecimal actual = Conversions.baseToNano(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToPico() {
        BigDecimal expected = BigDecimal.TEN.pow(12, mc);
        BigDecimal actual = Conversions.baseToPico(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToFemto() {
        BigDecimal expected = BigDecimal.TEN.pow(15, mc);
        BigDecimal actual = Conversions.baseToFemto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToAtto() {
        BigDecimal expected = BigDecimal.TEN.pow(18, mc);
        BigDecimal actual = Conversions.baseToAtto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToZepto() {
        BigDecimal expected = BigDecimal.TEN.pow(21, mc);
        BigDecimal actual = Conversions.baseToZepto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testBaseToYocto() {
        BigDecimal expected = BigDecimal.TEN.pow(24, mc);
        BigDecimal actual = Conversions.baseToYocto(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testYottaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(24, mc);
        BigDecimal actual = Conversions.yottaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testZettaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(21, mc);
        BigDecimal actual = Conversions.zettaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testExaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(18, mc);
        BigDecimal actual = Conversions.exaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testPetaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(15, mc);
        BigDecimal actual = Conversions.petaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testTeraToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(12, mc);
        BigDecimal actual = Conversions.teraToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testGigaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(9, mc);
        BigDecimal actual = Conversions.gigaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testMegaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(6, mc);
        BigDecimal actual = Conversions.megaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testKiloToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(3, mc);
        BigDecimal actual = Conversions.kiloToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testHectoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(2, mc);
        BigDecimal actual = Conversions.hectoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testDekaToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(1, mc);
        BigDecimal actual = Conversions.dekaToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeciToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-1, mc);
        BigDecimal actual = Conversions.deciToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testCentiToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-2, mc);
        BigDecimal actual = Conversions.centiToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testMilliToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-3, mc);
        BigDecimal actual = Conversions.milliToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testMicroToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-6, mc);
        BigDecimal actual = Conversions.microToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testNanoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-9, mc);
        BigDecimal actual = Conversions.nanoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testPicoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-12, mc);
        BigDecimal actual = Conversions.picoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testFemtoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-15, mc);
        BigDecimal actual = Conversions.femtoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testAttoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-18, mc);
        BigDecimal actual = Conversions.attoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testZeptoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-21, mc);
        BigDecimal actual = Conversions.zeptoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }

    @Test
    public void testYoctoToBase() {
        BigDecimal expected = BigDecimal.TEN.pow(-24, mc);
        BigDecimal actual = Conversions.yoctoToBase(BigDecimal.ONE);

        assertEquals(expected, actual);
    }
}
