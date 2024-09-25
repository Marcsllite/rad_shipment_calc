package com.marcsllite.util.handler;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FolderHandlerTest {
    @Mock
    File file;
    FolderHandler folderHandler;
    final static String appFolderName = "UMass Lowell Radiation Safety";
    final static String dataFolderName = "Shipment Calculator";

    @BeforeEach
    public void setUp() throws IOException {
        folderHandler = spy(new FolderHandler(new PropHandlerTestObj()));
    }

    @Test
    void testController_NullPropHandler() {
        FolderHandler handler = null;
        try {
            handler = new FolderHandler(null);
        } catch (IOException e) {
            fail("No error should be thrown");
        }
        assertNotNull(handler.getPropHandler());
    }

    @Test
    void testCreateFolder_NullPath() {
        assertNull(folderHandler.createFolder(null));
    }

    @Test
    @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
    void testCreateFolder_NotCreated() {
        String path = "!@#$%^&*(){}:\"<>?;|~<>?,./";
        File val = null;
        try{
            val = folderHandler.createFolder(path);
        } catch (Exception ignored) { }
        assertNull(val);
    }

    @Test
    void testSetAppFolderPath_Exception() {
        when(folderHandler.createFolder(anyString())).thenReturn(null);

        IOException ex = assertThrows(
            IOException.class, () -> folderHandler.setAppFolderPath()
        );

        assertTrue(ex.getMessage().contains("Failed to set up app folder"));
    }

    @Test
    void testSetDataFolder_Exception() {
        when(folderHandler.createFolder(anyString())).thenReturn(null);

        RuntimeException ex = assertThrows(
            RuntimeException.class, () -> folderHandler.setDataFolder()
        );

        assertTrue(ex.getMessage().contains("Failed to set up data folder"));
    }

    @Test
    void testSetDataFolder() {
        when(folderHandler.getAppFolderPath()).thenReturn("path");

        String path = folderHandler.getAppFolderPath() + File.separator + dataFolderName;
        when(folderHandler.createFolder(anyString())).thenReturn(file);

        try {
            folderHandler.setDataFolder();
            assertEquals(path, folderHandler.getDataFolderPath());
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }
}
