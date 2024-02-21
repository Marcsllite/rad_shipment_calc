package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
public class SummaryPaneControllerGUITest extends GUITest {
    SummaryPaneController controller;
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.SUMMARY);
        controller = (SummaryPaneController) getController(SummaryPaneController.class);
    }

    @Test
    public void testStart() {
        FxAssert.verifyThat(FXIds.ANCHORPANE_SUMMARY, NodeMatchers.isVisible());
    }

    @Test
    public void testHideShow() {
        interact(() -> {
            AnchorPane summaryPane = getNode(FXIds.ANCHORPANE_SUMMARY);

            controller.hide();

            FxAssert.verifyThat(summaryPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(summaryPane, NodeMatchers.isVisible());
        });
    }

    @Test
    public void testSummaryPaneHandler_btnNext() {
        clickOn(FXIds.BTN_SAVE);
    }
}
