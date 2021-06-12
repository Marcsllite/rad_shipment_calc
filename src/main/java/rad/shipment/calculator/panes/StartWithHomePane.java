package rad.shipment.calculator.panes;

import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.view.FXMLView;

public class StartWithHomePane extends Main {
    final FXMLView homeView = FXMLView.HOME;

    @Override
    protected void displayInitialScene() { stageManager.switchScene(homeView); }
}
