package com.marcsllite;

import com.marcsllite.controller.BaseController;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.factory.ControllerFactory;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.EntityManagerHandler;
import com.marcsllite.util.handler.FolderHandler;
import com.marcsllite.util.handler.PropHandler;
import com.marcsllite.util.handler.StageHandler;
import javafx.application.Application;
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
    private static StageHandler stageHandler;
    private FolderHandler folderHandler;
    private DBService dbService;
    private PropHandler propHandler;
    private ControllerFactory controllerFactory;
    private FXMLView view;
    private boolean isModal = false;
    private BaseController.Page page = BaseController.Page.NONE;

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
                    getStageHandler().close();
                }
            }
        }
    }

    protected void init(FXMLView view, PropHandler propHandler, FolderHandler folderHandler, DBService dbService, ControllerFactory controllerFactory) throws IOException {
        setControllerFactory(controllerFactory);

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

        // init DB
        setDbService(dbService == null?
            new DBServiceImpl():
            dbService);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setStageHandler(new StageHandler(stage, getPropHandler(), getControllerFactory()));
        if(getPage() == null || BaseController.Page.NONE.equals(getPage())) {
            getStageHandler().show(getView());
        } else {
            getStageHandler().showModal(getView(), getPage());
        }
    }

    @Override
    public void stop() {
        if(EntityManagerHandler.getInstance() != null &&
            EntityManagerHandler.getInstance().getFactory() != null &&
            EntityManagerHandler.getInstance().getFactory().isOpen()) {
            EntityManagerHandler.getInstance().getFactory().close();
        }
    }

    /**
     * Getter function to get the database service
     *
     * @return the database service
     */
    public DBService getDbService() { return dbService; }


    /**
     * Getter function to get the StageHandler
     *
     * @return the StageHandler
     */
    public static StageHandler getStageHandler() {
        return stageHandler;
    }

    public static void setStageHandler(StageHandler stageHandler) {
        if(App.stageHandler == null) {
            App.stageHandler = stageHandler;
        }
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

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public FXMLView getView() {
        return view;
    }

    public void setView(FXMLView view) {
        this.view = view;
    }

    public boolean isModal() {
        return isModal;
    }

    public void setModal(boolean modal) {
        isModal = modal;
    }

    public BaseController.Page getPage() {
        return page;
    }

    public void setPage(BaseController.Page page) {
        this.page = page;
    }
}