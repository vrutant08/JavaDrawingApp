package com.drawingstudio.events;

import com.drawingstudio.canvas.DrawingCanvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse click and press/release events for the canvas
 */
public class CanvasMouseHandler implements MouseListener {
    private DrawingCanvas canvas;
    
    public CanvasMouseHandler(DrawingCanvas canvas) {
        this.canvas = canvas;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        canvas.handleMousePressed(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.handleMouseReleased(e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        canvas.handleMouseClicked(e);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }
}
