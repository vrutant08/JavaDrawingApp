package com.drawingstudio.shapes;

import java.awt.*;

/**
 * Represents a line shape
 */
public class LineShape extends ShapeBase {
    
    public LineShape(Point start, Point end, Color color, int strokeWidth) {
        super(start, end, color, strokeWidth);
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }
    
    @Override
    public String getType() {
        return "LINE";
    }
}
