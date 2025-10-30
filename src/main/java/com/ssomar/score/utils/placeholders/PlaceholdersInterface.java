package com.ssomar.score.utils.placeholders;

import com.ssomar.score.SCore;
import com.ssomar.score.math.MathExpressionEngine;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.numbers.RomanNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PlaceholdersInterface {

    public static boolean isNumeric(String strNum) {
        if (strNum == null)
            return false;
        try {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String replaceCalculPlaceholder(String s, String placeholder, String value, boolean isInteger) {
        return replaceCalculPlaceholder(s, placeholder, value, "", "", isInteger, false);
    }

    public static String replaceCalculPlaceholder(String s, String placeholder, String value, String optionalTagSurroundValue, String optionalTagEndBeforeSurround , boolean isInteger) {
        return replaceCalculPlaceholder(s, placeholder, value, optionalTagSurroundValue, optionalTagEndBeforeSurround, isInteger, false);
    }

    public static String replaceCalculPlaceholder(String s, String placeholder, String value, String optionalTagSurroundValue, String optionalTagEndBeforeSurround, boolean isInteger, boolean convertToRoman) {

        String result = s;
        if(optionalTagSurroundValue == null) optionalTagSurroundValue = "";
        if(optionalTagEndBeforeSurround == null) optionalTagEndBeforeSurround = "";

        if(!isNumeric(value)){
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &cInvalid value &6"+value+" &c used in placeholder calculation. String : &6"+s);

            while (result.contains(placeholder)) {
                result = result.replaceAll(placeholder, optionalTagSurroundValue+value+optionalTagEndBeforeSurround+optionalTagSurroundValue);
            }
            return result;
        }


        if(result.contains(placeholder)) {
            while (result.contains(placeholder + "+")) {
                String suit = result.split(placeholder + "\\+")[1];
                StringBuilder sb = new StringBuilder();
                for (char c : suit.toCharArray()) {
                    /* . is accepted because we accept double , not , because its used as separator for list so it can cause issue */
                    if ((c < '0' || c > '9') && !(c == '.')) break;
                    sb.append(c);
                }
                if (isNumeric(sb.toString())) {
                    double d = Double.parseDouble(sb.toString()) + Double.parseDouble(value);
                    String finalValue = String.valueOf(d);
                    if (isInteger) finalValue = String.valueOf((int) d);
                    if (convertToRoman) finalValue = RomanNumber.toRoman((int) d);
                    result = result.replaceFirst(placeholder + "\\+" + sb, optionalTagSurroundValue + finalValue +optionalTagEndBeforeSurround + optionalTagSurroundValue);
                } else {
                    result = result.replaceFirst(placeholder + "\\+" + sb, optionalTagSurroundValue+value+ optionalTagEndBeforeSurround+optionalTagSurroundValue);
                }
            }

            while (result.contains(placeholder + "-")) {
                String suit = result.split(placeholder + "-")[1];
                StringBuilder sb = new StringBuilder();
                for (char c : suit.toCharArray()) {
                    /* . is accepted because we accept double , not , because its used as separator for list so it can cause issue */
                    if ((c < '0' || c > '9') && !(c == '.')) break;
                    sb.append(c);
                }
                if (isNumeric(sb.toString())) {
                    double d = Double.parseDouble(value) - Double.parseDouble(sb.toString());
                    String finalValue = String.valueOf(d);
                    if (isInteger) finalValue = String.valueOf((int) d);
                    if (convertToRoman) finalValue = RomanNumber.toRoman((int) d);
                    result = result.replaceFirst(placeholder + "-" + sb, optionalTagSurroundValue + finalValue+optionalTagEndBeforeSurround+optionalTagSurroundValue);
                } else {
                    result = result.replaceFirst(placeholder + "-" + sb, optionalTagSurroundValue+value+optionalTagEndBeforeSurround+optionalTagSurroundValue);
                }
            }
            while (result.contains(placeholder)) {
                if (convertToRoman) {
                    try {
                        int i = Integer.valueOf(value);
                        result = result.replaceAll(placeholder, optionalTagSurroundValue+RomanNumber.toRoman(i)+optionalTagEndBeforeSurround+optionalTagSurroundValue);
                    } catch (Exception ignored) {
                    }
                } else result = result.replaceAll(placeholder, optionalTagSurroundValue+value+optionalTagEndBeforeSurround+optionalTagSurroundValue);
            }
        }
        return result;
    }

    /**
     * Replace math expression placeholders with their evaluated results
     * Supports syntax: %math:expression%
     * Examples:
     * - %math:2+3*4% -> 14
     * - %math:sqrt(16)% -> 4
     * - %math:if(5>3,10,20)% -> 10
     * - %math:min(5,max(2,8))% -> 5
     *
     * @param s The string containing math expressions
     * @return The string with evaluated math expressions
     */
    public static String replaceMathExpressions(String s) {
        if (s == null || !s.contains("%math:")) {
            return s;
        }

        String result = s;
        Pattern mathPattern = Pattern.compile("%math:([^%]+)%");
        Matcher matcher = mathPattern.matcher(result);

        while (matcher.find()) {
            String expression = matcher.group(1);
            try {
                String evaluatedValue = MathExpressionEngine.evaluateAsString(expression);
                result = result.replace("%math:" + expression + "%", evaluatedValue);
                // Reset matcher after modification
                matcher = mathPattern.matcher(result);
            } catch (Exception e) {
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cError evaluating math expression: &6%math:" + expression + "% &c- " + e.getMessage());
                // Leave the placeholder as is if evaluation fails
            }
        }

        return result;
    }
}
