package com.marcsllite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeoutException;

import com.marcsllite.util.BaseController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.stage.Stage;
class MainControllerTest {
    @BeforeAll
    public static void init() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    @Nested
    @DisplayName("Main Controller UI Tests")
    @ExtendWith(ApplicationExtension.class)
    class MainControllerTestUI {
        @Start
        public void start(Stage stage) throws Exception {
            new App().start(stage);
        }

        @AfterEach
        public void tearDown() throws TimeoutException {
            FxToolkit.hideStage();
        }

        @Test
        void testShowHomePane() {
            Platform.runLater(() -> {
                MainController.getInstance().showHomePane();
                WaitForAsyncUtils.waitForFxEvents();

                assertTrue(MainController.getInstance().getHomePaneController().homePane.isVisible());
            });
        }

        @Test
        void testShowReferencePane() {
            Platform.runLater(() -> {
                MainController.getInstance().showReferencePane();
                WaitForAsyncUtils.waitForFxEvents();

                assertTrue(MainController.getInstance().getReferencePaneController().referencePane.isVisible());
            });
        }
    }

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
        PrimaryController controller = new PrimaryController();
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getHomePaneController());
    }

    @Test
    void testRegisterController_ReferencePaneController() {
        SecondaryController controller = new SecondaryController();
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getReferencePaneController());
    }
}