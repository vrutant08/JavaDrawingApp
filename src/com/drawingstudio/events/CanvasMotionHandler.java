package com.drawingstudio.events;

import com.drawingstudio.canvas.DrawingCanvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Handles mouse motion events (dragging and moving) for the canvas
 */
public class CanvasMotionHandler implements MouseMotionListener {
    private DrawingCanvas canvas;
    
    public CanvasMotionHandler(DrawingCanvas canvas) {
        this.canvas = canvas;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        canvas.handleMouseDragged(e);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        canvas.handleMouseMoved(e);
    }
}
