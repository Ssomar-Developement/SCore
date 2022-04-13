package com.ssomar.score.utils.placeholders;

import com.ssomar.score.utils.RomanNumber;

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
		return replaceCalculPlaceholder(s, placeholder, value, isInteger, false);
	}

	public static String replaceCalculPlaceholder(String s, String placeholder, String value, boolean isInteger, boolean convertToRoman) {

		String result = s;

		while (result.contains(placeholder+"+")) {
			String suit = result.split(placeholder+"\\+")[1];
			StringBuilder sb = new StringBuilder();
			for (char c : suit.toCharArray()) {
				if (c == ' ' || c == ',' || c == ')')
					break; 
				sb.append(c);
			} 
			if (isNumeric(sb.toString())) {
				double d = Double.parseDouble(sb.toString()) + Double.parseDouble(value);
				String finalValue = String.valueOf(d);
				if(isInteger) finalValue = String.valueOf((int)d);
				if(convertToRoman) finalValue = RomanNumber.toRoman((int)d);
				result = result.replaceAll(placeholder+"\\+" + sb, "" + finalValue);
			} else {
				result = result.replaceAll(placeholder+"\\+" + sb, value);
			} 
		}

		while (result.contains(placeholder+"-")) {
			String suit = result.split(placeholder+"\\-")[1];
			StringBuilder sb = new StringBuilder();
			for (char c : suit.toCharArray()) {
				if (c == ' ' || c == ',' || c == ')' )
					break; 
				sb.append(c);
			} 
			if (isNumeric(sb.toString())) {
				double d = Double.parseDouble(value) - Double.parseDouble(sb.toString());
				String finalValue = String.valueOf(d);
				if(isInteger) finalValue = String.valueOf((int)d);
				if(convertToRoman) finalValue = RomanNumber.toRoman((int)d);
				result = result.replaceAll(placeholder+"\\+" + sb, "" + finalValue);
			} else {
				result = result.replaceAll(placeholder+"\\-" + sb, value);
			} 
		}
		while (result.contains(placeholder)) {
			if(convertToRoman) {
				try {
					int i = Integer.valueOf(value);
					result = result.replaceAll(placeholder, RomanNumber.toRoman(i));
				}catch (Exception e) {}
			}
			else result = result.replaceAll(placeholder, value);
		} 
		return result;
	}
}
