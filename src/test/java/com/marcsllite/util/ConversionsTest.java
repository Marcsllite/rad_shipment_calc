package com.marcsllite.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;
import java.util.List;

import com.marcsllite.App;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ConversionsTest {
    float toBeConverted = 1.0f;

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
    public void testConvertToMicroChecker(String siPrefix, float expected) {
        assertEquals(expected, Conversions.convertToMicro(toBeConverted, siPrefix), 0.0001f);
    }

    private Object[] convertToMicro_data() {
        return new Object[] {
            new Object[] {"Yotta (Y)", 1.0e30f},
            new Object[] {"Zetta (Z)", 1.0e27f},
            new Object[] {"Exa (E)", 1.0e24f},
            new Object[] {"Peta (P)", 1.0e21f},
            new Object[] {"Tera (T)", 1.0e18f},
            new Object[] {"Giga (G)", 1.0e15f},
            new Object[] {"Mega (M)", 1.0e12f},
            new Object[] {"Kilo (k)", 1.0e9f},
            new Object[] {"Hecto (h)", 1.0e8f},
            new Object[] {"Deka (da)", 1.0e7f},
            new Object[] {"Deci (d)", 1.0e5f},
            new Object[] {"Centi (c)", 1.0e4f},
            new Object[] {"Milli (m)", 1.0e3f},
            new Object[] {"Nano (n)", 1.0e-3f},
            new Object[] {"Pico (p)", 1.0e-6f},
            new Object[] {"Femto (f)", 1.0e-9f},
            new Object[] {"Atto (a)", 1.0e-12f},
            new Object[] {"Zepto (z)", 1.0e-15f},
            new Object[] {"Yocto (y)", 1.0e-18f},
            new Object[] {"fakePrefix", toBeConverted}
        };
    }

    @Test
    @Parameters(method = "convertToBase_data")
    public void convertToBaseChecker(float value, int prefixIndex, float expected) {
        assertEquals(expected, Conversions.convertToBase(value, prefixIndex), 0f);
    }

    private Object[] convertToBase_data() {
        return new Object[] {
            new Object[] {toBeConverted, -1, toBeConverted},
            new Object[] {1f, 0, 1.0e24f},
            new Object[] {1f, 1, 1.0e21f},
            new Object[] {1f, 2, 1.0e18f},
            new Object[] {1f, 3, 1.0e15f},
            new Object[] {1f, 4, 1.0e12f},
            new Object[] {1f, 5, 1.0e9f},
            new Object[] {1f, 6, 1.0e6f},
            new Object[] {1f, 7, 1.0e3f},
            new Object[] {1f, 8, 1.0e2f},
            new Object[] {1f, 9, 1.0e1f},
            new Object[] {1f, 10, toBeConverted},
            new Object[] {1f, 11, 1.0e-1f},
            new Object[] {1f, 12, 1.0e-2f},
            new Object[] {1f, 13, 1.0e-3f},
            new Object[] {1f, 14, 1.0e-6f},
            new Object[] {1f, 15, 1.0e-9f},
            new Object[] {1f, 16, 1.0e-12f},
            new Object[] {1f, 17, 1.0e-15f},
            new Object[] {1f, 18, 1.0e-18f},
            new Object[] {1f, 19, 1.0e-21f},
            new Object[] {1f, 20, 1.0e-24f}
        };
    }

    @Test
    public void testGetSiPrefixes() {
        List<String> expected = App.getList("siPrefixes");

        assertEquals(expected, Conversions.getSiPrefixes());
    }

    @Test
    public void testGetRadioactiveUnits() {
        List<String> expected = App.getList("radioactiveUnits");

        assertEquals(expected, Conversions.getRadioactiveUnits());
    }

    @Test
    public void testGetMassUnits() {
        List<String> expected = App.getList("massUnits");

        assertEquals(expected, Conversions.getMassUnits());
    }

    @Test
    public void testBqToCi() {
        float expected = 2.7e-11f;
        float actual = Conversions.bqToCi(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testCiToBq() {
        float expected = 3.7e10f;
        float actual = Conversions.ciToBq(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testGyToRad() {
        float expected = 1.0e2f;
        float actual = Conversions.gyToRad(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testRadToGy() {
        float expected = 1.0e-2f;
        float actual = Conversions.radToGy(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testSvToRem() {
        float expected = 1.0e2f;
        float actual = Conversions.svToRem(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testRemToSv() {
        float expected = 1.0e-2f;
        float actual = Conversions.remToSv(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testCkgToR() {
        float expected = 3.88e3f;
        float actual = Conversions.ckgToR(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testRToCkg() {
        float expected = 2.58e-4f;
        float actual = Conversions.rToCkg(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToYotta() {
        float expected = 1.0e-24f;
        float actual = Conversions.baseToYotta(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToZetta() {
        float expected = 1.0e-21f;
        float actual = Conversions.baseToZetta(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToExa() {
        float expected = 1.0e-18f;
        float actual = Conversions.baseToExa(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToPeta() {
        float expected = 1.0e-15f;
        float actual = Conversions.baseToPeta(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToTera() {
        float expected = 1.0e-12f;
        float actual = Conversions.baseToTera(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToGiga() {
        float expected = 1.0e-9f;
        float actual = Conversions.baseToGiga(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToMega() {
        float expected = 1.0e-6f;
        float actual = Conversions.baseToMega(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToKilo() {
        float expected = 1.0e-3f;
        float actual = Conversions.baseToKilo(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToHecto() {
        float expected = 1.0e-2f;
        float actual = Conversions.baseToHecto(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToDeka() {
        float expected = 1.0e-1f;
        float actual = Conversions.baseToDeka(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToDeci() {
        float expected = 1.0e1f;
        float actual = Conversions.baseToDeci(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToCenti() {
        float expected = 1.0e2f;
        float actual = Conversions.baseToCenti(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToMilli() {
        float expected = 1.0e3f;
        float actual = Conversions.baseToMilli(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToMicro() {
        float expected = 1.0e6f;
        float actual = Conversions.baseToMicro(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToNano() {
        float expected = 1.0e9f;
        float actual = Conversions.baseToNano(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToPico() {
        float expected = 1.0e12f;
        float actual = Conversions.baseToPico(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToFemto() {
        float expected = 1.0e15f;
        float actual = Conversions.baseToFemto(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToAtto() {
        float expected = 1.0e18f;
        float actual = Conversions.baseToAtto(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToZepto() {
        float expected = 1.0e21f;
        float actual = Conversions.baseToZepto(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testBaseToYocto() {
        float expected = 1.0e24f;
        float actual = Conversions.baseToYocto(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testYottaToBase() {
        float expected = 1.0e24f;
        float actual = Conversions.yottaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testZettaToBase() {
        float expected = 1.0e21f;
        float actual = Conversions.zettaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testExaToBase() {
        float expected = 1.0e18f;
        float actual = Conversions.exaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testPetaToBase() {
        float expected = 1.0e15f;
        float actual = Conversions.petaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testTeraToBase() {
        float expected = 1.0e12f;
        float actual = Conversions.teraToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testGigaToBase() {
        float expected = 1.0e9f;
        float actual = Conversions.gigaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testMegaToBase() {
        float expected = 1.0e6f;
        float actual = Conversions.megaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testKiloToBase() {
        float expected = 1.0e3f;
        float actual = Conversions.kiloToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testHectoToBase() {
        float expected = 1.0e2f;
        float actual = Conversions.hectoToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testDekaToBase() {
        float expected = 1.0e1f;
        float actual = Conversions.dekaToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testDeciToBase() {
        float expected = 1.0e-1f;
        float actual = Conversions.deciToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testCentiToBase() {
        float expected = 1.0e-2f;
        float actual = Conversions.centiToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testMilliToBase() {
        float expected = 1.0e-3f;
        float actual = Conversions.milliToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testMicroToBase() {
        float expected = 1.0e-6f;
        float actual = Conversions.microToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testNanoToBase() {
        float expected = 1.0e-9f;
        float actual = Conversions.nanoToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testPicoToBase() {
        float expected = 1.0e-12f;
        float actual = Conversions.picoToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testFemtoToBase() {
        float expected = 1.0e-15f;
        float actual = Conversions.femtoToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testAttoToBase() {
        float expected = 1.0e-18f;
        float actual = Conversions.attoToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testZeptoToBase() {
        float expected = 1.0e-21f;
        float actual = Conversions.zeptoToBase(1);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testYoctoToBase() {
        float expected = 1.0e-24f;
        float actual = Conversions.yoctoToBase(1);

        assertEquals(expected, actual, 0);
    }
}
