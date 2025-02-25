package com.ssomar.score.utils.numbers;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public class NTools implements Serializable {

    public static DecimalFormat numberFormat_1 = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance(Locale.US));
    public static DecimalFormat numberFormat_2 = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US));
    public static DecimalFormat numberFormat_3 = new DecimalFormat("#.000", DecimalFormatSymbols.getInstance(Locale.US));

    public static Optional<Integer> getInteger(String s) {
        Optional<Integer> result = Optional.empty();
        try {
            result = Optional.of(Integer.valueOf(s));
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }

    public static Optional<Double> getDouble(String s) {
        Optional<Double> result = Optional.empty();
        try {
            result = Optional.of(Double.valueOf(s));
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }

    public static Optional<Long> getLong(String s) {
        Optional<Long> result = Optional.empty();
        try {
            result = Optional.of(Long.valueOf(s));
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }

    public static Optional<Float> getFloat(String s) {
        Optional<Float> result = Optional.empty();
        try {
            result = Optional.of(Float.valueOf(s));
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }

    public static boolean isNumber(String s) {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static double reduceDouble(@NotNull double number, int numbersAfterComma) {
        /* Limit numbers after , */
        /* Static freqeuntly used formats for optimization */
        if(numbersAfterComma == 1){
            numberFormat_1.setRoundingMode(RoundingMode.HALF_UP);
            return Double.parseDouble(numberFormat_1.format(number).replaceAll("\\?", "").replace(",", "."));
        }
        else if(numbersAfterComma == 2){
            numberFormat_2.setRoundingMode(RoundingMode.HALF_UP);
            //SsomarDev.testMsg(numberFormat_2.format(number).replaceAll("\\?", "").replaceAll(",", "."), true);
            return Double.parseDouble(numberFormat_2.format(number).replaceAll("\\?", "").replaceAll(",", "."));
        }
        else if(numbersAfterComma == 3){
            numberFormat_3.setRoundingMode(RoundingMode.HALF_UP);
            return Double.parseDouble(numberFormat_3.format(number).replaceAll("\\?", "").replace(",", "."));
        }
        else{
            StringBuilder format = new StringBuilder("#.");
            for(int i = 0; i < numbersAfterComma; i++){
                format.append("0");
            }
            DecimalFormat numberFormat_other = new DecimalFormat(format.toString(), DecimalFormatSymbols.getInstance(Locale.US));
            return Double.valueOf(numberFormat_other.format(number).replace(",", "."));
        }
    }

    public static void main(String[] args) {
       System.out.println(reduceDouble(1.87656789, 2));

    }
}
