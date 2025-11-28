package com.drawingstudio.manager;

import com.drawingstudio.shapes.ShapeBase;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages undo/redo history for the canvas
 * Stores both the drawing image and shapes list
 */
public class HistoryManager {
    
    /**
     * Represents a saved state of the canvas
     */
    public static class CanvasState {
        private BufferedImage image;
        private List<ShapeBase> shapes;
        
        public CanvasState(BufferedImage img, List<ShapeBase> shapeList) {
            // Deep copy the image
            this.image = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            Graphics2D g = this.image.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            
            // Deep copy the shapes list
            this.shapes = new ArrayList<>(shapeList);
        }
        
        public BufferedImage getImage() { 
            return image; 
        }
        
        public List<ShapeBase> getShapes() { 
            return new ArrayList<>(shapes); 
        }
    }
    
    private List<CanvasState> undoHistory;
    private List<CanvasState> redoHistory;
    private static final int MAX_UNDO_STEPS = 10;
    
    public HistoryManager() {
        undoHistory = new ArrayList<>();
        redoHistory = new ArrayList<>();
    }
    
    /**
     * Save current state for undo functionality
     */
    public void saveState(BufferedImage image, List<ShapeBase> shapes) {
        if (undoHistory.size() >= MAX_UNDO_STEPS) {
            undoHistory.remove(0);
        }
        
        CanvasState currentState = new CanvasState(image, shapes);
        undoHistory.add(currentState);
        
        // Clear redo history when new action is performed
        redoHistory.clear();
    }
    
    /**
     * Undo last action
     * @return Previous state, or null if no undo available
     */
    public CanvasState undo(BufferedImage currentImage, List<ShapeBase> currentShapes) {
        if (!undoHistory.isEmpty()) {
            // Save current state to redo history
            CanvasState currentState = new CanvasState(currentImage, currentShapes);
            redoHistory.add(currentState);
            
            // Return previous state
            return undoHistory.remove(undoHistory.size() - 1);
        }
        return null;
    }
    
    /**
     * Redo previously undone action
     * @return Next state, or null if no redo available
     */
    public CanvasState redo(BufferedImage currentImage, List<ShapeBase> currentShapes) {
        if (!redoHistory.isEmpty()) {
            // Save current state to undo history
            CanvasState currentState = new CanvasState(currentImage, currentShapes);
            undoHistory.add(currentState);
            
            // Return next state
            return redoHistory.remove(redoHistory.size() - 1);
        }
        return null;
    }
    
    /**
     * Check if undo is available
     */
    public boolean canUndo() {
        return !undoHistory.isEmpty();
    }
    
    /**
     * Check if redo is available
     */
    public boolean canRedo() {
        return !redoHistory.isEmpty();
    }
    
    /**
     * Clear all history
     */
    public void clear() {
        undoHistory.clear();
        redoHistory.clear();
    }
}
