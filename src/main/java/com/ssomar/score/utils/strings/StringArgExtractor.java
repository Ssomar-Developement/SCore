package com.ssomar.score.utils.strings;

import java.util.Arrays;

public class StringArgExtractor {

    /**
     * It will extract the value of a prefix in a placeholder string (Without the percent signs at the front and back char)
     * @param input The string placeholder
     * @param keyArg the prefix you'd specify to extract its value
     * @return
     */
    public static String[] extractArgValue(String input, String keyArg) {
        if (!input.contains(keyArg+":")) return new String[]{};

        int start = input.indexOf(keyArg+":");

        // remove the first half of the string so you'd end up with the
        // target prefix, its value and the trailing string
        String firstChop = input.substring(start);

        // find all of the colon symbol positions
        int colon_leftmostIdx = firstChop.indexOf(":");
        // there are scenarios where there can be more than 1 trailing arguments, so we'd really wanna find the colon next to the target prefix's colon
        int colon_next_leftmostIdx = firstChop.substring(colon_leftmostIdx+1).indexOf(":");


        // then check if there's any trailing prefixes
        boolean isThereTrailingPrefix = colon_next_leftmostIdx > colon_leftmostIdx;
        if (isThereTrailingPrefix) {
            return firstChop.substring(colon_leftmostIdx+1, colon_next_leftmostIdx-1).split(",");
        } else {
            // if the last char is an underscore, that's not the logic's concern anymore
            int firstColonIdx = firstChop.indexOf(":");
            return new String[]{firstChop.substring(firstColonIdx+1)};
        }

    }

    /**
     * The "slots:" argument is required to be present in the string argument
     * to sum up the values of multiple EI/EB ItemStack variable values.
     * @param string
     * @param keyArg
     * @return
     */
    static int extractArgValueTotal(String string, String keyArg) {
        return 0;
    }

}
