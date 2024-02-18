package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class LimitsModelTest {
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    private final LimitsModelId DEFAULT_LIMITS_ID = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);
    private final float DEFAULT_VAL = 1.0f;
    LimitsModel model = new LimitsModel(DEFAULT_LIMITS_ID, DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL);

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_LIMITS_ID, model.getLimitsId());
        assertEquals(DEFAULT_VAL, model.getIa_limited());
        assertEquals(DEFAULT_VAL, model.getIa_package());
        assertEquals(DEFAULT_VAL, model.getLimited());
        assertNull(model.getBasePrefix());
    }

    @Test
    public void testSetLimitsId() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.LIQUID, LimitsModelId.Form.SPECIAL);
        model.setLimitsId(limitsId);
        assertEquals(limitsId, model.getLimitsId());
    }

    @Test
    public void testSetIa_limited() {
        float val = 4.5f;
        model.setIa_limited(val);
        assertEquals(val, model.getIa_limited(), 0.0f);
    }

    @Test
    public void testSetIa_package() {
        float val = 4.5f;
        model.setIa_package(val);
        assertEquals(val, model.getIa_package(), 0.0f);
    }

    @Test
    public void testSetLimited() {
        float val = 4.5f;
        model.setLimited(val);
        assertEquals(val, model.getLimited(), 0.0f);
    }
}