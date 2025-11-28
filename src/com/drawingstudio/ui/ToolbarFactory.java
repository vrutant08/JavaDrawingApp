package com.drawingstudio.ui;

import com.drawingstudio.utils.ColorUtils;
import java.awt.*;
import javax.swing.*;

/**
 * Factory class for creating and managing toolbar panels
 * Organizes UI components into logical sections
 */
public class ToolbarFactory {
    
    /**
     * Create the properties panel (color, brush size, tool selection)
     */
    public static JPanel createPropertiesPanel(
            Choice colorChoice, 
            Canvas colorPreviewBox, 
            JButton customColorBtn, 
            JButton colorPickerBtn,
            Choice brushChoice,
            Choice toolChoice,
            Color currentColor) {
        
        Color darkGray = new Color(40, 40, 40);
        
        JPanel propertiesPanel = new JPanel(new BorderLayout());
        propertiesPanel.setBackground(darkGray);
        
        JPanel propertiesHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        propertiesHeader.setBackground(darkGray);
        propertiesHeader.add(ButtonStyler.createStyledLabel("Properties:"));
        
        JPanel propertiesContent = new JPanel();
        propertiesContent.setLayout(new BoxLayout(propertiesContent, BoxLayout.Y_AXIS));
        propertiesContent.setBackground(darkGray);
        
        // Row 1: Green buttons (Custom Color and Color Picker)
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        buttonRow.setBackground(darkGray);
        buttonRow.add(customColorBtn);
        buttonRow.add(colorPickerBtn);
        propertiesContent.add(buttonRow);
        
        // Row 2: Color dropdown and preview
        JPanel colorRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        colorRow.setBackground(darkGray);
        colorRow.add(ButtonStyler.createStyledLabel("Color:"));
        colorRow.add(colorChoice);
        colorPreviewBox.setPreferredSize(new Dimension(60, 25));
        colorPreviewBox.setMinimumSize(new Dimension(60, 25));
        colorPreviewBox.setMaximumSize(new Dimension(60, 25));
        colorRow.add(colorPreviewBox);
        propertiesContent.add(colorRow);
        
        // Row 3: Brush size and Tool dropdowns
        JPanel toolRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        toolRow.setBackground(darkGray);
        toolRow.add(ButtonStyler.createStyledLabel("Brush Size:"));
        toolRow.add(brushChoice);
        toolRow.add(ButtonStyler.createStyledLabel("Tool:"));
        toolRow.add(toolChoice);
        propertiesContent.add(toolRow);
        
        propertiesPanel.add(propertiesHeader, BorderLayout.NORTH);
        propertiesPanel.add(propertiesContent, BorderLayout.CENTER);
        
        return propertiesPanel;
    }
    
    /**
     * Create the shapes panel (shape drawing buttons)
     */
    public static JPanel createShapesPanel(
            JButton lineBtn,
            JButton rectBtn,
            JButton ovalBtn,
            JButton triangleBtn,
            JButton diamondBtn) {
        
        Color darkGray = new Color(40, 40, 40);
        
        JPanel shapesPanel = new JPanel(new BorderLayout());
        shapesPanel.setBackground(darkGray);
        
        JPanel shapesHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        shapesHeader.setBackground(darkGray);
        shapesHeader.add(ButtonStyler.createStyledLabel("Shapes:"));
        
        JPanel shapesContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        shapesContent.setBackground(darkGray);
        shapesContent.add(lineBtn);
        shapesContent.add(rectBtn);
        shapesContent.add(ovalBtn);
        shapesContent.add(triangleBtn);
        shapesContent.add(diamondBtn);
        
        shapesPanel.add(shapesHeader, BorderLayout.NORTH);
        shapesPanel.add(shapesContent, BorderLayout.CENTER);
        
        return shapesPanel;
    }
    
    /**
     * Create the actions panel (clear, undo, redo, save, load)
     */
    public static JPanel createActionsPanel(
            JButton clearBtn,
            JButton undoBtn,
            JButton redoBtn,
            JButton saveBtn,
            JButton loadBtn) {
        
        Color darkGray = new Color(40, 40, 40);
        
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBackground(darkGray);
        
        JPanel actionHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        actionHeader.setBackground(darkGray);
        actionHeader.add(ButtonStyler.createStyledLabel("Actions:"));
        
        JPanel actionContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        actionContent.setBackground(darkGray);
        actionContent.add(clearBtn);
        actionContent.add(undoBtn);
        actionContent.add(redoBtn);
        actionContent.add(saveBtn);
        actionContent.add(loadBtn);
        
        actionPanel.add(actionHeader, BorderLayout.NORTH);
        actionPanel.add(actionContent, BorderLayout.CENTER);
        
        return actionPanel;
    }
    
    /**
     * Create the main toolbar panel containing all sections
     */
    public static JPanel createMainToolbar(
            JPanel propertiesPanel,
            JPanel shapesPanel,
            JPanel actionsPanel) {
        
        Color darkGray = new Color(40, 40, 40);
        
        JPanel toolPanel = new JPanel(new GridLayout(1, 3, 10, 5));
        toolPanel.setPreferredSize(new Dimension(1000, 120));
        toolPanel.setBackground(darkGray);
        
        toolPanel.add(propertiesPanel);
        toolPanel.add(shapesPanel);
        toolPanel.add(actionsPanel);
        
        return toolPanel;
    }
}
