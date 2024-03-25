package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class SummaryPaneControllerGUITest extends GUITest {
    SummaryPaneController controller;

    public SummaryPaneControllerGUITest() {
        super(FXMLView.SUMMARY);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (SummaryPaneController) getController();
    }

    @Test
    void testStart() {
        FxAssert.verifyThat(FXIds.ANCHORPANE_SUMMARY, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            AnchorPane summaryPane = getNode(FXIds.ANCHORPANE_SUMMARY);

            controller.hide();

            FxAssert.verifyThat(summaryPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(summaryPane, NodeMatchers.isVisible());
        });
    }

    @Test
    void testSummaryPaneHandler_btnNext() {
        clickOn(FXIds.BTN_SAVE);
    }
}
