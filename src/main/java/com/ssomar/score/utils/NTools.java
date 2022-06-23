package com.ssomar.score.utils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

public class NTools implements Serializable {

	public static Optional<Integer> getInteger(String s) {
		Optional<Integer> result = Optional.empty();
		try {
			result = Optional.of(Integer.valueOf(s));
		}
		catch(NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static Optional<Double> getDouble(String s) {
		Optional<Double> result = Optional.empty();
		try {
			result = Optional.of(Double.valueOf(s));
		}
		catch(NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static Optional<Long> getLong(String s) {
		Optional<Long> result = Optional.empty();
		try {
			result = Optional.of(Long.valueOf(s));
		}
		catch(NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static Optional<Float> getFloat(String s) {
		Optional<Float> result = Optional.empty();
		try {
			result = Optional.of(Float.valueOf(s));
		}
		catch(NumberFormatException e) {
			return result;
		}
		return result;
	}
	
	public static boolean isNumber(String s) {
		try {
			Double.valueOf(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static double reduceDouble(@NotNull double number, int numbersAfterComma){
		/* Limit numbers after , */
		StringBuilder sb = new StringBuilder("");
		int limit = numbersAfterComma;
		boolean startCount = false;
		int cpt = 0;
		for(char c : (new BigDecimal(number).toPlainString()).toCharArray()){
			if(cpt == limit){
				break;
			}
			else if(startCount){
				cpt++;
			}
			else if(c == ',' || c == '.'){
				startCount = true;
			}
			sb.append(c);
		}
		try{
			return Double.parseDouble(sb.toString());
		}catch (Exception e){
			e.printStackTrace();
			return number;
		}
	}
}
