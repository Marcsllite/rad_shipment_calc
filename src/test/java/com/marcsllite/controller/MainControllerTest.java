package com.marcsllite.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        BaseController controller = new BaseController(){
            @Override
            public void show() {}

            @Override
            public void hide() {}
        };

        MainController.getInstance().registerController(controller);

        assertNotEquals(controller, MainController.getInstance().getMenuPaneController());
        assertNotEquals(controller, MainController.getInstance().getHomePaneController());
        assertNotEquals(controller, MainController.getInstance().getReferencePaneController());
    }

    @Test
    void testRegisterController_MenuPaneController() {
        MenuPaneController controller = new MenuPaneController();
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getMenuPaneController());
    }

    @Test
    void testRegisterController_HomePaneController() {
        HomePaneController controller = new HomePaneController();

        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getHomePaneController());
    }

    @Test
    void testRegisterController_ReferencePaneController() {
        ReferencePaneController controller = new ReferencePaneController();

        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getReferencePaneController());
    }
}
