package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class IsotopeModelTest {
    private final String DEFAULT_NAME = "Name";
    private final String DEFAULT_ABBR = "Abbr";
    private final IsotopeModelId DEFAULT_ISO_ID = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
    IsotopeModel model = new IsotopeModel(DEFAULT_ISO_ID);

    @Test
    void testConstructor() {
        assertEquals(DEFAULT_ISO_ID, model.getIsotopeId());
        assertNull(model.getBasePrefix());
    }

    @Test
    void testSetIsoId() {
        IsotopeModelId isoId = new IsotopeModelId("Testing", "123");
        model.setIsotopeId(isoId);
        assertEquals(isoId, model.getIsotopeId());
    }

    @Test
    void testSetShipments() {
        ShipmentsModel shipmentsModel = mock(ShipmentsModel.class);
        List<ShipmentsModel> exp = new ArrayList<>();
        exp.add(shipmentsModel);
        model.setShipments(exp);
        assertEquals(exp, model.getShipments());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}