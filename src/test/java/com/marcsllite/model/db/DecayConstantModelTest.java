package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class DecayConstantModelTest {
    private final String DEFAULT_ABBR = "Abbr";
    private final float DEFAULT_VAL = 1.0f;
    DecayConstantModel model = new DecayConstantModel(DEFAULT_ABBR, DEFAULT_VAL);

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_ABBR, model.getAbbr());
        assertEquals(DEFAULT_VAL, model.getValue(), 0.0f);
        assertEquals(Conversions.SIPrefix.BASE, model.getBasePrefix());
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