package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.A1Model;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class A1DaoImplTest extends DBTest {
    @InjectMocks
    A1DaoImpl dao;
    @Mock
    A1Model model;

    @Test
    public void testGetA1() {
        String abbr = "abbr";
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getA1(abbr));
    }
}