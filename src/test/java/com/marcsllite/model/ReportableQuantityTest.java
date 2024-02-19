package com.marcsllite.model;

import com.marcsllite.controller.GUITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportableQuantityTest {
    private final String DEFAULT_ABBR = "Abbr";
    ReportableQuantity reportQuan = new ReportableQuantity(GUITest.propHandler, DEFAULT_ABBR);

    private final float DEFAULT_INT = -2.0f;

    @Test
    public void testConstructor_NoValues() {
        assertEquals(DEFAULT_INT, reportQuan.getDefaultVal());
        assertEquals(DEFAULT_ABBR, reportQuan.getAbbr());
        assertEquals(DEFAULT_INT, reportQuan.getCurie(), 0.0f);
        assertEquals(DEFAULT_INT, reportQuan.getTeraBq(), 0.0f);
    }

    @Test
    public void testConstructor_WithValues() {
        String abbr = "abracadabra";
        float val = 5.2f;
        reportQuan = new ReportableQuantity(GUITest.propHandler, abbr, val, val);

        assertEquals(DEFAULT_INT, reportQuan.getDefaultVal());
        assertEquals(abbr, reportQuan.getAbbr());
        assertEquals(val, reportQuan.getCurie(), 0.0f);
        assertEquals(val, reportQuan.getTeraBq(), 0.0f);
    }

}