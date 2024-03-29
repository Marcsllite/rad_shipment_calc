package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.PropManager;
import com.marcsllite.util.StageManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private StageManager stageManager;

    @Override
    public void start(Stage stage) {
        stageManager = new StageManager(stage, null);
        stageManager.show(FXMLView.MAIN);
    }

    /**
     * Getter function to get the StageManager
     *
     * @return the StageManager
     */
    public StageManager getStageManager() { return stageManager; }

    /**
     * Helper class to initialize application for testing using StageManager implementation
     * Adds the children of the given FXMLView to this javafx.scene.Parent
     */
    public static class AppPane extends Parent {
        public AppPane(FXMLView view, PropManager propManager) {
            super();
            StageManager stageManager = new StageManager(null, propManager);
            getChildren().addAll(
                stageManager.loadViewNodeHierarchy(view).getChildrenUnmodifiable()
            );
        }
    }
}