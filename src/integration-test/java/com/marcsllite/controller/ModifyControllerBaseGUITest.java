package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.model.Nuclide;
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
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

abstract class ModifyControllerBaseGUITest extends GUITest {
    ModifyController controller;
    StackPane stackPaneModify;

    // First Page
    VBox vBoxFirstPage;
    TextField txtFieldNuclideName;
    TextField txtFieldA0;
    ComboBox<String> comboBoxA0Prefix;
    ChoiceBox<String> choiceBoxA0RadUnit;
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
    ChoiceBox<String> choiceBoxMassUnit;
    ChoiceBox<String> choiceBoxNature;
    ChoiceBox<String> choiceBoxState;
    ChoiceBox<String> choiceBoxForm;
    CheckBox chckBoxSameMass;
    CheckBox chckBoxSameNSF;
    Button btnBack;
    Button btnFinish;
    Text txtSecondPageStatus;

    public ModifyControllerBaseGUITest(BaseController.Page page) {
        super(FXMLView.MODIFY, page == null? BaseController.Page.NONE : page);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ModifyController) getController();

        stackPaneModify = controller.modifyPane;
        // First Page
        vBoxFirstPage = controller.vBoxFirstPage;
        txtFieldNuclideName = controller.txtFieldNuclideName;
        txtFieldA0 = controller.txtFieldA0;
        comboBoxA0Prefix = controller.comboBoxA0Prefix;
        choiceBoxA0RadUnit = controller.choiceBoxA0RadUnit;
        vBoxMoreInfo = controller.vBoxMoreInfo;
        hBoxAddInfoTop = controller.hBoxAddInfoTop;
        vBoxLifeSpan = controller.vBoxLifeSpan;
        radioBtnShortLived = controller.radioBtnShortLived;
        radioBtnLongLived = controller.radioBtnLongLived;
        vBoxLungAbs = controller.vBoxLungAbs;
        radioBtnSlowLungAbs = controller.radioBtnSlowLungAbs;
        radioBtnMediumLungAbs = controller.radioBtnMediumLungAbs;
        radioBtnFastLungAbs = controller.radioBtnFastLungAbs;
        txtFirstPageStatus = controller.txtFirstPageStatus;
        btnNext = controller.btnNext;
        // Second Page
        vBoxSecondPage = controller.vBoxSecondPage;
        datePicker = controller.datePicker;
        txtFieldMass = controller.txtFieldMass;
        comboBoxMassPrefix = controller.comboBoxMassPrefix;
        choiceBoxMassUnit = controller.choiceBoxMassUnit;
        choiceBoxNature = controller.choiceBoxNature;
        choiceBoxState = controller.choiceBoxState;
        choiceBoxForm = controller.choiceBoxForm;
        chckBoxSameMass = controller.chckBoxSameMass;
        chckBoxSameNSF = controller.chckBoxSameNSF;
        btnBack = controller.btnBack;
        btnFinish = controller.btnFinish;
        txtSecondPageStatus = controller.txtSecondPageStatus;
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(stackPaneModify, NodeMatchers.isVisible());
        //Assertions.assertEquals(getPage(), controller.getPage());

        goToPage(1);
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxA0Prefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxA0RadUnit.getItems());
        goToPage(2);
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxMassPrefix.getItems());
        assertEquals(Conversions.MassUnit.getFxValues(), choiceBoxMassUnit.getItems());
        assertEquals(Nuclide.Nature.getFxValues(), choiceBoxNature.getItems());
        assertEquals(LimitsModelId.State.getFxValues(), choiceBoxState.getItems());
        assertEquals(LimitsModelId.Form.getFxValues(), choiceBoxForm.getItems());
    }

    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        FxAssert.verifyThat(stackPaneModify, NodeMatchers.isInvisible());

        interact(() ->controller.show());

        FxAssert.verifyThat(stackPaneModify, NodeMatchers.isVisible());
    }

    @Test
    void testMassListener_NullBlank() {
        goToPage(2);
        String goodMass = "21";

        setDate(LocalDate.now());
        setMass("  ");
        FxAssert.verifyThat(btnFinish, NodeMatchers.isDisabled());

        setMass(goodMass);
        FxAssert.verifyThat(btnFinish, NodeMatchers.isEnabled());

        setMass(null);
        FxAssert.verifyThat(btnFinish, NodeMatchers.isDisabled());

        setMass(goodMass);
        FxAssert.verifyThat(btnFinish, NodeMatchers.isEnabled());

        setMass("NAN");
        FxAssert.verifyThat(btnFinish, NodeMatchers.isDisabled());
    }

    protected void goToPage(int pageNum) {
        interact(() ->{
            vBoxFirstPage.setVisible(pageNum == 1);
            vBoxSecondPage.setVisible(pageNum == 2);
        });
        assertEquals(pageNum == 1, vBoxFirstPage.isVisible());
        assertEquals(pageNum == 2, vBoxSecondPage.isVisible());
    }

    protected void assertSettingField(int filteredIsoSize, VBox vbox, Matcher<Node> nodeMatcher) {
        assertEquals("Incorrect number of filtered Isotopes based off search.",filteredIsoSize, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(vbox);
        FxAssert.verifyThat(btnNext, nodeMatcher);
    }

    protected void assertAdditionalInfoShowing(VBox vbox) {
        String lifeSpanVisible = "Life Span additional info is visible. Error: ";
        String lifeSpanInvisible = "Life Span additional info is invisible. Error: ";
        String lungAbsVisible = "Lung Absorption additional info is visible. Error: ";
        String lungAbsInvisible = "Lung Absorption additional info is invisible. Error: ";
        if(vbox == null) {
            FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isInvisible(),
                stringBuilder -> new StringBuilder(lifeSpanVisible + stringBuilder.toString()));
            FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isInvisible(),
                stringBuilder -> new StringBuilder(lungAbsVisible + stringBuilder.toString()));
        } else if(vbox == vBoxLifeSpan){
            FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isVisible(),
                stringBuilder -> new StringBuilder(lifeSpanInvisible + stringBuilder.toString()));
            FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isInvisible(),
                stringBuilder -> new StringBuilder(lungAbsVisible + stringBuilder.toString()));
        } else if(vbox == vBoxLungAbs) {
            FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isInvisible(),
                stringBuilder -> new StringBuilder(lifeSpanVisible + stringBuilder.toString()));
            FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible(),
                stringBuilder -> new StringBuilder(lungAbsInvisible + stringBuilder.toString()));
        }
    }

    protected void clearFirstPageForm() {
        goToPage(1);
        setNuclideName(null);
        setInitialActivity(null);
        setSIPrefix(1, null);
        setRadUnit(null);
        setLifeSpan(null);
        setLungAbsorption(null);

    }

    protected void setNuclideName(String name) {
        setString(name, txtFieldNuclideName);
    }

    protected void setInitialActivity(String str) {
        setNumber(str, txtFieldA0);
    }

    protected void setSIPrefix(int page, Conversions.SIPrefix siPrefix) {
        if(siPrefix == null) {
            siPrefix = Conversions.SIPrefix.BASE;
        }

        Conversions.SIPrefix finalPrefix = siPrefix;
        if (page == 1) {
            interact(() -> comboBoxA0Prefix.setValue(finalPrefix.getVal()));
            assertEquals(finalPrefix.getVal(), comboBoxA0Prefix.getValue());
        } else {
            interact(() -> comboBoxMassPrefix.setValue(finalPrefix.getVal()));
            assertEquals(finalPrefix.getVal(), comboBoxMassPrefix.getValue());
        }
    }

    protected void setRadUnit(Conversions.RadUnit radUnit) {
        if(radUnit == null) {
            radUnit = Conversions.RadUnit.CURIE;
        }

        Conversions.RadUnit finalUnit = radUnit;
        interact(() -> choiceBoxA0RadUnit.setValue(finalUnit.getVal()));
        assertEquals(finalUnit.getVal(), choiceBoxA0RadUnit.getValue());
    }

    protected void setLifeSpan(Nuclide.LifeSpan lifeSpan) {
        if(lifeSpan == null) {
            lifeSpan = Nuclide.LifeSpan.REGULAR;
        }

        switch (lifeSpan) {
            case SHORT:
                interact(() -> radioBtnShortLived.setSelected(true));
                assertTrue(radioBtnShortLived.selectedProperty().get());
                break;
            case LONG:
                interact(() -> radioBtnLongLived.setSelected(true));
                assertTrue(radioBtnLongLived.selectedProperty().get());
                break;
            default:
                interact(() -> radioBtnShortLived.setSelected(false));
                interact(() -> radioBtnLongLived.setSelected(false));
                assertFalse(radioBtnShortLived.selectedProperty().get());
                assertFalse(radioBtnLongLived.selectedProperty().get());

        }
    }

    protected void setLungAbsorption(Nuclide.LungAbsorption lungAbsorption) {
        if(lungAbsorption == null) {
            lungAbsorption = Nuclide.LungAbsorption.NONE;
        }

        switch (lungAbsorption) {
            case SLOW:
                interact(() -> radioBtnSlowLungAbs.setSelected(true));
                assertTrue(radioBtnSlowLungAbs.selectedProperty().get());
                break;
            case MEDIUM:
                interact(() -> radioBtnMediumLungAbs.setSelected(true));
                assertTrue(radioBtnMediumLungAbs.selectedProperty().get());
                break;
            case FAST:
                interact(() -> radioBtnFastLungAbs.setSelected(true));
                assertTrue(radioBtnFastLungAbs.selectedProperty().get());
                break;
            default:
                interact(() -> radioBtnSlowLungAbs.setSelected(false));
                interact(() -> radioBtnMediumLungAbs.setSelected(false));
                interact(() -> radioBtnFastLungAbs.setSelected(false));
                assertFalse(radioBtnSlowLungAbs.selectedProperty().get());
                assertFalse(radioBtnMediumLungAbs.selectedProperty().get());
                assertFalse(radioBtnFastLungAbs.selectedProperty().get());
        }
    }

    protected void clearSecondPageForm() {
        goToPage(2);
        setDate(null);
        setMass(null);
        setSIPrefix(2, null);
        setMassUnit(null);
        setNature(null);
        setState(null);
        setForm(null);
        chckBoxSameMass.setSelected(false);
        chckBoxSameNSF.setSelected(false);
    }

    protected void setDate(LocalDate localDate) {
        setDate(localDate, datePicker);
    }

    protected void setMass(String str) {
        setNumber(str, txtFieldMass);
    }

    protected void setMassUnit(Conversions.MassUnit massUnit) {
        if(massUnit == null) {
            massUnit = Conversions.MassUnit.GRAMS;
        }

        Conversions.MassUnit finalUnit = massUnit;
        interact(() -> choiceBoxMassUnit.setValue(finalUnit.getVal()));
        assertEquals(finalUnit.getVal(), choiceBoxMassUnit.getValue());
    }

    protected void setNature(Nuclide.Nature nature) {
        if(nature == null) {
            nature = Nuclide.Nature.REGULAR;
        }

        Nuclide.Nature finalNature = nature;
        interact(() -> choiceBoxNature.setValue(finalNature.getVal()));
        assertEquals(finalNature.getVal(), choiceBoxNature.getValue());
    }

    protected void setState(LimitsModelId.State state) {
        if(state == null) {
            state = LimitsModelId.State.SOLID;
        }

        LimitsModelId.State finalState = state;
        interact(() -> choiceBoxState.setValue(finalState.getVal()));
        assertEquals(finalState.getVal(), choiceBoxState.getValue());
    }

    protected void setForm(LimitsModelId.Form form) {
        if(form == null) {
            form = LimitsModelId.Form.NORMAL;
        }

        LimitsModelId.Form finalForm = form;
        interact(() -> choiceBoxForm.setValue(finalForm.getVal()));
        assertEquals(finalForm.getVal(), choiceBoxForm.getValue());
    }
}
