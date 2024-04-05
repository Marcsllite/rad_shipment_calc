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

import java.io.IOException;

public class SplashScreenController {
    @FXML public GridPane gridPaneSplash;
    @FXML public Label labelSplash;
    @FXML public ProgressBar progressSplash;
    private PropHandler propHandler;
    private DBService dbService;
    SplashScreenTask splashScreenTask = null;

    public SplashScreenController() throws IOException {
        this(null);
    }

    public SplashScreenController(PropHandler propHandler) throws IOException {
        setPropHandler(propHandler == null? new PropHandlerFactory().getPropHandler(null): propHandler);
        setDbService(new DBServiceImpl(getPropHandler()));
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
            getDbService().getAllIsotopes();

            updateProgress(2, 2);

            Platform.runLater(() -> {
                App.loadPrimaryStage();
                App.getStageHandler().closeSecondary();
            });

            return null;
        }
    }
}
