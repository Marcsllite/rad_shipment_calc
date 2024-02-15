package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.Set;

public abstract class GUITest extends FxRobot {
    FXMLView view;
    protected App.AppPane root;
    public static PropHandler propHandler =  new PropHandler() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return ("defaultNum".equals(key))? "-2.0" : "";
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("defaultNum");
        }
    };

    protected void start(Stage stage, FXMLView view) {
        this.view = view;
        root = new App.AppPane(stage, view, propHandler);
        root.getStageHandler().getPrimaryStage().show();
    }
}