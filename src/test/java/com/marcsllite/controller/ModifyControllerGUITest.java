package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;

class ModifyControllerGUITest extends GUITest {
    ModifyController controller;
    StackPane stackPaneModify;
    
    // First Page
    VBox vBoxFirstPage;
    TextField txtFieldIsoName;
    TextField txtFieldA0;
    ComboBox<String> comboBoxA0Prefix;
    ChoiceBox<String> choiceBoxA0Name;
    VBox vBoxMoreInfo;
    HBox hBoxAddInfoTop;
    VBox vBoxShortLong;
    RadioButton radioBtnShortLived;
    RadioButton radioBtnLongLived;
    VBox vBoxLungAbs;
    RadioButton radioBtnSlowLungAbs;
    RadioButton radioBtnMediumLungAbs;
    RadioButton radioBtnFastLungAbs;
    Text txtFirstPageStatus;
    Button btnNext;

    // Second Page
    VBox vBoxSecondPage;
    DatePicker datePicker;
    TextField txtFieldMass;
    ComboBox<String> comboBoxMassPrefix;
    ChoiceBox<String> choiceBoxMassName;
    ChoiceBox<String> choiceBoxNature;
    ChoiceBox<String> choiceBoxState;
    ChoiceBox<String> choiceBoxForm;
    CheckBox chckBoxSameMass;
    CheckBox chckBoxSameNSF;
    Button btnBack;
    Button btnFinish;
    Text txtSecondPageStatus;

    public ModifyControllerGUITest() {
        super(FXMLView.MODIFY);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ModifyController) getController();
        stackPaneModify = getNode(FXIds.STACKPANE_MODIFY);
        // First Page
        vBoxFirstPage = getNode(FXIds.VBOX_FIRST_PAGE);
        txtFieldIsoName = getNode(FXIds.TXTFIELD_ISO_NAME);
        txtFieldA0 = getNode(FXIds.TXTFIELD_A0);
        comboBoxA0Prefix = getNode(FXIds.COMBOBOX_A0_PREFIX);
        choiceBoxA0Name = getNode(FXIds.CHOICEBOX_AO_NAME);
        vBoxMoreInfo = getNode(FXIds.VBOX_MORE_INFO);
        hBoxAddInfoTop = getNode(FXIds.HBOX_ADD_INFO_TOP);
        vBoxShortLong = getNode(FXIds.VBOX_SHORT_LONG);
        radioBtnShortLived = getNode(FXIds.RADIO_SHORT_LIVED);
        radioBtnLongLived = getNode(FXIds.RADIO_LONG_LIVED);
        vBoxLungAbs = getNode(FXIds.VBOX_LUNG_ABS);
        radioBtnSlowLungAbs = getNode(FXIds.RADIO_SLOW_LUNG);
        radioBtnMediumLungAbs = getNode(FXIds.RADIO_MEDIUM_LUNG);
        radioBtnFastLungAbs = getNode(FXIds.RADIO_FAST_LUNG);
        txtFirstPageStatus = getNode(FXIds.TXT_FIRST_PAGE_STATUS);
        btnNext = getNode(FXIds.BTN_NEXT);
        // Second Page
        vBoxSecondPage = getNode(FXIds.VBOX_SECOND_PAGE);
        datePicker = getNode(FXIds.DATE_PICKER);
        txtFieldMass = getNode(FXIds.TXTFIELD_MASS);
        comboBoxMassPrefix = getNode(FXIds.COMBOBOX_MASS_PREFIX);
        choiceBoxMassName = getNode(FXIds.CHOICEBOX_MASS_NAME);
        choiceBoxNature = getNode(FXIds.CHOICEBOX_NATURE);
        choiceBoxState = getNode(FXIds.CHOICEBOX_STATE);
        choiceBoxForm = getNode(FXIds.CHOICEBOX_FORM);
        chckBoxSameMass = getNode(FXIds.CHCKBOX_SAME_MASS);
        chckBoxSameNSF = getNode(FXIds.CHCKBOX_SAME_NSF);
        btnBack = getNode(FXIds.BTN_BACK);
        btnFinish = getNode(FXIds.BTN_FINISH);
        txtSecondPageStatus = getNode(FXIds.TXT_SECOND_PAGE_STATUS);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(FXIds.STACKPANE_MODIFY, NodeMatchers.isVisible());

        interact(() -> {
            assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxA0Prefix.getItems());
            assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxA0Name.getItems());
            assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxMassPrefix.getItems());
            assertEquals(Isotope.Mass.getFxValues(), choiceBoxMassName.getItems());
            assertEquals(Isotope.Nature.getFxValues(), choiceBoxNature.getItems());
            assertEquals(LimitsModelId.State.getFxValues(), choiceBoxState.getItems());
            assertEquals(LimitsModelId.Form.getFxValues(), choiceBoxForm.getItems());

            assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
            assertEquals(Isotope.RadUnit.CURIE.getVal(), choiceBoxA0Name.getSelectionModel().getSelectedItem());
            assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
            assertEquals(Isotope.Mass.GRAMS.getVal(), choiceBoxMassName.getSelectionModel().getSelectedItem());
        });
    }

    private void goToPage(int pageNum) {
        vBoxFirstPage.setVisible(pageNum == 1);
        vBoxSecondPage.setVisible(pageNum == 2);
    }
    @Test
    void testHideShow() {
        interact(() -> {
            controller.hide();

            FxAssert.verifyThat(stackPaneModify, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(stackPaneModify, NodeMatchers.isVisible());
        });
    }

    @Test
    void testModifyHandler_btnNext() {
        interact(()-> {
            goToPage(1);

            clickOn(btnNext);
        });
    }

    @Test
    void testModifyHandler_chckBoxSameMass() {
        interact(()-> {
            goToPage(2);

            clickOn(chckBoxSameMass);});
    }

    @Test
    void testModifyHandler_chckBoxSameNSF() {
        interact(()-> {
            goToPage(2);

            clickOn(chckBoxSameNSF);});
    }

    @Test
    void testModifyHandler_btnBack() {
        interact(()-> {
            goToPage(2);

            clickOn(btnBack);});
    }

    @Test
    void testModifyHandler_btnFinish() {
        interact(()-> {
            goToPage(2);

            clickOn(btnFinish);});
    }
}
