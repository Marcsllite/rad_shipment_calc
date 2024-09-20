package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.PropManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.Set;

public abstract class GUITest extends FxRobot {
    FXMLView view;
    protected Parent root;
    public static PropManager propManager =  new PropManager() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return ("defaultInt".equals(key))? "-2.0" : "";
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("defaultInt");
        }
    };

    void start(Stage stage, FXMLView view) {
        this.view = view;
        root = new App.AppPane(view, propManager);
        stage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
        stage.show();
    }
}