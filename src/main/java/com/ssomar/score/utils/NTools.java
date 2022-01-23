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

	public static double reduceDouble(double number, int numbersAfterComma){
		/* Limit numbers after , */
		StringBuilder sb = new StringBuilder();
		int limit = numbersAfterComma;
		boolean startCount = false;
		int cpt = 0;
		for(char c : (number+"").toCharArray()){
			if(cpt == limit){
				break;
			}
			else if(startCount){
				cpt++;
			}
			else if(c == ','){
				startCount = true;
			}
			sb.append(c);
		}
		return Double.parseDouble(sb.toString());
	}

}
