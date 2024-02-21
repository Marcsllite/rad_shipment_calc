package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class HomePaneControllerGUITest extends GUITest {
    @Spy
    HomePaneController controller;
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.HOME);
        controller = (HomePaneController) getController(HomePaneController.class);
    }

    @Test
    public void testInit() {
        FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isVisible());
    }

    @Test
    public void testHideShow() {
        interact(() -> {
            GridPane homePane = getNode(FXIds.GRIDPANE_HOME);

            controller.hide();

            FxAssert.verifyThat(homePane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(homePane, NodeMatchers.isVisible());
        });
    }

    @Test
    public void testHomePaneHandler_btnAdd() {
        clickOn(FXIds.BTN_ADD);
    }

    @Test
    public void testHomePaneHandler_btnEdit() {
        clickOn(FXIds.BTN_EDIT);
    }

    @Test
    public void testHomePaneHandler_btnRemove() {
        clickOn(FXIds.BTN_REMOVE);
    }

    @Test
    public void testHomePaneHandler_btnCalculate() {
        clickOn(FXIds.BTN_CALCULATE);
    }
}
