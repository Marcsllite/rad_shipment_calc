package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.IsotopeModelId;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HomePaneControllerTest {
    HomePaneController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = spy(new HomePaneController(new PropHandlerTestObj()));
        assertEquals(BaseController.Page.HOME, controller.getPage());
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.homePaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }

    @Test
    void testIsIsoInTable_NullIsotope() {
        assertFalse(controller.isIsoInTable(null));
    }

    @Test
    void testIsIsoInTable_NullShipment() {
        controller.setShipment(null);
        assertFalse(controller.isIsoInTable(new Isotope(new IsotopeModelId())));
    }

    @Test
    void testIsIsoInTable_EmptyTable() {
        Shipment shipment = spy(Shipment.class);
        shipment.setIsotopes(FXCollections.observableArrayList());
        controller.setShipment(shipment);

        assertFalse(controller.isIsoInTable(new Isotope(new IsotopeModelId())));
    }

    @Test
    void testIsIsoInTable() {
        Shipment shipment = spy(Shipment.class);
        Isotope iso = new Isotope(new IsotopeModelId());
        shipment.setIsotopes(FXCollections.observableArrayList(iso));
        controller.setShipment(shipment);

        Isotope iso2 = new Isotope(new IsotopeModelId());
        assertTrue(controller.isIsoInTable(iso2));

        iso2.setLungAbsorption(Isotope.LungAbsorption.FAST);
        assertFalse(controller.isIsoInTable(iso2));

        iso2.setLifeSpan(Isotope.LifeSpan.LONG);
        assertFalse(controller.isIsoInTable(iso2));

        iso2.setAbbr("differentAbbr");
        assertFalse(controller.isIsoInTable(iso2));
    }

    @Test
    void testUpdateIsotope_NullIsotope() {
        controller.updateIsotope(null);

        verify(controller, times(0)).getShipment();
    }
}