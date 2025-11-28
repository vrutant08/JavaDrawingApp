package com.drawingstudio.utils;

import com.drawingstudio.shapes.*;
import java.awt.Color;
import java.awt.Point;

/**
 * Factory class for creating shape objects
 */
public class ShapeUtils {
    
    /**
     * Create a shape based on type string
     */
    public static ShapeBase createShape(String type, Point start, Point end, Color color, int strokeWidth) {
        switch (type.toUpperCase()) {
            case "LINE":
                return new LineShape(start, end, color, strokeWidth);
            case "RECTANGLE":
                return new RectShape(start, end, color, strokeWidth);
            case "OVAL":
                return new OvalShape(start, end, color, strokeWidth);
            case "TRIANGLE":
                return new TriangleShape(start, end, color, strokeWidth);
            case "DIAMOND":
                return new DiamondShape(start, end, color, strokeWidth);
            default:
                return null;
        }
    }
    
    /**
     * Check if shape type is valid
     */
    public static boolean isValidShapeType(String type) {
        String upperType = type.toUpperCase();
        return upperType.equals("LINE") || 
               upperType.equals("RECTANGLE") || 
               upperType.equals("OVAL") || 
               upperType.equals("TRIANGLE") || 
               upperType.equals("DIAMOND");
    }
}
