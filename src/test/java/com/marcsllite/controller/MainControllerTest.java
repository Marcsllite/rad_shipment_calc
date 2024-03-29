package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class MainControllerTest {
    @Test
    void testRegisterController_NullParam() {
        MainController controller = MainController.getInstance();
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> controller.registerController(null)
        );
        assertTrue(exception.getMessage().contains("Controller cannot be null"));
    }

    @Test
    void testRegisterController_BaseController() {
        BaseController controller = null;
        try {
            controller = new BaseController(){
                @Override
                public void show() {}

                @Override
                public void hide() {}
            };
        } catch (IOException e) {
            fail("Failed to initialize test object");
        }

        MainController.getInstance().registerController(controller);

        assertNotEquals(controller, MainController.getInstance().getMenuPaneController());
        assertNotEquals(controller, MainController.getInstance().getHomePaneController());
        assertNotEquals(controller, MainController.getInstance().getReferencePaneController());
    }

    @Test
    void testRegisterController_MenuPaneController() {
        MenuPaneController controller = null;
        try {
            controller = new MenuPaneController(new PropHandlerTestObj());
        } catch (IOException e) {
            fail("Failed to create MenuPaneController");
        }

        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getMenuPaneController());
    }

    @Test
    void testRegisterController_HomePaneController() {
        HomePaneController controller = null;
        try {
            controller = new HomePaneController(new PropHandlerTestObj());
        } catch (IOException e) {
            fail("Failed to create MenuPaneController");
        }

        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getHomePaneController());
    }

    @Test
    void testRegisterController_ReferencePaneController() {
        ReferencePaneController controller = null;
        try {
            controller = new ReferencePaneController(new PropHandlerTestObj());
        } catch (IOException e) {
            fail("Failed to create MenuPaneController");
        }

        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getReferencePaneController());
    }
}
