package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import com.marcsllite.util.handler.StageHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Stop;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.query.EmptyNodeQueryException;

import java.util.Set;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.testfx.api.FxAssert.assertContext;


/**
 * Class to test {@code @FXML} annotated functions
 * or functions that interact with {@code @FXML} nodes
 */
public abstract class GUITest extends FxRobot {
    protected FXMLView view;
    protected App.AppPane root;
    public static final PropHandler propHandler =  new PropHandler() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return ("defaultNum".equals(key))? "-2.0" : "";
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("defaultNum");
        }
    };

    protected void start(Stage stage, FXMLView view) {
        this.view = view;
        root = new App.AppPane(stage, view, propHandler);
        root.getStageHandler().switchScene(view);
        stage.show();
        baseStageAssertions();
    }

    @Stop
    protected void stop() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        root = null;
        view = null;
    }

    protected void baseStageAssertions() {
        StageHandler stageHandler = root.getStageHandler();
        Stage stage = stageHandler.getPrimaryStage();

        assertEquals(view, stageHandler.getCurrentView());
        assertEquals(view.getTitle(), stage.getTitle());
        assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
        assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
        assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
        assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
        assertFalse(stage.isFullScreen());
        assertFalse(stage.isMaximized());
        assertFalse(stage.getIcons().isEmpty());
    }

    /**
     * Helper function to get the initialized controller that is used
     * for the current FXMLView that is used in the GUITest
     * @param clazz the name of the controller class linked to the FXMLView that is displayed
     * @return  The controller class of the FXMLView or null if not found
     */
    protected Object getController(Class<?> clazz){
        return root.getStageHandler()
            .getFactory()
            .getController(clazz.getName());
    }

    /**
     * org.testfx.api.FxAssert#toNode(String) function to lookup the JavaFX node
     * from the gui using the node's id. Original function is private
     * @param nodeId the id of the fxml node
     * @return the first node found with the given id
     * @param <T> javafx.scene.Node object
     * @throws EmptyNodeQueryException if no node is found
     */
    protected <T extends Node> T getNode(String nodeId) throws EmptyNodeQueryException {
        NodeFinder nodeFinder = assertContext().getNodeFinder();

        return nodeFinder.lookup(nodeId).query();
    }
}