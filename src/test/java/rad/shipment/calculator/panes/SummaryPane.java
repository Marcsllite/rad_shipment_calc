package rad.shipment.calculator.panes;

import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.gui.SummaryPaneControllerTest;

import java.util.logging.Logger;

public class SummaryPane {
    private static Logger LOGR = Logger.getLogger(SummaryPane.class.getName()); // matches the logger in the affected class
    private final SummaryPaneControllerTest driver;

    public SummaryPane(SummaryPaneControllerTest driver) {
        this.driver = driver;
        if (!driver.getPrimaryStage().getTitle().equals(Main.getString("summaryPane"))) {
            throw new IllegalArgumentException("Did not receive the Summary Pane to test Summary");
        }
    }

    /*//////////////////////////////////////////////// SUMMARY PANE //////////////////////////////////////////////////*/

    /*///////////////////////////////////////////////// CONVENIENCE //////////////////////////////////////////////////*/

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
}
