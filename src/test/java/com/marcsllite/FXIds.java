package com.marcsllite;

/**
 * Test Class holding Constants in order to improve maintenance and centralize all
 * Java fx:id's.
 */
public class FXIds {
    private FXIds() {}

    // Splash Screen
    public static final String GRIDPANE_SPLASH = "#gridPaneSplash";
    public static final String LABEL_SPLASH = "#labelSplash";
    public static final String PROGRESS_SPLASH = "#progressSplash";

    // Main
    public static final String GRIDPANE_MENU = "#menuPane";
    public static final String GRIDPANE_HOME = "#homePane";
    public static final String GRIDPANE_REFERENCE = "#referencePane";

    // Menu Pane
    public static final String STACKPANE_LOGO = "#stackPaneLogo";
    public static final String IMGVIEW_COLOR_LOGO = "#imgViewColorLogo";
    public static final String IMGVIEW_GREY_LOGO = "#imgViewGreyLogo";
    public static final String BTN_SHIPMENT = "#btnShipment";
    public static final String IMGVIEW_SHIPMENT = "#imgViewShipment";
    public static final String BTN_REFERENCE = "#btnReference";
    public static final String IMGVIEW_REFERENCE = "#imgViewReference";

    // Home Pane
    public static final String BTN_ADD = "#btnAdd";
    public static final String BTN_EDIT = "#btnEdit";
    public static final String BTN_REMOVE = "#btnRemove";
    public static final String TABLEVIEW_HOME = "#tableViewHome";
    public static final String TABLE_COL_ISOTOPES = "#tableColIsotope";
    public static final String TABLE_COL_HALF_LIFE = "#tableColHalfLife";
    public static final String TABLE_COL_ACTIVITY = "#tableColActivity";
    public static final String TABLE_COL_REF_DATE = "#tableColRefDate";
    public static final String TABLE_COL_MASS = "#tableColMass";
    public static final String BTN_CALCULATE = "#btnCalculate";

    // Reference Pane
    public static final String TXTFIELD_SEARCH = "#txtFieldSearch";
    public static final String TABLEVIEW_SEARCH = "#tableViewSearch";
    public static final String TABLE_COL_REF_ABBR = "#tableColRefAbbr";
    public static final String TABLE_COL_REF_NAME = "#tableColRefName";
    public static final String TXTFIELD_A1 = "#txtFieldA1";
    public static final String COMBOBOX_REF_A1_PREFIX = "#comboBoxRefA1Prefix";
    public static final String CHOICEBOX_REF_A1_RAD_UNIT = "#choiceBoxRefA1RadUnit";
    public static final String TXTFIELD_A2 = "#txtFieldA2";
    public static final String COMBOBOX_REF_A2_PREFIX = "#comboBoxRefA2Prefix";
    public static final String CHOICEBOX_REF_A2_RAD_UNIT = "#choiceBoxRefA2RadUnit";
    public static final String TXTFIELD_DECAY_CONSTANT = "#txtFieldDecayConst";
    public static final String TXTFIELD_EXEMPT_CON = "#txtFieldExemptCon";
    public static final String COMBOBOX_REF_EXEMPT_CON_PREFIX = "#comboBoxRefExemptConPrefix";
    public static final String CHOICEBOX_REF_EXEMPT_CON_RAD_UNIT = "#choiceBoxRefExemptConRadUnit";
    public static final String TXTFIELD_EXEMPT_LIMIT = "#txtFieldExemptLim";
    public static final String COMBOBOX_REF_EXEMPT_LIM_PREFIX = "#comboBoxRefExemptLimPrefix";
    public static final String CHOICEBOX_REF_EXEMPT_LIM_RAD_UNIT = "#choiceBoxRefExemptLimRadUnit";
    public static final String TXTFIELD_HALF_LIFE = "#txtFieldHalfLife";
    public static final String TXTFIELD_REPORTABLE_QUANTITY = "#txtFieldReportQuan";
    public static final String COMBOBOX_REF_REPORT_QUAN_PREFIX = "#comboBoxRefReportQuanPrefix";
    public static final String CHOICEBOX_REF_REPORT_QUAN_RAD_UNIT = "#choiceBoxRefReportQuanRadUnit";

    // Modify
    public static final String STACKPANE_MODIFY = "#modifyPane";
    public static final String VBOX_FIRST_PAGE = "#vBoxFirstPage";
    public static final String TXTFIELD_ISO_NAME = "#txtFieldIsoName";
    public static final String TXTFIELD_A0 = "#txtFieldA0";
    public static final String COMBOBOX_A0_PREFIX = "#comboBoxA0Prefix";
    public static final String CHOICEBOX_AO_NAME = "#choiceBoxA0Name";
    public static final String VBOX_MORE_INFO = "#vBoxMoreInfo";
    public static final String HBOX_ADD_INFO_TOP = "#hBoxAddInfoTop";
    public static final String VBOX_LIFE_SPAN = "#vBoxLifeSpan";
    public static final String RADIO_SHORT_LIVED = "#radioBtnShortLived";
    public static final String TOGGLE_LIFE_SPAN = "#toggleGrpLifeSpan";
    public static final String RADIO_LONG_LIVED = "#radioBtnLongLived";
    public static final String VBOX_LUNG_ABS = "#vBoxLungAbs";
    public static final String RADIO_SLOW_LUNG = "#radioBtnSlowLungAbs";
    public static final String TOGGLE_LUNG_ABS = "#toggleGrpLungAbs";
    public static final String RADIO_MEDIUM_LUNG = "#radioBtnMediumLungAbs";
    public static final String RADIO_FAST_LUNG = "#radioBtnFastLungAbs";
    public static final String TXT_FIRST_PAGE_STATUS = "#txtFirstPageStatus";
    public static final String BTN_NEXT = "#btnNext";

    public static final String VBOX_SECOND_PAGE = "#vBoxSecondPage";
    public static final String DATE_PICKER = "#datePicker";
    public static final String TXTFIELD_MASS = "#txtFieldMass";
    public static final String COMBOBOX_MASS_PREFIX = "#comboBoxMassPrefix";
    public static final String CHOICEBOX_MASS_NAME = "#choiceBoxMassName";
    public static final String CHOICEBOX_NATURE = "#choiceBoxNature";
    public static final String CHOICEBOX_STATE = "#choiceBoxState";
    public static final String CHOICEBOX_FORM = "#choiceBoxForm";
    public static final String CHCKBOX_SAME_MASS = "#chckBoxSameMass";
    public static final String CHCKBOX_SAME_NSF = "#chckBoxSameNSF";
    public static final String BTN_BACK = "#btnBack";
    public static final String BTN_FINISH = "#btnFinish";
    public static final String TXT_SECOND_PAGE_STATUS = "#txtSecondPageStatus";

    // Shipment Details
    public static final String STACKPANE_SHIPMENT_DETAILS = "#shipmentDetails";
    public static final String VBOX_SHIP_DETAILS = "#vBoxShipDetails";
    public static final String DATE_PICKER_SHIP_DETAILS = "#datePickerShipDetails";
    public static final String TXTFIELD_MASS_SHIP_DETAILS = "#txtFieldMassShipDetails";
    public static final String COMBOBOX_MASS_PREFIX_SHIP_DETAILS = "#comboBoxMassPrefixShipDetails";
    public static final String CHOICEBOX_MASS_NAME_SHIP_DETAILS = "#choiceBoxMassNameShipDetails";
    public static final String CHOICEBOX_NATURE_SHIP_DETAILS = "#choiceBoxNatureShipDetails";
    public static final String CHOICEBOX_STATE_SHIP_DETAILS = "#choiceBoxStateShipDetails";
    public static final String CHOICEBOX_FORM_SHIP_DETAILS = "#choiceBoxFormShipDetails";
    public static final String BTN_SAVE_SHIP_DETAILS = "#btnSaveShipDetails";

    // Summary
    public static final String ANCHORPANE_SUMMARY = "#summaryPane";
    public static final String TXTAREA_SUMMARY = "#txtAreaSummary";
    public static final String BTN_SAVE_SUMMARY = "#btnSaveSummary";
}
