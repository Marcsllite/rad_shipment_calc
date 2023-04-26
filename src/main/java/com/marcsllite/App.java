package com.marcsllite;

import com.marcsllite.util.FXMLView;
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
        stageManager = new StageManager(stage);
        stageManager.show(FXMLView.MAIN);
    }

    /**
     * Getter function to get the StageManager
     *
     * @return the StageManager
     */
    public StageManager getStageManager() { return stageManager; }

    public static class AppPane extends Parent {
        private StageManager stageManager;

        public AppPane() {
            super();
            stageManager = new StageManager(null);
            getChildren().addAll(
                stageManager.loadViewNodeHierarchy(FXMLView.MAIN).getChildrenUnmodifiable()
            );
        }

        
    }
}