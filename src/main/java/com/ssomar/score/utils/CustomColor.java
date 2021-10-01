package com.ssomar.score.utils;

import org.bukkit.Color;

import static org.bukkit.Color.AQUA;

public class CustomColor {

	public static Color valueOf(String s) {

		s = s.toUpperCase();
		switch(s) {
		case "AQUA":
			return Color.AQUA;
		case "BLACK":
			return Color.BLACK;
		case "BLUE":
			return Color.BLUE;
		case "FUCHSIA":
			return Color.FUCHSIA;
		case "GRAY":
			return Color.GRAY;
		case "GREEN":
			return Color.GREEN;
		case "LIME":
			return Color.LIME;
		case "MAROON":
			return Color.MAROON;
		case "NAVY":
			return Color.NAVY;
		case "OLIVE":
			return Color.OLIVE;
		case "ORANGE":
			return Color.ORANGE;
		case "PURPLE":
			return Color.PURPLE;
		case "RED":
			return Color.RED;
		case "SILVER":
			return Color.SILVER;
		case "TEAL":
			return Color.TEAL;
		case "WHITE":
			return Color.WHITE;
		case "YELLOW":
			return Color.YELLOW;
		default: 
			return null;
		}
	}

	public static Color[] values(){
		Color [] values = {
				Color.AQUA,
				Color.BLACK,
				Color.BLUE,
				Color.FUCHSIA,
				Color.GRAY,
				Color.GREEN,
				Color.LIME,
				Color.MAROON,
				Color.NAVY,
				Color.OLIVE,
				Color.ORANGE,
				Color.PURPLE,
				Color.RED,
				Color.SILVER,
				Color.TEAL,
				Color.WHITE,
				Color.YELLOW
		};
		return values;
	}

	public static Color getNext(Color color){
		if (AQUA.equals(color)) {
			return Color.BLACK;
		}
		else if(color.equals(Color.BLACK)){
			return Color.BLUE;
		}
		else if(color.equals(Color.BLUE)){
			return Color.FUCHSIA;
		}
		else if(color.equals(Color.FUCHSIA)){
			return Color.GRAY;
		}
		else if(color.equals(Color.GRAY)){
			return Color.GREEN;
		}
		else if(color.equals(Color.GREEN)){
			return Color.LIME;
		}
		else if(color.equals(Color.LIME)){
			return Color.MAROON;
		}
		else if(color.equals(Color.MAROON)){
			return Color.NAVY;
		}
		else if(color.equals(Color.NAVY)){
			return Color.OLIVE;
		}
		else if(color.equals(Color.OLIVE)){
			return Color.ORANGE;
		}
		else if(color.equals(Color.ORANGE)){
			return Color.PURPLE;
		}
		else if(color.equals(Color.PURPLE)){
			return Color.RED;
		}
		else if(color.equals(Color.RED)){
			return Color.SILVER;
		}
		else if(color.equals(Color.SILVER)){
			return Color.TEAL;
		}
		else if(color.equals(Color.TEAL)){
			return Color.WHITE;
		}
		else if(color.equals(Color.WHITE)){
			return Color.YELLOW;
		}
		else {
			return Color.AQUA;
		}
	}
}
