package com.marcsllite.util.factory;

import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SummaryPaneController;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertTrue;

class ControllerFactoryTest {
    ControllerFactory factory = new ControllerFactory();

    @Test
    void testCall() {
        // Create new Controller
        Class clazz = MainController.class;
        Object ret = factory.call(clazz);
        
        assertTrue(ret instanceof MainController);

        clazz = MenuPaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof MenuPaneController);

        clazz = HomePaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof HomePaneController);

        clazz = ReferencePaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof ReferencePaneController);

        clazz = ModifyController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof ModifyController);

        clazz = ShipmentDetailsController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof ShipmentDetailsController);

        clazz = SummaryPaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof SummaryPaneController);
    }
}