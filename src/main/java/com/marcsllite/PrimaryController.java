package com.marcsllite;

import com.marcsllite.util.BaseController;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class PrimaryController extends BaseController {
  @FXML GridPane homePane;

  @Override
  public void show() {
    homePane.setVisible(true);
    homePane.toFront();
  }

  @Override
  public void hide() {
    homePane.setVisible(false);
    homePane.toBack();
  }
}
