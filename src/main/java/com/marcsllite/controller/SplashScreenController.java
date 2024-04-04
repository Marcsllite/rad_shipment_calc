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
    Thread thread = null;

    public SplashScreenController() throws IOException {
        this(null);
    }

    public SplashScreenController(PropHandler propHandler) throws IOException {
        setPropHandler(propHandler == null? new PropHandlerFactory().getPropHandler(null): propHandler);
        setDbService(new DBServiceImpl(getPropHandler()));
    }

    @FXML
    public void initialize() {
        startTask(new SplashScreenTask());
    }

    protected void startTask(SplashScreenTask target){
        if(splashScreenTask != null && splashScreenTask.isRunning()) {
            splashScreenTask.cancel();
            thread.interrupt();
        }

        splashScreenTask = target;
        thread = new Thread(splashScreenTask);
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
            try {
                updateMessage("Initializing and validating database...");
                updateProgress(0, 2);
                getDbService().validateDb();

                updateMessage("Initializing Reference Page Table...");
                updateProgress(1, 2);
                getDbService().getAllIsotopes();

                updateProgress(2, 2);
                Thread.sleep(50);

                Platform.runLater(() -> {
                    App.loadPrimaryStage();
                    gridPaneSplash.getScene().getWindow().hide();
                });
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }
}
