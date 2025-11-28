package com.drawingstudio.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Dialog manager for creating and showing custom dialogs
 * Handles color picker dialog and other modal dialogs
 */
public class DialogManager {
    
    /**
     * Show custom color picker dialog
     * @param parent Parent frame
     * @param currentColor Current selected color
     * @return Selected color, or null if cancelled
     */
    public static Color showColorPickerDialog(JFrame parent, Color currentColor) {
        JDialog colorDialog = new JDialog(parent, "Choose Custom Color", true);
        colorDialog.setLayout(new BorderLayout());
        colorDialog.setSize(450, 400);
        colorDialog.setLocationRelativeTo(parent);
        
        ColorPalettePanel palettePanel = new ColorPalettePanel();
        colorDialog.add(palettePanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        Color dialogBtnColor = new Color(52, 152, 219);
        
        for (JButton btn : new JButton[]{okBtn, cancelBtn}) {
            btn.setFont(new Font("Arial", Font.BOLD, 11));
            btn.setBackground(dialogBtnColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(80, 30));
        }
        
        final Color[] selectedColor = {currentColor};
        
        okBtn.addActionListener(e -> {
            selectedColor[0] = palettePanel.getSelectedColor();
            colorDialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> {
            selectedColor[0] = null;
            colorDialog.dispose();
        });
        
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        colorDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        colorDialog.setVisible(true);
        return selectedColor[0];
    }
}
