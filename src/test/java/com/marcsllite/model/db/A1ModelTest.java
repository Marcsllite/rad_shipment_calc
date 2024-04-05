package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class A1ModelTest {
    private final String DEFAULT_ABBR = "Abbr";
    private final float DEFAULT_VAL = 1.0f;
    A1Model model = new A1Model(DEFAULT_ABBR, DEFAULT_VAL);

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ABBR, model.getAbbr());
        assertEquals(DEFAULT_VAL, model.getValue(), 0.0f);
        assertEquals(Conversions.SIPrefix.TERA, model.getBasePrefix());
    }

    @Test
    void testSetAbbr() {
        String abbr = "newAbbr";
        model.setAbbr(abbr);
        assertEquals(abbr, model.getAbbr());
    }

    @Test
    void testSetValue() {
        float val = 45.2f;
        model.setValue(val);
        assertEquals(val, model.getValue(), 0.0f);
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}