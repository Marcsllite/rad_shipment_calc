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

import static org.mockito.Mockito.mock;

public class ControllerFactoryTestObj extends ControllerFactory {
    @Override
    public Object call(Class<?> param) {
        String name = param.getName();
        Object ret = getController(name);

        if(name.equals(MainController.class.getName())) {
            ret = ret == null? mock(MainController.class) : ret;
        } else if(name.equals(MenuPaneController.class.getName())) {
            ret = ret == null? mock(MenuPaneController.class) : ret;
        } else if(name.equals(HomePaneController.class.getName())) {
            ret = ret == null? mock(HomePaneController.class) : ret;
        } else if(name.equals(ReferencePaneController.class.getName())) {
            ret = ret == null? mock(ReferencePaneController.class) : ret;
        } else if(name.equals(ModifyController.class.getName())) {
            ret = ret == null? mock(ModifyController.class) : ret;
        } else if(name.equals(ShipmentDetailsController.class.getName())) {
            ret = ret == null? mock(ShipmentDetailsController.class) : ret;
        } else if(name.equals(SummaryPaneController.class.getName())) {
            ret = ret == null? mock(SummaryPaneController.class) : ret;
        }

        if((ret instanceof BaseController ||
            ret instanceof MainController) &&
            !controllerMap.containsKey(name)) {
            controllerMap.put(name+ "GUITest", ret);
        }
        return ret;
    }
}
