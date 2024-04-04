package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
        gridPaneMenu = GUITest.getNode(FXIds.GRIDPANE_MENU);
        gridPaneHome = GUITest.getNode(FXIds.GRIDPANE_HOME);
        gridPaneRef = GUITest.getNode(FXIds.GRIDPANE_REFERENCE);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneMenu, NodeMatchers.isVisible());
        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isVisible());
        FxAssert.verifyThat(gridPaneRef, NodeMatchers.isInvisible());
    }

    @Test
    void testShowHomePane() {
        interact(() ->controller.showHomePane());

        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isVisible());
        FxAssert.verifyThat(gridPaneRef, NodeMatchers.isInvisible());
    }

    @Test
    void testShowReferencePane() {
        interact(() ->controller.showReferencePane());

        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isInvisible());
        FxAssert.verifyThat(gridPaneRef, NodeMatchers.isVisible());
    }
}
