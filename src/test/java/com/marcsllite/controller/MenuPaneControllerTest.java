package com.marcsllite.controller;

import com.marcsllite.util.handler.ImageHandler;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuPaneControllerTest {
    MenuPaneController controller = new MenuPaneController();

    @Test
    public void testSetButtonColor_NullBtn_NullColor() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> controller.setButtonColor(null, null)
        );
        assertTrue(ex.getMessage().contains("The menu button cannot be null"));
        assertTrue(ex.getMessage().contains("The color cannot be null"));
    }

    @Test
    public void testSetButtonColor_NullBtn_ProperColor() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> controller.setButtonColor(null, ImageHandler.Colors.UML_BLUE)
        );
        assertTrue(ex.getMessage().contains("The menu button cannot be null"));
    }

    @Test
    public void testSetCurrentButton_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> controller.setCurrentButton(null)
        );
        assertTrue(ex.getMessage().contains("The current button cannot be null"));
    }

    @Test
    public void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> controller.menuPaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}
