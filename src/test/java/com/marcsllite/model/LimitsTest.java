package com.marcsllite.model;


import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LimitsTest {
    Limits limits = new Limits();
    private final RadBigDecimal DEFAULT_NUM = RadBigDecimal.NEG_INFINITY_OBJ;
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    
    @Test
    void testConstructor_PropHandler() {
        assertEquals(DEFAULT_STATE, limits.getLimitsId().getState());
        assertEquals(DEFAULT_FORM, limits.getLimitsId().getForm());
        assertEquals(DEFAULT_NUM, limits.getIaLimited());
        assertEquals(DEFAULT_NUM, limits.getIaPackage());
        assertEquals(DEFAULT_NUM, limits.getLimited());
    }

    @Test
    void testConstructor_WithValues() {
        String val = RadBigDecimal.valueOf(2.5f).toDisplayString();
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.LIQUID,
            LimitsModelId.Form.SPECIAL);

        limits = new Limits(limitsId, val, val, val);

        assertEquals(limitsId, limits.getLimitsId());
        assertEquals(val, limits.getIaLimitedStr());
        assertEquals(val, limits.getIaPackageStr());
        assertEquals(val, limits.getLimitedStr());
    }

    @Test
    void testToString() {
        String val = RadBigDecimal.valueOf(2.5f).toDisplayString();
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.LIQUID,
            LimitsModelId.Form.SPECIAL);

        limits = new Limits(limitsId, val, val, val);

        String actual = limits.toString();
        assertTrue(actual.contains(limits.getLimitsId().toString()));
        assertTrue(actual.contains(limits.getIaLimitedStr()));
        assertTrue(actual.contains(limits.getIaPackageStr()));
        assertTrue(actual.contains(limits.getLimitedStr()));
    }
}