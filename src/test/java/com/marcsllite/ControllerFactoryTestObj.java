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

    private MainController mainController;

    public ControllerFactoryTestObj() {
        this(null, null);
    }
    public ControllerFactoryTestObj(DBService dbService) {
        this(dbService, null);
    }
    public ControllerFactoryTestObj(DBService dbService, MainController mainController) {
        super(dbService);
        this.mainController = mainController == null? MainController.getInstance() : mainController;
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
                ret = mainController;
            } else if(name.equals(MenuPaneController.class.getName())) {
                MenuPaneController menu = new MenuPaneController(propHandler);
                menu.setMain(mainController);
                ret = menu;
            } else if(name.equals(HomePaneController.class.getName())) {
                HomePaneController home = new HomePaneController(propHandler);
                home.setMain(mainController);
                ret = home;
            } else if(name.equals(ReferencePaneController.class.getName())) {
                ReferencePaneController reference = new ReferencePaneController(propHandler);
                reference.setMain(mainController);
                ret = reference;
            } else if(name.equals(ModifyController.class.getName())) {
                ModifyController modify = new ModifyController(getPage(), propHandler);
                modify.setMain(mainController);
                modify.setDbService(dbService);
                ret = modify;
            } else if(name.equals(ShipmentDetailsController.class.getName())) {
                ShipmentDetailsController details = new ShipmentDetailsController(propHandler);
                details.setMain(mainController);
                ret = details;
            } else if(name.equals(SummaryPaneController.class.getName())) {
                SummaryPaneController summary = new SummaryPaneController(propHandler);
                summary.setMain(mainController);
                ret = summary;
            }

            return ret;
        } catch (IOException ioe) {
            return null;
        }
    }
}
