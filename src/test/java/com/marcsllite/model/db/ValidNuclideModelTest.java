package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
class ValidNuclideModelTest {
    private final String DEFAULT_SYMBOL = "XX";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final ValidNuclideModelId DEFAULT_ISO_ID = new ValidNuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    ValidNuclideModel model = new ValidNuclideModel(DEFAULT_ISO_ID);

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ISO_ID, model.getIsotopeId());
        assertEquals(DEFAULT_SYMBOL, model.getName());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetIsoId() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("ZZ", "123");
        model.setIsotopeId(isoId);
        assertEquals(isoId, model.getIsotopeId());
    }

    @Test
    void testSetName() {
        String exp = "Expected Name";
        model.setName(exp);
        assertEquals(exp, model.getName());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}