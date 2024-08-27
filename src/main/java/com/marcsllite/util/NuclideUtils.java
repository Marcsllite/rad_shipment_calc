package com.marcsllite.util;

import com.marcsllite.model.Nuclide;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuclideUtils {
    public static final String LUNG_ABS_PATTERN = "(slow|medium|fast) *$";
    public static final String LIFE_SPAN_PATTERN = "\\((.*)\\) *$";

    private NuclideUtils() {}

    public static Nuclide.LifeSpan parseLifeSpanFromMassNumber(String massNumber) {
        Nuclide.LifeSpan ret = Nuclide.LifeSpan.REGULAR;
        if(massNumber != null) {
            Pattern lifeSpanPattern = Pattern.compile(LIFE_SPAN_PATTERN);
            Matcher lifeSpanMatch = lifeSpanPattern.matcher(massNumber);

            if (lifeSpanMatch.find()) {
                ret = Nuclide.LifeSpan.toLifeSpan(lifeSpanMatch.group(1));
            }
        }
        return ret;
    }

    public static Nuclide.LungAbsorption parseLungAbsFromMassNumber(String massNumber) {
        Nuclide.LungAbsorption ret = Nuclide.LungAbsorption.NONE;
        if(massNumber != null) {
            Pattern lungAbsPattern = Pattern.compile(LUNG_ABS_PATTERN);
            Matcher lungAbsMatch = lungAbsPattern.matcher(massNumber);
            if (lungAbsMatch.find()) {
                ret = Nuclide.LungAbsorption.toLungAbsorption(lungAbsMatch.group(0));
            }
        }
        return ret;
    }
}
