package com.marcsllite.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ReferencePaneController extends BaseController {
  @FXML GridPane referencePane;

  @Override
  @FXML public void initialize() {
    super.initialize();
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
