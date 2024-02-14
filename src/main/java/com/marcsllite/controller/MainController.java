package com.marcsllite.controller;

import javafx.fxml.FXML;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController {
    // DECLARING FXML CONTROLLERS
    @FXML private MenuPaneController menuPaneController;
    @FXML private PrimaryController homePaneController;
    @FXML private SecondaryController referencePaneController;

    private static final Logger logr = LogManager.getLogger();

    private MainController() {}

    private static class MainControllerSingleton {
        private static final MainController INSTANCE = new MainController();
    }

    /**
     * Getter Function to get this class' current instance
     * If instance is null, a new one will be created
     * 
     * @return the current MainController instance
     */
    public static MainController getInstance() {
        return MainControllerSingleton.INSTANCE;
    }

    /**
     * FXML Function to initialize GUI
     */
    @FXML private void initialize(){    
        showHomePane();
    }

    /**
     * Function to initialize the fxml controllers this
     * 
     * @param c the base controller to be register
     */
    public void registerController(BaseController c){
        if(c == null) {
            var e = new IllegalArgumentException("Controller cannot be null");
            logr.throwing(Level.FATAL, e);
            throw e;
        }

        if(c instanceof MenuPaneController) {
            menuPaneController = (MenuPaneController) c;
        } else if(c instanceof PrimaryController) {
            homePaneController = (PrimaryController) c;
        } else if(c instanceof SecondaryController) {
            referencePaneController = (SecondaryController) c;
        }
    }

    public MenuPaneController getMenuPaneController() {
        return menuPaneController;
    }

    public PrimaryController getHomePaneController() {
        return homePaneController;
    }

    public SecondaryController getReferencePaneController() {
        return referencePaneController;
    }

    public void showHomePane() {
        homePaneController.show();
        referencePaneController.hide();
    }

    public void showReferencePane() {
        referencePaneController.show();
        homePaneController.hide();
    }
}
