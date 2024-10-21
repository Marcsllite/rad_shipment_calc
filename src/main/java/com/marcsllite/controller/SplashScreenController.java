package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashScreenController {
    @FXML GridPane gridPaneSplash;
    @FXML Label labelSplash;
    @FXML ProgressBar progressSplash;
    private PropHandler propHandler;
    private DBService dbService;
    SplashScreenTask splashScreenTask = null;
    private final boolean openMainApp;

    public SplashScreenController() throws IOException {
        this(null, false);
    }

    public SplashScreenController(PropHandler propHandler, boolean openMainApp) throws IOException {
        this.propHandler = propHandler == null? new PropHandlerFactory().getPropHandler(null): propHandler;
        this.dbService = new DBServiceImpl(getPropHandler());
        this.openMainApp = openMainApp;
    }

    @FXML
    public void initialize() {
        show();
        startTask(new SplashScreenTask());
    }

    public void show() {
        gridPaneSplash.setVisible(true);
    }

    protected void startTask(SplashScreenTask target){
        if(splashScreenTask != null && splashScreenTask.isRunning()) {
            splashScreenTask.cancel();
        }

        splashScreenTask = target;
        Thread thread = new Thread(splashScreenTask);
        bindSplashScreenTask(splashScreenTask);
        thread.setDaemon(true);
        thread.start();
    }

    protected void bindSplashScreenTask(Task<Void> target) {
        unbindSplashScreenTask();
        labelSplash.textProperty().bind(target.messageProperty());
        progressSplash.progressProperty().bind(target.progressProperty());
    }

    protected void unbindSplashScreenTask() {
        labelSplash.textProperty().unbind();
        progressSplash.progressProperty().unbind();
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    protected class SplashScreenTask extends Task<Void> {
        @Override
        protected Void call() {
            updateMessage("Initializing and validating database...");
            updateProgress(0, 2);
            getDbService().validateDb();

            updateMessage("Initializing Isotopes for Reference Page Table...");
            updateProgress(1, 2);
            getDbService().getAllNuclides();

            updateProgress(2, 2);

            Platform.runLater(() -> {
                App.getStageHandler().closeSecondary();
                if(openMainApp) {
                    App.getStageHandler().setPrimaryStage(new Stage());
                    App.showPrimaryStage();
                }
            });

            return null;
        }
    }
}
