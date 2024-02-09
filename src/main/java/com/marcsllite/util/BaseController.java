package com.marcsllite.util;

import com.marcsllite.MainController;
import javafx.fxml.FXML;

public abstract class BaseController {
    protected final MainController main = MainController.getInstance();

    /**
     * FXML Function to initialize GUI
     * Links controller to main controller (mediator)
     */
    @FXML
    public void initialize(){
        main.registerController(this);
    }

    /**
     * Function to show the controller's scene
     */
    public abstract void show();
    
    /**
     * Function to hide the controller's scene
     */
    public abstract void hide();
}
