package com.marcsllite.util.handler;

import com.marcsllite.controller.BaseController;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.factory.ControllerFactory;
import com.marcsllite.util.factory.PropHandlerFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;

public class StageHandler {
    private static final Logger logr = LogManager.getLogger();
    private final Stage primaryStage;
    private Stage secondaryStage;
    private FXMLLoader loader;
    private ControllerFactory factory;
    private FXMLView curView;
    private PropHandler propHandler;
    private static final String NULL_ERROR = "FXML View is null";
    private static final String FATAL_ERROR = "Unable to show {} scene";
    protected static final String DEFAULT_MSG = "No Message";
    private static final String KEEP_PLATFORM_OPEN_PROPERTY = "keepPlatformOpen";

    public StageHandler(Stage stage) throws IOException {
        this(stage, null, null);
    }

    public StageHandler(Stage stage, PropHandler propHandler, ControllerFactory factory) throws IOException {
        primaryStage = stage;
        getPrimaryStage().setOnCloseRequest(e -> Platform.exit());
        setPropHandler(propHandler == null? new PropHandlerFactory().getPropHandler(null) : propHandler);
        setFactory(factory == null? new ControllerFactory() : factory);
        setCurView(null);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getSecondaryStage() {
        return secondaryStage;
    }

    public void setSecondaryStage(Stage secondaryStage) {
        this.secondaryStage = secondaryStage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
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

    public Object getController() {
        return getLoader() == null? null: getLoader().getController();
    }

    public void switchScene(FXMLView view, BaseController.Page page) throws RuntimeException {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        } else if(view.equals(getCurrentView())) {
            return;
        }

        getFactory().setPage(page);
        Parent root = loadViewNodeHierarchy(view);
        getPrimaryStage().setScene(new Scene(root, view.getWidth(), view.getHeight()));
        getPrimaryStage().setMinWidth(view.getWidth());
        getPrimaryStage().setMinHeight(view.getHeight());
        getPrimaryStage().setMaxWidth(view.getMaxWidth());
        getPrimaryStage().setMaxHeight(view.getMaxHeight());
        getPrimaryStage().setFullScreen(false);
        getPrimaryStage().setMaximized(false);
        getPrimaryStage().setTitle(view.getTitle());
        getPrimaryStage().getIcons().add(view.getIconImage());
        getPrimaryStage().centerOnScreen();

        setCurView(view);
    }

    public void switchSceneModal(FXMLView view, BaseController.Page page) throws RuntimeException {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        } else if(view.equals(getCurrentView())) {
            return;
        }

        getFactory().setPage(page);
        Parent root = loadViewNodeHierarchy(view);
        setSecondaryStage(new Stage());
        getSecondaryStage().initModality(Modality.APPLICATION_MODAL);
        getSecondaryStage().setScene(new Scene(root, view.getWidth(), view.getHeight()));
        getSecondaryStage().setMinWidth(view.getWidth());
        getSecondaryStage().setMinHeight(view.getHeight());
        getSecondaryStage().setMaxWidth(view.getMaxWidth());
        getSecondaryStage().setMaxHeight(view.getMaxHeight());
        getSecondaryStage().setFullScreen(false);
        getSecondaryStage().setMaximized(false);
        getSecondaryStage().setResizable(false);
        getSecondaryStage().setTitle(view.getTitle());
        getSecondaryStage().getIcons().add(view.getIconImage());
        getSecondaryStage().centerOnScreen();

        setCurView(view);
    }

    public void showSplashScreen() throws RuntimeException {
        setCurView(FXMLView.SPLASH);

        try {
            Parent root = loadViewNodeHierarchy(getCurrentView());
            setSecondaryStage(new Stage());
            getSecondaryStage().initStyle(StageStyle.UNDECORATED);
            getSecondaryStage().setScene(new Scene(root, getCurrentView().getWidth(), getCurrentView().getHeight()));
            getSecondaryStage().setMinWidth(getCurrentView().getWidth());
            getSecondaryStage().setMinHeight(getCurrentView().getHeight());
            getSecondaryStage().setMaxWidth(getCurrentView().getMaxWidth());
            getSecondaryStage().setMaxHeight(getCurrentView().getMaxHeight());
            getSecondaryStage().setFullScreen(false);
            getSecondaryStage().setMaximized(false);
            getSecondaryStage().setResizable(false);
            getSecondaryStage().setTitle(getCurrentView().getTitle());
            getSecondaryStage().getIcons().add(getCurrentView().getIconImage());
            getSecondaryStage().centerOnScreen();

            getSecondaryStage().show();
        } catch (Exception exception) {
            logr.catching(Level.FATAL, exception);
            logr.atLevel(Level.FATAL)
                .withThrowable(exception)
                .log(FATAL_ERROR, getCurrentView().getName());
            // Can only initialize one FX Thread per JVM
            // Do not call Platform.exit when testing because other tests that
            // require the FX Thread will fail or be ignored
            if(System.getProperty(KEEP_PLATFORM_OPEN_PROPERTY) == null) {
                closePrimary();
            }
        }
    }

    public void show(FXMLView view, BaseController.Page page) throws RuntimeException {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        }

        try {
            switchScene(view, page);
            getPrimaryStage().show();
        } catch (Exception exception) {
            logr.catching(Level.FATAL, exception);
            logr.atLevel(Level.FATAL)
                .withThrowable(exception)
                .log(FATAL_ERROR, view.getName());
            // Can only initialize one FX Thread per JVM
            // Do not call Platform.exit when testing because other tests that
            // require the FX Thread will fail or be ignored
            if(System.getProperty(KEEP_PLATFORM_OPEN_PROPERTY) == null) {
                closePrimary();
            }
        }
    }

    public void showModal(FXMLView view, BaseController.Page page) throws RuntimeException {
        if (view == null) {
            throw new InvalidParameterException(NULL_ERROR);
        }

        try {
            switchSceneModal(view, page);
            BaseController controller = (BaseController) getController();
            getSecondaryStage().setOnCloseRequest(e -> {
                e.consume();
                logr.debug("Closing the {}", view.getName());
                getSecondaryStage().close();
            });
            

            if(controller.isInit()) {
                getSecondaryStage().showAndWait();
                logr.debug("Opening the {}", view.getName());
            } else {
                String msg = String.format("Failed to initialize modal window %s", view.getName());
                InstantiationException ie = new InstantiationException(msg);
                logr.throwing(ie);
                throw ie;
            }
        } catch (Exception exception) {
            logr.catching(Level.FATAL, exception);
            logr.atLevel(Level.FATAL)
                .withThrowable(exception)
                .log(FATAL_ERROR, view.getName());
            // Can only initialize one FX Thread per JVM
            // Do not call Platform.exit when testing because other tests that
            // require the FX Thread will fail or be ignored
            if(System.getProperty(KEEP_PLATFORM_OPEN_PROPERTY) == null) {
                closePrimary();
            }
        }
    }

    public void closePrimary() {
        getPrimaryStage().close();
    }

    public void closeSecondary() {
        if(getSecondaryStage() != null) {
            getSecondaryStage().close();
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
        setLoader(new FXMLLoader());
        getLoader().setLocation(getClass().getResource(view.getFxmlLoc()));
        getLoader().setControllerFactory(getFactory());
        getLoader().setResources(getPropHandler());
        try {
            rootNode = getLoader().load();
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndThrowException(errMsg, exception);
            // Can only initialize one FX Thread per JVM
            // Do not call Platform.exit when testing because other tests that
            // require the FX Thread will fail or be ignored
            if(System.getProperty(KEEP_PLATFORM_OPEN_PROPERTY) == null) {
                closePrimary();
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
