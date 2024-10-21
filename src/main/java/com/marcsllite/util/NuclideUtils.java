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

    public static String capitalizeFirstLetter(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return str;
    }

    /**
     * Get the {@link Nuclide.LifeSpan} from the nuclide's mass number
     * @param massNumber the nuclide's mass number
     * @return the {@link Nuclide.LifeSpan} associated with the nuclide
     */
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

    /**
     * Get the {@link Nuclide.LungAbsorption} from the nuclide's mass number
     * @param massNumber the nuclide's mass number
     * @return the {@link Nuclide.LungAbsorption} associated with the nuclide
     */
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

    /**
     * Generates a {@link Nuclide} from the nuclide notation
     * @param nuclideNotation the nuclide notation<br>
     *                        (Name-MassNumber or Symbol-MassNumber)
     * @return A {@link Nuclide} with name and nuclideId populated
     */
    public static Nuclide parseNuclideId(String nuclideNotation) {
        Nuclide nuclide = new Nuclide();
        NuclideModelId id = new NuclideModelId();
        if(StringUtils.isNotBlank(nuclideNotation)) {
            Pattern symbolPattern = Pattern.compile(NuclideUtils.SYMBOL_PATTERN);
            Matcher symbolMatch = symbolPattern.matcher(nuclideNotation);
            if(symbolMatch.find()) {
                String symbolStr = capitalizeFirstLetter(symbolMatch.group(1).toLowerCase());
                if(symbolStr.length() > NuclideModelId.SYMBOL_MAX_LENGTH) {
                    nuclide.setName(symbolStr);
                } else {
                    id.setSymbol(symbolStr);
                }
            }

            Pattern massNumPattern = Pattern.compile(NuclideUtils.MASS_NUMBER_PATTERN);
            Matcher massNumMatch = massNumPattern.matcher(nuclideNotation);
            if(massNumMatch.find()) {
                String massNumStr = massNumMatch.group(1);
                if(massNumStr.length() <= NuclideModelId.MASS_NUMBER_MAX_LENGTH) {
                    id.setMassNumber(massNumStr);
                }
            }
        }
        nuclide.setNuclideId(id);
        return nuclide;
    }
}
