package rad.shipment.calculator.panes;

import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.view.FXMLView;

public class StartWithSummaryPane extends Main {
    final FXMLView summaryView = FXMLView.SUMMARY;

    @Override
    protected void displayInitialScene() { stageManager.switchScene(summaryView); }
}
