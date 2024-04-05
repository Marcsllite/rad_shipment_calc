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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

class ModifyControllerAddGUITest extends GUITest {
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

    public ModifyControllerAddGUITest() {
        super(FXMLView.MODIFY, BaseController.Page.ADD);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ModifyController) getController();
        stackPaneModify = GUITest.getNode(FXIds.STACKPANE_MODIFY);
        // First Page
        vBoxFirstPage = GUITest.getNode(FXIds.VBOX_FIRST_PAGE);
        txtFieldIsoName = GUITest.getNode(FXIds.TXTFIELD_ISO_NAME);
        txtFieldA0 = GUITest.getNode(FXIds.TXTFIELD_A0);
        comboBoxA0Prefix = GUITest.getNode(FXIds.COMBOBOX_A0_PREFIX);
        choiceBoxA0Name = GUITest.getNode(FXIds.CHOICEBOX_AO_NAME);
        vBoxMoreInfo = GUITest.getNode(FXIds.VBOX_MORE_INFO);
        hBoxAddInfoTop = GUITest.getNode(FXIds.HBOX_ADD_INFO_TOP);
        vBoxShortLong = GUITest.getNode(FXIds.VBOX_SHORT_LONG);
        radioBtnShortLived = GUITest.getNode(FXIds.RADIO_SHORT_LIVED);
        radioBtnLongLived = GUITest.getNode(FXIds.RADIO_LONG_LIVED);
        vBoxLungAbs = GUITest.getNode(FXIds.VBOX_LUNG_ABS);
        radioBtnSlowLungAbs = GUITest.getNode(FXIds.RADIO_SLOW_LUNG);
        radioBtnMediumLungAbs = GUITest.getNode(FXIds.RADIO_MEDIUM_LUNG);
        radioBtnFastLungAbs = GUITest.getNode(FXIds.RADIO_FAST_LUNG);
        txtFirstPageStatus = GUITest.getNode(FXIds.TXT_FIRST_PAGE_STATUS);
        btnNext = GUITest.getNode(FXIds.BTN_NEXT);
        // Second Page
        vBoxSecondPage = GUITest.getNode(FXIds.VBOX_SECOND_PAGE);
        datePicker = GUITest.getNode(FXIds.DATE_PICKER);
        txtFieldMass = GUITest.getNode(FXIds.TXTFIELD_MASS);
        comboBoxMassPrefix = GUITest.getNode(FXIds.COMBOBOX_MASS_PREFIX);
        choiceBoxMassName = GUITest.getNode(FXIds.CHOICEBOX_MASS_NAME);
        choiceBoxNature = GUITest.getNode(FXIds.CHOICEBOX_NATURE);
        choiceBoxState = GUITest.getNode(FXIds.CHOICEBOX_STATE);
        choiceBoxForm = GUITest.getNode(FXIds.CHOICEBOX_FORM);
        chckBoxSameMass = GUITest.getNode(FXIds.CHCKBOX_SAME_MASS);
        chckBoxSameNSF = GUITest.getNode(FXIds.CHCKBOX_SAME_NSF);
        btnBack = GUITest.getNode(FXIds.BTN_BACK);
        btnFinish = GUITest.getNode(FXIds.BTN_FINISH);
        txtSecondPageStatus = GUITest.getNode(FXIds.TXT_SECOND_PAGE_STATUS);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(FXIds.STACKPANE_MODIFY, NodeMatchers.isVisible());
        Assertions.assertEquals(BaseController.Page.ADD, controller.getPage());

        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxA0Prefix.getItems());
        assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxA0Name.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxMassPrefix.getItems());
        assertEquals(Isotope.MassUnit.getFxValues(), choiceBoxMassName.getItems());
        assertEquals(Isotope.Nature.getFxValues(), choiceBoxNature.getItems());
        assertEquals(LimitsModelId.State.getFxValues(), choiceBoxState.getItems());
        assertEquals(LimitsModelId.Form.getFxValues(), choiceBoxForm.getItems());

        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.RadUnit.CURIE.getVal(), choiceBoxA0Name.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.MassUnit.GRAMS.getVal(), choiceBoxMassName.getSelectionModel().getSelectedItem());
    }

    private void goToPage(int pageNum) {
        interact(() ->{
            vBoxFirstPage.setVisible(pageNum == 1);
            vBoxSecondPage.setVisible(pageNum == 2);
        });
    }
    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        FxAssert.verifyThat(stackPaneModify, NodeMatchers.isInvisible());

        interact(() ->controller.show());

        FxAssert.verifyThat(stackPaneModify, NodeMatchers.isVisible());
    }

    @Test
    void testModifyHandler_btnNext() {
        goToPage(1);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText(" "));
        assertEquals(0, controller.getSearchFilteredIsos().size());
        setInitialActivity(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText(null));
        assertEquals(0, controller.getSearchFilteredIsos().size());
        setInitialActivity("1sdf23");
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("A"));
        assertEquals(0, controller.getSearchFilteredIsos().size());
        setInitialActivity("1sdf23");
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("Anny"));
        assertEquals(1, controller.getSearchFilteredIsos().size());
        setInitialActivity("123");
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());
        
        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }

    protected void setInitialActivity(String str) {
        interact(() ->txtFieldA0.setText(str));

        if(str != null) {
            try {
                float initialActivity = Float.parseFloat(str);
                assertEquals(initialActivity, Float.valueOf(txtFieldA0.getText()));
            } catch (Exception e) {
                String replacedStr = str.replaceAll("\\D", "");
                assertEquals(replacedStr, txtFieldA0.getText());
            }
        } else {
            assertNull(txtFieldA0.getText());
        }
    }

    @Test
    void testModifyHandler_chckBoxSameMass() {
        goToPage(2);

        clickOn(chckBoxSameMass);
        assertTrue(chckBoxSameMass.isSelected());
        
        FxAssert.verifyThat(txtFieldMass, NodeMatchers.isDisabled());
        FxAssert.verifyThat(comboBoxMassPrefix, NodeMatchers.isDisabled());
        FxAssert.verifyThat(choiceBoxMassName, NodeMatchers.isDisabled());

        clickOn(chckBoxSameMass);
        assertFalse(chckBoxSameMass.isSelected());

        FxAssert.verifyThat(txtFieldMass, NodeMatchers.isEnabled());
        FxAssert.verifyThat(comboBoxMassPrefix, NodeMatchers.isEnabled());
        FxAssert.verifyThat(choiceBoxMassName, NodeMatchers.isEnabled());
    }

    @Test
    void testModifyHandler_chckBoxSameNSF() {
        goToPage(2);

        clickOn(chckBoxSameNSF);
        assertTrue(chckBoxSameNSF.isSelected());

        FxAssert.verifyThat(choiceBoxNature, NodeMatchers.isDisabled());
        FxAssert.verifyThat(choiceBoxState, NodeMatchers.isDisabled());
        FxAssert.verifyThat(choiceBoxForm, NodeMatchers.isDisabled());

        clickOn(chckBoxSameNSF);
        assertFalse(chckBoxSameNSF.isSelected());

        FxAssert.verifyThat(choiceBoxNature, NodeMatchers.isEnabled());
        FxAssert.verifyThat(choiceBoxState, NodeMatchers.isEnabled());
        FxAssert.verifyThat(choiceBoxForm, NodeMatchers.isEnabled());
    }

    @Test
    void testModifyHandler_btnBack() {
        goToPage(2);

        clickOn(btnBack);

        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isVisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isInvisible());
    }

    @Test
    void testModifyHandler_btnFinish() {
        goToPage(2);

        clickOn(btnFinish);
    }
}
