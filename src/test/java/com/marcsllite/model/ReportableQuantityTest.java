package com.marcsllite.model;

import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.util.handler.PropHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportableQuantityTest {
    private final String DEFAULT_ABBR = "Abbr";
    ReportableQuantity reportQuan = new ReportableQuantity(DEFAULT_ABBR) {
        @Override
        public PropHandler getPropHandler() {
            return new PropHandlerTestObj();
        }
    };

    private final float DEFAULT_NUM = -2.0f;

    @Test
    void testConstructor_NoValues() {
        assertEquals(DEFAULT_NUM, reportQuan.getDefaultVal());
        assertEquals(DEFAULT_ABBR, reportQuan.getAbbr());
        assertEquals(DEFAULT_NUM, reportQuan.getCurie(), 0.0f);
        assertEquals(DEFAULT_NUM, reportQuan.getTeraBq(), 0.0f);
    }

    @Test
    void testConstructor_WithValues() {
        String abbr = "abracadabra";
        float val = 5.2f;
        reportQuan = new ReportableQuantity(abbr, val, val){
            @Override
            public PropHandler getPropHandler() {
                return new PropHandlerTestObj();
            }
        };

        assertEquals(DEFAULT_NUM, reportQuan.getDefaultVal());
        assertEquals(abbr, reportQuan.getAbbr());
        assertEquals(val, reportQuan.getCurie(), 0.0f);
        assertEquals(val, reportQuan.getTeraBq(), 0.0f);
    }

}