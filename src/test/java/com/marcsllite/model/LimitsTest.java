package com.marcsllite.model;


import com.marcsllite.GUITest;
import com.marcsllite.model.db.LimitsModel;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class LimitsTest {
    Limits limits = new Limits(GUITest.propHandler);
    private final float DEFAULT_INT = -2.0f;
    private final LimitsModel.State DEFAULT_STATE = LimitsModel.State.SOLID;
    private final LimitsModel.Form DEFAULT_FORM = LimitsModel.Form.NORMAL;
    
    @Test
    public void testConstructor_PropHandler() {
        assertEquals(DEFAULT_INT, limits.getDefaultVal());
        assertEquals(DEFAULT_STATE, limits.getState());
        assertEquals(DEFAULT_FORM, limits.getForm());
        assertEquals(DEFAULT_INT, limits.getIa_limited(), 0.0f);
        assertEquals(DEFAULT_INT, limits.getIa_package(), 0.0f);
        assertEquals(DEFAULT_INT, limits.getLimited(), 0.0f);
    }

    @Test
    public void testConstructor_WithValues() {
        float val = 2.5f;
        
        limits = new Limits(GUITest.propHandler,
            LimitsModel.State.LIQUID,
            LimitsModel.Form.SPECIAL,
            val,
            val,
            val);
        
        assertEquals(DEFAULT_INT, limits.getDefaultVal());
        assertEquals(LimitsModel.State.LIQUID, limits.getState());
        assertEquals(LimitsModel.Form.SPECIAL, limits.getForm());
        assertEquals(val, limits.getIa_limited(), 0.0f);
        assertEquals(val, limits.getIa_package(), 0.0f);
        assertEquals(val, limits.getLimited(), 0.0f);
    }
}