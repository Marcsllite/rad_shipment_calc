package com.marcsllite.controller;

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

    @Test
    void testEditInit() {
        // First Page
        assertEquals(getEditingNuclide().getDisplayNameNotation(), txtFieldNuclideName.getText());
        if(getEditingNuclide().getInitActivity().isNegativeInfinity()) {
            assertEquals("0", txtFieldA0.getText());
        } else {
            assertEquals(getEditingNuclide().getInitActivity().toString(), txtFieldA0.getText());
        }
        assertEquals(getEditingNuclide().getInitActivityPrefix().getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getInitActivityUnit().getVal(), choiceBoxA0RadUnit.getSelectionModel().getSelectedItem());
        assertInitLifeSpan();
        assertInitLungAbsorption();
        assertEquals("", txtFirstPageStatus.getText());
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());
        // Second Page
        assertEquals(getEditingNuclide().getRefDate(), datePicker.getValue());
        assertEquals(getEditingNuclide().getMass().toString(), txtFieldMass.getText());
        assertEquals(getEditingNuclide().getMassPrefix().getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getMassUnit().getVal(), choiceBoxMassUnit.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getNature().getVal(), choiceBoxNature.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getLimitsId().getState().getVal(), choiceBoxState.getSelectionModel().getSelectedItem());
        assertEquals(getEditingNuclide().getLimitsId().getForm().getVal(), choiceBoxForm.getSelectionModel().getSelectedItem());
        assertFalse(chckBoxSameMass.isSelected());
        assertFalse(chckBoxSameNSF.isSelected());
        assertEquals("", txtSecondPageStatus.getText());
        FxAssert.verifyThat(btnBack, NodeMatchers.isEnabled());
        FxAssert.verifyThat(btnFinish, NodeMatchers.isEnabled());
    }

    private void assertInitLifeSpan() {
        switch (getEditingNuclide().getLifeSpan()) {
            case SHORT:
                FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isVisible());
                assertTrue(radioBtnShortLived.isSelected());
                assertFalse(radioBtnLongLived.isSelected());
                break;
            case LONG:
                FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isVisible());
                assertFalse(radioBtnShortLived.isSelected());
                assertTrue(radioBtnLongLived.isSelected());
                break;
            default:
                FxAssert.verifyThat(vBoxLifeSpan, NodeMatchers.isInvisible());
                assertFalse(radioBtnShortLived.isSelected());
                assertFalse(radioBtnLongLived.isSelected());
        }
    }

    private void assertInitLungAbsorption() {
        switch (getEditingNuclide().getLungAbsorption()) {
            case SLOW:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
                assertTrue(radioBtnSlowLungAbs.isSelected());
                assertFalse(radioBtnMediumLungAbs.isSelected());
                assertFalse(radioBtnFastLungAbs.isSelected());
                break;
            case MEDIUM:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
                assertFalse(radioBtnSlowLungAbs.isSelected());
                assertTrue(radioBtnMediumLungAbs.isSelected());
                assertFalse(radioBtnFastLungAbs.isSelected());
                break;
            case FAST:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isVisible());
                assertFalse(radioBtnSlowLungAbs.isSelected());
                assertFalse(radioBtnMediumLungAbs.isSelected());
                assertTrue(radioBtnFastLungAbs.isSelected());
                break;
            default:
                FxAssert.verifyThat(vBoxLungAbs, NodeMatchers.isInvisible());
                assertFalse(radioBtnSlowLungAbs.isSelected());
                assertFalse(radioBtnMediumLungAbs.isSelected());
                assertFalse(radioBtnFastLungAbs.isSelected());
        }
    }
}
