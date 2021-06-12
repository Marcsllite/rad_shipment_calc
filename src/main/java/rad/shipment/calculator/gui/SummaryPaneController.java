package rad.shipment.calculator.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.util.logging.Logger;

public class SummaryPaneController {
    @FXML private TextArea txtAreaSummary;
    @FXML private Button btnSummarySave;

    // Declaring variables
    private final static Logger LOGR = Logger.getLogger(SummaryPaneController.class.getName());  // getting logger

    /*//////////////////////////////////////////// SUMMARY PANE CONTROLLER ///////////////////////////////////////////*/

    /**
     * FXML function to handle any summary pane button pressed
     *
     * @param event the action performed
     */
    @FXML private void SummaryPaneHandler(ActionEvent event) {
        if(event.getSource() == btnSummarySave) saveBtnHandler();
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/
    /**
     * Helper function to handle the save button being clicked
     */
    private void saveBtnHandler() {
        LOGR.info("User clicked the " + btnSummarySave.getText() + " button.");
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    /**
     * Getter function to get the text area on the summary page
     *
     * @return the text area on the summary page
     */
    public TextArea getTxtAreaSummary() { return txtAreaSummary; }
}
