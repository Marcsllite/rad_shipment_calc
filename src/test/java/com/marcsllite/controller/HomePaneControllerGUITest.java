package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.model.Isotope;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

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
        clickOn(btnAdd);
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
