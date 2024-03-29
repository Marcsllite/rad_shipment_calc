package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomePaneControllerTest {
    HomePaneController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = new HomePaneController(new PropHandlerTestObj());
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.homePaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}