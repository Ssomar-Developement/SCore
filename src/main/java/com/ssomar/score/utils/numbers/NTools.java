package com.ssomar.score.utils.numbers;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
        // Use direct multiplication and division instead of string formatting
        if (numbersAfterComma >= 0) {
            double factor = Math.pow(10, numbersAfterComma);
            return Math.round(number * factor) / factor;
        }

        // Handle negative numbersAfterComma or other special cases by falling back to formatter
        // Create a reusable thread-local formatter map for rare cases
        else {
            return formatWithDecimalFormat(number, numbersAfterComma);
        }
    }

    // Extract formatter logic to a separate method for the rare cases
    private static double formatWithDecimalFormat(double number, int numbersAfterComma) {
        // Use a ConcurrentHashMap to cache formatters by precision
        DecimalFormat formatter = FORMATTER_CACHE.computeIfAbsent(numbersAfterComma, precision -> {
            StringBuilder pattern = new StringBuilder("#.");
            for (int i = 0; i < precision; i++) {
                pattern.append("0");
            }
            DecimalFormat df = new DecimalFormat(pattern.toString(), DecimalFormatSymbols.getInstance(Locale.US));
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df;
        });

        return Double.parseDouble(formatter.format(number));
    }

    // Static cache of formatters
    private static final ConcurrentHashMap<Integer, DecimalFormat> FORMATTER_CACHE = new ConcurrentHashMap<>();

    // Initialize the three most common formatters
    static {
        for (int i = 1; i <= 3; i++) {
            FORMATTER_CACHE.computeIfAbsent(i, precision -> {
                StringBuilder pattern = new StringBuilder("#.");
                for (int j = 0; j < precision; j++) {
                    pattern.append("0");
                }
                DecimalFormat df = new DecimalFormat(pattern.toString(), DecimalFormatSymbols.getInstance(Locale.US));
                df.setRoundingMode(RoundingMode.HALF_UP);
                return df;
            });
        }
    }

    public static void main(String[] args) {
       System.out.println(reduceDouble(1.87656789, 2));

    }
}
