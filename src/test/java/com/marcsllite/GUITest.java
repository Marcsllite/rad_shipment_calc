package com.marcsllite;

import com.marcsllite.service.DBService;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.FolderHandler;
import com.marcsllite.util.handler.StageHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.query.EmptyNodeQueryException;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.assertContext;


/**
 * Class to test {@code @FXML} annotated functions
 * or functions that interact with {@code @FXML} nodes
 */
@ExtendWith(ApplicationExtension.class)
public abstract class GUITest extends FxRobot {
    protected FXMLView view;
    private StageHandler stageHandler;
    private final FolderHandler folderHandler;
    private final DBService dbService;
    private final EntityManagerFactory emFactory;
    private final EntityManager entityManager;
    private final App app;

    public GUITest(FXMLView view) {
        this.view = view;
        folderHandler = mock(FolderHandler.class);
        folderHandler.setPropHandler(new PropHandlerTestObj());
        emFactory = mock(EntityManagerFactory.class);
        entityManager = mock(EntityManager.class);
        dbService = mock(DBService.class);
        app = new App(false);
    }



    @Start
    protected void start(Stage stage) throws IOException, TimeoutException {
        Platform.setImplicitExit(false);
        when(folderHandler.getDataFolderPath()).thenReturn(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
        when(emFactory.createEntityManager()).thenReturn(entityManager);

        app.init(view, new PropHandlerTestObj(), folderHandler, emFactory, dbService);

        app.start(stage);
        stageHandler = app.getStageHandler();
    }

    @BeforeEach
    public void beforeEach() {
        Assumptions.assumeTrue(FxToolkit.isFXApplicationThreadRunning());
        baseStageAssertions();
    }

    protected void baseStageAssertions() {
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
     * @return  The controller class of the FXMLView or null if not found
     */
    protected Object getController(){
        return stageHandler
            .getFactory()
            .getController(this.getClass().getName().replace("GUITest", ""));
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