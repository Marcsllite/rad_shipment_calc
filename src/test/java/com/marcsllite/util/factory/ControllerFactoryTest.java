package com.marcsllite.util.factory;

import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SummaryPaneController;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class ControllerFactoryTest {
    ControllerFactory factory = new ControllerFactory();

    @Test
    void testGetController_NullName() {
        assertNull(factory.getController(null));
    }

    @Test
    void testGetController_NotInMap() {
        factory.controllerMap.clear();
        assertNull(factory.getController("name"));
    }

    @Test
    void testGetController() {
        String name = "name";
        String value = "value";
        factory.controllerMap.put(name, value);
        assertEquals(value, factory.getController(name));
    }

    @Test
    void testCall_InMap() {
        String name = this.getClass().getName();
        String value = "value";
        
        ControllerFactory factorySpy = spy(factory);
        
        factorySpy.controllerMap.put(name, value);
        assertEquals(value, factorySpy.call(this.getClass()));
        verify(factorySpy).getController(name);
    }

    @Test
    void testCall() {
        factory.controllerMap.clear();
        // Create new Controller
        Class clazz = MainController.class;
        Object ret = factory.call(clazz);
        
        assertTrue(ret instanceof MainController);
        assertEquals(1, factory.controllerMap.size());

        clazz = MenuPaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof MenuPaneController);
        assertEquals(2, factory.controllerMap.size());

        clazz = HomePaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof HomePaneController);
        assertEquals(3, factory.controllerMap.size());

        clazz = ReferencePaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof ReferencePaneController);
        assertEquals(4, factory.controllerMap.size());

        clazz = ModifyController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof ModifyController);
        assertEquals(5, factory.controllerMap.size());

        clazz = ShipmentDetailsController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof ShipmentDetailsController);
        assertEquals(6, factory.controllerMap.size());

        clazz = SummaryPaneController.class;
        ret = factory.call(clazz);

        assertTrue(ret instanceof SummaryPaneController);
        assertEquals(7, factory.controllerMap.size());
    }
}