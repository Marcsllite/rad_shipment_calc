package com.marcsllite;

import com.marcsllite.util.FXMLView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

public abstract class GUITest extends FxRobot {
    FXMLView view;
    protected Parent root;

    void start(Stage stage, FXMLView view) {
        this.view = view;
        root = new App.AppPane(view);
        stage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
        stage.show();
    }
}
