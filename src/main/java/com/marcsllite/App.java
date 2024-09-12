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
    private static final Logger logr = LogManager.getLogger();
    private static StageHandler stageHandler;
    private FolderHandler folderHandler;
    private DBService dbService;
    private PropHandler propHandler;
    private ControllerFactory controllerFactory;
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
                init(null, null, null, null, null);
            } catch (IOException e) {
                logr.catching(Level.FATAL, e);
                logr.fatal("Failed to start application");
                // Can only initialize one FX Thread per JVM
                // Do not call Platform.exit when testing because other tests that
                // require the FX Thread will fail or be ignored
                if(System.getProperty("keepPlatformOpen") == null) {
                    getStageHandler().closePrimary();
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
        App.setStageHandler(new StageHandler(stage, getPropHandler(), getControllerFactory()));
        if(isShowSplash()) {
            App.getStageHandler().showSplashScreen();
        } else {
            App.getStageHandler().show(App.getView(), App.getPage());
        }
    }

    public static void loadPrimaryStage() {
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
        App.stageHandler = stageHandler;
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

    public static FXMLView getView() {
        return view;
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