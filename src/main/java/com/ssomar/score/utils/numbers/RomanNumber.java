package com.ssomar.score.utils.numbers;

import java.util.NavigableMap;
import java.util.TreeMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RomanNumber {

	private static final NavigableMap<Integer, String> MAP = new TreeMap<>();

	static {
		MAP.put(1000, "M");
		MAP.put(900, "CM");
		MAP.put(500, "D");
		MAP.put(400, "CD");
		MAP.put(100, "C");
		MAP.put(90, "XC");
		MAP.put(50, "L");
		MAP.put(40, "XL");
		MAP.put(10, "X");
		MAP.put(9, "IX");
		MAP.put(5, "V");
		MAP.put(4, "IV");
		MAP.put(1, "I");
	}

	public static String toRoman(final int number) {
		// The Romans did not know zero.
		if (number == 0)
			return "0";

		final int key = MAP.floorKey(number);
		return number == key ? MAP.get(number) : MAP.get(key) + toRoman(number - key);
	}
}