package com.marcsllite.controller;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

public class ReferencePaneController extends BaseController {
  @FXML GridPane referencePane;

  private final PropHandler propHandler;

  public ReferencePaneController() {
    this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
  }

  public ReferencePaneController(PropHandler propHandler) {
    this.propHandler = propHandler;
  }

  @Override
  public void show() {
    referencePane.setVisible(true);
    referencePane.toFront();
  }

  @Override
  public void hide() {
    referencePane.setVisible(false);
    referencePane.toBack();
  }
}
