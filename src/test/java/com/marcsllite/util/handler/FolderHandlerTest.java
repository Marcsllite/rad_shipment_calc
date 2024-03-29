package com.marcsllite.util.handler;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.filechooser.FileSystemView;
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
import static org.mockito.Mockito.verify;
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
    @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
    void testSetAppFolderPath_InvalidName() {
        String name ="?.\"*.*.?";

        IOException exception = assertThrows(
            IOException.class, () -> folderHandler.setAppFolderPath(name)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set up app folder"));
    }

    @Test
    void testSetAppFolderPath_MakeNewFolder() {
        String name ="Default Dir";
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;

        when(folderHandler.createFolder(anyString())).thenReturn(file);

        try {
            folderHandler.setAppFolderPath(name);
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
        assertEquals(path, folderHandler.getAppFolderPath());
        verify(folderHandler).setDataFolder();
    }

    @ParameterizedTest
    @MethodSource("setAppFolderPathException_data")
    void testSetAppFolderPath_NullEmptyName(String dirName, String expected) {
        when(folderHandler.createFolder(anyString())).thenReturn(file);

        try {
            folderHandler.setAppFolderPath(dirName);
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
        assertEquals(expected, folderHandler.getAppFolderPath());
        verify(folderHandler).setDataFolder();
    }
  
    private static Object[] setAppFolderPathException_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + appFolderName;
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
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
