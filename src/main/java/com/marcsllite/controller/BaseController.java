package com.marcsllite.controller;

import javafx.fxml.FXML;

public abstract class BaseController {
    protected MainController main;

    public BaseController() {
        main = MainController.getInstance();
    }

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
