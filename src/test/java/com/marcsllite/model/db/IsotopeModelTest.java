package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class IsotopeModelTest {
    private final String DEFAULT_NAME = "Name";
    private final String DEFAULT_ABBR = "Abbr";
    IsotopeModel model = new IsotopeModel(new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR));

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_NAME, model.getName());
        assertEquals(DEFAULT_ABBR, model.getAbbr());
        assertNull(model.getBasePrefix());
    }

    @Test
    public void setName() {
        String name = "newName";
        model.setName(name);
        assertEquals(name, model.getName());
    }

    @Test
    public void setAbbr() {
        String abbr = "newAbbr";
        model.setAbbr(abbr);
        assertEquals(abbr, model.getAbbr());
    }
}