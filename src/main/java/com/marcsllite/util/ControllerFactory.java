package com.marcsllite.util;

import com.marcsllite.MainController;
import com.marcsllite.MenuPaneController;
import com.marcsllite.PrimaryController;
import com.marcsllite.SecondaryController;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = null;

        if(name.equals(MainController.class.getName())) {
            ret = MainController.getInstance();
        } else if(name.equals(MenuPaneController.class.getName())) {
            ret = new MenuPaneController();
        } else if(name.equals(PrimaryController.class.getName())) {
            ret = new PrimaryController();
        } else if(name.equals(SecondaryController.class.getName())) {
            ret = new SecondaryController();
        }

        return ret;
    }
}
