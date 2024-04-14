package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.service.DBService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModifyControllerTest {
    ModifyController controller;

    @BeforeEach
    public void setUp() throws IOException {
        DBService dbService = mock(DBService.class);
        when(dbService.getAllNuclideModels()).thenReturn(FXCollections.observableArrayList());
        controller = new ModifyController(BaseController.Page.NONE, new PropHandlerTestObj()) {
            @Override
            public DBService getDbService() {
                return dbService;
            }
        };
        assertEquals(BaseController.Page.NONE, controller.getPage());
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.modifyHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }
}