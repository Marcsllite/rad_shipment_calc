package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.HalfLifeModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class HalfLifeDaoImplTest extends DBTest {
    @InjectMocks
    HalfLifeDaoImpl dao;
    @Mock
    HalfLifeModel model;

    @Test
    public void testGetHalfLife() {
        String abbr = "abbr";
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getHalfLife(abbr));
    }
}