package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ModifyControllerTest {
    ModifyController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = new ModifyController(BaseController.Page.NONE, new PropHandlerTestObj());
        assertEquals(BaseController.Page.NONE, controller.getPage());
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