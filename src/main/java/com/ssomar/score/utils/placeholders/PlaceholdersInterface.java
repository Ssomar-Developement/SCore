package com.ssomar.score.utils.placeholders;

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

		String result = s;

		while (result.contains(placeholder+"+")) {
			String suit = result.split(placeholder+"\\+")[1];
			StringBuilder sb = new StringBuilder();
			for (char c : suit.toCharArray()) {
				if (c == ' ' || c == ',')
					break; 
				sb.append(c);
			} 
			if (isNumeric(sb.toString())) {
				double d = Double.parseDouble(sb.toString()) + Double.parseDouble(value);
				if(isInteger) result = result.replaceAll(placeholder+"\\+" + sb, "" + (int) d);
				else result = result.replaceAll(placeholder+"\\+" + sb, "" + d);
			} else {
				result = result.replaceAll(placeholder+"\\+" + sb, value);
			} 
		}

		while (result.contains(placeholder+"-")) {
			String suit = result.split(placeholder+"\\-")[1];
			StringBuilder sb = new StringBuilder();
			for (char c : suit.toCharArray()) {
				if (c == ' ' || c == ',' )
					break; 
				sb.append(c);
			} 
			if (isNumeric(sb.toString())) {
				double d = Double.parseDouble(value) - Double.parseDouble(sb.toString());
				if(isInteger) result = result.replaceAll(placeholder+"\\-" + sb, "" + (int) d);
				else result = result.replaceAll(placeholder+"\\-" + sb, "" + d);
			} else {
				result = result.replaceAll(placeholder+"\\-" + sb, value);
			} 
		}
		while (result.contains(placeholder)) {
			result = result.replaceAll(placeholder, value);
		} 
		return result;
	}
}
