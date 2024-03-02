package com.marcsllite.util.handler;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.factory.ControllerFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.Objects;

public class StageHandler {
    private static final Logger logr = LogManager.getLogger();
    private final Stage primaryStage;
    private ControllerFactory factory;
    private FXMLView curView;
    private PropHandler propHandler;

    private static final String NULL_ERROR = "FXML View is null";
    protected static final String DEFAULT_MSG = "No Message";

    public StageHandler(Stage stage) {
        this(stage, null, null);
    }

    public StageHandler(Stage stage, PropHandler propHandler, ControllerFactory factory) {
        primaryStage = stage;
        setPropHandler(propHandler == null? new PropHandler() : propHandler);
        setFactory(factory == null? new ControllerFactory() : factory);
        setCurView(null);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public FXMLView getCurrentView() {
        return curView;
    }

    public void setCurView(FXMLView curView) {
        this.curView = curView;
    }

    public ControllerFactory getFactory() {
        return factory;
    }

    public void setFactory(ControllerFactory factory) {
        this.factory = factory;
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public void switchScene(FXMLView view) {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        } else if(view.equals(getCurrentView())) {
            return;
        }

        Parent root = loadViewNodeHierarchy(view);
        primaryStage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
        primaryStage.setMinWidth(view.getWidth());
        primaryStage.setMinHeight(view.getHeight());
        primaryStage.setMaxWidth(view.getMaxWidth());
        primaryStage.setMaxHeight(view.getMaxHeight());
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.setTitle(view.getTitle());
        primaryStage.getIcons().add(view.getIconImage());
        primaryStage.centerOnScreen();

        curView = view;
    }

    public void show(FXMLView view) throws RuntimeException {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        }

        try {
            switchScene(view);
            primaryStage.show();
        } catch (Exception exception) {
            logr.catching(Level.FATAL, exception);
            logr.atLevel(Level.FATAL)
                .withThrowable(exception)
                .log("Unable to show {} scene", view.getName());
            // Can only initialize one FX Thread per JVM
            // Do not call Platform.exit when testing because other tests that
            // require the FX Thread will fail or be ignored
            if(System.getProperty("keepPlatformOpen") == null) {
                Platform.exit();
            }
        }
    }

    public Parent loadViewNodeHierarchy(FXMLView view) throws RuntimeException {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        }

        String errMsg = "Unable to load FXML view " + view.getFxmlName();

        if(getPropHandler().keySet().isEmpty()) {
            logAndThrowException(errMsg.concat(": The resource bundle contains no values."), null);
        }

        Parent rootNode = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(view.getFxmlLoc()));
        loader.setControllerFactory(factory);
        loader.setResources(getPropHandler());
        try {
            rootNode = loader.load();
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndThrowException(errMsg, exception);
            // Can only initialize one FX Thread per JVM
            // Do not call Platform.exit when testing because other tests that
            // require the FX Thread will fail or be ignored
            if(System.getProperty("keepPlatformOpen") == null) {
                Platform.exit();
            }
        }
        return rootNode;
    }

    @SuppressWarnings("java:S112")
    // ignored rule java:S112 as RuntimeException is used to match previous exception type
    protected void logAndThrowException(String errorMsg, Exception exception) throws RuntimeException {
        if (errorMsg == null || errorMsg.isBlank()) {
            errorMsg = StageHandler.DEFAULT_MSG;
        }

        if (exception == null) {
            logr.log(Level.FATAL, errorMsg);
        } else {
            logr.catching(Level.FATAL, exception);
        }
        var e = new RuntimeException(errorMsg);
        logr.throwing(Level.FATAL, e);
        throw e;
    }
}
