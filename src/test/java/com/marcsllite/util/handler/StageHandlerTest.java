package com.marcsllite.util.handler;

import com.marcsllite.ControllerFactoryTestObj;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.controller.BaseController;
import com.marcsllite.controller.HomePaneController;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.FXMLView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StageHandlerTest {
    final static String propMsg = "This is a proper message";
    final static String defaultMessage = StageHandler.DEFAULT_MSG;
    private StageHandler stageHandler;
    private Stage primaryStage;
    private DBService dbService;

    @BeforeEach
    public void setUp() {
        try {
            primaryStage = mock(Stage.class);
            dbService = mock(DBServiceImpl.class);
            stageHandler = spy(new StageHandler(primaryStage, new PropHandlerTestObj(), new ControllerFactoryTestObj(dbService)));
        } catch (IOException e) {
            fail("Failed to initialize test object");
        }
    }

    @Test
    void testGetController_NullLoader() {
        doReturn(null).when(stageHandler).getLoader();
        assertNull(stageHandler.getController());
    }

    @Test
    void testGetController() {
        String str = "str";
        FXMLLoader loader = mock(FXMLLoader.class);
        doReturn(loader).when(stageHandler).getLoader();
        when(loader.getController()).thenReturn(str);
        assertEquals(str, stageHandler.getController());
    }

    @Test
    void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.switchScene(null, null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

    @Test
    void testSwitchSceneModal_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.switchSceneModal(null, null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

    @Test
    void testSwitchSceneModal_NotModifyCurrentView() {
        FXMLView view = FXMLView.HOME;

        doReturn(view).when(stageHandler).getCurrentView();
        stageHandler.switchSceneModal(view, BaseController.Page.NONE);
        verify(stageHandler, times(0)).getFactory();
        verify(stageHandler, times(0)).loadViewNodeHierarchy(view);
    }

    @Test
    void testSwitchSceneModal_ModifyCurrentView() {
        FXMLView view = FXMLView.MODIFY;
        Stage stage = mock(Stage.class);

        Platform.runLater(
            () -> {
                doReturn(view).when(stageHandler).getCurrentView();
                when(dbService.getAllNuclideModels()).thenReturn(FXCollections.observableArrayList());
                doReturn(null).when(stageHandler).loadViewNodeHierarchy(view);
                doReturn(stage).when(stageHandler).getSecondaryStage();

                stageHandler.switchSceneModal(view, BaseController.Page.NONE);

                verify(stage).initModality(Modality.APPLICATION_MODAL);
                verify(stage).setScene(any());
                verify(stage).setMinWidth(view.getWidth());
                verify(stage).setMinHeight(view.getHeight());
                verify(stage).setMaxWidth(view.getMaxWidth());
                verify(stage).setMaxHeight(view.getMaxHeight());
                verify(stage).setFullScreen(false);
                verify(stage).setMaximized(false);
                verify(stage).setResizable(false);
                verify(stage).setTitle(view.getTitle());
                verify(stage).getIcons();
                verify(stage).centerOnScreen();
                verify(stageHandler).loadViewNodeHierarchy(view);
            }
        );
    }

    @Test
    void testShow_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.show(null, null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

    @Test
    void testShowModal_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.showModal(null, null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }
  
    @Test
    @ClearSystemProperty(key = "keepPlatformOpen")
    void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;

        doNothing().when(primaryStage).close();

        stageHandler.show(view, null);
        verify(stageHandler, times(2)).closePrimary();
    }

    @Test
    @ClearSystemProperty(key = "keepPlatformOpen")
    void testShowModal_EmptyView() {
        FXMLView view = FXMLView.TEST;

        doReturn(primaryStage).when(stageHandler).getSecondaryStage();
        doNothing().when(primaryStage).close();

        try {
            stageHandler.showModal(view, null);
            verify(stageHandler, times(2)).closeSecondary();
        } catch(Exception e) {
            fail("An exception should not have been thrown");
        }
    }

    @Test
    @ClearSystemProperty(key = "keepPlatformOpen")
    void testShowModal_NotInit() {
        FXMLView view = FXMLView.HOME;
        BaseController.Page page = BaseController.Page.NONE;
        HomePaneController controller = mock(HomePaneController.class);

        doNothing().when(stageHandler).switchSceneModal(view, page);
        doReturn(controller).when(stageHandler).getController();
        doReturn(primaryStage).when(stageHandler).getSecondaryStage();
        doNothing().when(primaryStage).close();

        stageHandler.showModal(view, page);

        verify(stageHandler).closeSecondary();
    }

    @Test
    void testLoadViewNodeHierarchy_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.loadViewNodeHierarchy(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

    @Test
    void testLoadViewNodeHierarchy_EmptyProperties() {
        try {
            stageHandler = new StageHandler(primaryStage);
        } catch (IOException e) {
            fail("Failed to initialize test object");
        }
        PropHandler propHandler = stageHandler.getPropHandler();
        propHandler.setProp(new Properties());
        stageHandler.setPropHandler(propHandler);

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(FXMLView.TEST)
        );
        assertTrue(exception.getMessage().contains("The resource bundle contains no values."));
    }

    @Test
    @ClearSystemProperty(key = "keepPlatformOpen")
    void testLoadViewNodeHierarchy_EmptyView() {
        FXMLView view = FXMLView.TEST;

        doReturn(null).when(stageHandler).getSecondaryStage();
        doNothing().when(primaryStage).close();

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(view)
        );
        String expected = "Unable to load FXML view " + view.getFxmlName();
        String expected2 = "The resource bundle contains no values.";
        assertTrue(exception.getMessage().contains(expected));
        assertFalse(exception.getMessage().contains(expected2));
        verify(stageHandler).closePrimary();
    }

    @Test
    @ClearSystemProperty(key = "keepPlatformOpen")
    void testLoadViewNodeHierarchy_LoadExceptionPrimary() throws IOException {
        FXMLView view = FXMLView.TEST;
        FXMLLoader loader = mock(FXMLLoader.class);

        doReturn(loader).when(stageHandler).getLoader();
        when(loader.load()).thenReturn(null);
        doReturn(null).when(stageHandler).getSecondaryStage();
        doNothing().when(primaryStage).close();

        assertThrows(RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(view));

         verify(stageHandler).closePrimary();
    }

    @Test
    @ClearSystemProperty(key = "keepPlatformOpen")
    void testLoadViewNodeHierarchy_LoadExceptionSecondary() throws IOException {
        FXMLView view = FXMLView.TEST;
        FXMLLoader loader = mock(FXMLLoader.class);

        doReturn(loader).when(stageHandler).getLoader();
        when(loader.load()).thenReturn(null);
        doReturn(primaryStage).when(stageHandler).getSecondaryStage();
        doNothing().when(primaryStage).close();

        assertThrows(RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(view));

        verify(stageHandler).closeSecondary();
    }

    @ParameterizedTest
    @MethodSource("logAndThrowException_data")
    void testLogAndThrowException(String errorMsg, Exception exception, String expectedMsg) {
        RuntimeException except = stageHandler.logAndThrowException(errorMsg, exception);
        assertEquals(expectedMsg, except.getMessage());
    }
  
    private static Object[] logAndThrowException_data() {
        Exception properException = new Exception(propMsg);
        return new Object[] {
            new Object[] { null, null, defaultMessage },
            new Object[] { "", null, defaultMessage },
            new Object[] { propMsg, null, propMsg },
            new Object[] { null, properException, defaultMessage },
            new Object[] { "", properException, defaultMessage },
            new Object[] { propMsg, properException, propMsg }
        };
    }
}
