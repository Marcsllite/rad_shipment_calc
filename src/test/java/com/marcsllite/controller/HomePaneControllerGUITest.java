package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.model.Isotope;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class HomePaneControllerGUITest extends GUITest {
    @Spy
    HomePaneController controller;
    GridPane gridPaneHome;
    Button btnAdd;
    Button btnEdit;
    Button btnRemove;
    TableView<Isotope> tableViewHome;
    Button btnCalculate;
    StackPane stackPaneModify;

    public HomePaneControllerGUITest() {
        super(FXMLView.HOME);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (HomePaneController) getController();
        gridPaneHome = getNode(FXIds.GRIDPANE_HOME);
        btnAdd = getNode(FXIds.BTN_ADD);
        btnEdit = getNode(FXIds.BTN_EDIT);
        btnRemove = getNode(FXIds.BTN_REMOVE);
        tableViewHome = getNode(FXIds.TABLEVIEW_HOME);
        btnCalculate = getNode(FXIds.BTN_CALCULATE);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isVisible());
        FxAssert.verifyThat(btnAdd, NodeMatchers.isEnabled());
        FxAssert.verifyThat(btnEdit, NodeMatchers.isDisabled());
        FxAssert.verifyThat(btnRemove, NodeMatchers.isDisabled());
        FxAssert.verifyThat(btnCalculate, NodeMatchers.isDisabled());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            controller.hide();

            FxAssert.verifyThat(gridPaneHome, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(gridPaneHome, NodeMatchers.isVisible());
        });
    }

    @Test
    void testHomePaneHandler_btnAdd() {
        interact(() -> clickOn(btnAdd));
        stackPaneModify = getNode(FXIds.STACKPANE_MODIFY);
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isShowing());
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isFocused());

        interact(() -> window(stackPaneModify).hide());
        FxAssert.verifyThat(window(gridPaneHome), WindowMatchers.isShowing());
        FxAssert.verifyThat(window(gridPaneHome), WindowMatchers.isFocused());
    }

    @Test
    void testHomePaneHandler_btnEdit() {
        clickOn(btnEdit);
    }

    @Test
    void testHomePaneHandler_btnRemove() {
        clickOn(btnRemove);
    }

    @Test
    void testHomePaneHandler_btnCalculate() {
        clickOn(btnCalculate);
    }
}
