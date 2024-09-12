package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        anchorPaneSummary = controller.summaryPane;
        txtAreaSummary = controller.txtAreaSummary;
        btnSaveSummary = controller.btnSaveSummary;
    }

    @Test
    void testStart() {
        assertTrue(anchorPaneSummary.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        assertFalse(anchorPaneSummary.isVisible());

        interact(() ->controller.show());

        assertTrue(anchorPaneSummary.isVisible());
    }

    @Disabled("FXML method it tests has not yet been implemented")
    void testSummaryPaneHandler_btnNext() {
        clickOn(btnSaveSummary);
    }
}
