package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class IsotopeModelTest {
    private final String DEFAULT_NAME = "Name";
    private final String DEFAULT_ABBR = "Abbr";
    private final IsotopeModelId DEFAULT_ISO_ID = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
    IsotopeModel model = new IsotopeModel(DEFAULT_ISO_ID);

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ISO_ID, model.getIsotopeId());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetIsoId() {
        IsotopeModelId isoId = new IsotopeModelId("Testing", "123");
        model.setIsotopeId(isoId);
        assertEquals(isoId, model.getIsotopeId());
    }
}