package com.marcsllite.controller;

import com.marcsllite.util.RadBigDecimal;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class ModifyControllerEditGUITest extends ModifyControllerBaseGUITest {
    public ModifyControllerEditGUITest() {
        super();
        setPage(BaseController.Page.EDIT);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
    }

    @BeforeEach
    public void setUp() {
        goToPage(1);
    }

    private void assertInitLifeSpan() {
        switch (getEditingNuclide().getLifeSpan()) {
            case SHORT:
                FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isVisible());
                assertTrue(radioBtnShortLived.selectedProperty().get());
                assertFalse(radioBtnLongLived.selectedProperty().get());
                break;
            case LONG:
                FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isVisible());
                assertFalse(radioBtnShortLived.selectedProperty().get());
                assertTrue(radioBtnLongLived.selectedProperty().get());
                break;
            default:
                FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isInvisible());
                assertFalse(radioBtnShortLived.selectedProperty().get());
                assertFalse(radioBtnLongLived.selectedProperty().get());
        }
    }

    private void assertInitLungAbsorption() {
        switch (getEditingNuclide().getLungAbsorption()) {
            case SLOW:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
                assertTrue(radioBtnSlowLungAbs.selectedProperty().get());
                assertFalse(radioBtnMediumLungAbs.selectedProperty().get());
                assertFalse(radioBtnFastLungAbs.selectedProperty().get());
                break;
            case MEDIUM:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
                assertFalse(radioBtnSlowLungAbs.selectedProperty().get());
                assertTrue(radioBtnMediumLungAbs.selectedProperty().get());
                assertFalse(radioBtnFastLungAbs.selectedProperty().get());
                break;
            case FAST:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
                assertFalse(radioBtnSlowLungAbs.selectedProperty().get());
                assertFalse(radioBtnMediumLungAbs.selectedProperty().get());
                assertTrue(radioBtnFastLungAbs.selectedProperty().get());
                break;
            default:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isInvisible());
                assertFalse(radioBtnSlowLungAbs.selectedProperty().get());
                assertFalse(radioBtnMediumLungAbs.selectedProperty().get());
                assertFalse(radioBtnFastLungAbs.selectedProperty().get());
        }
    }

    @Test
    void testEditInit() {
        controller.initEditPage();

        goToPage(1);
        assertEquals(getEditingNuclide().getDisplayNameNotation(), txtFieldNuclideName.textProperty().get());
        if(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING.equals(getEditingNuclide().getInitActivityStr())) {
            assertNull(txtFieldA0.textProperty().get());
        } else {
            assertEquals(getEditingNuclide().getInitActivityStr(), txtFieldA0.textProperty().get());
        }
        assertEquals(getEditingNuclide().getInitActivityPrefix().getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getInitActivityUnit().getVal(), choiceBoxA0RadUnit.getSelectionModel().getSelectedItem());
        assertInitLifeSpan();
        assertInitLungAbsorption();
        assertEquals("", txtFirstPageStatus.textProperty().get());
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());
        goToPage(2);
        assertEquals(getEditingNuclide().getRefDate(), datePicker.getValue());
        if(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING.equals(getEditingNuclide().getMassStr())) {
            assertNull(txtFieldMass.textProperty().get());
        } else {
            assertEquals(getEditingNuclide().getMassStr(), txtFieldMass.textProperty().get());
        }
        assertEquals(getEditingNuclide().getMassPrefix().getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getMassUnit().getVal(), choiceBoxMassUnit.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getNature().getVal(), choiceBoxNature.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getLimitsId().getState().getVal(), choiceBoxState.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getLimitsId().getForm().getVal(), choiceBoxForm.getSelectionModel().getSelectedItem());
        assertFalse(chckBoxSameMass.selectedProperty().get());
        assertFalse(chckBoxSameNSF.selectedProperty().get());
        assertEquals("", txtSecondPageStatus.textProperty().get());
        FxAssert.verifyThat(btnBack, NodeMatchers.isEnabled());
        FxAssert.verifyThat(btnFinish, NodeMatchers.isEnabled());
    }
}
