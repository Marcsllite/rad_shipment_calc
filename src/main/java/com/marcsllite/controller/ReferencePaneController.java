package com.marcsllite.controller;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.PTableColumn;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Locale;
import java.util.stream.Collectors;

public class ReferencePaneController extends BaseController {
    @FXML GridPane referencePane;
    @FXML TextField txtFieldSearch;
    @FXML TableView<IsotopeModelId> tableViewSearch;
    @FXML PTableColumn<IsotopeModelId, Label> tableColRefAbbr;
    @FXML PTableColumn<IsotopeModelId, Label> tableColRefName;
    @FXML Label labelA1;
    @FXML Label labelA2;
    @FXML Label labelDecayConst;
    @FXML Label labelExemptCon;
    @FXML Label labelExemptLim;
    @FXML Label labelHalfLife;
    @FXML Label labelReportQuan;

    public ReferencePaneController() {
        this(null);
    }

    public ReferencePaneController(PropHandler propHandler) {
        super(propHandler);
    }

    @Override
    @FXML public void initialize() {
        super.initialize();
        initTable();
    }

    @Override
    public void show() {
        referencePane.setVisible(true);
        referencePane.toFront();
    }

    @Override
    public void hide() {
        referencePane.setVisible(false);
        referencePane.toBack();
    }

    protected void initTable() {
        tableColRefAbbr.setCellValueFactory(new PropertyValueFactory<>("abbr"));
        tableColRefName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewSearch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ObservableList<IsotopeModelId> allIsotopes = getDbService().getAllIsotopes()
                .stream()
                .map(Isotope::getIsoId)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tableViewSearch.setItems(allIsotopes);

        setupSearchFiltering(allIsotopes);
    }

    protected void setupSearchFiltering(ObservableList<IsotopeModelId> list) {
        FilteredList<IsotopeModelId> filteredData =  new FilteredList<>(list, b -> true);

        txtFieldSearch.textProperty().addListener((observable, oldV, newV) -> filteredData.setPredicate(modelId -> {
            if(newV == null|| newV.isBlank() || newV.isEmpty()) {
                return true;
            }

            String searchStr = newV.toLowerCase(Locale.ROOT);

            if(modelId.getAbbr().toLowerCase().contains(searchStr)) {
                return true;
            } else {
                return modelId.getName().toLowerCase().contains(searchStr);
            }
        }));

        SortedList<IsotopeModelId> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewSearch.comparatorProperty());
        tableViewSearch.setItems(sortedData);
    }
}
