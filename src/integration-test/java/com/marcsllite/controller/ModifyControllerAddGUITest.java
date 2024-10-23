package com.marcsllite.controller;

import com.marcsllite.TestUtils;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        setNuclidesInTable(false);
        goToPage(1);
    }

    private void setNuclidesInTable(boolean val) {
        HomePaneController homePaneController = MainController.getInstance().getHomePaneController();
        when(homePaneController.isNuclideInTable(any())).thenReturn(val);
    }

    @Test
    void testAddInit() {
        interact(() -> controller.initAddPage());

        assertNull(txtFieldNuclideName.getText());
        assertNull(txtFieldA0.getText());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.CURIE.getVal(), choiceBoxA0RadUnit.getSelectionModel().getSelectedItem());
        assertFalse(hBoxAddInfoTop.isVisible());
        assertFalse(vBoxLungAbs.isVisible());
        assertFalse(vBoxLifeSpan.isVisible());
        assertTrue(btnNext.isDisabled());

        assertEquals(LocalDate.now(), datePicker.getValue());
        assertNull(txtFieldMass.getText());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.MassUnit.GRAMS.getVal(), choiceBoxMassUnit.getSelectionModel().getSelectedItem());
        assertEquals(Nuclide.Nature.REGULAR.getVal(), choiceBoxNature.getSelectionModel().getSelectedItem());
        assertEquals(LimitsModelId.State.SOLID.getVal(), choiceBoxState.getSelectionModel().getSelectedItem());
        assertEquals(LimitsModelId.Form.NORMAL.getVal(), choiceBoxForm.getSelectionModel().getSelectedItem());
        assertTrue(btnFinish.isDisabled());
    }

    @Test
    void testAddNuclideAlreadyInTable() {
        setNuclidesInTable(true);
        setNuclideName("name");
        assertEquals("Nuclide already in the table.", txtFirstPageStatus.getText());
    }

    @ParameterizedTest
    @MethodSource("noRadio_data")
    void testBtnNext_EnabledDisabled_NoRadio_Checker(String name, String a0, boolean isBtnNextDisabled, int filteredIsoSize) {
        setNuclidesInTable(false);

        setNuclideName(name);
        assertSettingField(filteredIsoSize, null, true);

        setInitialActivity(a0);
        assertSettingField(filteredIsoSize, null, isBtnNextDisabled);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertSettingField(0, null, true);

        setNuclideName(name);
        assertSettingField(filteredIsoSize, null, isBtnNextDisabled);
    }

    private static Object[] noRadio_data() {
        TestUtils.TestNuclide regularNuclide = TestUtils.getRegularNuclide();
        String symbolNotation = regularNuclide.getDisplaySymbolNotation();
        return new Object[] {
            new Object[] { regularNuclide.getSymbol(), "1sdf23", true, TestUtils.equalsTestNuclide(regularNuclide.getSymbol()) },
            new Object[] { " ", "1sdf23", true, 0 },
            new Object[] { " ", " ", true, 0 },
            new Object[] { symbolNotation, "123", false, TestUtils.equalsTestNuclide(symbolNotation) }
        };
    }


    @Test
    void testBtnNextHandler_NoRadio() {
        setInitialActivity("123");
        assertSettingField(TestUtils.getTestNuclideSize(), null, true);

        setNuclideName(TestUtils.getRegularNuclide().getDisplaySymbolNotation());
        assertSettingField(1, null, false);

        clickOn(btnNext);
        assertFalse(vBoxFirstPage.isVisible());
        assertTrue(vBoxSecondPage.isVisible());
    }

    @ParameterizedTest
    @MethodSource("radioLifeSpan_data")
    void testBtnNext_EnabledDisabled_RadioLifeSpan_Checker(String name, String a0, Nuclide.LifeSpan lifeSpan, boolean isBtnNextDisabled, int filteredIsoSize, boolean visibleRadio) {
        VBox additionalInfo = visibleRadio? vBoxLifeSpan : null;

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, true);

        setInitialActivity(a0);
        assertSettingField(filteredIsoSize, additionalInfo, true);

        setLifeSpan(lifeSpan);
        assertSettingField(filteredIsoSize, additionalInfo, isBtnNextDisabled);

        clearFirstPageForm();
        setInitialActivity(a0);
        assertSettingField(0, null, true);

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, true);

        setLifeSpan(lifeSpan);
        assertSettingField(filteredIsoSize, additionalInfo, isBtnNextDisabled);
    }

    private static Object[] radioLifeSpan_data() {
        TestUtils.TestNuclide lifeSpanNuclide = TestUtils.getLifeSpanNuclide();
        String shortSymbol = lifeSpanNuclide.getDisplaySymbolNotation();
        String fullSymbol = lifeSpanNuclide.getFullSymbolNotation();
        return new Object[] {
            new Object[] { " ", " ", Nuclide.LifeSpan.SHORT, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LifeSpan.SHORT, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LifeSpan.SHORT, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LifeSpan.SHORT, false, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LifeSpan.SHORT, false, TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LifeSpan.LONG, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LifeSpan.LONG, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LifeSpan.LONG, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LifeSpan.LONG, false, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LifeSpan.LONG, false, TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LifeSpan.REGULAR, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LifeSpan.REGULAR, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LifeSpan.REGULAR, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LifeSpan.REGULAR, true, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LifeSpan.REGULAR, true, TestUtils.equalsTestNuclide(fullSymbol), true }
        };
    }

    @Test
    void testBtnNextHandler_RadioLifeSpan() {
        TestUtils.TestNuclide lifeSpanNuclide = TestUtils.getLifeSpanNuclide();

        setInitialActivity("123");
        assertSettingField(TestUtils.getTestNuclideSize(), null, true);

        setNuclideName(lifeSpanNuclide.getDisplaySymbolNotation());
        assertSettingField(TestUtils.equalsTestNuclide(lifeSpanNuclide.getDisplaySymbolNotation()), vBoxLifeSpan, true);

        setNuclideName(lifeSpanNuclide.getFullSymbolNotation());
        int filteredIsoSize = TestUtils.equalsTestNuclide(lifeSpanNuclide.getFullSymbolNotation());
        assertSettingField(filteredIsoSize, vBoxLifeSpan, true);

        setLifeSpan(Nuclide.LifeSpan.SHORT);
        assertSettingField(filteredIsoSize, vBoxLifeSpan, false);

        clickOn(btnNext);
        assertFalse(vBoxFirstPage.isVisible());
        assertTrue(vBoxSecondPage.isVisible());
    }
    
    @ParameterizedTest
    @MethodSource("radioLungAbsorption_data")
    void testBtnNext_EnabledDisabled_RadioLungAbsorption_Checker(String name, String a0, Nuclide.LungAbsorption lungAbs, boolean isBtnNextDisabled, int filteredIsoSize, boolean visibleRadio) {
        VBox additionalInfo;
        if(visibleRadio) {
            additionalInfo = vBoxLungAbs;
        } else {
            additionalInfo = null;
        }

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, true);

        setInitialActivity(a0);
        assertSettingField(filteredIsoSize, additionalInfo, true);

        if(visibleRadio) {
            setLungAbsorption(lungAbs);
            assertSettingField(filteredIsoSize, additionalInfo, isBtnNextDisabled);
        }

        clearFirstPageForm();
        setInitialActivity(a0);
        assertSettingField(0, null, true);

        setNuclideName(name);
        assertSettingField(filteredIsoSize, additionalInfo, true);

        if(visibleRadio) {
            setLungAbsorption(lungAbs);
            assertSettingField(filteredIsoSize, additionalInfo, isBtnNextDisabled);
        }
    }

    private static Object[] radioLungAbsorption_data() {
        TestUtils.TestNuclide lungAbsNuclide = TestUtils.getLungAbsNuclide();
        String shortSymbol = lungAbsNuclide.getDisplaySymbolNotation();
        String fullSymbol = lungAbsNuclide.getFullSymbolNotation();
        return new Object[] {
            new Object[] { " ", " ", Nuclide.LungAbsorption.SLOW, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.SLOW, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.SLOW, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.SLOW, false, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.SLOW, false, TestUtils.equalsTestNuclide(fullSymbol), true },
            
            new Object[] { " ", " ", Nuclide.LungAbsorption.MEDIUM, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.MEDIUM, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.MEDIUM, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.MEDIUM, false, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.MEDIUM, false, TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LungAbsorption.FAST, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.FAST, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.FAST, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.FAST, false, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.FAST, false, TestUtils.equalsTestNuclide(fullSymbol), true },

            new Object[] { " ", " ", Nuclide.LungAbsorption.NONE, true, 0, false },
            new Object[] { shortSymbol.substring(0,1), "1sdf23", Nuclide.LungAbsorption.NONE, true, TestUtils.equalsTestNuclide(shortSymbol.substring(0,1)), false },
            new Object[] { " ", "1sdf23", Nuclide.LungAbsorption.NONE, true, 0, false },
            new Object[] { shortSymbol, "123", Nuclide.LungAbsorption.NONE, true, TestUtils.equalsTestNuclide(shortSymbol), true },
            new Object[] { fullSymbol, "123", Nuclide.LungAbsorption.NONE, true, TestUtils.equalsTestNuclide(fullSymbol), true }
        };
    }

    @Test
    void testBtnNextHandler_RadioLungAbsorption() {
        TestUtils.TestNuclide lungAbsorptionNuclide = TestUtils.getLungAbsNuclide();

        setInitialActivity("123");
        assertSettingField(TestUtils.getTestNuclideSize(), null, true);

        int filteredIsoSize = TestUtils.equalsTestNuclide(lungAbsorptionNuclide.getDisplaySymbolNotation());
        setNuclideName(lungAbsorptionNuclide.getDisplaySymbolNotation());
        assertSettingField(filteredIsoSize, vBoxLungAbs, true);

        setNuclideName(lungAbsorptionNuclide.getFullSymbolNotation());
        assertSettingField(filteredIsoSize, vBoxLungAbs, true);

        setLungAbsorption(Nuclide.LungAbsorption.FAST);
        assertSettingField(filteredIsoSize, vBoxLungAbs, false);

        clickOn(btnNext);
        assertFalse(vBoxFirstPage.isVisible());
        assertTrue(vBoxSecondPage.isVisible());
    }

    @Test
    void testModifyHandler_chckBoxSameMass() {
        goToPage(2);

        clickOn(chckBoxSameMass);
        assertTrue(chckBoxSameMass.isSelected());
        
        assertTrue(txtFieldMass.isDisabled());
        assertTrue(comboBoxMassPrefix.isDisabled());
        assertTrue(choiceBoxMassUnit.isDisabled());

        clickOn(chckBoxSameMass);
        assertFalse(chckBoxSameMass.isSelected());

        assertFalse(txtFieldMass.isDisabled());
        assertFalse(comboBoxMassPrefix.isDisabled());
        assertFalse(choiceBoxMassUnit.isDisabled());
    }

    @Test
    void testModifyHandler_chckBoxSameNSF() {
        goToPage(2);

        clickOn(chckBoxSameNSF);
        assertTrue(chckBoxSameNSF.isSelected());

        assertTrue(choiceBoxNature.isDisabled());
        assertTrue(choiceBoxState.isDisabled());
        assertTrue(choiceBoxForm.isDisabled());

        clickOn(chckBoxSameNSF);
        assertFalse(chckBoxSameNSF.isSelected());

        assertFalse(choiceBoxNature.isDisabled());
        assertFalse(choiceBoxState.isDisabled());
        assertFalse(choiceBoxForm.isDisabled());
    }

    @Test
    void testModifyHandler_btnBack() {
        goToPage(2);

        clickOn(btnBack);

        assertTrue(vBoxFirstPage.isVisible());
        assertFalse(vBoxSecondPage.isVisible());
    }

    @Test
    void testModifyHandler_btnFinish() {
        TestUtils.TestNuclide testNuclide = TestUtils.getRegularNuclide();
        setNuclideName(testNuclide.getDisplaySymbolNotation());
        assertSettingField(1, null, true);

        String initActivity = "123";
        setInitialActivity(initActivity);
        assertSettingField(1, null, false);

        clickOn(btnNext);

        LocalDate date = LocalDate.now();
        setDate(date);
        String mass = "1";
        setMass(mass);
        assertFalse(btnFinish.isDisabled());

        Shipment shipmentMock = mock(Shipment.class);
        HomePaneController homePaneController = MainController.getInstance().getHomePaneController();
        when(homePaneController.getShipment()).thenReturn(shipmentMock);

        clickOn(btnFinish);

        ArgumentCaptor<Nuclide> nuclideCaptor = ArgumentCaptor.forClass(Nuclide.class);
        verify(shipmentMock).add(nuclideCaptor.capture());

        Nuclide addedNuclide = nuclideCaptor.getValue();
        assertEquals(testNuclide.getAtomicNumber(), addedNuclide.getAtomicNumber());
        assertEquals(testNuclide.getName(), addedNuclide.getName());
        assertEquals(testNuclide.getSymbol(), addedNuclide.getNuclideId().getSymbol());
        assertEquals(testNuclide.getMassNumber(), addedNuclide.getMassNumber());
        assertEquals(testNuclide.getLifeSpan(), addedNuclide.getLifeSpan());
        assertEquals(testNuclide.getLungAbsorption(), addedNuclide.getLungAbsorption());
        assertEquals(initActivity, addedNuclide.getInitActivity().toDisplayString());
        assertEquals(mass, addedNuclide.getMass().toDisplayString());
        assertEquals(date, addedNuclide.getRefDate());
    }
}
