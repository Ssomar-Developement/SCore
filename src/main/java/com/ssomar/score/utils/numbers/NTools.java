package com.ssomar.score.utils.numbers;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

import com.ssomar.score.SsomarDev;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NTools implements Serializable {

	public static final DecimalFormat numberFormat_1 = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance(Locale.US));
	public static final DecimalFormat numberFormat_2 = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US));
	public static final DecimalFormat numberFormat_3 = new DecimalFormat("#.000", DecimalFormatSymbols.getInstance(Locale.US));

	public static Optional<Integer> getInteger(final String s) {
		Optional<Integer> result = Optional.empty();
		try {
			result = Optional.of(Integer.valueOf(s));
		} catch (final NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static Optional<Double> getDouble(final String s) {
		Optional<Double> result = Optional.empty();
		try {
			result = Optional.of(Double.valueOf(s));
		} catch (final NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static Optional<Long> getLong(final String s) {
		Optional<Long> result = Optional.empty();
		try {
			result = Optional.of(Long.valueOf(s));
		} catch (final NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static Optional<Float> getFloat(final String s) {
		Optional<Float> result = Optional.empty();
		try {
			result = Optional.of(Float.valueOf(s));
		} catch (final NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static boolean isNumber(final String s) {
		try {
			Double.valueOf(s);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static double reduceDouble(final double number, final int numbersAfterComma) {
		// Limit numbers after ",".
		//
		// Static frequently used formats for optimization.
		if (numbersAfterComma == 1) {
			numberFormat_1.setRoundingMode(RoundingMode.HALF_UP);

			return Double.parseDouble(numberFormat_1.format(number).replaceAll("\\?", "").replace(",", "."));
		}

		if (numbersAfterComma == 2) {
			numberFormat_2.setRoundingMode(RoundingMode.HALF_UP);
			SsomarDev.testMsg(numberFormat_2.format(number).replaceAll("\\?", "").replaceAll(",", "."), true);

			return Double.parseDouble(numberFormat_2.format(number).replaceAll("\\?", "").replaceAll(",", "."));
		}

		if (numbersAfterComma == 3) {
			numberFormat_3.setRoundingMode(RoundingMode.HALF_UP);

			return Double.parseDouble(numberFormat_3.format(number).replaceAll("\\?", "").replace(",", "."));
		}

		final StringBuilder format = new StringBuilder("#.");

		for (int i = 0; i < numbersAfterComma; i++)
			format.append("0");

		final DecimalFormat numberFormat_other = new DecimalFormat(format.toString(), DecimalFormatSymbols.getInstance(Locale.US));
		return Double.parseDouble(numberFormat_other.format(number).replace(",", "."));
	}

	public static double range(final double value, final double minimum, final double maximum) {
		return Math.min(Math.max(value, minimum), maximum);
	}

	public static int range(final int value, final int minimum, final int maximum) {
		return Math.min(Math.max(value, minimum), maximum);
	}

	public static long range(final long value, final long minimum, final long maximum) {
		return Math.min(Math.max(value, minimum), maximum);
	}

	public static float range(final float value, final float minimum, final float maximum) {
		return Math.min(Math.max(value, minimum), maximum);
	}

	public static void main(final String[] args) {
		System.out.println(reduceDouble(1.87656789, 2));
	}
}
