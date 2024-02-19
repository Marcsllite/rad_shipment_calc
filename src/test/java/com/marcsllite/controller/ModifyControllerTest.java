package com.marcsllite.controller;

import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModifyControllerTest {
    ModifyController c = new ModifyController(GUITest.propHandler);
    @Test
    public void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> c.modifyHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}