package com.marcsllite.util.factory;

import com.marcsllite.controller.BaseController;
import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SummaryPaneController;
import com.marcsllite.util.handler.PropHandler;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerFactory implements Callback<Class<?>, Object> {
    final PropHandler propHandler;
    final Map<String, Object> controllerMap;

    public ControllerFactory(PropHandler propHandler) {
        this.propHandler = propHandler;
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
        Object ret = null;

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
            name.equals(Objects.requireNonNull(ret).getClass().getName())) {
            controllerMap.put(name, ret);
        }
        return ret;
    }
}
