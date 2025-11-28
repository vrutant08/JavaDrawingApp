package com.drawingstudio.shapes;

import java.awt.*;

/**
 * Represents a rectangle shape
 */
public class RectShape extends ShapeBase {
    
    public RectShape(Point start, Point end, Color color, int strokeWidth) {
        super(start, end, color, strokeWidth);
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);
        
        g2d.drawRect(x, y, width, height);
    }
    
    @Override
    public String getType() {
        return "RECTANGLE";
    }
}
