package com.marcsllite.util.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FolderHandlerTest {
    FolderHandler folderHandler;
    @Mock
    File file;
    PropHandler propHandler =  new PropHandler() {
        @Override
        protected Object handleGetObject(String key) {
            if("appFolderName".equals(key)) {
                return appFolderName;
            }
            return "";
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("appFolderName");
        }
    };
    final static String appFolderName = "UMass Lowell Radiation Safety";
    final static String dataFolderName = "Shipment Calculator";

    @BeforeEach
    public void setup() {
        folderHandler = Mockito.spy(new FolderHandler(propHandler));
    }

    @AfterEach
    public void afterEach() {
        folderHandler = null;
    }

    @Test
    public void testCreateFolder_NullPath() {
        assertNull(folderHandler.createFolder(null));
    }

    @Test
    @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
    public void testCreateFolder_NotCreated() {
        String path = "!@#$%^&*(){}:\"<>?;|~<>?,./";
        File val = null;
        try{
            val = folderHandler.createFolder(path);
        } catch (Exception ignored) { }
        assertNull(val);
    }

    @Test
    @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
    public void testSetAppFolderPath_InvalidName() {
        String name ="?.\"*.*.?";

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> folderHandler.setAppFolderPath(name)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set up app folder"));
    }

    @Test
    public void testSetAppFolderPath_MakeNewFolder() {
        String name ="Default Dir";
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;

        when(folderHandler.createFolder(anyString())).thenReturn(file);

        folderHandler.setAppFolderPath(name);
        assertEquals(path, folderHandler.getAppFolderPath());
        verify(folderHandler).setDataFolder();
    }

    @ParameterizedTest
    @MethodSource("setAppFolderPathException_data")
    public void testSetAppFolderPath_NullEmptyName(String dirName, String expected) {
        when(folderHandler.createFolder(anyString())).thenReturn(file);

        folderHandler.setAppFolderPath(dirName);
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
    public void testSetDataFolder_Exception() {
        when(folderHandler.createFolder(anyString())).thenReturn(null);

        RuntimeException ex = assertThrows(
            RuntimeException.class, () -> folderHandler.setDataFolder()
        );

        assertTrue(ex.getMessage().contains("Failed to set up data folder"));
    }

    @Test
    public void testSetDataFolder() {
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
