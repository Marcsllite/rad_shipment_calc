package com.marcsllite;

import com.marcsllite.controller.BaseController;
import com.marcsllite.controller.HomePaneController;
import com.marcsllite.controller.MainController;
import com.marcsllite.controller.MenuPaneController;
import com.marcsllite.controller.ReferencePaneController;
import com.marcsllite.model.Nuclide;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.OSUtil;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.handler.FolderHandler;
import com.marcsllite.util.handler.StageHandler;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.codehaus.plexus.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.query.EmptyNodeQueryException;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.assertContext;


/**
 * Class to test {@code @FXML} annotated functions
 * or functions that interact with {@code @FXML} nodes
 */
@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GUITest extends FxRobot {
    protected FXMLView view;
    private StageHandler stageHandler;
    private FolderHandler folderHandler;
    private DBService dbService;
    private App app;
    private BaseController.Page page;
    private MainController mainController;
    private Nuclide editingNuclide;

    public GUITest(FXMLView view, BaseController.Page page) {
        this.view = view;
        this.page = page == null? BaseController.Page.NONE : page;
    }

    @BeforeAll
    public void beforeAll() {
        app = new App(false, false);
        setupMainController();

        PropHandlerTestObj testPropHandler = new PropHandlerTestObj();
        folderHandler = mock(FolderHandler.class);
        folderHandler.setPropHandler(testPropHandler);
        dbService = new DBServiceImpl(testPropHandler);
        assertEquals(1, getDbService().validateDb());
    }

    @Start
    protected void start(Stage stage) throws IOException, TimeoutException {
        Platform.setImplicitExit(false);
        when(folderHandler.getDataFolderPath()).thenReturn(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());

        app.init(view, new PropHandlerTestObj(), folderHandler, getDbService(), getControllerFactory());
        App.setPage(getPage());

        app.start(stage);
        setStageHandler(App.getStageHandler());
    }

    @Stop
    public void stop() {
        app.stop();
    }

    @BeforeEach
    public void beforeEach() {
        Assumptions.assumeTrue(FxToolkit.isFXApplicationThreadRunning());
        baseStageAssertions(getStageHandler().getPrimaryStage());
    }

    private ControllerFactoryTestObj getControllerFactory() {
        switch (getPage()) {
            case ADD:
            case EDIT:
                return new ControllerFactoryTestObj(getDbService(), mainController);
            default:
                return new ControllerFactoryTestObj(getDbService());
        }
    }

    protected void baseStageAssertions(Stage stage) {
        assertEquals(view, getStageHandler().getCurrentView());
        assertEquals(view.getTitle(), stage.getTitle());
        assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
        assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
        assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
        assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
        assertFalse(stage.isFullScreen());
        if(!OSUtil.isMac()) {
            assertFalse(stage.isMaximized());
        }
        assertFalse(stage.getIcons().isEmpty());
    }

    public BaseController.Page getPage() {
        return page;
    }

    public Nuclide getEditingNuclide() { return editingNuclide; }

    public void setPage(BaseController.Page page) {
        this.page = page == null? BaseController.Page.NONE : page;
    }

    public DBService getDbService() {
        return dbService;
    }

    protected StageHandler getStageHandler() {
        return stageHandler;
    }

    public void setStageHandler(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
    }

    private void setupMainController() {
        mainController = MainController.getInstance();
        MenuPaneController menuPaneController = mock(MenuPaneController.class);
        HomePaneController homePaneController = mock(HomePaneController.class);
        ReferencePaneController referencePaneController = mock(ReferencePaneController.class);
        mainController.registerController(menuPaneController);
        mainController.registerController(homePaneController);
        mainController.registerController(referencePaneController);

        switch (getPage()) {
            case ADD:
                when(homePaneController.isNuclideInTable(any())).thenReturn(false);
                break;
            case EDIT:
                editingNuclide = TestUtils.createNuclide();
                when(homePaneController.getSelectedNuclides()).thenReturn(List.of(editingNuclide));
                break;
            default:
        }
    }

    /**
     * Helper function to get the initialized controller that is used
     * for the current FXMLView that is used in the GUITest
     * @return  The controller class of the FXMLView or null if not found
     */
    protected Object getController(){
        return getStageHandler().getController();
    }

    protected void selectRow(TableView<Nuclide> table, int index) {
        selectRows(table, index, index+1);
    }

    protected void selectRows(TableView<Nuclide> table, int start, int end) {
        if(table.getItems().isEmpty()) {
            fail("Table has no items to select from");
        }

        interact(() -> {
            for (int i = start; i < end; i++) {
                table.getSelectionModel().select(i);
            }
        });
        assertEquals("Unable to select rows: {"+start+", "+(end-1)+"} from the table",end-start, table.getSelectionModel().getSelectedItems().size());
    }

    protected void clearSelection(TableView<Nuclide> table) {
        if(!table.getSelectionModel().isEmpty()) {
            interact(() -> table.getSelectionModel().clearSelection());
        }
        assertNull(table.getSelectionModel().getSelectedItem());
    }

    protected void setDate(LocalDate localDate, DatePicker datePicker) {
        interact(() -> datePicker.setValue(localDate));
        assertEquals(localDate, datePicker.getValue());
    }

    protected void setString(String str, TextField field) {
        interact(() -> field.setText(str));
        assertEquals(str, field.textProperty().get());
    }

    protected void setNumber(String str, TextField field) {
        interact(() -> field.setText(str));
        verifyNumericalText(field, str);
    }

    private void verifyNumericalText(TextField field, String str) {
        String replacedStr = StringUtils.isBlank(str)? null : str.replaceAll("[^\\d.]", "");
        if (StringUtils.isBlank(replacedStr)) {
            Assertions.assertNull(field.textProperty().get());
        } else {
            RadBigDecimal initialActivity = new RadBigDecimal(replacedStr);
            assertEquals(replacedStr, field.textProperty().get());
            assertEquals(initialActivity, new RadBigDecimal(field.textProperty().get()));
        }
    }

    /**
     * org.testfx.api.FxAssert#toNode(String) function to look up the JavaFX node
     * from the gui using the node's id. Original function is private
     * @param nodeId the id of the fxml node
     * @return the first node found with the given id
     * @param <T> javafx.scene.Node object
     * @throws EmptyNodeQueryException if no node is found
     */
    public static <T extends Node> T getNode(String nodeId) throws EmptyNodeQueryException {
        NodeFinder nodeFinder = assertContext().getNodeFinder();

        return nodeFinder.lookup(nodeId).query();
    }
}