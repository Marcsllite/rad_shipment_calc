package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import static junit.framework.Assert.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class ModifyControllerGUITest extends GUITest {
    ModifyController controller;
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.MODIFY);
        controller = (ModifyController) getController(ModifyController.class);
    }

    @Test
    public void testInit() {
        FxAssert.verifyThat(FXIds.STACKPANE_MODIFY, NodeMatchers.isVisible());

        interact(() -> {
            ChoiceBox<String> choiceBoxA0Prefix = getNode(FXIds.CHOICEBOX_A0_PREFIX);
            ChoiceBox<String> choiceBoxA0Name = getNode(FXIds.CHOICEBOX_AO_NAME);
            ChoiceBox<String> choiceBoxMassPrefix = getNode(FXIds.CHOICE_MASS_PREFIX);
            ChoiceBox<String> choiceBoxMassName = getNode(FXIds.CHOICE_MASS_NAME);
            ChoiceBox<String> choiceBoxNature = getNode(FXIds.CHOICE_NATURE);
            ChoiceBox<String> choiceBoxState = getNode(FXIds.CHOICE_STATE);
            ChoiceBox<String> choiceBoxForm = getNode(FXIds.CHOICE_FORM);

            assertEquals(Conversions.SIPrefix.getFxValues(), choiceBoxA0Prefix.getItems());
            assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxA0Name.getItems());
            assertEquals(Conversions.SIPrefix.getFxValues(), choiceBoxMassPrefix.getItems());
            assertEquals(Isotope.Mass.getFxValues(), choiceBoxMassName.getItems());
            assertEquals(Isotope.Nature.getFxValues(), choiceBoxNature.getItems());
            assertEquals(LimitsModelId.State.getFxValues(), choiceBoxState.getItems());
            assertEquals(LimitsModelId.Form.getFxValues(), choiceBoxForm.getItems());

            assertEquals(Conversions.SIPrefix.BASE.getVal(), choiceBoxA0Prefix.getSelectionModel().getSelectedItem());
            assertEquals(Isotope.RadUnit.CURIE.getVal(), choiceBoxA0Name.getSelectionModel().getSelectedItem());
            assertEquals(Conversions.SIPrefix.BASE.getVal(), choiceBoxMassPrefix.getSelectionModel().getSelectedItem());
            assertEquals(Isotope.Mass.GRAMS.getVal(), choiceBoxMassName.getSelectionModel().getSelectedItem());
        });
    }

    private void goToPage(int pageNum) {
        VBox vBoxFirstPage = getNode(FXIds.VBOX_FIRST_PAGE);
        VBox vBoxSecondPage = getNode(FXIds.VBOX_SECOND_PAGE);

        vBoxFirstPage.setVisible(pageNum == 1);
        vBoxSecondPage.setVisible(pageNum == 2);
    }
    @Test
    public void testHideShow() {
        interact(() -> {
            StackPane modifyPane = getNode(FXIds.STACKPANE_MODIFY);

            controller.hide();

            FxAssert.verifyThat(modifyPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(modifyPane, NodeMatchers.isVisible());
        });
    }

    @Test
    public void testModifyHandler_btnNext() {
        interact(()-> {
            goToPage(1);

            clickOn(FXIds.BTN_NEXT);
        });
    }

    @Test
    public void testModifyHandler_chckBoxSameMass() {
        interact(()-> {
            goToPage(2);

            clickOn(FXIds.CHCKBOX_SAME_MASS);});
    }

    @Test
    public void testModifyHandler_chckBoxSameNSF() {
        interact(()-> {
            goToPage(2);

            clickOn(FXIds.CHCBOX_SAME_NSF);});
    }

    @Test
    public void testModifyHandler_btnBack() {
        interact(()-> {
            goToPage(2);

            clickOn(FXIds.BTN_BACK);});
    }

    @Test
    public void testModifyHandler_btnFinish() {
        interact(()-> {
            goToPage(2);

            clickOn(FXIds.BTN_FINISH);});
    }
}
