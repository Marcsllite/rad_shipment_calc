package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class MenuPaneControllerGUITest extends GUITest {
    @Start
    void start(Stage stage) {
        super.start(stage, FXMLView.MENU);
    }
}