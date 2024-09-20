package com.marcsllite.model;

import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.handler.PropHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportableQuantityTest {
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    ReportableQuantity reportQuan = new ReportableQuantity(DEFAULT_ID) {
        @Override
        public PropHandler getPropHandler() {
            return new PropHandlerTestObj();
        }
    };

    private final RadBigDecimal DEFAULT_NUM = RadBigDecimal.NEG_INFINITY_OBJ;

    @Test
    void testConstructor_NoValues() {
        assertEquals(DEFAULT_NUM, reportQuan.getDefaultVal());
        assertEquals(DEFAULT_ID, reportQuan.getNuclideId());
        assertEquals(DEFAULT_NUM, reportQuan.getCurie());
        assertEquals(DEFAULT_NUM, reportQuan.getTeraBq());
    }

    @Test
    void testConstructor_WithValues() {
        RadBigDecimal val = RadBigDecimal.valueOf(5.2f);
        reportQuan = new ReportableQuantity(DEFAULT_ID, val, val){
            @Override
            public PropHandler getPropHandler() {
                return new PropHandlerTestObj();
            }
        };

        assertEquals(DEFAULT_NUM, reportQuan.getDefaultVal());
        assertEquals(DEFAULT_ID, reportQuan.getNuclideId());
        assertEquals(val, reportQuan.getCurie());
        assertEquals(val, reportQuan.getTeraBq());
    }

    @Test
    void testToString() {
        RadBigDecimal val = RadBigDecimal.valueOf(5.2f);
        reportQuan = new ReportableQuantity(DEFAULT_ID, val, val){
            @Override
            public PropHandler getPropHandler() {
                return new PropHandlerTestObj();
            }
        };

        String actual = reportQuan.toString();
        assertTrue(actual.contains(DEFAULT_ID.toString()));
        assertTrue(actual.contains(val + " TBq"));
        assertTrue(actual.contains(val + " Ci"));
    }
}