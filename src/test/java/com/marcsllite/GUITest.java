package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Stop;

import java.util.Set;
import java.util.concurrent.TimeoutException;


public abstract class GUITest extends FxRobot {
    protected FXMLView view;
    protected App.AppPane root;
    public static final PropHandler propHandler =  new PropHandler() {
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
        root.getStageHandler().switchScene(view);
        stage.show();
    }

    @Stop
    protected void stop() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        root = null;
        view = null;
    }
}