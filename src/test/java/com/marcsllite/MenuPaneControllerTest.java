package com.marcsllite;

import com.marcsllite.util.ImageHandler;
import com.marcsllite.util.PropManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuPaneControllerTest {
    MenuPaneController c;
    public static PropManager propManager =  new PropManager() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return ("defaultInt".equals(key))? defaultInt : "";
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("defaultInt");
        }
    };
    final static String defaultInt = "-2.0";

    @BeforeEach
    public void setUp() {
        c = new MenuPaneController(propManager);
    }

    @Test
    public void testSetButtonColor_NullBtn_NullColor() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.setButtonColor(null, null)
        );
        assertTrue(ex.getMessage().contains("The menu button cannot be null"));
        assertTrue(ex.getMessage().contains("The color cannot be null"));
    }

    @Test
    public void testSetButtonColor_NullBtn_ProperColor() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.setButtonColor(null, ImageHandler.Colors.UML_BLUE)
        );
        assertTrue(ex.getMessage().contains("The menu button cannot be null"));
    }

    @Test
    public void testSetCurrentButton_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.setCurrentButton(null)
        );
        assertTrue(ex.getMessage().contains("The current button cannot be null"));
    }

    @Test
    public void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.menuPaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}
