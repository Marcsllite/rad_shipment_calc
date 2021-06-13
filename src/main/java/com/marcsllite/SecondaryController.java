package com.marcsllite;

import com.marcsllite.util.FXMLView;

import javafx.fxml.FXML;

public class SecondaryController {
  @FXML
  private void switchToPrimary() {
    App.getStageManager().switchScene(FXMLView.PRIMARY);
  }
}
