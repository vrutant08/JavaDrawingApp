package com.drawingstudio.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JButton with rounded corners and customizable border color
 */
public class RoundedButton extends JButton {
    private static final int ARC = 20;
    protected Color borderColor = Color.BLACK;
    
    public RoundedButton(String text) {
        super(text);
        initializeButton();
    }
    
    public RoundedButton(String text, Color borderColor) {
        super(text);
        this.borderColor = borderColor;
        initializeButton();
    }
    
    private void initializeButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorderPainted(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill rounded rectangle
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
        
        // Draw border with custom color
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);
        
        g2.dispose();
        super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to prevent default border painting
    }
}
