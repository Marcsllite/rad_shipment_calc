package com.marcsllite;

import com.marcsllite.util.handler.PropHandler;

import java.util.Properties;

public class PropHandlerTestObj extends PropHandler {
    public PropHandlerTestObj() {
        Properties properties = new Properties();
        
        properties.put("fakeKey", "");
        properties.put("properMessage", "This is a proper message");
        properties.put("properException", "This is a proper Exception");
        properties.put("replacePropString_noReplacements", "This string doesn’t contain any replacements");
        properties.put("replacePropString_oneReplacement", "This string contains {0} replacements");
        properties.put("replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement");
        properties.put("replacePropString_twoReplacements", "This string contains {0}, {1} replacements");
        properties.put("replacePropString_threeReplacements", "This string contains {0}, {1}, {2} replacements");
        properties.put("replacePropString_oneSameReplacements", "This string contains a replacement here: {0}, and the same replacement here: {0}");
        properties.put("replacePropString_twoSameReplacements", "First: {0}, Second: {1}, Third: {0}, Fourth: {1}");
        properties.put("getList_TrailingDelimiters", "This|List|has|trailing|delimiters||||");
        properties.put("getList_SpacesWithinElements", "element 1 has spaces|element 2 does too");
        properties.put("getList_SpacesAroundElements", "     there are spaces around this element    |   spaces around here too    ");
        properties.put("getList_ProperList", "This|is|a|proper|list");
        properties.put("mainPane", "UMass Lowell - Rad Shipment Calculator");
        properties.put("mainWidth", "600.0");
        properties.put("defaultNum", "-2.0");
        properties.put("appFolderName", "UMass Lowell Radiation Safety");
        properties.put("dataFolderName", "Shipment Calculator");

        setProp(properties);
    }
}
