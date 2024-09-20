package com.marcsllite.util;

import com.marcsllite.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConversionsTest {
    final static RadBigDecimal toBeConverted = TestUtils.getRandomRadBigDecimalWithDecimalPrecision(RadBigDecimal.DEFAULT_DEC_PRECISION);
    MockedStatic<Conversions> mockedConversions;

    @BeforeEach
    public void setUp() {
        mockedConversions = mockStatic(Conversions.class, Mockito.CALLS_REAL_METHODS);
    }

    @AfterEach
    public void tearDown() {
        mockedConversions.close();
    }
    
    @ParameterizedTest(name = "Convert 7.5 {0} to base = {1}")
    @MethodSource("convertToBaseException_data")
    void testConvertToBaseException(RadBigDecimal value, Conversions.SIPrefix prefix, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Conversions.convertToBase(value, prefix)
        );
        assertTrue(exception.getMessage().contains(message));
    }

    private static Object[] convertToBaseException_data() {
        return new Object[] {
            new Object[] {null, Conversions.SIPrefix.YOTTA, "Invalid Value"},
            new Object[] {toBeConverted, null, "Invalid SI Prefix"}
        };
    }
    
    @ParameterizedTest(name = "Convert {0} {1} to base = {3}")
    @MethodSource("convertToBase_data")
    void testConvertToBase(RadBigDecimal value, Conversions.SIPrefix prefix, RadBigDecimal expected) {
        assertEquals(0, expected.compareTo(Conversions.convertToBase(value, prefix)));
    }

    private static Object[] convertToBase_data() {
        return new Object[] {
            new Object[] {toBeConverted, Conversions.SIPrefix.YOTTA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(24))},
            new Object[] {toBeConverted, Conversions.SIPrefix.ZETTA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(21))},
            new Object[] {toBeConverted, Conversions.SIPrefix.EXA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(18))},
            new Object[] {toBeConverted, Conversions.SIPrefix.PETA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(15))},
            new Object[] {toBeConverted, Conversions.SIPrefix.TERA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(12))},
            new Object[] {toBeConverted, Conversions.SIPrefix.GIGA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(9))},
            new Object[] {toBeConverted, Conversions.SIPrefix.MEGA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(6))},
            new Object[] {toBeConverted, Conversions.SIPrefix.KILO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(3))},
            new Object[] {toBeConverted, Conversions.SIPrefix.HECTO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(2))},
            new Object[] {toBeConverted, Conversions.SIPrefix.DEKA, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(1))},
            new Object[] {toBeConverted, Conversions.SIPrefix.BASE, toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.DECI, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-1))},
            new Object[] {toBeConverted, Conversions.SIPrefix.CENTI, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-2))},
            new Object[] {toBeConverted, Conversions.SIPrefix.MILLI, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-3))},
            new Object[] {toBeConverted, Conversions.SIPrefix.MICRO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-6))},
            new Object[] {toBeConverted, Conversions.SIPrefix.NANO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-9))},
            new Object[] {toBeConverted, Conversions.SIPrefix.PICO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-12))},
            new Object[] {toBeConverted, Conversions.SIPrefix.FEMTO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-15))},
            new Object[] {toBeConverted, Conversions.SIPrefix.ATTO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-18))},
            new Object[] {toBeConverted, Conversions.SIPrefix.ZEPTO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-21))},
            new Object[] {toBeConverted, Conversions.SIPrefix.YOCTO, toBeConverted.multiply(new RadBigDecimal(BigDecimal.TEN).pow(-24))}
        };
    }

    @ParameterizedTest(name = "Convert 7.5 {0} to base = {1}")
    @MethodSource("convertToPrefixException_data")
    void testConvertToPrefixException(RadBigDecimal value, Conversions.SIPrefix start, Conversions.SIPrefix end, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Conversions.convertToPrefix(value, start, end)
        );
        assertTrue(exception.getMessage().contains(message));
        mockedConversions.verify(() -> Conversions.convertToBase(value, start), times(0));
    }

    private static Object[] convertToPrefixException_data() {
        return new Object[] {
            new Object[] {null, Conversions.SIPrefix.YOTTA, Conversions.SIPrefix.YOTTA, "Invalid Value"},
            new Object[] {toBeConverted, null, Conversions.SIPrefix.YOTTA, "Invalid starting SI Prefix"},
            new Object[] {toBeConverted, Conversions.SIPrefix.YOTTA, null, "Invalid ending SI Prefix"}
        };
    }

    @ParameterizedTest(name = "Convert {0} {1} to base = {3}")
    @MethodSource("convertToPrefix_data")
    void testConvertToPrefix(RadBigDecimal value, Conversions.SIPrefix start, Conversions.SIPrefix end, RadBigDecimal expected) {
        assertEquals(0, expected.compareTo(Conversions.convertToPrefix(value, start, end)));
        mockedConversions.verify(() -> Conversions.convertToBase(value, start));
    }

    private static Object[] convertToPrefix_data() {
        return new Object[] {
            new Object[] {toBeConverted, Conversions.SIPrefix.YOTTA, Conversions.SIPrefix.YOTTA, toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.ZETTA, Conversions.SIPrefix.ZETTA, toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.EXA, Conversions.SIPrefix.EXA, toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.PETA, Conversions.SIPrefix.PETA,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.TERA, Conversions.SIPrefix.TERA,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.GIGA, Conversions.SIPrefix.GIGA,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.MEGA, Conversions.SIPrefix.MEGA,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.KILO, Conversions.SIPrefix.KILO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.HECTO, Conversions.SIPrefix.HECTO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.DEKA, Conversions.SIPrefix.DEKA,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.BASE, Conversions.SIPrefix.BASE,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.DECI, Conversions.SIPrefix.DECI,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.CENTI, Conversions.SIPrefix.CENTI,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.MILLI, Conversions.SIPrefix.MILLI,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.MICRO, Conversions.SIPrefix.MICRO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.NANO, Conversions.SIPrefix.NANO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.PICO, Conversions.SIPrefix.PICO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.FEMTO, Conversions.SIPrefix.FEMTO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.ATTO, Conversions.SIPrefix.ATTO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.ZEPTO, Conversions.SIPrefix.ZEPTO,toBeConverted},
            new Object[] {toBeConverted, Conversions.SIPrefix.YOCTO, Conversions.SIPrefix.YOCTO,toBeConverted}
        };
    }

    @Test
    void testBqToCi_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.bqToCi(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testBqToCi() {
        RadBigDecimal expected = RadBigDecimal.valueOf(2.7).multiply(new RadBigDecimal(BigDecimal.TEN).pow(-11));
        RadBigDecimal actual = Conversions.bqToCi(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testCiToBq_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.ciToBq(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testCiToBq() {
        RadBigDecimal expected = RadBigDecimal.valueOf(3.7).multiply(new RadBigDecimal(BigDecimal.TEN).pow(10));
        RadBigDecimal actual = Conversions.ciToBq(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testGyToRad_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.gyToRad(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testGyToRad() {
        RadBigDecimal expected = new RadBigDecimal(BigDecimal.TEN).pow(2);
        RadBigDecimal actual = Conversions.gyToRad(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testRadToGy_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.radToGy(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testRadToGy() {
        RadBigDecimal expected = new RadBigDecimal(BigDecimal.TEN).pow(-2);
        RadBigDecimal actual = Conversions.radToGy(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testSvToRem_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.svToRem(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testSvToRem() {
        RadBigDecimal expected = new RadBigDecimal(BigDecimal.TEN).pow(2);
        RadBigDecimal actual = Conversions.svToRem(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testRemToSv_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.remToSv(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testRemToSv() {
        RadBigDecimal expected = new RadBigDecimal(BigDecimal.TEN).pow(-2);
        RadBigDecimal actual = Conversions.remToSv(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testCkgToR_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.ckgToR(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testCkgToR() {
        RadBigDecimal expected = RadBigDecimal.valueOf(3.88).multiply(new RadBigDecimal(BigDecimal.TEN).pow(3));
        RadBigDecimal actual = Conversions.ckgToR(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testRToCkg_Null() {
        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> Conversions.rToCkg(null)
        );

        assertEquals("Invalid Value", ipe.getMessage());
    }

    @Test
    void testRToCkg() {
        RadBigDecimal expected = RadBigDecimal.valueOf(2.58).multiply(new RadBigDecimal(BigDecimal.TEN).pow(-4));
        RadBigDecimal actual = Conversions.rToCkg(new RadBigDecimal(BigDecimal.ONE));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void testToMassUnit() {
        String str = "";
        Conversions.MassUnit actual = Conversions.MassUnit.toMass(str);
        assertNull(actual);

        str = "g";
        actual = Conversions.MassUnit.toMass(str);
        assertEquals(Conversions.MassUnit.GRAMS, actual);

        str = "GRAMS (g)";
        actual = Conversions.MassUnit.toMass(str);
        assertEquals(Conversions.MassUnit.GRAMS, actual);

        str = "fake";
        actual = Conversions.MassUnit.toMass(str);
        assertNull(actual);
    }

    @Test
    void testToDoseUnit() {
        String str = "";
        Conversions.DoseUnit actual = Conversions.DoseUnit.toDoseUnit(str);
        assertNull(actual);

        str = "g";
        actual = Conversions.DoseUnit.toDoseUnit(str);
        assertNull(actual);

        str = "gy";
        actual = Conversions.DoseUnit.toDoseUnit(str);
        assertEquals(Conversions.DoseUnit.GRAY, actual);

        str = "roentgen equivalent, man (rem)";
        actual = Conversions.DoseUnit.toDoseUnit(str);
        assertEquals(Conversions.DoseUnit.REM, actual);

        str = "roentgen Equivalent, MaN (rem)";
        actual = Conversions.DoseUnit.toDoseUnit(str);
        assertEquals(Conversions.DoseUnit.REM, actual);

        str = "fake";
        actual = Conversions.DoseUnit.toDoseUnit(str);
        assertNull(actual);
    }

    @Test
    void testToExposureUnit() {
        String str = "";
        Conversions.ExposureUnit actual = Conversions.ExposureUnit.toExposureUnit(str);
        assertNull(actual);

        str = "r";
        actual = Conversions.ExposureUnit.toExposureUnit(str);
        assertEquals(Conversions.ExposureUnit.ROENTGEN, actual);

        str = "C/kg";
        actual = Conversions.ExposureUnit.toExposureUnit(str);
        assertEquals(Conversions.ExposureUnit.COULOMB_KILOGRAM, actual);

        str = "roentgen (R)";
        actual = Conversions.ExposureUnit.toExposureUnit(str);
        assertEquals(Conversions.ExposureUnit.ROENTGEN, actual);

        str = "coulomb/kilogram (C/kg)";
        actual = Conversions.ExposureUnit.toExposureUnit(str);
        assertEquals(Conversions.ExposureUnit.COULOMB_KILOGRAM, actual);

        str = "fake";
        actual = Conversions.ExposureUnit.toExposureUnit(str);
        assertNull(actual);
    }

    @Test
    void testToRadUnit() {
        String str = "";
        Conversions.RadUnit actual = Conversions.RadUnit.toRadUnit(str);
        assertNull(actual);

        str = "Curie (Ci)";
        actual = Conversions.RadUnit.toRadUnit(str);
        assertEquals(Conversions.RadUnit.CURIE, actual);

        str = "CI";
        actual = Conversions.RadUnit.toRadUnit(str);
        assertEquals(Conversions.RadUnit.CURIE, actual);

        str = "fake";
        actual = Conversions.RadUnit.toRadUnit(str);
        assertNull(actual);
    }

    @Test
    void testToSIPrefix() {
        String str = "";
        Conversions.SIPrefix actual = Conversions.SIPrefix.toSIPrefix(str);
        assertEquals(Conversions.SIPrefix.BASE, actual);
    
        str = "m";
        actual = Conversions.SIPrefix.toSIPrefix(str);
        assertEquals(Conversions.SIPrefix.MILLI, actual);
    
        str = "M";
        actual = Conversions.SIPrefix.toSIPrefix(str);
        assertEquals(Conversions.SIPrefix.MEGA, actual);
    
        str = "Mega (M)";
        actual = Conversions.SIPrefix.toSIPrefix(str);
        assertEquals(Conversions.SIPrefix.MEGA, actual);
    
        str = "mEGA (M)";
        actual = Conversions.SIPrefix.toSIPrefix(str);
        assertEquals(Conversions.SIPrefix.MEGA, actual);
    
        str = "fake";
        actual = Conversions.SIPrefix.toSIPrefix(str);
        assertNull(actual);
    }
}
