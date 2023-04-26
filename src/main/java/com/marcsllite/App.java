package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private StageManager stageManager;

    /**
     * Main function to run application
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        setStageManager(stage);
        stageManager.show(FXMLView.MAIN);
    }

    /**
     * Create a new StageManager with root at given stage
     * 
     * @param stage the root stage
     */
    private void setStageManager(Stage stage) {
        stageManager = new StageManager(stage);
    }

    /**
     * Getter function to get the StageManager
     *
     * @return the StageManager
     */
    public StageManager getStageManager() { return stageManager; }
}