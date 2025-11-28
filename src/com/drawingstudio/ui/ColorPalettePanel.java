package com.drawingstudio.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Custom color palette panel with HSB gradient picker
 * Allows users to select custom colors visually
 */
public class ColorPalettePanel extends JPanel {
    private Color selectedColor = Color.BLACK;
    private Canvas paletteCanvas;
    private Canvas previewCanvas;
    private JLabel rgbLabel;
    
    public ColorPalettePanel() {
        setLayout(new BorderLayout());
        
        paletteCanvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        float hue = (float) x / width;
                        float saturation = 1.0f;
                        float brightness = 1.0f - (float) y / height;
                        Color c = Color.getHSBColor(hue, saturation, brightness);
                        g.setColor(c);
                        g.fillRect(x, y, 1, 1);
                    }
                }
            }
        };
        paletteCanvas.setSize(350, 250);
        
        paletteCanvas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectColorAt(e.getX(), e.getY());
            }
        });
        
        paletteCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                selectColorAt(e.getX(), e.getY());
            }
        });
        
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewCanvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.setColor(selectedColor);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        previewCanvas.setSize(80, 80);
        
        rgbLabel = new JLabel("RGB: 0, 0, 0");
        rgbLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        previewPanel.add(new JLabel("Selected Color:"), BorderLayout.NORTH);
        previewPanel.add(previewCanvas, BorderLayout.CENTER);
        previewPanel.add(rgbLabel, BorderLayout.SOUTH);
        
        add(paletteCanvas, BorderLayout.CENTER);
        add(previewPanel, BorderLayout.EAST);
    }
    
    private void selectColorAt(int x, int y) {
        int width = paletteCanvas.getWidth();
        int height = paletteCanvas.getHeight();
        
        if (x >= 0 && x < width && y >= 0 && y < height) {
            float hue = (float) x / width;
            float saturation = 1.0f;
            float brightness = 1.0f - (float) y / height;
            selectedColor = Color.getHSBColor(hue, saturation, brightness);
            
            previewCanvas.repaint();
            rgbLabel.setText("RGB: " + selectedColor.getRed() + ", " + 
                           selectedColor.getGreen() + ", " + selectedColor.getBlue());
        }
    }
    
    public Color getSelectedColor() {
        return selectedColor;
    }
}
