package com.drawingstudio.manager;

import com.drawingstudio.shapes.ShapeBase;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Handles file operations for saving and loading drawings
 */
public class FileManager {
    
    /**
     * Save drawing to file
     * @param file File to save to
     * @param drawingImage The drawing image to save
     * @param shapes List of shapes to render on top
     * @return true if successful, false otherwise
     */
    public static boolean saveToFile(File file, BufferedImage drawingImage, List<ShapeBase> shapes) {
        try {
            // Create composite image with shapes
            BufferedImage composite = new BufferedImage(
                drawingImage.getWidth(), 
                drawingImage.getHeight(), 
                BufferedImage.TYPE_INT_RGB
            );
            
            Graphics2D compG2d = composite.createGraphics();
            compG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw background
            compG2d.drawImage(drawingImage, 0, 0, null);
            
            // Draw shapes on top
            for (ShapeBase shape : shapes) {
                shape.draw(compG2d);
            }
            
            compG2d.dispose();
            
            // Ensure .png extension
            String filename = file.getName().toLowerCase();
            if (!filename.endsWith(".png")) {
                file = new File(file.getParent(), file.getName() + ".png");
            }
            
            // Save as PNG
            ImageIO.write(composite, "png", file);
            System.out.println("Drawing saved to: " + file.getAbsolutePath());
            return true;
            
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load drawing from file
     * @param file File to load from
     * @return Loaded image, or null if failed
     */
    public static BufferedImage loadFromFile(File file) {
        try {
            BufferedImage loadedImage = ImageIO.read(file);
            if (loadedImage != null) {
                System.out.println("Image loaded from: " + file.getAbsolutePath());
                return loadedImage;
            }
            return null;
            
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            return null;
        }
    }
}
