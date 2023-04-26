package com.marcsllite;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;

import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.testfx.matcher.base.NodeMatchers;

public class AppTest extends GUITest {

    @Override
    @Start
    public void start(Stage stage) {
        view = FXMLView.MAIN;
        
        Parent root = new App.AppPane();
        Scene scene = new Scene(root, view.getWidth(), view.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testStart() {
        FxAssert.verifyThat(FXIds.MENU_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.HOME_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.REF_PANE, NodeMatchers.isInvisible());
    }
}
