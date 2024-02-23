package com.marcsllite.controller;

import com.marcsllite.util.handler.PropHandler;
import javafx.fxml.FXML;

public abstract class BaseController {
    private MainController main;
    private PropHandler propHandler;

    public BaseController() {
        setPropHandler(new PropHandler());
        setMain(MainController.getInstance());
    }

    /**
     * FXML Function to initialize GUI
     * Links controller to main controller (mediator)
     */
    @FXML
    public void initialize(){
        getMain().registerController(this);
    }

    /**
     * Function to show the controller's scene
     */
    public abstract void show();
    
    /**
     * Function to hide the controller's scene
     */
    public abstract void hide();

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public MainController getMain() {
        return main;
    }

    public void setMain(MainController main) {
        this.main = main;
    }
}
