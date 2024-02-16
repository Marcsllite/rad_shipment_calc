package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class HalfLifeModelTest {
    private final String DEFAULT_ABBR = "Abbr";
    private final float DEFAULT_VAL = 1.0f;
    HalfLifeModel model = new HalfLifeModel(DEFAULT_ABBR, DEFAULT_VAL);

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_ABBR, model.getAbbr());
        assertEquals(DEFAULT_VAL, model.getValue(), 0.0f);
        assertNull(model.getBasePrefix());
    }

    @Test
    public void setAbbr() {
        String abbr = "newAbbr";
        model.setAbbr(abbr);
        assertEquals(abbr, model.getAbbr());
    }

    @Test
    public void setValue() {
        float val = 45.2f;
        model.setValue(val);
        assertEquals(val, model.getValue(), 0.0f);
    }
}