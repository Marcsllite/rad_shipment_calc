package com.marcsllite.controller;

import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainControllerTest {

    @Test
    public void testRegisterController_NullParam() {
        MainController controller = MainController.getInstance();
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> controller.registerController(null)
        );
        assertTrue(exception.getMessage().contains("Controller cannot be null"));
    }

    @Test
    public void testRegisterController_BaseController() {
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
    public void testRegisterController_MenuPaneController() {
        MenuPaneController controller = new MenuPaneController(GUITest.propHandler);
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getMenuPaneController());
    }

    @Test
    public void testRegisterController_HomePaneController() {
        HomePaneController controller = new HomePaneController(GUITest.propHandler);
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getHomePaneController());
    }

    @Test
    public void testRegisterController_ReferencePaneController() {
        SecondaryController controller = new SecondaryController();
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getReferencePaneController());
    }
}
