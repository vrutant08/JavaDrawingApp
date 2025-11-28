package com.drawingstudio.utils;

import java.awt.Color;

/**
 * Utility class for color operations
 */
public class ColorUtils {
    
    /**
     * Get Color from color name string
     */
    public static Color getColorFromName(String name) {
        switch (name.toLowerCase()) {
            case "black": return Color.BLACK;
            case "red": return Color.RED;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "orange": return Color.ORANGE;
            case "pink": return Color.PINK;
            case "cyan": return Color.CYAN;
            case "magenta": return Color.MAGENTA;
            case "white": return Color.WHITE;
            default: return Color.BLACK;
        }
    }
    
    /**
     * Get color name from Color object
     */
    public static String getColorName(Color color) {
        Color[] colors = {
            Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, 
            Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, 
            Color.MAGENTA, Color.WHITE
        };
        String[] names = {
            "Black", "Red", "Green", "Blue", 
            "Yellow", "Orange", "Pink", "Cyan", 
            "Magenta", "White"
        };
        
        for (int i = 0; i < colors.length; i++) {
            if (color.equals(colors[i])) {
                return names[i];
            }
        }
        return "Black";
    }
    
    /**
     * Format color as RGB string
     */
    public static String formatRGB(Color color) {
        return String.format("RGB(%d, %d, %d)", 
            color.getRed(), color.getGreen(), color.getBlue());
    }
}
