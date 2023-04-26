package com.marcsllite.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Objects;

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
  
  public Parent loadViewNodeHierarchy(FXMLView view) throws RuntimeException {
    if (view == null) { 
      throw new InvalidParameterException(NULL_ERROR);
    }

    Parent rootNode = null;
    URL location = getClass().getResource(view.getFxmlLoc());
    try {
      rootNode = FXMLLoader.load(Objects.requireNonNull(location), null, null, factory);
      Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
    } catch (Exception exception) {
      logAndThrowException("Unable to load FXML view " + view.getFxmlName(), exception);
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
