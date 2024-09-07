package com.marcsllite.controller;

import com.marcsllite.TestUtils;
import com.marcsllite.model.Nuclide;
import com.marcsllite.util.Conversions;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
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
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ModifyControllerAddGUITest extends ModifyControllerBaseGUITest {
    public ModifyControllerAddGUITest() {
        super(BaseController.Page.ADD);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
    }

    @BeforeEach
    public void setUp() {
        clearFirstPageForm();
        clearSecondPageForm();
        goToPage(1);
    }

    private void setNuclidesInTable(boolean val) {
        HomePaneController homePaneController = MainController.getInstance().getHomePaneController();
        when(homePaneController.isNuclideInTable(any())).thenReturn(val);
    }

    @Test
    void testAddInit() {
        interact(() -> controller.initAddPage());

        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.CURIE.getVal(), choiceBoxA0RadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.MassUnit.GRAMS.getVal(), choiceBoxMassUnit.getSelectionModel().getSelectedItem());
        FxAssert.verifyThat(btnNext, NodeMatchers.isDisabled());
    }

    @Test
    void testAddNuclideAlreadyInTable() {
        setNuclidesInTable(true);
        setNuclideName("name");
        assertEquals("Nuclide already in the table.", txtFirstPageStatus.getText());
    }

    @ParameterizedTest
    @MethodSource("noRadio_data")
    void testBtnNext_EnabledDisabled_NoRadio_Checker(String name, String a0, Matcher<Node> matcher, int filteredIsoSize) {
        setNuclidesInTable(false);

        setNuclideName(name);
        assertSettingField(filteredIsoSize, null, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertSettingField(filteredIsoSize, null, matcher);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertSettingField(0, null, NodeMatchers.isDisabled());

        setNuclideName(name);
        assertSettingField(filteredIsoSize, null, matcher);
    }

    private static Object[] noRadio_data() {
        TestUtils.TestNuclide regularNuclide = TestUtils.getRegularNuclide();
        String symbolNotation = regularNuclide.getDisplaySymbolNotation();
        return new Object[] {
            new Object[] { regularNuclide.getSymbol(), "1sdf23", NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(regularNuclide.getSymbol()) },
            new Object[] { " ", "1sdf23", NodeMatchers.isDisabled(), 0 },
            new Object[] { " ", " ", NodeMatchers.isDisabled(), 0 },
            new Object[] { symbolNotation, "123", NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(symbolNotation) }
        };
    }
    

    @Test
    void testBtnNextHandler_NoRadio() {
        setInitialActivity("123");
        assertSettingField(0, null, NodeMatchers.isDisabled());

        setNuclideName(TestUtils.getRegularNuclide().getDisplaySymbolNotation());
        assertSettingField(1, null, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }

    @ParameterizedTest
    @MethodSource("radioLifeSpan_data")
    void testBtnNext_EnabledDisabled_RadioLifeSpan_Checker(String name, String a0, Nuclide.LifeSpan lifeSpan, Matcher<Node> matcher, int filteredIsoSize, boolean visibleRadio) {
        VBox additionalInfo = visibleRadio? vBoxLifeSpan : null;

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertSettingField(filteredIsoSize, additionalInfo, NodeMatchers.isDisabled());

        setLifeSpan(lifeSpan);
        assertSettingField(filteredIsoSize, additionalInfo, matcher);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertSettingField(0, null, NodeMatchers.isDisabled());

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, NodeMatchers.isDisabled());

        setLifeSpan(lifeSpan);
        assertSettingField(filteredIsoSize, additionalInfo, matcher);
    }

    private static Object[] radioLifeSpan_data() {
        TestUtils.TestNuclide lifeSpanNuclide = TestUtils.getLifeSpanNuclide();
        String shortSymbol = lifeSpanNuclide.getDisplaySymbolNotation();
        String fullSymbol = lifeSpanNuclide.getFullSymbolNotation();
        return new Object[] {
            new Object[] { " ", " ", Nuclide.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LifeSpan.SHORT, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LifeSpan.SHORT, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LifeSpan.SHORT, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LifeSpan.SHORT, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LifeSpan.LONG, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LifeSpan.LONG, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LifeSpan.LONG, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LifeSpan.LONG, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LifeSpan.REGULAR, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(fullSymbol), true }
        };
    }

    @Test
    void testBtnNextHandler_RadioLifeSpan() {
        TestUtils.TestNuclide lifeSpanNuclide = TestUtils.getLifeSpanNuclide();

        setInitialActivity("123");
        assertSettingField(TestUtils.getTestNuclideSize(), null, NodeMatchers.isDisabled());

        setNuclideName(lifeSpanNuclide.getDisplaySymbolNotation());
        assertSettingField(TestUtils.equalsTestNuclide(lifeSpanNuclide.getDisplaySymbolNotation()), vBoxLifeSpan, NodeMatchers.isDisabled());

        setNuclideName(lifeSpanNuclide.getFullSymbolNotation());
        int filteredIsoSize = TestUtils.equalsTestNuclide(lifeSpanNuclide.getFullSymbolNotation());
        assertSettingField(filteredIsoSize, vBoxLifeSpan, NodeMatchers.isDisabled());

        setLifeSpan(Nuclide.LifeSpan.SHORT);
        assertSettingField(filteredIsoSize, vBoxLifeSpan, NodeMatchers.isEnabled());

        clickOn(btnNext);
        FxAssert.verifyThat(vBoxFirstPage, NodeMatchers.isInvisible());
        FxAssert.verifyThat(vBoxSecondPage, NodeMatchers.isVisible());
    }
    
    @ParameterizedTest
    @MethodSource("radioLungAbsorption_data")
    void testBtnNext_EnabledDisabled_RadioLungAbsorption_Checker(String name, String a0, Nuclide.LungAbsorption lungAbs, Matcher<Node> matcher, int filteredIsoSize, boolean visibleRadio) {
        VBox additionalInfo;
        if(visibleRadio) {
            additionalInfo = vBoxLungAbs;
        } else {
            additionalInfo = null;
        }

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, NodeMatchers.isDisabled());

        setInitialActivity(a0);
        assertSettingField(filteredIsoSize, additionalInfo, NodeMatchers.isDisabled());

        if(visibleRadio) {
            setLungAbsorption(lungAbs);
            assertSettingField(filteredIsoSize, additionalInfo, matcher);
        }

        clearFirstPageForm();
        setInitialActivity(a0);
        assertSettingField(0, null, NodeMatchers.isDisabled());

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, NodeMatchers.isDisabled());

        if(visibleRadio) {
            setLungAbsorption(lungAbs);
            assertSettingField(filteredIsoSize, additionalInfo, matcher);
        }
    }

    private static Object[] radioLungAbsorption_data() {
        TestUtils.TestNuclide lungAbsNuclide = TestUtils.getLungAbsNuclide();
        String shortSymbol = lungAbsNuclide.getDisplaySymbolNotation();
        String fullSymbol = lungAbsNuclide.getFullSymbolNotation();
        return new Object[] {
            new Object[] { " ", " ", Nuclide.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.SLOW, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.SLOW, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.SLOW, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.SLOW, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(fullSymbol), true },
            
            new Object[] { " ", " ", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.MEDIUM, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.FAST, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.FAST, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.FAST, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.FAST, NodeMatchers.isEnabled(), TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.NONE, NodeMatchers.isDisabled(), TestUtils.equalsTestNuclide(fullSymbol), true }
        };
    }

    @Test
    void testBtnNextHandler_RadioLungAbsorption() {
        TestUtils.TestNuclide lungAbsorptionNuclide = TestUtils.getLungAbsNuclide();

        setInitialActivity("123");
        assertSettingField(0, null, NodeMatchers.isDisabled());

        int filteredIsoSize = TestUtils.equalsTestNuclide(lungAbsorptionNuclide.getDisplaySymbolNotation());
        setNuclideName(lungAbsorptionNuclide.getDisplaySymbolNotation());
        assertSettingField(filteredIsoSize, vBoxLungAbs, NodeMatchers.isDisabled());

        setNuclideName(lungAbsorptionNuclide.getFullSymbolNotation());
        assertSettingField(filteredIsoSize, vBoxLungAbs, NodeMatchers.isDisabled());

        setLungAbsorption(Nuclide.LungAbsorption.FAST);
        assertSettingField(filteredIsoSize, vBoxLungAbs, NodeMatchers.isEnabled());

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
