package com.drawingstudio.shapes;

import java.awt.*;

/**
 * Abstract base class for all drawable shapes
 * Defines common properties and methods for shapes
 */
public abstract class ShapeBase {
    protected Point startPoint;
    protected Point endPoint;
    protected Color color;
    protected int strokeWidth;
    
    public ShapeBase(Point start, Point end, Color color, int strokeWidth) {
        this.startPoint = new Point(start);
        this.endPoint = new Point(end);
        this.color = color;
        this.strokeWidth = strokeWidth;
    }
    
    /**
     * Draw the shape on the given graphics context
     */
    public abstract void draw(Graphics2D g2d);
    
    /**
     * Check if a point is within this shape's bounds
     * Used for selection and eraser functionality
     */
    public boolean contains(Point p) {
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);
        
        // Add padding for easier selection
        int padding = strokeWidth + 5;
        return p.x >= x - padding && p.x <= x + width + padding &&
               p.y >= y - padding && p.y <= y + height + padding;
    }
    
    // Getters
    public Point getStartPoint() { return new Point(startPoint); }
    public Point getEndPoint() { return new Point(endPoint); }
    public Color getColor() { return color; }
    public int getStrokeWidth() { return strokeWidth; }
    
    /**
     * Get the type of this shape as a string
     */
    public abstract String getType();
}
