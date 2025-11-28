package com.drawingstudio.app;

import com.drawingstudio.canvas.DrawingCanvas;
import com.drawingstudio.events.CanvasMouseHandler;
import com.drawingstudio.events.CanvasMotionHandler;
import com.drawingstudio.manager.FileDialogManager;
import com.drawingstudio.ui.*;
import com.drawingstudio.utils.ColorUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 * Main application class for Simple Drawing Studio
 * Minimal class that only handles application initialization and event coordination
 * All heavy lifting is delegated to respective packages
 */
public class SimpleDrawingApp extends JFrame implements ActionListener {
    private DrawingCanvas canvas;
    private Color currentColor = Color.BLACK;
    private int brushSize = 3;
    private String currentTool = "BRUSH";
    
    // UI Components
    private JButton lineBtn, rectBtn, ovalBtn, triangleBtn, diamondBtn;
    private JButton clearBtn, undoBtn, redoBtn, saveBtn, loadBtn, colorPickerBtn, customColorBtn;
    private Choice colorChoice, brushChoice, toolChoice;
    private Label statusLabel;
    private Canvas colorPreviewBox;
    
    public SimpleDrawingApp() {
        setTitle("Simple Drawing Studio - AWT Version");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        setVisible(true);
    }
    
    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        canvas = new DrawingCanvas(this);
        
        // Create buttons using RoundedButton
        lineBtn = new RoundedButton("Line", new Color(41, 128, 185));
        rectBtn = new RoundedButton("Rectangle", new Color(41, 128, 185));
        ovalBtn = new RoundedButton("Oval", new Color(41, 128, 185));
        triangleBtn = new RoundedButton("Triangle", new Color(41, 128, 185));
        diamondBtn = new RoundedButton("Diamond", new Color(41, 128, 185));
        
        clearBtn = new RoundedButton("Clear", new Color(192, 57, 43));
        undoBtn = new RoundedButton("Undo", new Color(192, 57, 43));
        redoBtn = new RoundedButton("Redo", new Color(192, 57, 43));
        saveBtn = new RoundedButton("Save", new Color(192, 57, 43));
        loadBtn = new RoundedButton("Load", new Color(192, 57, 43));
        
        colorPickerBtn = new RoundedButton("Color Picker", new Color(39, 174, 96));
        customColorBtn = new RoundedButton("Custom Color...", new Color(39, 174, 96));
        
        // Apply styling using ButtonStyler
        ButtonStyler.styleShapeButtons(lineBtn, rectBtn, ovalBtn, triangleBtn, diamondBtn);
        ButtonStyler.styleActionButtons(clearBtn, undoBtn, redoBtn, saveBtn, loadBtn);
        ButtonStyler.styleColorButtons(colorPickerBtn, customColorBtn);
        
        // Create choice components
        colorChoice = new Choice();
        String[] colors = {"Black", "Red", "Green", "Blue", "Yellow", "Orange", "Pink", "Cyan", "Magenta", "White", "Custom..."};
        for (String color : colors) {
            colorChoice.add(color);
        }
        
        brushChoice = new Choice();
        for (int i = 1; i <= 10; i++) {
            brushChoice.add(String.valueOf(i));
        }
        brushChoice.select("3");
        
        toolChoice = new Choice();
        toolChoice.add("Brush");
        toolChoice.add("Eraser");
        toolChoice.add("Color Picker");
        
        // Color preview box
        colorPreviewBox = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.setColor(currentColor);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.GRAY);
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        colorPreviewBox.setPreferredSize(new Dimension(60, 25));
        colorPreviewBox.setMinimumSize(new Dimension(60, 25));
        colorPreviewBox.setMaximumSize(new Dimension(60, 25));
        
        statusLabel = new Label("Tool: " + currentTool + " | Color: Black | Brush Size: " + brushSize);
    }
    
    /**
     * Setup the layout using ToolbarFactory
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create panels using ToolbarFactory
        JPanel propertiesPanel = ToolbarFactory.createPropertiesPanel(
            colorChoice, colorPreviewBox, customColorBtn, colorPickerBtn,
            brushChoice, toolChoice, currentColor
        );
        
        JPanel shapesPanel = ToolbarFactory.createShapesPanel(
            lineBtn, rectBtn, ovalBtn, triangleBtn, diamondBtn
        );
        
        JPanel actionsPanel = ToolbarFactory.createActionsPanel(
            clearBtn, undoBtn, redoBtn, saveBtn, loadBtn
        );
        
        JPanel toolPanel = ToolbarFactory.createMainToolbar(
            propertiesPanel, shapesPanel, actionsPanel
        );
        
        add(toolPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Add action listeners to buttons
        lineBtn.addActionListener(this);
        rectBtn.addActionListener(this);
        ovalBtn.addActionListener(this);
        triangleBtn.addActionListener(this);
        diamondBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        undoBtn.addActionListener(this);
        redoBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        colorPickerBtn.addActionListener(this);
        customColorBtn.addActionListener(this);
        
        // Add item listeners for choices
        colorChoice.addItemListener(e -> handleColorChange());
        brushChoice.addItemListener(e -> handleBrushSizeChange());
        toolChoice.addItemListener(e -> handleToolChange());
        
        // Add mouse event handlers to canvas
        canvas.addMouseListener(new CanvasMouseHandler(canvas));
        canvas.addMouseMotionListener(new CanvasMotionHandler(canvas));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Line":
                setTool("LINE");
                break;
            case "Rectangle":
                setTool("RECTANGLE");
                break;
            case "Oval":
                setTool("OVAL");
                break;
            case "Triangle":
                setTool("TRIANGLE");
                break;
            case "Diamond":
                setTool("DIAMOND");
                break;
            case "Color Picker":
                setTool("COLOR_PICKER");
                statusLabel.setText("Color Picker: Click on canvas to pick a color | Tool: " + currentTool);
                break;
            case "Custom Color...":
                handleCustomColor();
                break;
            case "Clear":
                canvas.clearCanvas();
                break;
            case "Undo":
                canvas.undo();
                break;
            case "Redo":
                canvas.redo();
                break;
            case "Save":
                handleSave();
                break;
            case "Load":
                handleLoad();
                break;
        }
    }
    
    /**
     * Set current drawing tool
     */
    private void setTool(String tool) {
        currentTool = tool;
        canvas.setCurrentTool(currentTool);
        toolChoice.select(0); // Reset to Brush in dropdown
        updateStatusLabel();
    }
    
    /**
     * Handle color change from dropdown
     */
    private void handleColorChange() {
        String colorName = colorChoice.getSelectedItem();
        if (colorName.equals("Custom...")) {
            handleCustomColor();
        } else {
            currentColor = ColorUtils.getColorFromName(colorName);
            canvas.setCurrentColor(currentColor);
            colorPreviewBox.repaint();
            updateStatusLabel();
        }
    }
    
    /**
     * Handle brush size change
     */
    private void handleBrushSizeChange() {
        brushSize = Integer.parseInt(brushChoice.getSelectedItem());
        canvas.setBrushSize(brushSize);
        updateStatusLabel();
    }
    
    /**
     * Handle tool change from dropdown
     */
    private void handleToolChange() {
        String selectedTool = toolChoice.getSelectedItem();
        switch (selectedTool) {
            case "Brush":
                currentTool = "BRUSH";
                break;
            case "Eraser":
                currentTool = "ERASER";
                break;
            case "Color Picker":
                currentTool = "COLOR_PICKER";
                break;
        }
        canvas.setCurrentTool(currentTool);
        updateStatusLabel();
    }
    
    /**
     * Handle custom color selection using DialogManager
     */
    private void handleCustomColor() {
        Color newColor = DialogManager.showColorPickerDialog(this, currentColor);
        if (newColor != null) {
            currentColor = newColor;
            canvas.setCurrentColor(currentColor);
            colorPreviewBox.repaint();
            updateStatusLabel();
        }
    }
    
    /**
     * Handle save operation using FileDialogManager
     */
    private void handleSave() {
        File file = FileDialogManager.showSaveDialog(this);
        if (file != null) {
            canvas.saveToFile(file);
        }
    }
    
    /**
     * Handle load operation using FileDialogManager
     */
    private void handleLoad() {
        File file = FileDialogManager.showLoadDialog(this);
        if (file != null) {
            canvas.loadFromFile(file);
        }
    }
    
    /**
     * Update status label with current tool, color, and brush size
     */
    private void updateStatusLabel() {
        statusLabel.setText("Tool: " + currentTool + " | Color: " + 
            ColorUtils.getColorName(currentColor) + " | Brush Size: " + brushSize);
    }
    
    /**
     * Called by canvas when color is picked
     */
    public void setPickedColor(Color color) {
        this.currentColor = color;
        canvas.setCurrentColor(color);
        colorPreviewBox.repaint();
        
        String colorInfo = ColorUtils.formatRGB(color);
        statusLabel.setText(colorInfo + " | Tool: " + currentTool + " | Brush Size: " + brushSize);
        
        // Update color choice dropdown
        String colorName = ColorUtils.getColorName(color);
        for (int i = 0; i < colorChoice.getItemCount(); i++) {
            if (colorChoice.getItem(i).contains(colorName)) {
                colorChoice.select(i);
                return;
            }
        }
        colorChoice.select(colorChoice.getItemCount() - 1);
    }
    
    public static void main(String[] args) {
        new SimpleDrawingApp();
    }
}
