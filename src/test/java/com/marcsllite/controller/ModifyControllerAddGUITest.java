package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.FXMLView;
import javafx.scene.Node;
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
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    VBox vBoxLifeSpan;
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


        stackPaneModify = GUITest.getNode(FXIds.STACK_PANE_MODIFY);
        // First Page
        vBoxFirstPage = GUITest.getNode(FXIds.VBOX_FIRST_PAGE);
        txtFieldIsoName = GUITest.getNode(FXIds.TXT_FIELD_ISO_NAME);
        txtFieldA0 = GUITest.getNode(FXIds.TXT_FIELD_A0);
        comboBoxA0Prefix = GUITest.getNode(FXIds.COMBO_BOX_A0_PREFIX);
        choiceBoxA0Name = GUITest.getNode(FXIds.CHOICE_BOX_AO_RAD_UNIT);
        vBoxMoreInfo = GUITest.getNode(FXIds.VBOX_MORE_INFO);
        hBoxAddInfoTop = GUITest.getNode(FXIds.HBOX_ADD_INFO_TOP);
        vBoxLifeSpan = GUITest.getNode(FXIds.VBOX_LIFE_SPAN);
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
        txtFieldMass = GUITest.getNode(FXIds.TXT_FIELD_MASS);
        comboBoxMassPrefix = GUITest.getNode(FXIds.COMBO_BOX_MASS_PREFIX);
        choiceBoxMassName = GUITest.getNode(FXIds.CHOICE_BOX_MASS_UNIT);
        choiceBoxNature = GUITest.getNode(FXIds.CHOICE_BOX_NATURE);
        choiceBoxState = GUITest.getNode(FXIds.CHOICE_BOX_STATE);
        choiceBoxForm = GUITest.getNode(FXIds.CHOICE_BOX_FORM);
        chckBoxSameMass = GUITest.getNode(FXIds.CHCK_BOX_SAME_MASS);
        chckBoxSameNSF = GUITest.getNode(FXIds.CHCK_BOX_SAME_NSF);
        btnBack = GUITest.getNode(FXIds.BTN_BACK);
        btnFinish = GUITest.getNode(FXIds.BTN_FINISH);
        txtSecondPageStatus = GUITest.getNode(FXIds.TXT_SECOND_PAGE_STATUS);
    }

    @BeforeEach
    public void setUp() {
        clearFirstPageForm();
        goToPage(1);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(FXIds.STACK_PANE_MODIFY, NodeMatchers.isVisible());
        Assertions.assertEquals(BaseController.Page.ADD, controller.getPage());

        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxA0Prefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxA0Name.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxMassPrefix.getItems());
        assertEquals(Conversions.MassUnit.getFxValues(), choiceBoxMassName.getItems());
        assertEquals(Isotope.Nature.getFxValues(), choiceBoxNature.getItems());
        assertEquals(LimitsModelId.State.getFxValues(), choiceBoxState.getItems());
        assertEquals(LimitsModelId.Form.getFxValues(), choiceBoxForm.getItems());

        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.CURIE.getVal(), choiceBoxA0Name.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.MassUnit.GRAMS.getVal(), choiceBoxMassName.getSelectionModel().getSelectedItem());
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

    protected void setupEnabledDisabled() {
        MainController mainController = mock(MainController.class);
        HomePaneController homePaneController = mock(HomePaneController.class);
        controller.setMain(mainController);
        when(mainController.getHomePaneController()).thenReturn(homePaneController);
        when(homePaneController.isIsoInTable(any())).thenReturn(false);

        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());
    }

    @ParameterizedTest
    @MethodSource("noRadio_data")
    void testBtnNext_EnabledDisabled_NoRadio_Checker(String name, String a0, Matcher<Node> matcher, int filteredIsoSize) {
        setupEnabledDisabled();

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, matcher);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertEquals(0, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, matcher);
    }

    private static Object[] noRadio_data() {
        return new Object[] {
            new Object[] { " ", null, NodeMatchers.isDisabled(), 0 },
            new Object[] { null, "1sdf23", NodeMatchers.isDisabled(), 0 },
            new Object[] { "A", "1sdf23", NodeMatchers.isDisabled(), 0 },
            new Object[] { "Anny", "123", NodeMatchers.isEnabled(), 1 }
        };
    }
    

    @Test
    void testBtnNextHandler_NoRadio() {
        setupEnabledDisabled();
        
        setInitialActivity("123");
        assertEquals(0, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("Anny"));
        assertEquals(1, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }

    protected void clearFirstPageForm() {
        interact(() -> txtFieldIsoName.setText(null));
        setInitialActivity(null);
        setLifeSpan(null);
        setLungAbsorption(null);
    }

    protected void assertAdditionalInfoShowing(VBox vbox) {
        if(vbox == null) {
            FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isInvisible());
            FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isInvisible());
        } else if(vbox == vBoxLifeSpan){
            FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isVisible());
            FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isInvisible());
        } else if(vbox == vBoxLungAbs) {
            FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isInvisible());
            FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
        }
    }

    @ParameterizedTest
    @MethodSource("radioLifeSpan_data")
    void testBtnNext_EnabledDisabled_RadioLifeSpan_Checker(String name, String a0, Isotope.LifeSpan lifeSpan, Matcher<Node> matcher, int filteredIsoSize, VBox visibleRadio) {
        setupEnabledDisabled();

        visibleRadio = visibleRadio == null? null : vBoxLifeSpan;

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setLifeSpan(lifeSpan);
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, matcher);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setLifeSpan(lifeSpan);
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, matcher);
    }

    private static Object[] radioLifeSpan_data() {
        return new Object[] {
            new Object[] { " ", null, Isotope.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bfi(short)", "123", Isotope.LifeSpan.SHORT, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Isotope.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bfi(short)", "123", Isotope.LifeSpan.LONG, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Isotope.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bfi(short)", "123", Isotope.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 1, new VBox() }
        };
    }

    @Test
    void testBtnNextHandler_RadioLifeSpan() {
        setupEnabledDisabled();

        setInitialActivity("123");
        assertEquals(4, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("Bfi(short)"));
        assertEquals(1, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(vBoxLifeSpan);

        setLifeSpan(Isotope.LifeSpan.SHORT);
        assertAdditionalInfoShowing(vBoxLifeSpan);
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }
    
    @ParameterizedTest
    @MethodSource("radioLungAbsorption_data")
    void testBtnNext_EnabledDisabled_RadioLungAbsorption_Checker(String name, String a0, Isotope.LungAbsorption lungAbs, Matcher<Node> matcher, int filteredIsoSize, VBox visibleRadio) {
        setupEnabledDisabled();

        if(visibleRadio != null && filteredIsoSize == 0) {
            visibleRadio = vBoxLifeSpan;
        } else if(filteredIsoSize == 1) {
            visibleRadio = vBoxLungAbs;
        } else {
            visibleRadio = null;
        }

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        if(filteredIsoSize == 1) {
            setLungAbsorption(lungAbs);
        }
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, matcher);
        
        clearFirstPageForm();
        setInitialActivity(a0);
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        if(filteredIsoSize == 1) {
            setLungAbsorption(lungAbs);
        }
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, matcher);
    }

    private static Object[] radioLungAbsorption_data() {
        return new Object[] {
            new Object[] { " ", null, Isotope.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bstf", "123", Isotope.LungAbsorption.SLOW, NodeMatchers.isEnabled(), 1, new VBox() },
            
            new Object[] { " ", null, Isotope.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bstf", "123", Isotope.LungAbsorption.MEDIUM, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Isotope.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bstf", "123", Isotope.LungAbsorption.FAST, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Isotope.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Isotope.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Isotope.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bstf", "123", Isotope.LungAbsorption.NONE, NodeMatchers.isDisabled(), 1, new VBox() }
        };
    }

    @Test
    void testBtnNextHandler_RadioLungAbsorption() {
        setupEnabledDisabled();

        setInitialActivity("123");
        assertEquals(0, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("Bstf"));
        assertEquals(1, controller.getSearchFilteredIsos().size());
        assertAdditionalInfoShowing(vBoxLungAbs);

        setLungAbsorption(Isotope.LungAbsorption.FAST);
        assertAdditionalInfoShowing(vBoxLungAbs);
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

    protected void setLifeSpan(Isotope.LifeSpan lifeSpan) {
        if(lifeSpan == null) {
            lifeSpan = Isotope.LifeSpan.REGULAR;
        }

        switch (lifeSpan) {
            case SHORT:
                interact(() -> radioBtnShortLived.setSelected(true));
                break;
            case LONG:
                interact(() -> radioBtnLongLived.setSelected(true));
                break;
            default:
                interact(() -> radioBtnShortLived.setSelected(false));
                interact(() -> radioBtnLongLived.setSelected(false));
        }
    }

    protected void setLungAbsorption(Isotope.LungAbsorption lungAbsorption) {
        if(lungAbsorption == null) {
            lungAbsorption = Isotope.LungAbsorption.NONE;
        }

        switch (lungAbsorption) {
            case SLOW:
                interact(() -> radioBtnSlowLungAbs.setSelected(true));
                break;
            case MEDIUM:
                interact(() -> radioBtnMediumLungAbs.setSelected(true));
                break;
            case FAST:
                interact(() -> radioBtnFastLungAbs.setSelected(true));
                break;
            default:
                interact(() -> radioBtnSlowLungAbs.setSelected(false));
                interact(() -> radioBtnMediumLungAbs.setSelected(false));
                interact(() -> radioBtnFastLungAbs.setSelected(false));
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
