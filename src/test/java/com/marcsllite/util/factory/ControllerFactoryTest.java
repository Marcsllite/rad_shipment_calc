package com.marcsllite.util.factory;

import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ModifyController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.controller.ShipmentDetailsController;
import com.marcsllite.controller.SplashScreenController;
import com.marcsllite.controller.SummaryPaneController;
import com.marcsllite.service.DBService;
import com.marcsllite.util.Conversions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@SuppressWarnings("rawtypes")
@ExtendWith(MockitoExtension.class)
class ControllerFactoryTest {
    ControllerFactory factory;
    DBService dbService;

    @BeforeEach
    public void setUp() {
        dbService = mock(DBService.class);
        factory = new ControllerFactory(dbService);
    }

    @Test
    void testCall() {
        // Create new Controller
        Class clazz = SplashScreenController.class;
        Object ret = factory.call(clazz);

        assertInstanceOf(SplashScreenController.class, ret);

        clazz = MainController.class;
        ret = factory.call(clazz);

        assertInstanceOf(MainController.class, ret);

        clazz = MenuPaneController.class;
        ret = factory.call(clazz);

        assertInstanceOf(MenuPaneController.class, ret);

        clazz = HomePaneController.class;
        ret = factory.call(clazz);

        assertInstanceOf(HomePaneController.class, ret);

        clazz = ReferencePaneController.class;
        ret = factory.call(clazz);

        assertInstanceOf(ReferencePaneController.class, ret);

        clazz = ModifyController.class;
        ret = factory.call(clazz);

        assertInstanceOf(ModifyController.class, ret);
        assertEquals(dbService, ((ModifyController) ret).getDbService());

        clazz = ShipmentDetailsController.class;
        ret = factory.call(clazz);

        assertInstanceOf(ShipmentDetailsController.class, ret);

        clazz = SummaryPaneController.class;
        ret = factory.call(clazz);

        assertInstanceOf(SummaryPaneController.class, ret);

        clazz = Conversions.class;
        assertNull(factory.call(clazz));
    }
}