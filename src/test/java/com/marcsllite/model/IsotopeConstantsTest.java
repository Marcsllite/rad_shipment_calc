package com.marcsllite.model;


import com.marcsllite.DBTest;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.service.DBService;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class IsotopeConstantsTest extends DBTest {
    private final float DEFAULT_NUM = -2.0f;
    IsotopeConstants constants = new IsotopeConstants(DEFAULT_NUM) {
        @Override
        public DBService getDbService() {
            return dbService;
        }
    };
    private final String DEFAULT_NAME = "Abbreviation";
    private final String DEFAULT_ABBR = "Abbr";
    private final float DEFAULT_A1 = 1.0f;
    private final float DEFAULT_A2 = 1.0f;
    private final float DEFAULT_DECAY_CONST = 1.0f;
    private final float DEFAULT_EXEMPT_CON = 1.0f;
    private final float DEFAULT_EXEMPT_LIM = 1.0f;
    private final float DEFAULT_HALF_LIFE = 1.0f;
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    private final float DEFAULT_IA_LIMITED_LIM = 1.5f;
    private final float DEFAULT_IA_PACKAGE_LIM = 2.0f;
    private final float DEFAULT_LIMITED_LIM = 3.5f;
    private final float DEFAULT_CI_REPORT_QUAN = 1.0f;
    private final float DEFAULT_TBQ_REPORT_QUAN = 0.037f;

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_NUM, constants.getDefaultVal());
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    public void testDbInit_Exception() {
        IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);

        when(dbService.getA1(any())).thenThrow(new RuntimeException());

        try {
            constants.dbInit(isoId, limitsId);
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }
}