package com.marcsllite.controller;

import com.marcsllite.model.Nuclide;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
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
import static junit.framework.Assert.assertTrue;

class ModifyControllerAddGUITest extends ModifyControllerBaseGUITest {
    public ModifyControllerAddGUITest() {
        super();
        setPage(BaseController.Page.ADD);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
    }

    @ParameterizedTest
    @MethodSource("noRadio_data")
    void testBtnNext_EnabledDisabled_NoRadio_Checker(String name, String a0, Matcher<Node> matcher, int filteredIsoSize) {
        setupEnabledDisabled();

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, matcher);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertEquals(0, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, matcher);
    }

    private static Object[] noRadio_data() {
        return new Object[] {
            new Object[] { " ", null, NodeMatchers.isDisabled(), 0 },
            new Object[] { null, "1sdf23", NodeMatchers.isDisabled(), 0 },
            new Object[] { "An", "1sdf23", NodeMatchers.isDisabled(), 0 },
            new Object[] { "An-1", "123", NodeMatchers.isEnabled(), 1 }
        };
    }
    

    @Test
    void testBtnNextHandler_NoRadio() {
        setupEnabledDisabled();
        
        setInitialActivity("123");
        assertEquals(0, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("An-1"));
        assertEquals(1, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }

    @ParameterizedTest
    @MethodSource("radioLifeSpan_data")
    void testBtnNext_EnabledDisabled_RadioLifeSpan_Checker(String name, String a0, Nuclide.LifeSpan lifeSpan, Matcher<Node> matcher, int filteredIsoSize, VBox visibleRadio) {
        setupEnabledDisabled();

        visibleRadio = visibleRadio == null? null : vBoxLifeSpan;

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredNuclides().size());
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
        assertEquals(filteredIsoSize, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        setLifeSpan(lifeSpan);
        assertAdditionalInfoShowing(visibleRadio);
        FxAssert.verifyThat(btnNext, matcher);
    }

    private static Object[] radioLifeSpan_data() {
        return new Object[] {
            new Object[] { " ", null, Nuclide.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bf-1", "123", Nuclide.LifeSpan.SHORT, NodeMatchers.isEnabled(), 1, new VBox() },
            new Object[] { "Bf-1(short)", "123", Nuclide.LifeSpan.SHORT, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Nuclide.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bf-1", "123", Nuclide.LifeSpan.LONG, NodeMatchers.isEnabled(), 1, new VBox() },
            new Object[] { "Bf-1(short)", "123", Nuclide.LifeSpan.LONG, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bf-1", "123", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 1, new VBox() },
            new Object[] { "Bf-1(short)", "123", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 1, new VBox() }
        };
    }

    @Test
    void testBtnNextHandler_RadioLifeSpan() {
        setupEnabledDisabled();

        setInitialActivity("123");
        assertEquals(4, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("Bf-1"));
        assertEquals(1, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(vBoxLifeSpan);

        interact(() -> txtFieldIsoName.setText("Bf-1(short)"));
        assertEquals(1, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(vBoxLifeSpan);

        setLifeSpan(Nuclide.LifeSpan.SHORT);
        assertAdditionalInfoShowing(vBoxLifeSpan);
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }
    
    @ParameterizedTest
    @MethodSource("radioLungAbsorption_data")
    void testBtnNext_EnabledDisabled_RadioLungAbsorption_Checker(String name, String a0, Nuclide.LungAbsorption lungAbs, Matcher<Node> matcher, int filteredIsoSize, VBox visibleRadio) {
        setupEnabledDisabled();

        if(visibleRadio != null && filteredIsoSize == 0) {
            visibleRadio = vBoxLifeSpan;
        } else if(filteredIsoSize == 1) {
            visibleRadio = vBoxLungAbs;
        } else {
            visibleRadio = null;
        }

        interact(() -> txtFieldIsoName.setText(name));
        assertEquals(filteredIsoSize, controller.getSearchFilteredNuclides().size());
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
        assertEquals(filteredIsoSize, controller.getSearchFilteredNuclides().size());
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
            new Object[] { " ", null, Nuclide.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bs-1", "123", Nuclide.LungAbsorption.SLOW, NodeMatchers.isEnabled(), 1, new VBox() },
            new Object[] { "Bs-1fast", "123", Nuclide.LungAbsorption.SLOW, NodeMatchers.isEnabled(), 1, new VBox() },
            
            new Object[] { " ", null, Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bs-1", "123", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isEnabled(), 1, new VBox() },
            new Object[] { "Bs-1fast", "123", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Nuclide.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bs-1", "123", Nuclide.LungAbsorption.FAST, NodeMatchers.isEnabled(), 1, new VBox() },
            new Object[] { "Bs-1fast", "123", Nuclide.LungAbsorption.FAST, NodeMatchers.isEnabled(), 1, new VBox() },

            new Object[] { " ", null, Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, null },
            new Object[] { null, "1sdf23", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, null },
            new Object[] { "B", "1sdf23", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, new VBox() },
            new Object[] { "Bs-1", "123", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 1, new VBox() },
            new Object[] { "Bs-1fast", "123", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 1, new VBox() }
        };
    }

    @Test
    void testBtnNextHandler_RadioLungAbsorption() {
        setupEnabledDisabled();

        setInitialActivity("123");
        assertEquals(0, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(null);
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());

        interact(() -> txtFieldIsoName.setText("Bs-1"));
        assertEquals(1, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(vBoxLungAbs);

        interact(() -> txtFieldIsoName.setText("Bs-1fast"));
        assertEquals(1, controller.getSearchFilteredNuclides().size());
        assertAdditionalInfoShowing(vBoxLungAbs);

        setLungAbsorption(Nuclide.LungAbsorption.FAST);
        assertAdditionalInfoShowing(vBoxLungAbs);
        FxAssert.verifyThat(btnNext, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }

    @Test
    void testModifyHandler_chckBoxSameMass() {
        goToPage(2);

        clickOn(chckBoxSameMass);
        assertTrue(chckBoxSameMass.isSelected());
        
        FxAssert.verifyThat(txtFieldMass, NodeMatchers.isDisabled());
        FxAssert.verifyThat(comboBoxMassPrefix, NodeMatchers.isDisabled());
        FxAssert.verifyThat(choiceBoxMassUnit, NodeMatchers.isDisabled());

        clickOn(chckBoxSameMass);
        assertFalse(chckBoxSameMass.isSelected());

        FxAssert.verifyThat(txtFieldMass, NodeMatchers.isEnabled());
        FxAssert.verifyThat(comboBoxMassPrefix, NodeMatchers.isEnabled());
        FxAssert.verifyThat(choiceBoxMassUnit, NodeMatchers.isEnabled());
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
}
