package com.marcsllite.util;

import java.security.InvalidParameterException;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
  private static final Logger logr = LogManager.getLogger();
  
  private final Stage primaryStage;
  
  private FXMLLoader fxmlLoader;
  
  private static final String NULL_ERROR = "FXML View is null";
  
  public StageManager(Stage stage) {
    this.primaryStage = stage;
  }
  
  public Stage getPrimaryStage() {
    return this.primaryStage;
  }
  
  public void switchScene(FXMLView view) {
    if (view == null)
      throw new InvalidParameterException(NULL_ERROR); 
    Parent root = loadViewNodeHierarchy(view);
    this.primaryStage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
    this.primaryStage.setMinWidth(view.getWidth());
    this.primaryStage.setMinHeight(view.getHeight());
    this.primaryStage.setMaxWidth(view.getMaxWidth());
    this.primaryStage.setMaxHeight(view.getMaxHeight());
    this.primaryStage.setFullScreen(false);
    this.primaryStage.setMaximized(false);
    show(view);
  }
  
  public <T> T getController() { return (T) this.fxmlLoader.getController(); }
  
  protected void show(FXMLView view) throws RuntimeException {
    if (view == null)
      throw new InvalidParameterException(NULL_ERROR); 
    this.primaryStage.setTitle(view.getTitle());
    this.primaryStage.getIcons().add(view.getIconImage());
    this.primaryStage.centerOnScreen();
    try {
      this.primaryStage.show();
    } catch (Exception exception) {
      logAndThrowException("Unable to show scene titled " + view.getTitle(), exception);
      Platform.exit();
    } 
  }
  
  protected Parent loadViewNodeHierarchy(FXMLView view) throws RuntimeException {
    if (view == null)
      throw new InvalidParameterException(NULL_ERROR); 
    Parent rootNode = null;
    try {
      this.fxmlLoader = new FXMLLoader(getClass().getResource(view.getFxmlLoc()));
      rootNode = (Parent)this.fxmlLoader.load();
      Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
    } catch (Exception exception) {
      logAndThrowException("Unable to load FXML view " + view.getFxmlName(), exception);
      Platform.exit();
    } 
    return rootNode;
  }
  
  @SuppressWarnings("java:S112")
  protected void logAndThrowException(String errorMsg, Exception exception) throws RuntimeException {
    if (errorMsg == null || errorMsg.isBlank())
      errorMsg = Util.getString("defaultMessage"); 
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
