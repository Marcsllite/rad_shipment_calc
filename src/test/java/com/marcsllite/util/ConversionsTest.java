package com.marcsllite.util;

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
import java.math.MathContext;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConversionsTest {
    final static BigDecimal toBeConverted = BigDecimal.valueOf(7.5d);
    final static MathContext mc = Conversions.context;
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
    void testConvertToBaseException(BigDecimal value, Conversions.SIPrefix prefix, String message) {
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

    @ParameterizedTest(name = "Convert 7.5 {0} to base = {1}")
    @MethodSource("convertToPrefixException_data")
    void testConvertToPrefixException(BigDecimal value, Conversions.SIPrefix start, Conversions.SIPrefix end, String message) {
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
    void testConvertToPrefix(BigDecimal value, Conversions.SIPrefix start, Conversions.SIPrefix end, BigDecimal expected) {
        assertEquals(expected, Conversions.convertToPrefix(value, start, end));
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
}
