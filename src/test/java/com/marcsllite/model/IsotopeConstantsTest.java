package com.marcsllite.model;


import com.marcsllite.DBTest;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.service.DBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.when;

class IsotopeConstantsTest extends DBTest {
    private final float DEFAULT_NUM = -2.0f;
    IsotopeConstants constants;
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
    public void setUp() {
        constants = new IsotopeConstants(DEFAULT_NUM) {
            @Override
            public DBService getDbService() {
                return dbService;
            }
        };
    }

    @Test
    void testConstructor() {
        constants = new IsotopeConstants(DEFAULT_NUM);

        assertEquals(DEFAULT_NUM, constants.getDefaultVal(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getA1(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getA2(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getDecayConstant(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getExemptConcentration(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getExemptLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getHalfLife(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getIaLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getIaPackageLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getCurieReportQuan(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getTeraBqReportQuan(), 0.0f);
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testDbInit_Exception() {
        IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);

        when(dbService.getA1(isoId.getAbbr())).thenThrow(new RuntimeException());

        try {
            constants.dbInit(isoId, limitsId);
            assertEquals(DEFAULT_NUM, constants.getA1(), 0.0f);
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testDbInit() {
        IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);
        float expected = 3.14159f;
        
        when(dbService.getA1(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getA2(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getDecayConstant(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getExemptConcentration(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getExemptLimit(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getHalfLife(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getIALimited(limitsId)).thenReturn(expected);
        when(dbService.getIAPackage(limitsId)).thenReturn(expected);
        when(dbService.getLimited(limitsId)).thenReturn(expected);
        when(dbService.getCiReportQuan(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getTBqReportQuan(isoId.getAbbr())).thenReturn(expected);

        try {
            constants.dbInit(isoId, limitsId);

            assertEquals(expected, constants.getA1(), 0.0f);
            assertEquals(expected, constants.getA2(), 0.0f);
            assertEquals(expected, constants.getDecayConstant(), 0.0f);
            assertEquals(expected, constants.getExemptConcentration(), 0.0f);
            assertEquals(expected, constants.getExemptLimit(), 0.0f);
            assertEquals(expected, constants.getHalfLife(), 0.0f);
            assertEquals(expected, constants.getIaLimitedLimit(), 0.0f);
            assertEquals(expected, constants.getIaPackageLimit(), 0.0f);
            assertEquals(expected, constants.getLimitedLimit(), 0.0f);
            assertEquals(expected, constants.getCurieReportQuan(), 0.0f);
            assertEquals(expected, constants.getTeraBqReportQuan(), 0.0f);
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }
}