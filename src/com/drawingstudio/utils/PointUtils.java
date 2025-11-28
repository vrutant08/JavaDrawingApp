package com.drawingstudio.utils;

import java.awt.Point;

/**
 * Utility class for point operations
 */
public class PointUtils {
    
    /**
     * Calculate distance between two points
     */
    public static double distance(Point p1, Point p2) {
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Calculate midpoint between two points
     */
    public static Point midpoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
    
    /**
     * Check if point is within bounds
     */
    public static boolean isWithinBounds(Point p, int width, int height) {
        return p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
    }
}
