package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ShipmentDetailsControllerTest {
    SummaryPaneController controller;

    @BeforeEach
    public void setUp() {
        try {
            controller = new SummaryPaneController(new PropHandlerTestObj());
        } catch(IOException ioe) {
            fail("Failed to initialize test class");
        }
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.summaryPaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}