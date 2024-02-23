package com.marcsllite.model;


import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.handler.PropHandler;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class LimitsTest {
    Limits limits = new Limits() {
        @Override
        public PropHandler getPropHandler() {
            return new PropHandlerTestObj();
        }
    };
    private final float DEFAULT_NUM = -2.0f;
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    
    @Test
    public void testConstructor_PropHandler() {
        assertEquals(DEFAULT_NUM, limits.getDefaultVal());
        assertEquals(DEFAULT_STATE, limits.getState());
        assertEquals(DEFAULT_FORM, limits.getForm());
        assertEquals(DEFAULT_NUM, limits.getIa_limited(), 0.0f);
        assertEquals(DEFAULT_NUM, limits.getIa_package(), 0.0f);
        assertEquals(DEFAULT_NUM, limits.getLimited(), 0.0f);
    }

    @Test
    public void testConstructor_WithValues() {
        float val = 2.5f;
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.LIQUID,
            LimitsModelId.Form.SPECIAL);

        limits = new Limits(limitsId, val, val, val) {
            @Override
            public PropHandler getPropHandler() {
                return new PropHandlerTestObj();
            }
        };
        
        assertEquals(DEFAULT_NUM, limits.getDefaultVal());
        assertEquals(limitsId.getState(), limits.getState());
        assertEquals(limitsId.getForm(), limits.getForm());
        assertEquals(val, limits.getIa_limited(), 0.0f);
        assertEquals(val, limits.getIa_package(), 0.0f);
        assertEquals(val, limits.getLimited(), 0.0f);
    }
}