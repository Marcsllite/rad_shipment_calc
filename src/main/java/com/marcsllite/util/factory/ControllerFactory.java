package com.marcsllite.util.factory;

import com.marcsllite.controller.BaseController;
import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SplashScreenController;
import com.marcsllite.controller.SummaryPaneController;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private static final Logger logr = LogManager.getLogger();
    private BaseController.Page page;

    public BaseController.Page getPage() {
        return page;
    }

    public void setPage(BaseController.Page page) {
        this.page = page;
    }

    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = null;

        try {
            if(name.equals(SplashScreenController.class.getName())) {
                ret = new SplashScreenController();
            } else if(name.equals(MainController.class.getName())) {
                ret = MainController.getInstance();
            } else if(name.equals(MenuPaneController.class.getName())) {
                ret = new MenuPaneController();
            } else if(name.equals(HomePaneController.class.getName())) {
                ret = new HomePaneController();
            } else if(name.equals(ReferencePaneController.class.getName())) {
                ret = new ReferencePaneController();
            } else if(name.equals(ModifyController.class.getName())) {
                ret = new ModifyController(getPage());
            } else if(name.equals(ShipmentDetailsController.class.getName())) {
                ret = new ShipmentDetailsController();
            } else if(name.equals(SummaryPaneController.class.getName())) {
                ret = new SummaryPaneController();
            }

            return ret;
        } catch (IOException ioe) {
            logr.catching(ioe);
            return null;
        }
    }
}
