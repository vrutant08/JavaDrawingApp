package com.drawingstudio.shapes;

import java.awt.*;

/**
 * Represents a triangle shape
 */
public class TriangleShape extends ShapeBase {
    
    public TriangleShape(Point start, Point end, Color color, int strokeWidth) {
        super(start, end, color, strokeWidth);
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        int[] xPoints = {startPoint.x, endPoint.x, startPoint.x + (startPoint.x - endPoint.x)};
        int[] yPoints = {startPoint.y, endPoint.y, endPoint.y};
        
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
    
    @Override
    public String getType() {
        return "TRIANGLE";
    }
}
