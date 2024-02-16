package com.marcsllite.model;

import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportableQuantityTest {
    private final String DEFAULT_ABBR = "Abbr";
    ReportableQuantity reportQuan = new ReportableQuantity(GUITest.propHandler, DEFAULT_ABBR);

    private final float DEFAULT_INT = -2.0f;

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_ABBR, reportQuan.getAbbr());
        assertEquals(DEFAULT_INT, reportQuan.getCurie(), 0.0f);
        assertEquals(DEFAULT_INT, reportQuan.getTeraBq(), 0.0f);
    }

}