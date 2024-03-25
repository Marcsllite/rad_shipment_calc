package com.marcsllite;

import com.marcsllite.controller.BaseController;
import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SummaryPaneController;
import com.marcsllite.util.factory.ControllerFactory;
import com.marcsllite.util.handler.PropHandler;

public class ControllerFactoryTestObj extends ControllerFactory {
    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = getController(name);

        if(ret == null) {
            PropHandler propHandler = new PropHandlerTestObj();
            if(name.equals(MainController.class.getName())) {
                ret = MainController.getInstance();
            } else if(name.equals(MenuPaneController.class.getName())) {
                ret = new MenuPaneController(propHandler);
            } else if(name.equals(HomePaneController.class.getName())) {
                ret = new HomePaneController(propHandler);
            } else if(name.equals(ReferencePaneController.class.getName())) {
                ret = new ReferencePaneController(propHandler);
            } else if(name.equals(ModifyController.class.getName())) {
                ret = new ModifyController(propHandler);
            } else if(name.equals(ShipmentDetailsController.class.getName())) {
                ret = new ShipmentDetailsController(propHandler);
            } else if(name.equals(SummaryPaneController.class.getName())) {
                ret = new SummaryPaneController(propHandler);
            }

            if(ret instanceof BaseController ||
                ret instanceof MainController) {
                controllerMap.put(name, ret);
            }
        }

        return ret;
    }
}
