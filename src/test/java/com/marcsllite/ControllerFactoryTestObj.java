package com.marcsllite;

import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SplashScreenController;
import com.marcsllite.controller.SummaryPaneController;
import com.marcsllite.service.DBService;
import com.marcsllite.util.factory.ControllerFactory;
import com.marcsllite.util.handler.PropHandler;

import java.io.IOException;

public class ControllerFactoryTestObj extends ControllerFactory {

    public ControllerFactoryTestObj() {
        super();
    }
    public ControllerFactoryTestObj(DBService dbService) {
        super(dbService);
    }

    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = null;

        try {
            PropHandler propHandler = new PropHandlerTestObj();
            if(name.equals(SplashScreenController.class.getName())) {
                ret = new SplashScreenController(propHandler);
            } else if(name.equals(MainController.class.getName())) {
                ret = MainController.getInstance();
            } else if(name.equals(MenuPaneController.class.getName())) {
                ret = new MenuPaneController(propHandler);
            } else if(name.equals(HomePaneController.class.getName())) {
                ret = new HomePaneController(propHandler);
            } else if(name.equals(ReferencePaneController.class.getName())) {
                ret = new ReferencePaneController(propHandler) {
                    @Override
                    public DBService getDbService() {
                        return ControllerFactoryTestObj.this.getDbService();
                    }
                };
            } else if(name.equals(ModifyController.class.getName())) {
                ret = new ModifyController(getPage(), propHandler);
            } else if(name.equals(ShipmentDetailsController.class.getName())) {
                ret = new ShipmentDetailsController(propHandler);
            } else if(name.equals(SummaryPaneController.class.getName())) {
                ret = new SummaryPaneController(propHandler);
            }

            return ret;
        } catch (IOException ioe) {
            return null;
        }
    }
}
