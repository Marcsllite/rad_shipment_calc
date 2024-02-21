package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
public class MainControllerGUITest extends GUITest {
    MainController controller;

    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.MAIN);
        controller = (MainController) getController(MainController.class);
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
