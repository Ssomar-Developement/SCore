package com.ssomar.score.utils;

public class NTools {
	
	public static boolean isNumber(String s) {
		try {
			Double.valueOf(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

}
