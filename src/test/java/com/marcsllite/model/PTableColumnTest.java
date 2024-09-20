package com.marcsllite.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PTableColumnTest {
    PTableColumn<String, String> tableColumn = new PTableColumn<>();

    @Test
    void testConstructor() {
        tableColumn = new PTableColumn<>();
        assertEquals(1.0, tableColumn.getPercentageWidth());
    }

    @ParameterizedTest
    @MethodSource("testSetPercentageWidth_data")
    void testSetPercentageWidth_InvalidValue(double val, String errMsg) {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, () -> tableColumn.setPercentageWidth(val)
        );
        assertEquals(errMsg, ex.getMessage());

    }

    private static Object[] testSetPercentageWidth_data() {
        String msg = "The provided percentage width is not between 0.0 and 1.0. Value is: %1$s";
        return new Object[] {
            new Object[] { -1.5, String.format(msg, -1.5)},
            new Object[] { 1.5, String.format(msg, 1.5)}
        };
    }

    @Test
    void testSetPercentageWidth() {
        double val = 0.5;
        tableColumn.setPercentageWidth(val);
        assertEquals(val, tableColumn.getPercentageWidth(), 0.0);
    }
}
