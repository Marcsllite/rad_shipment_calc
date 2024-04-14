package com.marcsllite.model.db;

import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class LimitsModelTest {
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    private final LimitsModelId DEFAULT_LIMITS_ID = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);
    private final RadBigDecimal DEFAULT_VAL = RadBigDecimal.valueOf(1.0f);
    LimitsModel model = new LimitsModel(DEFAULT_LIMITS_ID, DEFAULT_VAL.toString(), DEFAULT_VAL.toString(), DEFAULT_VAL.toString());

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_LIMITS_ID, model.getLimitsId());
        assertEquals(DEFAULT_VAL, model.getIaLimited());
        assertEquals(DEFAULT_VAL, model.getIaPackage());
        assertEquals(DEFAULT_VAL, model.getLimited());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetLimitsId() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.LIQUID, LimitsModelId.Form.SPECIAL);
        model.setLimitsId(limitsId);
        assertEquals(limitsId, model.getLimitsId());
    }

    @Test
    void testSetIa_limited() {
        RadBigDecimal val = RadBigDecimal.valueOf(4.5f);
        model.setIaLimited(val);
        assertEquals(val, model.getIaLimited());
    }

    @Test
    void testSetIa_package() {
        RadBigDecimal val = RadBigDecimal.valueOf(4.5f);
        model.setIaPackage(val);
        assertEquals(val, model.getIaPackage());
    }

    @Test
    void testSetLimited() {
        RadBigDecimal val = RadBigDecimal.valueOf(4.5f);
        model.setLimited(val);
        assertEquals(val, model.getLimited());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}