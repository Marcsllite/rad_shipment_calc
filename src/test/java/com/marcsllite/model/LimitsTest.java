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
    public void testConstructor() {
        assertEquals(DEFAULT_INT, limits.getDefaultVal());
        assertEquals(DEFAULT_STATE, limits.getState());
        assertEquals(DEFAULT_FORM, limits.getForm());
        assertEquals(DEFAULT_INT, limits.getIa_limited(), 0.0f);
        assertEquals(DEFAULT_INT, limits.getIa_package(), 0.0f);
        assertEquals(DEFAULT_INT, limits.getLimited(), 0.0f);
    }
}