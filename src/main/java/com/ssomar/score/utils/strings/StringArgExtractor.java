package com.ssomar.score.utils.strings;

import java.util.Arrays;

public class StringArgExtractor {

    /**
     * It will extract the value of a prefix in a placeholder string (Without the percent signs at the front and back char)
     * @param string The string placeholder
     * @param keyArg the prefix you'd specify to extract its value
     * @return
     */
    public static String[] extractArgValue(String string, String keyArg) {
        // Sample text: "executableitems_id:Focalors_var:shield_slot:10"
        String value = string;

        String[] separatedStrings = value.split("_");
        for (String keyVal : separatedStrings) {
            if (keyVal.startsWith(keyArg)) {
                String extractedValue = keyVal.split(":")[1];
                return extractedValue.split(",");
            }
        }

        return new String[]{};
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
