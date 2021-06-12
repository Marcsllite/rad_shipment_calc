package rad.shipment.calculator.panes;

import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.view.FXMLView;

public class StartWithMenuPane extends Main {
    final FXMLView menuView = FXMLView.MENU;

    @Override
    protected void displayInitialScene() { stageManager.switchScene(menuView); }
}
