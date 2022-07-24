package com.ssomar.score.utils;

import org.bukkit.Color;

import static org.bukkit.Color.AQUA;

public class CustomColor {

    public static Color valueOf(String s) {

        s = s.toUpperCase();
        switch (s) {
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
            case "NO_COLOR":
                return Color.fromRGB(1, 2, 3);
            default:
                return null;
        }
    }

    public static String getName(Color color) {
        if (AQUA.equals(color)) {
            return "AQUA";
        } else if (color.equals(Color.BLACK)) {
            return "BLACK";
        } else if (color.equals(Color.BLUE)) {
            return "BLUE";
        } else if (color.equals(Color.FUCHSIA)) {
            return "FUCHSIA";
        } else if (color.equals(Color.GRAY)) {
            return "GRAY";
        } else if (color.equals(Color.GREEN)) {
            return "GREEN";
        } else if (color.equals(Color.LIME)) {
            return "LIME";
        } else if (color.equals(Color.MAROON)) {
            return "MAROON";
        } else if (color.equals(Color.NAVY)) {
            return "NAVY";
        } else if (color.equals(Color.OLIVE)) {
            return "OLIVE";
        } else if (color.equals(Color.ORANGE)) {
            return "ORANGE";
        } else if (color.equals(Color.PURPLE)) {
            return "PURPLE";
        } else if (color.equals(Color.RED)) {
            return "RED";
        } else if (color.equals(Color.SILVER)) {
            return "SILVER";
        } else if (color.equals(Color.TEAL)) {
            return "TEAL";
        } else if (color.equals(Color.WHITE)) {
            return "WHITE";
        } else if (color.equals(Color.YELLOW)) {
            return "YELLOW";
        } else if (color.equals(Color.fromRGB(1, 2, 3))) {
            return "NO_COLOR";
        }
        return "AQUA";
    }

    public static Color[] values() {
        Color[] values = {
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
                Color.YELLOW,
                Color.fromRGB(1, 2, 3)
        };
        return values;
    }

    public static Color getNext(Color color) {
        if (AQUA.equals(color)) {
            return Color.BLACK;
        } else if (color.equals(Color.BLACK)) {
            return Color.BLUE;
        } else if (color.equals(Color.BLUE)) {
            return Color.FUCHSIA;
        } else if (color.equals(Color.FUCHSIA)) {
            return Color.GRAY;
        } else if (color.equals(Color.GRAY)) {
            return Color.GREEN;
        } else if (color.equals(Color.GREEN)) {
            return Color.LIME;
        } else if (color.equals(Color.LIME)) {
            return Color.MAROON;
        } else if (color.equals(Color.MAROON)) {
            return Color.NAVY;
        } else if (color.equals(Color.NAVY)) {
            return Color.OLIVE;
        } else if (color.equals(Color.OLIVE)) {
            return Color.ORANGE;
        } else if (color.equals(Color.ORANGE)) {
            return Color.PURPLE;
        } else if (color.equals(Color.PURPLE)) {
            return Color.RED;
        } else if (color.equals(Color.RED)) {
            return Color.SILVER;
        } else if (color.equals(Color.SILVER)) {
            return Color.TEAL;
        } else if (color.equals(Color.TEAL)) {
            return Color.WHITE;
        } else if (color.equals(Color.WHITE)) {
            return Color.YELLOW;
        } else if (color.equals(Color.YELLOW)) {
            return Color.fromRGB(1, 2, 3);
        } else {
            return Color.AQUA;
        }
    }
}
