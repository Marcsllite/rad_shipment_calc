package com.marcsllite.controller;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SummaryPaneControllerTest {
    ShipmentDetailsController controller = new ShipmentDetailsController();
    @Test
    public void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.shipmentDetailsHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}