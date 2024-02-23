package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LimitsModelIdTest {
    @Test
    public void testEquals_NullObj() {
        LimitsModelId limitsId = new LimitsModelId();
        assertNotEquals(null, limitsId);
    }

    @Test
    public void testEquals_WrongClass() {
        LimitsModelId limitsId = new LimitsModelId();
        assertNotEquals(limitsId, new Object());
    }

    @Test
    public void testEquals_DiffState() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);
        LimitsModelId limitsId2 = new LimitsModelId(LimitsModelId.State.LIQUID, LimitsModelId.Form.NORMAL);
        assertNotEquals(limitsId, limitsId2);
        assertNotSame(limitsId.hashCode(), limitsId2.hashCode());
    }

    @Test
    public void testEquals_DiffForm() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);
        LimitsModelId limitsId2 = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.SPECIAL);
        assertNotEquals(limitsId, limitsId2);
        assertNotSame(limitsId.hashCode(), limitsId2.hashCode());
    }

    @Test
    public void testEquals() {
        LimitsModelId limitsId = new LimitsModelId();
        LimitsModelId limitsId2 = new LimitsModelId();
        assertEquals(limitsId, limitsId2);
        assertEquals(limitsId.hashCode(), limitsId2.hashCode());
    }
}
