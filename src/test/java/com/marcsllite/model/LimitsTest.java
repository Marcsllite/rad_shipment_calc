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
    void testConstructor_PropHandler() {
        assertEquals(DEFAULT_NUM, limits.getDefaultVal());
        assertEquals(DEFAULT_STATE, limits.getLimitsId().getState());
        assertEquals(DEFAULT_FORM, limits.getLimitsId().getForm());
        assertEquals(DEFAULT_NUM, limits.getIaLimited(), 0.0f);
        assertEquals(DEFAULT_NUM, limits.getIaPackage(), 0.0f);
        assertEquals(DEFAULT_NUM, limits.getLimited(), 0.0f);
    }

    @Test
    void testConstructor_WithValues() {
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
        assertEquals(limitsId, limits.getLimitsId());
        assertEquals(val, limits.getIaLimited(), 0.0f);
        assertEquals(val, limits.getIaPackage(), 0.0f);
        assertEquals(val, limits.getLimited(), 0.0f);
    }
}