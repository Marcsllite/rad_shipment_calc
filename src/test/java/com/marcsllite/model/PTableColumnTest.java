package com.marcsllite.model;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PTableColumnTest {
    PTableColumn<String, String> tableColumn = new PTableColumn<>();

    @Test
    public void testConstructor() {
        tableColumn = new PTableColumn<>();
        assertEquals(1.0, tableColumn.getPercentageWidth());
    }

    @Test
    public void testSetPercentageWidth_InvalidValue() {
        double val = 2.5;
        String errMsg = String.format("The provided percentage width is not between 0.0 and 1.0. Value is: %1$s", val);
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, () -> tableColumn.setPercentageWidth(val)
        );
        assertEquals(errMsg, ex.getMessage());

    }

    @Test
    public void testSetPercentageWidth() {
        double val = 0.5;
        tableColumn.setPercentageWidth(val);
        assertEquals(val, tableColumn.getPercentageWidth(), 0.0);
    }
}
