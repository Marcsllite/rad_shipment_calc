package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ExemptLimitModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ExemptLimitDaoImplTest extends DBTest {
    @InjectMocks
    ExemptLimitDaoImpl dao;
    @Mock
    ExemptLimitModel model;

    @Test
    public void testGetExemptLimit() {
        String abbr = "abbr";
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getExemptLimit(abbr));
    }
}