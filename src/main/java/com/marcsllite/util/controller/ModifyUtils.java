package com.marcsllite.util.controller;

import com.marcsllite.model.Nuclide;
import com.marcsllite.model.db.NuclideModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.marcsllite.util.NuclideUtils.LIFE_SPAN_PATTERN;
import static com.marcsllite.util.NuclideUtils.LUNG_ABS_PATTERN;

public class ModifyUtils {
    private static final Logger logr = LogManager.getLogger();

    private ModifyUtils() {}

    public static void bindManagedPropToVisibility(Node node) {
        node.managedProperty().unbind();

        // visibility of the node will decide if it is rendered by the parent pane
        node.managedProperty().bind(node.visibleProperty());
    }

    public static void bindDisabledPropToCheckBox(Node node, CheckBox checkBox) {
        node.disableProperty().unbind();
        node.disableProperty().bind(checkBox.selectedProperty());
    }

    @SuppressWarnings("unchecked")
    public static boolean setupDropDownItems(Control control, ObservableList<String> items) {
        try {
            if (control instanceof ComboBox<?> comboBox) {
                ((ComboBox<String>) comboBox).setItems(items);
            }
            if (control instanceof ChoiceBox<?> choiceBox) {
                ((ChoiceBox<String>) choiceBox).setItems(items);
            }
        } catch (Exception e) {
            logr.catching(e);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static boolean selectDropDownOption(Control control, String option) {
        try {
            if (control instanceof ComboBox<?> comboBox) {
                ((ComboBox<String>) comboBox).getSelectionModel().select(option);
            }
            if (control instanceof ChoiceBox<?> choiceBox) {
                ((ChoiceBox<String>) choiceBox).getSelectionModel().select(option);
            }
        } catch (Exception e) {
            logr.catching(e);
            return false;
        }
        return true;
    }

    public static void setupTextListener(TextField field, ChangeListener<String> listener) {
        field.textProperty().removeListener(listener);
        field.textProperty().addListener(listener);
    }

    public static void setupToggleListener(ToggleGroup toggle, ChangeListener<Toggle> listener) {
        toggle.selectedToggleProperty().removeListener(listener);
        toggle.selectedToggleProperty().addListener(listener);
    }

    public static void setPredicate(FilteredList<NuclideModel> list, Predicate<NuclideModel> predicate) {
        list.setPredicate(Objects.requireNonNullElseGet(predicate, () -> nuclide -> false));
    }

    public static Predicate<NuclideModel> filteringPredicate(String str) {
        return nuclide -> doesNuclideMatchSearch(nuclide, str);
    }

    public static boolean doesNuclideMatchSearch(NuclideModel nuclide, String str) {
        if(nuclide == null || StringUtils.isBlank(str)) {
            return false;
        }

        String searchStr = str.trim()
            .replaceAll(LIFE_SPAN_PATTERN, "")
            .replaceAll(LUNG_ABS_PATTERN, "");
        String symbol = nuclide.getDisplaySymbolNotation();
        String name = nuclide.getDisplayNameNotation();

        return symbol.equalsIgnoreCase(searchStr) || name.equalsIgnoreCase(searchStr);
    }

    public static void setRadioBtnData(RadioButton radioBtn, String data) {
        radioBtn.setText(data);
        radioBtn.setUserData(data);
    }

    public static void setNuclideLifeSpan(Nuclide nuclide, ToggleGroup toggleGrp) {
        if (toggleGrp.getSelectedToggle() == null) {
            nuclide.setLifeSpan(Nuclide.LifeSpan.REGULAR);
        } else {
            RadioButton radioBtn = (RadioButton) toggleGrp.getSelectedToggle();
            nuclide.getNuclideId().setMassNumber(nuclide.getNuclideId().getMassNumber() + radioBtn.getUserData());
            nuclide.setLifeSpan(Nuclide.LifeSpan.toLifeSpan((String) radioBtn.getUserData()));
        }
    }

    public static void setNuclideLungAbs(Nuclide nuclide, ToggleGroup toggleGrp) {
        if (toggleGrp.getSelectedToggle() == null) {
            nuclide.setLungAbsorption(Nuclide.LungAbsorption.NONE);
        } else {
            RadioButton radioBtn = (RadioButton) toggleGrp.getSelectedToggle();
            nuclide.getNuclideId().setMassNumber(nuclide.getNuclideId().getMassNumber() + radioBtn.getUserData());
            nuclide.setLungAbsorption(Nuclide.LungAbsorption.toLungAbsorption((String) radioBtn.getUserData()));
        }
    }

    public static boolean setLifeSpanToggle(ToggleGroup toggleGrp, Nuclide.LifeSpan lifeSpan) {
        boolean ret = false;
        Optional<Toggle> toggle = toggleGrp.getToggles()
            .stream()
            .filter(t ->
                Objects.equals(
                    Nuclide.LifeSpan.toLifeSpan((String) t.getUserData()),
                    lifeSpan)
            ).findFirst();

        if(toggle.isPresent()) {
            toggleGrp.selectToggle(toggle.get());
            ret = true;
        }
        return ret;
    }

    public static boolean setLungAbsToggle(ToggleGroup toggleGrp, Nuclide.LungAbsorption lungAbs) {
        boolean ret = false;
        Optional<Toggle> toggle = toggleGrp.getToggles()
            .stream()
            .filter(t ->
                Objects.equals(
                    Nuclide.LungAbsorption.toLungAbsorption((String) t.getUserData()),
                    lungAbs)
            ).findFirst();

        if(toggle.isPresent()) {
            toggleGrp.selectToggle(toggle.get());
            ret = true;
        }
        return ret;
    }

    public static boolean nuclideListMinimumEquals(List<NuclideModel> nuclides) {
        return nuclides.stream()
            .map(nuclide -> nuclide.getNuclideId().getDisplaySymbolNotation())
            .distinct()
            .count() == 1;
    }

    public static Nuclide getFilteredNuclide(List<NuclideModel> nuclides) {
        Nuclide nuclide = null;
        // only one nuclide in list or all the nuclide have the same name (minus lifeSpan/LungAbsorption)
        if(nuclides.size() == 1 || ModifyUtils.nuclideListMinimumEquals(nuclides) ) {
            nuclide = new Nuclide(nuclides.get(0));
        }
        return nuclide;
    }
}
