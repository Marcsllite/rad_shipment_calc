package com.marcsllite.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class StageManager {
  private static final Logger logr = LogManager.getLogger();
  
  private final Stage primaryStage;
  private final ControllerFactory factory;
  private FXMLView curView;
  
  private static final String NULL_ERROR = "FXML View is null";
  protected static final String DEFAULT_MSG = "No Message";

  public StageManager(Stage stage) {
    primaryStage = stage;
    factory = new ControllerFactory();
    curView = null;
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public FXMLView getCurrentView() {
    return curView;
  }
  
  public void switchScene(FXMLView view) {
    if (view == null) {
      throw new InvalidParameterException(NULL_ERROR);
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
  }
  
  public void show(FXMLView view) throws RuntimeException {
    if (view == null) {
      throw new InvalidParameterException(NULL_ERROR);
    }

    try {
      switchScene(view);
      primaryStage.show();
    } catch (Exception exception) {
      logAndThrowException("Unable to show " + view.getName() + " scene", exception);
      Platform.exit();
    }
    curView = view;
  }
  
  public Parent loadViewNodeHierarchy(FXMLView view, PropManager... properties) throws RuntimeException {
    if (view == null) { 
      throw new InvalidParameterException(NULL_ERROR);
    }

    String err_msg = "Unable to load FXML view " + view.getFxmlName();
    PropManager propManager;
    if(Arrays.stream(properties).findAny().isEmpty()) {
      propManager = (PropManager) ResourceBundle.getBundle(PropManager.PROP_NAME, new Locale("en", "US"), new PropManagerControl());
    } else {
      propManager = properties[0];
    }

    if(propManager.keySet().isEmpty()) {
      logAndThrowException(err_msg.concat(": The resource bundle contains no values."), null);
    }

    Parent rootNode = null;
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource(view.getFxmlLoc()));
    loader.setControllerFactory(factory);
    loader.setResources(propManager);
    try {
      rootNode = loader.load();
      Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
    } catch (Exception exception) {
      logAndThrowException(err_msg, exception);
      Platform.exit();
    } 
    return rootNode;
  }
  
  @SuppressWarnings("java:S112")
  // ignored rule java:S112 as RuntimeException is used to match previous exception type
  protected void logAndThrowException(String errorMsg, Exception exception) throws RuntimeException {
    if (errorMsg == null || errorMsg.isBlank()) {
      errorMsg = StageManager.DEFAULT_MSG;
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
