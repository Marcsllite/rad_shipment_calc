package com.marcsllite.controller;

import javafx.fxml.FXML;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController {
    // DECLARING FXML CONTROLLERS
    @FXML MenuPaneController menuPaneController;
    @FXML HomePaneController homePaneController;
    @FXML ReferencePaneController referencePaneController;

    private static final Logger logr = LogManager.getLogger(MainController.class);

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

        if(c instanceof MenuPaneController controller) {
            menuPaneController = controller;
        } else if(c instanceof HomePaneController controller) {
            homePaneController = controller;
        } else if(c instanceof ReferencePaneController controller) {
            referencePaneController = controller;
        }
    }

    public MenuPaneController getMenuPaneController() {
        return menuPaneController;
    }

    public HomePaneController getHomePaneController() {
        return homePaneController;
    }

    public ReferencePaneController getReferencePaneController() {
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
