package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
public class ReferencePaneControllerGUITest extends GUITest {
    ReferencePaneController controller;
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.REFERENCE);
        controller = (ReferencePaneController) getController(ReferencePaneController.class);
    }

    @Test
    public void testInit() {
        FxAssert.verifyThat(FXIds.GRIDPANE_REFERENCE, NodeMatchers.isVisible());
    }

    @Test
    public void testHideShow() {
        interact(() -> {
            GridPane refPane = getNode(FXIds.GRIDPANE_REFERENCE);

            controller.hide();

            FxAssert.verifyThat(refPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(refPane, NodeMatchers.isVisible());
        });
    }
}
