package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModifyControllerTest {
    ModifyController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = new ModifyController(new PropHandlerTestObj());
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.modifyHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}