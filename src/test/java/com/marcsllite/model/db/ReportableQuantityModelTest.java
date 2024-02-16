package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class ReportableQuantityModelTest {
    private final String DEFAULT_ABBR = "Abbr";
    private final float DEFAULT_VAL = 1.0f;
    ReportableQuantityModel model = new ReportableQuantityModel(DEFAULT_ABBR, DEFAULT_VAL, DEFAULT_VAL);

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_ABBR, model.getAbbr());
        assertEquals(DEFAULT_VAL, model.getCurie(), 0.0f);
        assertEquals(DEFAULT_VAL, model.getTeraBq(), 0.0f);
        assertNull(model.getBasePrefix());
    }

    @Test
    public void setAbbr() {
        String abbr = "newAbbr";
        model.setAbbr(abbr);
        assertEquals(abbr, model.getAbbr());
    }

    @Test
    public void setCurie() {
        float val = 45.2f;
        model.setCurie(val);
        assertEquals(val, model.getCurie(), 0.0f);
    }

    @Test
    public void setTeraBq() {
        float val = 45.2f;
        model.setTeraBq(val);
        assertEquals(val, model.getTeraBq(), 0.0f);
    }
}