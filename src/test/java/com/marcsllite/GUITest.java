package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.PropManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;

public abstract class GUITest extends FxRobot {
    FXMLView view;
    protected Parent root;
    AbstractMap<String, String> stringsMap = new HashMap<>();
    PropManager propManager = new PropManager() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return (stringsMap.get(key) == null)? "" : stringsMap.get(key);
        }

        @Override
        protected Set<String> handleKeySet() {
            return stringsMap.keySet();
        }
    };

    void init() {
        stringsMap.put("defaultInt", "-2.0");
    }

    void start(Stage stage, FXMLView view) {
        init();
        this.view = view;
        root = new App.AppPane(view, propManager);
        stage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
        stage.show();
    }
}