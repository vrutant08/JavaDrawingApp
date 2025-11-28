package com.drawingstudio.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for styling buttons consistently
 * Handles button appearance and styling across the application
 */
public class ButtonStyler {
    
    /**
     * Style shape buttons (blue theme)
     */
    public static void styleShapeButtons(JButton... buttons) {
        Color shapeColor = new Color(52, 152, 219);
        
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBackground(shapeColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(100, 35));
        }
    }
    
    /**
     * Style action buttons (red theme)
     */
    public static void styleActionButtons(JButton... buttons) {
        Color actionColor = new Color(231, 76, 60);
        
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBackground(actionColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(90, 35));
        }
    }
    
    /**
     * Style color buttons (green theme)
     */
    public static void styleColorButtons(JButton... buttons) {
        Color colorButtonColor = new Color(46, 204, 113);
        
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBackground(colorButtonColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(80, 28));
        }
    }
    
    /**
     * Create a styled label for toolbar
     */
    public static Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(40, 40, 40));
        return label;
    }
}
