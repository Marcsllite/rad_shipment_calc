package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import com.marcsllite.util.handler.StageHandler;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private StageHandler stageHandler;

    @Override
    public void start(Stage stage) {
        stageHandler = new StageHandler(stage, null);
        stageHandler.show(FXMLView.MAIN);
    }

    /**
     * Getter function to get the StageHandler
     *
     * @return the StageHandler
     */
    public StageHandler getStageManager() { return stageHandler; }

    /**
     * Helper class to initialize application for testing using StageHandler implementation
     * Adds the children of the given FXMLView to this javafx.scene.Parent
     */
    public static class AppPane extends Parent {
        final StageHandler stageHandler;
        public AppPane(Stage stage, FXMLView view, PropHandler propHandler) {
            super();
            stageHandler = new StageHandler(stage, propHandler);
            stageHandler.switchScene(view);
            getChildren().addAll(
                stageHandler.getPrimaryStage().getScene().rootProperty().get().getChildrenUnmodifiable()
            );
        }

        public StageHandler getStageHandler() {
            return stageHandler;
        }
    }
}