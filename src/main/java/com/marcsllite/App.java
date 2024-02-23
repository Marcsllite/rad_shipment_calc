package com.marcsllite;

import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.FolderHandler;
import com.marcsllite.util.handler.PropHandler;
import com.marcsllite.util.handler.StageHandler;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static final Logger logr = LogManager.getLogger();
    private static final String PERSISTENCE_UNIT_NAME = "com.marcsllite.db";
    @PersistenceUnit
    private EntityManagerFactory factory;
    private StageHandler stageHandler;
    private FolderHandler folderHandler;
    private DBService dbService;
    private PropHandler propHandler;
    private FXMLView view;

    public App() {
        this(true);
    }

    protected App(boolean doInit) {
        if(doInit) {
            try {
                init(null, null, null, null, null);
            } catch (IOException e) {
                logr.catching(Level.FATAL, e);
                logr.fatal("Failed to start application");
                // Can only initialize one FX Thread per JVM
                // Do not call Platform.exit when testing because other tests that
                // require the FX Thread will fail or be ignored
                if(System.getProperty("keepPlatformOpen") == null) {
                    Platform.exit();
                }
            }
        }
    }

    protected void init(FXMLView view, PropHandler propHandler, FolderHandler folderHandler, EntityManagerFactory emFactory, DBService dbService) throws IOException {
        setView(view == null?
            FXMLView.MAIN:
            view);

        // setup properties
        setPropHandler(propHandler == null?
            new PropHandlerFactory().getPropHandler(null):
            propHandler);

        // setup folder structure
        setFolderHandler(folderHandler == null?
            new FolderHandler(getPropHandler()):
            folderHandler);
        System.setProperty("h2.baseDir", getFolderHandler().getDataFolderPath());

        // Init JPA
        setFactory(emFactory == null?
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME):
            emFactory);

        // init DB
        setDbService(dbService == null?
            new DBServiceImpl():
            dbService);
    }

    @Override
    public void start(Stage stage) {
        stageHandler = new StageHandler(stage, getPropHandler(), null);
        stageHandler.show(getView());
    }

    @Override
    public void stop() {
        if(factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    /**
     * Getter function to get the StageHandler
     *
     * @return the StageHandler
     */
    public StageHandler getStageManager() { return stageHandler; }

    /**
     * Getter function to get the database service
     *
     * @return the database service
     */
    public DBService getDbService() { return dbService; }


    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public StageHandler getStageHandler() {
        return stageHandler;
    }

    public void setStageHandler(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
    }

    public FolderHandler getFolderHandler() {
        return folderHandler;
    }

    public void setFolderHandler(FolderHandler folderHandler) {
        this.folderHandler = folderHandler;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public FXMLView getView() {
        return view;
    }

    public void setView(FXMLView view) {
        this.view = view;
    }
}