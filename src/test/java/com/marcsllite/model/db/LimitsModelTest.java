package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class LimitsModelTest {
    private final LimitsModel.State DEFAULT_STATE = LimitsModel.State.SOLID;
    private final LimitsModel.Form DEFAULT_FORM = LimitsModel.Form.NORMAL;
    private final float DEFAULT_VAL = 1.0f;
    LimitsModel model = new LimitsModel(new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM), DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL);

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_STATE, model.getState());
        assertEquals(DEFAULT_FORM, model.getForm());
        assertEquals(DEFAULT_VAL, model.getIa_limited());
        assertEquals(DEFAULT_VAL, model.getIa_package());
        assertEquals(DEFAULT_VAL, model.getLimited());
        assertNull(model.getBasePrefix());
    }

    @Test
    public void testSetState() {
        LimitsModel.State state = LimitsModel.State.GAS;
        model.setState(state);
        assertEquals(state, model.getState());
    }

    @Test
    public void testSetForm() {
        LimitsModel.Form state = LimitsModel.Form.SPECIAL;
        model.setForm(state);
        assertEquals(state, model.getForm());
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