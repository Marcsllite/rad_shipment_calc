package com.marcsllite.util.handler;

import com.marcsllite.ControllerFactoryTestObj;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.util.FXMLView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxToolkit;

import java.security.InvalidParameterException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StageHandlerTest {
    final static String propMsg = "This is a proper message";
    final static String defaultMessage = StageHandler.DEFAULT_MSG;
    private StageHandler stageHandler;
    static Stage stage;
    
    @BeforeAll
    public void beforeAll() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        stageHandler = spy(new StageHandler(stage, new PropHandlerTestObj(), new ControllerFactoryTestObj()));
    }

    @Test
    void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.switchScene(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

    @Test
    void testShow_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.show(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }
  
    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;
        try {
            stageHandler.show(view);
        } catch(Exception e) {
            fail("An exception should not have been thrown");
        }
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
        stageHandler = new StageHandler(stage);
        PropHandler propHandler = stageHandler.getPropHandler();
        propHandler.setProp(new Properties());
        stageHandler.setPropHandler(propHandler);

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(FXMLView.TEST)
        );
        assertTrue(exception.getMessage().contains("The resource bundle contains no values."));
    }

    @Test
    void testLoadViewNodeHierarchy_EmptyView() {
        FXMLView view = FXMLView.TEST;

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(view)
        );
        String expected = "Unable to load FXML view " + view.getFxmlName();
        String expected2 = "The resource bundle contains no values.";
        assertTrue(exception.getMessage().contains(expected));
        assertFalse(exception.getMessage().contains(expected2));
    }
  
    @ParameterizedTest
    @MethodSource("logAndThrowException_data")
    void testLogAndThrowException(String errorMsg, Exception exception, String expectedMsg) {
        RuntimeException except = assertThrows(
            RuntimeException.class, () -> stageHandler.logAndThrowException(errorMsg, exception)
        );
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
