package com.marcsllite.util;

import com.marcsllite.model.Nuclide;
import com.marcsllite.model.db.NuclideModelId;
import org.codehaus.plexus.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuclideUtils {
    public static final String SYMBOL_PATTERN = "^(.*)-";
    public static final String MASS_NUMBER_PATTERN = "-(.*)$";
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
                ret = Nuclide.LungAbsorption.toLungAbsorption(lungAbsMatch.group(1));
            }
        }
        return ret;
    }

    public static NuclideModelId parseNuclideId(String str) {
        NuclideModelId id = new NuclideModelId();
        if(StringUtils.isNotBlank(str)) {
            Pattern symbolPattern = Pattern.compile(NuclideUtils.SYMBOL_PATTERN);
            Matcher symbolMatch = symbolPattern.matcher(str);
            if(symbolMatch.find()) {
                id.setSymbol(StringUtils.capitalizeFirstLetter(symbolMatch.group(1).toLowerCase()));
            }

            Pattern massNumPattern = Pattern.compile(NuclideUtils.MASS_NUMBER_PATTERN);
            Matcher massNumMatch = massNumPattern.matcher(str);
            if(massNumMatch.find()) {
                id.setMassNumber(massNumMatch.group(1));
            }
        }
        return id;
    }
}
