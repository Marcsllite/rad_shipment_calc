package com.marcsllite.util.factory;

import com.marcsllite.controller.BaseController;
import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SummaryPaneController;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory implements Callback<Class<?>, Object> {
    protected final Map<String, Object> controllerMap;

    public ControllerFactory() {
        controllerMap = new HashMap<>();
    }

    public Object getController(String name) {
        if(name == null) {
            return null;
        }

        return controllerMap.get(name);
    }

    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = getController(name);

        if(ret == null) {
            if(name.equals(MainController.class.getName())) {
                ret = MainController.getInstance();
            } else if(name.equals(MenuPaneController.class.getName())) {
                ret = new MenuPaneController();
            } else if(name.equals(HomePaneController.class.getName())) {
                ret = new HomePaneController();
            } else if(name.equals(ReferencePaneController.class.getName())) {
                ret = new ReferencePaneController();
            } else if(name.equals(ModifyController.class.getName())) {
                ret = new ModifyController();
            } else if(name.equals(ShipmentDetailsController.class.getName())) {
                ret = new ShipmentDetailsController();
            } else if(name.equals(SummaryPaneController.class.getName())) {
                ret = new SummaryPaneController();
            }

            if(ret instanceof BaseController ||
                ret instanceof MainController) {
                controllerMap.put(name, ret);
            }
        }

        return ret;
    }
}
