package com.marcsllite;

import com.marcsllite.util.BaseController;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class SecondaryController extends BaseController {
  @FXML GridPane referencePane;

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
