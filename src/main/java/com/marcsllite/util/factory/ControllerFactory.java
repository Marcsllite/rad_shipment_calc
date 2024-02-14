package com.marcsllite.util.factory;

import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.PrimaryController;
import com.marcsllite.controller.SecondaryController;
import com.marcsllite.util.handler.PropHandler;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    final PropHandler propHandler;

    public ControllerFactory(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = null;

        if(name.equals(MainController.class.getName())) {
            ret = MainController.getInstance();
        } else if(name.equals(MenuPaneController.class.getName())) {
            ret = new MenuPaneController(propHandler);
        } else if(name.equals(PrimaryController.class.getName())) {
            ret = new PrimaryController();
        } else if(name.equals(SecondaryController.class.getName())) {
            ret = new SecondaryController();
        }

        return ret;
    }
}
