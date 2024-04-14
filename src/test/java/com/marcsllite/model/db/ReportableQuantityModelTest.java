package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class ReportableQuantityModelTest {
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    private final RadBigDecimal DEFAULT_VAL = RadBigDecimal.valueOf(1.0f);
    ReportableQuantityModel model = new ReportableQuantityModel(DEFAULT_ID, DEFAULT_VAL.toString(), DEFAULT_VAL.toString());

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ID, model.getNuclideId());
        assertEquals(DEFAULT_VAL, model.getCurie());
        assertEquals(DEFAULT_VAL, model.getTeraBq());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetNuclideId() {
        NuclideModelId id = new NuclideModelId("test", "mn");
        model.setNuclideId(id);
        assertEquals(id, model.getNuclideId());
    }

    @Test
    void setCurie() {
        RadBigDecimal val = RadBigDecimal.valueOf(45.2f);
        model.setCurie(val);
        assertEquals(val, model.getCurie());
    }

    @Test
    void setTeraBq() {
        RadBigDecimal val = RadBigDecimal.valueOf(45.2f);
        model.setTeraBq(val);
        assertEquals(val, model.getTeraBq());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}