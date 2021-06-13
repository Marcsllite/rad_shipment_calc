package com.marcsllite;

import com.marcsllite.util.FXMLView;

import javafx.fxml.FXML;

public class PrimaryController {
  @FXML
  private void switchToSecondary() {
    App.getStageManager().switchScene(FXMLView.SECONDARY);
  }
}
