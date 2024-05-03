// noinspection JSUnusedGlobalSymbols

/*
Copyright (c) 2019 by Marc Pierre. All rights reserved.
Author: Marc Pierre, Marc.Pierre@kronos.com.,
updated by Marc Pierre on June 18, 2019, at 10:19 AM
Description: Javascript to sort the table in the Duration Test Tracker tool status report
*/
/* USE ONLY MULTILINE QUOTES (when adding to html file new line characters are not added, so the file is one line)
 * NOTE: function name linked with the below line from HTMLTableWriter.java file (in method createTable()):
 *       colHead.setAttribute("onclick", "sortTable(" + i + ")");
 */

/* Declaring global variables */
let table = document.getElementById("table"), /*holds the table in the document*/
    rows,  /* array of all the rows in the table */
    switching = false, /*boolean that describes if a switch should be made*/
    i, /* holds the index of the current row */
    x, /*holds one of the elements to be compared*/
    y, /*holds the second element to be compared*/
    shouldSwitch, /*boolean that describes if the rows should switch or not*/
    shouldBreak, /*boolean that describes if the code should break out of while loop*/
    isAsc = true, /*boolean that describes if sorting in ascending order or not*/
    switchCount; /*keeps track oh how much switching was done*/

/**
* @Author w3schools https://www.w3schools.com/howto/howto_js_sort_table.asp
* Function to sort the table using the given column index and title of column
*
* @param colIndex the index of the column to sort
* @param colName the name of the column to sort
*/
function sortTable(colIndex, colName) {
    switchCount = 0;
    table = document.getElementById("table");
    isAsc = true;   /* Set the sorting direction to ascending: */
    /* Make a loop that will continue until no switching has been done: */
    do {
        switching = false;  /* Start by saying: no switching is done: */
        rows = table.rows;  /* getting all the rows in the tbody*/

        for (i = 1; i < (rows.length - 1); i++) {  /* Loop through all table rows (except the first, which contains table headers): */
            /* Start by saying there should be no switching and no breaking out of the loop */
            shouldSwitch = false;
            shouldBreak = false;

            /* Get the two elements you want to compare, one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[colIndex]; /*getting the first element to compare (from current row)*/
            y = rows[i + 1].getElementsByTagName("TD")[colIndex];  /*getting the second element to compare (from next row)*/

            /* Check if the two rows should switch place, based on the direction, asc or desc: */
            switch (colName) {
                case "Number": sortNumbers(); break;
                case "DeviceID": sortDeviceIds(); break;
                case "DeviceType": sortDeviceTypes(); break;
                case "UpTime": sortUpTimes(); break;
                case "Hostname(IP)": sortHostnameIps(); break;
                case "Server Type": sortServerTypes(); break;
                case "Server Hostname": sortServerHostnames(); break;
                case "Status": sortStatuses(); break;
                case "Firmware": sortFirmwares(); break;
                case "Timezone": sortTimezones(); break;
                case "Biometrics": sortBiometrics(); break;
                case "Punch Upload Failure (last week)": sortPunchUploadFails(); break;
                default: break;
            }
            if (shouldBreak) break;  /*if after sorting we need to break out of loop then break*/
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            /* Each time a switch is done, increase this count by 1: */
            switchCount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchCount === 0 && isAsc) {
                isAsc = false;
                switching = true;
            }
        }
    } while (switching);
}

/**
 * Helper function to sort the Number column
 */
function sortNumbers() {
    if (isAsc) {
        if (Number(x.innerHTML) > Number(y.innerHTML)) {
            shouldSwitch = true;
            shouldBreak = true;
        } else {
            shouldSwitch = false;
            shouldBreak = false;
        }
    } else if (!isAsc) {
        if (Number(x.innerHTML) < Number(y.innerHTML)) {
            shouldSwitch = true;
            shouldBreak = true;
        } else {
            shouldSwitch = false;
            shouldBreak = false;
        }
    }
}
/**
 * Helper function to sort the DeviceID column
 */
function sortDeviceIds() { sortNumbers();  /* sorting the device ids numerically*/ }
/**
 * Helper function to sort the DeviceType column
 */
function sortDeviceTypes() {
    if (isAsc) {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
            /* If so, mark as a switch and break the loop: */
            shouldSwitch = true;
            shouldBreak = true;
        } else {
            shouldSwitch = false;
            shouldBreak = false;
        }
    } else {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
            /* If so, mark as a switch and break the loop: */
            shouldSwitch = true;
            shouldBreak = true;
        } else {
            shouldSwitch = false;
            shouldBreak = false;
        }
    }
}
/**
 * Helper function to sort the UpTime column
 */
function sortUpTimes() {
    /*i at the end of regex returns first string that matches (case-insensitive)*/
    const currentRowVal = x.innerHTML,
        nextRowVal = y.innerHTML,
        regexYRS = /(\d+)yrs?/i, /*regular expressions to get the years string from the uptime*/
        regexMOS = /(\d+)mos?/i,  /*regular expressions to get the months string from the uptime*/
        regexWKS = /(\d+)wks?/i,  /*regular expressions to get the weeks string from the uptime*/
        regexD = /(\d+)d/i,  /*regular expressions to get the days string from the uptime*/
        regexHRS = /(\d+)hrs?/i,  /*regular expressions to get the hours string from the uptime*/
        regexMINS = /(\d+)mins?/i,  /*regular expressions to get the minutes string from the uptime*/
        yearsX = (regexYRS.exec(currentRowVal) == null) ? 0 : Number(regexYRS.exec(currentRowVal)[1]),  /*array containing the results of the regular expression search*/
        monthsX = (regexMOS.exec(currentRowVal) == null) ? 0 : Number(regexMOS.exec(currentRowVal)[1]),  /*array containing the results of the regular expression search*/
        weeksX = (regexWKS.exec(currentRowVal) == null) ? 0 : Number(regexWKS.exec(currentRowVal)[1]),  /*array containing the results of the regular expression search*/
        daysX = (regexD.exec(currentRowVal) == null) ? 0 : Number(regexD.exec(currentRowVal)[1]),  /*array containing the results of the regular expression search*/
        hoursX = (regexHRS.exec(currentRowVal) == null) ? 0 : Number(regexHRS.exec(currentRowVal)[1]),  /*array containing the results of the regular expression search*/
        minutesX = (regexMINS.exec(currentRowVal) == null) ? 0 : Number(regexMINS.exec(currentRowVal)[1]),  /*array containing the results of the regular expression search*/
        yearsY = (regexYRS.exec(nextRowVal) == null) ? 0 : Number(regexYRS.exec(nextRowVal)[1]),  /*array containing the results of the regular expression search*/
        monthsY = (regexMOS.exec(nextRowVal) == null) ? 0 : Number(regexMOS.exec(nextRowVal)[1]),  /*array containing the results of the regular expression search*/
        weeksY = (regexWKS.exec(nextRowVal) == null) ? 0 : Number(regexWKS.exec(nextRowVal)[1]),  /*array containing the results of the regular expression search*/
        daysY = (regexD.exec(nextRowVal) == null) ? 0 : Number(regexD.exec(nextRowVal)[1]),  /*array containing the results of the regular expression search*/
        hoursY = (regexHRS.exec(nextRowVal) == null) ? 0 : Number(regexHRS.exec(nextRowVal)[1]),  /*array containing the results of the regular expression search*/
        minutesY = (regexMINS.exec(nextRowVal) == null) ? 0 : Number(regexMINS.exec(nextRowVal)[1]);  /*array containing the results of the regular expression search*/

    if (isAsc) {
        if (yearsX > yearsY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (yearsX < yearsY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (monthsX > monthsY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (monthsX < monthsY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (weeksX > weeksY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (weeksX < weeksY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (daysX > daysY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (daysX < daysY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (hoursX > hoursY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (hoursX < hoursY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (minutesX > minutesY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (minutesX < minutesY){
            shouldSwitch = false;
            shouldBreak = false;
        }
    } else if (!isAsc) {
        if (yearsX < yearsY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (yearsX > yearsY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (monthsX < monthsY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (monthsX > monthsY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (weeksX < weeksY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (weeksX > weeksY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (daysX < daysY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (daysX > daysY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (hoursX < hoursY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (hoursX > hoursY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
        else if (minutesX < minutesY){
            shouldSwitch = true;
            shouldBreak = true;
        } else if (minutesX > minutesY) {
            shouldSwitch = false;
            shouldBreak = false;
        }
    }
}
/**
 * Helper function to sort the Hostname(IP) column
 */
function sortHostnameIps() {
    sortDeviceTypes(); /* sorting the hostname(IP) like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Server Type column
 */
function sortServerTypes() {
    sortDeviceTypes(); /* sorting the server type like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Server Hostname column
 */
function sortServerHostnames() {
    sortDeviceTypes(); /* sorting the serverHostnames like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Status column
 */
function sortStatuses() {
    sortDeviceTypes(); /* sorting the states like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Firmware column
 */
function sortFirmwares() {
    sortDeviceTypes(); /* sorting the firmwares like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Timezone column
 */
function sortTimezones() {
    sortDeviceTypes(); /* sorting the timezones like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Biometrics column
 */
function sortBiometrics() {
    sortDeviceTypes(); /* sorting the biometrics like the device type (alphabetically)*/
}
/**
 * Helper function to sort the Punch Upload Failure (last week) column
 */
function sortPunchUploadFails() {
    sortNumbers(); /* sorting the server punch upload fail like the numbers (numerically)*/
}
