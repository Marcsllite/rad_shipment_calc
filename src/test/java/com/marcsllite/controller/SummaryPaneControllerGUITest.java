package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
    AnchorPane anchorPaneSummary;
    TextArea txtAreaSummary;
    Button btnSaveSummary;

    public SummaryPaneControllerGUITest() {
        super(FXMLView.SUMMARY, BaseController.Page.SUMMARY);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (SummaryPaneController) getController();
        anchorPaneSummary = GUITest.getNode(FXIds.ANCHORPANE_SUMMARY);
        txtAreaSummary = GUITest.getNode(FXIds.TXTAREA_SUMMARY);
        btnSaveSummary = GUITest.getNode(FXIds.BTN_SAVE_SUMMARY);
    }

    @Test
    void testStart() {
        FxAssert.verifyThat(anchorPaneSummary, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        FxAssert.verifyThat(anchorPaneSummary, NodeMatchers.isInvisible());

        interact(() ->controller.show());

        FxAssert.verifyThat(anchorPaneSummary, NodeMatchers.isVisible());
    }

    @Test
    void testSummaryPaneHandler_btnNext() {
        clickOn(btnSaveSummary);
    }
}
