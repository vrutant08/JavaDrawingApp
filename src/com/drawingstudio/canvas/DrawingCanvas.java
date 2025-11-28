package com.drawingstudio.canvas;

import com.drawingstudio.shapes.ShapeBase;
import com.drawingstudio.manager.HistoryManager;
import com.drawingstudio.manager.FileManager;
import com.drawingstudio.utils.ShapeUtils;
import com.drawingstudio.utils.PointUtils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * AWT-based drawing canvas with double buffering
 */
public class DrawingCanvas extends Canvas {
    private BufferedImage drawingImage;
    private BufferedImage offscreenBuffer;
    private Graphics2D g2d;
    private Color currentColor = Color.BLACK;
    private int brushSize = 3;
    private String currentTool = "BRUSH";
    private Object parentApp;
    
    // Drawing state
    private boolean isDrawing = false;
    private Point startPoint, endPoint;
    private Point lastPoint;
    
    // Managers
    private HistoryManager historyManager;
    
    // Shape preview
    private boolean showPreview = false;
    
    // Shape storage
    private List<ShapeBase> shapes;
    
    public DrawingCanvas(Object parent) {
        this.parentApp = parent;
        setBackground(Color.WHITE);
        
        historyManager = new HistoryManager();
        shapes = new ArrayList<>();
        
        initializeDrawingSurface();
    }
    
    private void initializeDrawingSurface() {
        int width = 1000;
        int height = 700;
        
        drawingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        offscreenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        g2d = drawingImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(currentColor);
        g2d.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        setSize(width, height);
    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        // Use double buffering to eliminate flicker
        Graphics2D bufferG2d = offscreenBuffer.createGraphics();
        bufferG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw the buffered image to offscreen buffer
        bufferG2d.drawImage(drawingImage, 0, 0, null);
        
        // Draw all shapes on top
        for (ShapeBase shape : shapes) {
            shape.draw(bufferG2d);
        }
        
        // Draw shape preview
        if (showPreview && isDrawing && startPoint != null && endPoint != null) {
            drawPreview(bufferG2d);
        }
        
        // Dispose buffer graphics and draw final result to screen
        bufferG2d.dispose();
        g.drawImage(offscreenBuffer, 0, 0, null);
    }
    
    private void drawPreview(Graphics2D bufferG2d) {
        bufferG2d.setColor(currentColor);
        bufferG2d.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        switch (currentTool) {
            case "LINE":
                bufferG2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                break;
            case "RECTANGLE":
                int x = Math.min(startPoint.x, endPoint.x);
                int y = Math.min(startPoint.y, endPoint.y);
                int width = Math.abs(endPoint.x - startPoint.x);
                int height = Math.abs(endPoint.y - startPoint.y);
                bufferG2d.drawRect(x, y, width, height);
                break;
            case "OVAL":
                x = Math.min(startPoint.x, endPoint.x);
                y = Math.min(startPoint.y, endPoint.y);
                width = Math.abs(endPoint.x - startPoint.x);
                height = Math.abs(endPoint.y - startPoint.y);
                bufferG2d.drawOval(x, y, width, height);
                break;
            case "TRIANGLE":
                int[] xPoints = {startPoint.x, endPoint.x, startPoint.x + (startPoint.x - endPoint.x)};
                int[] yPoints = {startPoint.y, endPoint.y, endPoint.y};
                bufferG2d.drawPolygon(xPoints, yPoints, 3);
                break;
            case "DIAMOND":
                int centerX = (startPoint.x + endPoint.x) / 2;
                int centerY = (startPoint.y + endPoint.y) / 2;
                int halfWidth = Math.abs(endPoint.x - startPoint.x) / 2;
                int halfHeight = Math.abs(endPoint.y - startPoint.y) / 2;
                int[] diamondX = {centerX, centerX + halfWidth, centerX, centerX - halfWidth};
                int[] diamondY = {centerY - halfHeight, centerY, centerY + halfHeight, centerY};
                bufferG2d.drawPolygon(diamondX, diamondY, 4);
                break;
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
    
    // Mouse event handlers (called by event handler classes)
    public void handleMousePressed(java.awt.event.MouseEvent e) {
        isDrawing = true;
        startPoint = e.getPoint();
        lastPoint = e.getPoint();
        
        // Handle eraser for shapes
        if (currentTool.equals("ERASER")) {
            // Check if clicking on a shape to delete it
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (shapes.get(i).contains(startPoint)) {
                    saveStateForUndo();
                    shapes.remove(i);
                    repaint();
                    return;
                }
            }
            // If not clicking on shape, use brush eraser
            saveStateForUndo();
            setupBrushGraphics();
        } else if (currentTool.equals("BRUSH")) {
            saveStateForUndo();
            setupBrushGraphics();
        } else if (currentTool.equals("COLOR_PICKER")) {
            // Color picker will be handled in mouseClicked
        }
    }
    
    public void handleMouseDragged(java.awt.event.MouseEvent e) {
        if (!isDrawing) return;
        
        Point currentPoint = e.getPoint();
        
        switch (currentTool) {
            case "BRUSH":
            case "ERASER":
                drawBrushStroke(lastPoint, currentPoint);
                lastPoint = currentPoint;
                repaint();
                break;
                
            case "LINE":
            case "RECTANGLE":
            case "OVAL":
            case "TRIANGLE":
            case "DIAMOND":
                endPoint = currentPoint;
                updateShapePreview();
                break;
        }
    }
    
    public void handleMouseReleased(java.awt.event.MouseEvent e) {
        if (!isDrawing) return;
        
        isDrawing = false;
        endPoint = e.getPoint();
        
        // Create shape if it's a shape tool
        if (ShapeUtils.isValidShapeType(currentTool)) {
            saveStateForUndo();
            ShapeBase shape = ShapeUtils.createShape(currentTool, startPoint, endPoint, currentColor, brushSize);
            if (shape != null) {
                shapes.add(shape);
            }
        }
        
        clearPreview();
        repaint();
    }
    
    public void handleMouseClicked(java.awt.event.MouseEvent e) {
        if (currentTool.equals("COLOR_PICKER")) {
            Point p = e.getPoint();
            if (PointUtils.isWithinBounds(p, drawingImage.getWidth(), drawingImage.getHeight())) {
                // Get color from the composite view
                BufferedImage composite = createCompositeImage();
                int rgb = composite.getRGB(p.x, p.y);
                Color pickedColor = new Color(rgb);
                currentColor = pickedColor;
                
                // Notify parent app
                try {
                    java.lang.reflect.Method method = parentApp.getClass().getMethod("setPickedColor", Color.class);
                    method.invoke(parentApp, pickedColor);
                } catch (Exception ex) {
                    // Ignore
                }
                
                repaint();
            }
        }
    }
    
    public void handleMouseMoved(java.awt.event.MouseEvent e) {
        // Can be used for hover effects in future
    }
    
    // Drawing methods
    private void setupBrushGraphics() {
        g2d.setColor(currentTool.equals("ERASER") ? Color.WHITE : currentColor);
        g2d.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }
    
    private void drawBrushStroke(Point start, Point end) {
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }
    
    // Preview methods
    private void updateShapePreview() {
        showPreview = true;
        repaint();
    }
    
    private void clearPreview() {
        showPreview = false;
    }
    
    // History management
    private void saveStateForUndo() {
        historyManager.saveState(drawingImage, shapes);
    }
    
    public void undo() {
        HistoryManager.CanvasState lastState = historyManager.undo(drawingImage, shapes);
        if (lastState != null) {
            restoreState(lastState);
        }
    }
    
    public void redo() {
        HistoryManager.CanvasState nextState = historyManager.redo(drawingImage, shapes);
        if (nextState != null) {
            restoreState(nextState);
        }
    }
    
    private void restoreState(HistoryManager.CanvasState state) {
        g2d.dispose();
        drawingImage = new BufferedImage(state.getImage().getWidth(), state.getImage().getHeight(), BufferedImage.TYPE_INT_RGB);
        g2d = drawingImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(state.getImage(), 0, 0, null);
        
        shapes.clear();
        shapes.addAll(state.getShapes());
        
        repaint();
    }
    
    // Canvas management
    public void clearCanvas() {
        saveStateForUndo();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, drawingImage.getWidth(), drawingImage.getHeight());
        g2d.setColor(currentColor);
        shapes.clear();
        repaint();
    }
    
    public void saveToFile(File file) {
        FileManager.saveToFile(file, drawingImage, shapes);
    }
    
    public void loadFromFile(File file) {
        BufferedImage loadedImage = FileManager.loadFromFile(file);
        if (loadedImage != null) {
            saveStateForUndo();
            
            // Clear current drawing
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, drawingImage.getWidth(), drawingImage.getHeight());
            shapes.clear();
            
            // Draw loaded image
            g2d.drawImage(loadedImage, 0, 0, null);
            g2d.setColor(currentColor);
            
            repaint();
        }
    }
    
    private BufferedImage createCompositeImage() {
        BufferedImage composite = new BufferedImage(drawingImage.getWidth(), drawingImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = composite.createGraphics();
        g.drawImage(drawingImage, 0, 0, null);
        for (ShapeBase shape : shapes) {
            shape.draw(g);
        }
        g.dispose();
        return composite;
    }
    
    // Setters
    public void setCurrentColor(Color color) {
        this.currentColor = color;
        if (g2d != null) {
            g2d.setColor(color);
        }
    }
    
    public void setBrushSize(int size) {
        this.brushSize = size;
        if (g2d != null) {
            g2d.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
    }
    
    public void setCurrentTool(String tool) {
        this.currentTool = tool;
        clearPreview();
        repaint();
    }
}
