package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HalfLifeModelTest {
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    private final RadBigDecimal DEFAULT_VAL = RadBigDecimal.valueOf(1.0f);
    final HalfLifeModel model = new HalfLifeModel(DEFAULT_ID, DEFAULT_VAL.toString());

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ID, model.getNuclideId());
        assertEquals(DEFAULT_VAL, model.getValue());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetNuclideId() {
        NuclideModelId id = new NuclideModelId("test", "mn");
        model.setNuclideId(id);
        assertEquals(id, model.getNuclideId());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}