package com.ssomar.score.utils;

public class StringCalculation {

	public static boolean calculation(String s, double number) {
		String calculStr = s;

		calculStr=calculStr.replaceAll(" ", "");
		if(calculStr.startsWith("<=") || calculStr.startsWith("=<")) {
			try {
				return number<=Double.valueOf(calculStr.substring(2, calculStr.length()));
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith("<")) {
			try {
				return number<Double.valueOf(calculStr.substring(1, calculStr.length()));
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith(">=") || calculStr.startsWith("=>")) {
			try {
				return number>=Double.valueOf(calculStr.substring(2, calculStr.length()));
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith(">")) {
			try {
				return number>Double.valueOf(calculStr.substring(1, calculStr.length()));
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith("==")) {
			try {
				return number==Double.valueOf(calculStr.substring(2, calculStr.length()));
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith("=")) {
			try {
				return number==Double.valueOf(calculStr.substring(1, calculStr.length()));
			}catch(Exception e) {
				return false;
			}
		}
		else {
			try {
				return number>=Double.valueOf(calculStr);
			}catch(Exception e) {
				return false;
			}
		}
	}
	
	public static boolean isStringCalculation(String s) {
		String calculStr = s;
		
		calculStr=calculStr.replaceAll(" ", "");
		if(calculStr.startsWith("<=") || calculStr.startsWith("=<")) {
			try {
				Double.valueOf(calculStr.substring(2, calculStr.length()));
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith("<")) {
			try {
				Double.valueOf(calculStr.substring(1, calculStr.length()));
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith(">=") || calculStr.startsWith("=>")) {
			try {
				Double.valueOf(calculStr.substring(2, calculStr.length()));
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith(">")) {
			try {
				Double.valueOf(calculStr.substring(1, calculStr.length()));
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith("==")) {
			try {
				Double.valueOf(calculStr.substring(2, calculStr.length()));
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		else if(calculStr.startsWith("=")) {
			try {
				Double.valueOf(calculStr.substring(1, calculStr.length()));
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		else {
			try {
				Double.valueOf(calculStr);
				return true;
			}catch(Exception e) {
				return false;
			}
		}
	}
}
