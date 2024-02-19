package com.marcsllite.model;


import com.marcsllite.DBTest;
import com.marcsllite.controller.GUITest;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static junit.framework.Assert.assertEquals;

class IsotopeConstantsTest extends DBTest {
    IsotopeConstants constants;
    private final float DEFAULT_INT = -2.0f;
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

    @BeforeEach
    public void beforeEach() {
//        super.initPU();
        constants = new IsotopeConstants(GUITest.propHandler, em);
    }

    @AfterEach
    public void afterEach() {
        factory = null;
        constants = null;
    }

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_INT, constants.getDefaultVal(), 0.0f);
    }

    @Disabled
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    public void testDbInit() {
        IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);

        constants.dbInit(isoId, limitsId);

        assertEquals(DEFAULT_A1, constants.getA1(), 0.0f);
        assertEquals(DEFAULT_A2, constants.getA2(), 0.0f);
        assertEquals(DEFAULT_DECAY_CONST, constants.getDecayConstant(), 0.0f);
        assertEquals(DEFAULT_EXEMPT_CON, constants.getExemptConcentration(), 0.0f);
        assertEquals(DEFAULT_EXEMPT_LIM, constants.getExemptLimit(), 0.0f);
        assertEquals(DEFAULT_HALF_LIFE, constants.getHalfLife(), 0.0f);
        assertEquals(DEFAULT_IA_LIMITED_LIM, constants.getIaLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_IA_PACKAGE_LIM, constants.getIaPackageLimit(), 0.0f);
        assertEquals(DEFAULT_LIMITED_LIM, constants.getLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_CI_REPORT_QUAN, constants.getCurieReportQuan(), 0.0f);
        assertEquals(DEFAULT_TBQ_REPORT_QUAN, constants.getTeraBqReportQuan(), 0.0f);
    }

}