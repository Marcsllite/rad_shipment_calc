package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class HomePaneControllerGUITest extends GUITest {
    @Spy
    HomePaneController controller;

    public HomePaneControllerGUITest() {
        super(FXMLView.HOME);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (HomePaneController) getController();
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            GridPane homePane = getNode(FXIds.GRIDPANE_HOME);

            controller.hide();

            FxAssert.verifyThat(homePane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(homePane, NodeMatchers.isVisible());
        });
    }

    @Test
    void testHomePaneHandler_btnAdd() {
        clickOn(FXIds.BTN_ADD);
    }

    @Test
    void testHomePaneHandler_btnEdit() {
        clickOn(FXIds.BTN_EDIT);
    }

    @Test
    void testHomePaneHandler_btnRemove() {
        clickOn(FXIds.BTN_REMOVE);
    }

    @Test
    void testHomePaneHandler_btnCalculate() {
        clickOn(FXIds.BTN_CALCULATE);
    }
}
