package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.FolderHandler;
import com.marcsllite.util.handler.PropHandler;
import com.marcsllite.util.handler.StageHandler;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    @PersistenceUnit
    private EntityManagerFactory factory;
    private final String PERSISTENCE_UNIT_NAME = "com.marcsllite.db";
    private StageHandler stageHandler;
    private FolderHandler folderHandler;

    @Override
    public void start(Stage stage) {
        folderHandler = new FolderHandler();
        System.setProperty("h2.baseDir", folderHandler.getDataFolderPath());
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
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
            getChildren().addAll(
                stageHandler.loadViewNodeHierarchy(view).getChildrenUnmodifiable()
            );
        }

        public StageHandler getStageHandler() {
            return stageHandler;
        }
    }
}