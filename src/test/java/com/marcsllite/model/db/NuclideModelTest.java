package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
class NuclideModelTest {
    private final int DEFAULT_ATOMIC_NUMBER = 1;
    private final String DEFAULT_NAME = "XX";
    private final String DEFAULT_SYMBOL = "XX";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ISO_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    final NuclideModel model = new NuclideModel(DEFAULT_ATOMIC_NUMBER, DEFAULT_NAME, DEFAULT_ISO_ID);

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ISO_ID, model.getNuclideId());
        assertEquals(DEFAULT_SYMBOL, model.getName());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetIsoId() {
        NuclideModelId isoId = new NuclideModelId("ZZ", "123");
        model.setNuclideId(isoId);
        assertEquals(isoId, model.getNuclideId());
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