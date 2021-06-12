package rad.shipment.calculator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyCode;

import java.security.InvalidParameterException;
import java.util.logging.Logger;

public class MainController {
    // DECLARING FXML CONTROLLERS
    @FXML private MenuPaneController menuPaneController = new MenuPaneController();
    @FXML private HomePaneController homePaneController = new HomePaneController();
    // TODO: Add Reference Pane

    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(MainController.class.getName());  // getting logger

    /**
     * FXML Function to initialize GUI (run when the edit.fxml file is loaded by the FXMLLoader)
     */
    @FXML private void initialize(){
        // injecting MainController in menuPaneController to allow
        // the menuPaneController to have access to the other Controllers using the MainController
        menuPaneController.injectMainController(this);
        homePaneController.injectMainController(this);

        // TODO: Make sure Home Page is visible and other pages are not
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to allow the given button to be fired
     * when the enter key is pressed and the btn is in focus
     *
     * @param btn the btn to add the listener to
     */
    static void setBtnFireOnEnter(Button btn){
        if(btn == null) throw new InvalidParameterException("button cannot be null");

        btn.setOnKeyPressed(
            event -> {
                if(event.getCode() == KeyCode.ENTER && btn.isFocused())
                    btn.fire();
            }
        );
    }

    /**
     * Helper function to allow the given Link to be fired
     * when the enter key is pressed and the btn is in focus
     *
     * @param link the link to add the listener to
     */
    static void setLinkFireOnEnter(Hyperlink link){
        if(link == null) throw new InvalidParameterException("link cannot be null");

        link.setOnKeyPressed(
            event -> {
                if(event.getCode() == KeyCode.ENTER && link.isFocused())
                    link.fire();
            }
        );
    }

    // TODO: Create Hide/Show Home Pane functions

    // TODO: Create Hide/Show Reference Pane functions
}
