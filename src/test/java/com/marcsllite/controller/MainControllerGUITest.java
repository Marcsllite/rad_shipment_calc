package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainControllerGUITest extends GUITest {
    MainController controller;

    public MainControllerGUITest(){
        super(FXMLView.MAIN);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (MainController) getController();
    }

    @Test
    public void testInit() {
        FxAssert.verifyThat(FXIds.GRIDPANE_MENU, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.GRIDPANE_REFERENCE, NodeMatchers.isInvisible());
    }

    @Test
    public void testShowHomePane() {
        interact(() -> {
            controller.showHomePane();

            FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isVisible());
            FxAssert.verifyThat(FXIds.GRIDPANE_REFERENCE, NodeMatchers.isInvisible());
        });
    }

    @Test
    public void testShowReferencePane() {
        interact(() -> {
            controller.showReferencePane();

            FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isInvisible());
            FxAssert.verifyThat(FXIds.GRIDPANE_REFERENCE, NodeMatchers.isVisible());
        });
    }
}
