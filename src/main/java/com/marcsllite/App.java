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
 * UMass Lowell Radiation Shipment Calculator<br>
 * <b>author</b>: marcsllite@gmail.com<br>
 * <b>source</b>:
 *  <a href="https://www.ecfr.gov/current/title-49/part-173/subpart-I">
 *      49 CFR Part 173 Subpart I: Class 7 (radioactive) Materials
 *  </a>
 */
public class App extends Application {
    private static final Logger logr = LogManager.getLogger(App.class);
    private static StageHandler stageHandler;
    private static FolderHandler folderHandler;
    private static DBService dbService;
    private static PropHandler propHandler;
    private static ControllerFactory controllerFactory;
    private static FXMLView view;
    private boolean showSplash;
    private static BaseController.Page page = BaseController.Page.NONE;

    public App() {
        this(true, true);
    }

    protected App(boolean doInit, boolean showSplash) {
        setShowSplash(showSplash);
        if(doInit) {
            try {
                init(null, null, null, null, null, null);
            } catch (IOException e) {
                logr.catching(Level.FATAL, e);
                logr.fatal("Failed to start application");
                // Can only initialize one FX Thread per JVM
                // Do not call Platform.exit when testing because other tests that
                // require the FX Thread will fail or be ignored
                if(System.getProperty(StageHandler.KEEP_PLATFORM_OPEN_PROPERTY) == null) {
                    getStageHandler().closePrimary();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }

    protected static void init(StageHandler stageHandler, FXMLView view, PropHandler propHandler, FolderHandler folderHandler, DBService dbService, ControllerFactory controllerFactory) throws IOException {
        App.view = view == null?
            FXMLView.MAIN:
            view;

        setPropHandler(propHandler == null?
            new PropHandlerFactory().getPropHandler(null):
            propHandler);

        setFolderHandler(folderHandler == null?
            new FolderHandler(getPropHandler()):
            folderHandler);
        System.setProperty("h2.baseDir", getFolderHandler().getDataFolderPath());

        setDbService(dbService == null?
            new DBServiceImpl():
            dbService);

        setControllerFactory(controllerFactory == null?
            new ControllerFactory(getDbService()) :
            controllerFactory);

        setStageHandler(stageHandler == null?
            new StageHandler(null, getPropHandler(), getControllerFactory()) :
            stageHandler);
    }

    @Override
    public void start(Stage stage) throws IOException {
        getStageHandler().setPrimaryStage(stage);
        if(isShowSplash()) {
            App.getStageHandler().showSplashScreen();
        } else {
            App.showPrimaryStage();
        }
    }

    public static void showPrimaryStage() throws RuntimeException {
        if(App.getStageHandler() == null) {
            RuntimeException rte = new RuntimeException("StageHandler not initialized");
            logr.throwing(rte);
            throw rte;
        }
        App.getStageHandler().show(App.getView(), App.getPage());
    }

    @Override
    public void stop() {
        if(EntityManagerHandler.getInstance() != null &&
            EntityManagerHandler.getInstance().getFactory() != null &&
            EntityManagerHandler.getInstance().getFactory().isOpen()) {
            EntityManagerHandler.getInstance().getFactory().close();
        }
        if(getStageHandler() != null) {
            getStageHandler().close();
        }
    }

    /**
     * Getter function to get the database service
     *
     * @return the database service
     */
    public static DBService getDbService() { return App.dbService; }


    /**
     * Getter function to get the StageHandler
     *
     * @return the StageHandler
     */
    public static StageHandler getStageHandler() {
        return App.stageHandler;
    }

    public static void setStageHandler(StageHandler stageHandler) {
        App.stageHandler = stageHandler;
    }

    public static FolderHandler getFolderHandler() {
        return App.folderHandler;
    }

    public static void setFolderHandler(FolderHandler folderHandler) {
        App.folderHandler = folderHandler;
    }

    public static void setDbService(DBService dbService) {
        App.dbService = dbService;
    }

    public static PropHandler getPropHandler() {
        return App.propHandler;
    }

    public static void setPropHandler(PropHandler propHandler) {
        App.propHandler = propHandler;
    }

    public static ControllerFactory getControllerFactory() {
        return App.controllerFactory;
    }

    public static void setControllerFactory(ControllerFactory controllerFactory) {
        App.controllerFactory = controllerFactory;
    }

    public static FXMLView getView() {
        return App.view;
    }

    public static void setView(FXMLView view) {
        App.view = view;
    }

    public boolean isShowSplash() {
        return showSplash;
    }

    public void setShowSplash(boolean showSplash) {
        this.showSplash = showSplash;
    }

    public static BaseController.Page getPage() {
        return App.page;
    }

    public static void setPage(BaseController.Page page) {
        App.page = page == null? BaseController.Page.NONE : page;
    }
}