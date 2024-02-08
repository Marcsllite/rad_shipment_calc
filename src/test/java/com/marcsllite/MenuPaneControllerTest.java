package com.marcsllite;

import com.marcsllite.util.ImageHandler;
import com.marcsllite.util.PropManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MenuPaneControllerTest {
    @Spy
    MenuPaneController c;
    PropManager propManager =  new PropManager() {
        @Override
        protected Object handleGetObject(String key) {
            return ("defaultInt".equals(key))? defaultInt : "";
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(List.of("defaultInt"));
        }
    };
    final static double defaultInt = -2.0;

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
