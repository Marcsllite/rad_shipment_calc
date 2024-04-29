package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class A1ModelTest {
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    private final RadBigDecimal DEFAULT_VAL = RadBigDecimal.valueOf(1.0f);
    A1Model model = new A1Model(DEFAULT_ID, DEFAULT_VAL);

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ID, model.getNuclideId());
        assertEquals(DEFAULT_VAL, model.getValue());
        assertEquals(Conversions.SIPrefix.TERA, model.getBasePrefix());
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