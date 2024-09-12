package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainControllerGUITest extends GUITest {
    MainController controller;
    GridPane gridPaneMenu;
    GridPane gridPaneHome;
    GridPane gridPaneRef;

    public MainControllerGUITest(){
        super(FXMLView.MAIN, BaseController.Page.MAIN);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (MainController) getController();
        gridPaneMenu = controller.getMenuPaneController().menuPane;
        gridPaneHome = controller.getHomePaneController().homePane;
        gridPaneRef = controller.getReferencePaneController().referencePane;
    }

    @Test
    void testInit() {
        assertTrue(gridPaneMenu.isVisible());
        assertTrue(gridPaneHome.isVisible());
        assertFalse(gridPaneRef.isVisible());
    }

    @Test
    void testShowHomePane() {
        interact(() ->controller.showHomePane());

        assertTrue(gridPaneHome.isVisible());
        assertFalse(gridPaneRef.isVisible());
    }

    @Test
    void testShowReferencePane() {
        interact(() ->controller.showReferencePane());

        assertFalse(gridPaneHome.isVisible());
        assertTrue(gridPaneRef.isVisible());
    }
}
