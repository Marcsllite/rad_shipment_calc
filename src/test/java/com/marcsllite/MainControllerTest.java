package com.marcsllite;

import com.marcsllite.util.BaseController;
// import com.marcsllite.util.ControllerFactory;
// import com.marcsllite.util.FXMLView;
// import javafx.application.Platform;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.testfx.api.FxToolkit;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.framework.junit5.Start;
// import org.testfx.util.WaitForAsyncUtils;

// import java.net.URL;
// import java.util.Objects;
// import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainControllerTest {
    // @Nested
    // @DisplayName("Main Controller UI Tests")
    // @ExtendWith(ApplicationExtension.class)
    // public class MainControllerTestUI {
    //     final FXMLView view = FXMLView.MAIN;
        
    //     @Start
    //     public void start(Stage stage) throws Exception {
    //         URL loc = getClass().getResource(view.getFxmlLoc());
    //         Parent root = FXMLLoader.load(Objects.requireNonNull(loc), null, null, new ControllerFactory());
    //         stage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
    //         stage.show();
    //     }

    //     @AfterEach
    //     public void tearDown() throws TimeoutException {
    //         FxToolkit.hideStage();
    //     }

    //     @Test
    //     public void testShowHomePane() {
    //      Platform.runLater(() -> {
    //          MainController.getInstance().showHomePane();
    //          WaitForAsyncUtils.waitForFxEvents();

    //          assertTrue(MainController.getInstance().getHomePaneController().homePane.isVisible());
    //      });
    //     }

    //     @Test
    //     public void testShowReferencePane() {
    //      Platform.runLater(() -> {
    //          MainController.getInstance().showReferencePane();
    //          WaitForAsyncUtils.waitForFxEvents();

    //          assertTrue(MainController.getInstance().getReferencePaneController().referencePane.isVisible());
    //      });
    //     }
    // }

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
        MenuPaneController controller = new MenuPaneController();
        
        MainController.getInstance().registerController(controller);

        assertEquals(controller, MainController.getInstance().getMenuPaneController());
    }

    @Test
    public void testRegisterController_HomePaneController() {
        PrimaryController controller = new PrimaryController();
        
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
