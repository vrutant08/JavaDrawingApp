import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * AWT-Based Drawing Application
 * A simplified version of the advanced drawing studio using AWT instead of Swing
 * Features: GUI canvas, basic shapes, brush drawing, colors, save/load functionality
 */
public class SimpleDrawingApp extends Frame implements ActionListener, MouseListener, MouseMotionListener {
    private DrawingCanvas canvas;
    private Color currentColor = Color.BLACK;
    private int brushSize = 3;
    private String currentTool = "BRUSH";
    private String currentBrushType = "NORMAL";
    
    // UI Components
    private Button lineBtn, rectBtn, ovalBtn, triangleBtn, diamondBtn;
    private Button clearBtn, undoBtn, redoBtn, saveBtn, loadBtn, colorPickerBtn, customColorBtn;
    private Choice colorChoice, brushChoice, toolChoice;
    private Panel toolPanel, canvasPanel;
    private Label statusLabel;
    
    // Drawing state
    private boolean isDrawing = false;
    private Point startPoint, lastPoint;
    
    public SimpleDrawingApp() {
        setTitle("Simple Drawing Studio - AWT Version");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        // Handle window closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        setVisible(true);
    }
    
    private void initializeComponents() {
        canvas = new DrawingCanvas(this);
        
        // Create buttons
        lineBtn = new Button("Line");
        rectBtn = new Button("Rectangle");
        ovalBtn = new Button("Oval");
        triangleBtn = new Button("Triangle");
        diamondBtn = new Button("Diamond");
        clearBtn = new Button("Clear");
        undoBtn = new Button("Undo");
        redoBtn = new Button("Redo");
        saveBtn = new Button("Save");
        loadBtn = new Button("Load");
        colorPickerBtn = new Button("Color Picker");
        customColorBtn = new Button("Custom Color...");
        
        // Create choice components with better color labels
        colorChoice = new Choice();
        colorChoice.add("Black");
        colorChoice.add("Red");
        colorChoice.add("Green");
        colorChoice.add("Blue");
        colorChoice.add("Yellow");
        colorChoice.add("Orange");
        colorChoice.add("Pink");
        colorChoice.add("Cyan");
        colorChoice.add("Magenta");
        colorChoice.add("White");
        colorChoice.add("Custom...");
        
        brushChoice = new Choice();
        for (int i = 1; i <= 10; i++) {
            brushChoice.add(String.valueOf(i));
        }
        brushChoice.select("3");
        
        toolChoice = new Choice();
        toolChoice.add("Brush");
        toolChoice.add("Eraser");
        toolChoice.add("Color Picker");
        
        // Create panels
        toolPanel = new Panel(new GridLayout(2, 1)); // Use GridLayout to stack rows
        canvasPanel = new Panel(new BorderLayout());
        
        // Status label
        statusLabel = new Label("Tool: " + currentTool + " | Color: Black | Brush Size: " + brushSize);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create sub-panels for better organization
        Panel propertiesPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel shapesPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel actionPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        
        // Properties Panel
        propertiesPanel.add(new Label("Color:"));
        propertiesPanel.add(colorChoice);
        propertiesPanel.add(customColorBtn);
        propertiesPanel.add(colorPickerBtn);
        propertiesPanel.add(new Label("Brush Size:"));
        propertiesPanel.add(brushChoice);
        propertiesPanel.add(new Label("Tool:"));
        propertiesPanel.add(toolChoice);
        
        // Shapes Panel
        shapesPanel.add(new Label("Shapes:"));
        shapesPanel.add(lineBtn);
        shapesPanel.add(rectBtn);
        shapesPanel.add(ovalBtn);
        shapesPanel.add(triangleBtn);
        shapesPanel.add(diamondBtn);
        
        // Action Panel
        actionPanel.add(new Label("Actions:"));
        actionPanel.add(clearBtn);
        actionPanel.add(undoBtn);
        actionPanel.add(redoBtn);
        actionPanel.add(saveBtn);
        actionPanel.add(loadBtn);
        
        // Combine panels
        // We'll put properties in the top row, and shapes + actions in the bottom row
        Panel topRow = new Panel(new FlowLayout(FlowLayout.LEFT));
        topRow.add(propertiesPanel);
        
        Panel bottomRow = new Panel(new FlowLayout(FlowLayout.LEFT));
        bottomRow.add(shapesPanel);
        bottomRow.add(actionPanel);
        
        toolPanel.add(topRow);
        toolPanel.add(bottomRow);
        
        // Add canvas to canvas panel
        canvasPanel.add(canvas, BorderLayout.CENTER);
        
        // Add panels to frame
        add(toolPanel, BorderLayout.NORTH);
        add(canvasPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        // Add action listeners
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
        colorChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String colorName = colorChoice.getSelectedItem();
                if (colorName.equals("Custom...")) {
                    openCustomColorDialog();
                } else {
                    currentColor = getColorFromName(colorName);
                    canvas.setCurrentColor(currentColor);
                    updateStatusLabel();
                }
            }
        });
        
        brushChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                brushSize = Integer.parseInt(brushChoice.getSelectedItem());
                canvas.setBrushSize(brushSize);
                updateStatusLabel();
            }
        });
        
        toolChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String selectedTool = toolChoice.getSelectedItem();
                if (selectedTool.equals("Brush")) {
                    currentTool = "BRUSH";
                } else if (selectedTool.equals("Eraser")) {
                    currentTool = "ERASER";
                } else if (selectedTool.equals("Color Picker")) {
                    currentTool = "COLOR_PICKER";
                }
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
            }
        });
        
        // Add mouse listeners to canvas
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Line":
                currentTool = "LINE";
                toolChoice.select(0); // Reset to Brush
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
                break;
            case "Rectangle":
                currentTool = "RECTANGLE";
                toolChoice.select(0); // Reset to Brush
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
                break;
            case "Oval":
                currentTool = "OVAL";
                toolChoice.select(0); // Reset to Brush
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
                break;
            case "Triangle":
                currentTool = "TRIANGLE";
                toolChoice.select(0); // Reset to Brush
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
                break;
            case "Diamond":
                currentTool = "DIAMOND";
                toolChoice.select(0); // Reset to Brush
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
                break;
            case "Color Picker":
                currentTool = "COLOR_PICKER";
                toolChoice.select("Color Picker");
                canvas.setCurrentTool(currentTool);
                updateStatusLabel();
                statusLabel.setText("Color Picker: Click on canvas to pick a color | Tool: " + currentTool);
                break;
            case "Custom Color...":
                openCustomColorDialog();
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
                saveDrawing();
                break;
            case "Load":
                loadDrawing();
                break;
        }
    }
    
    // Mouse event implementations
    @Override
    public void mousePressed(MouseEvent e) {
        isDrawing = true;
        startPoint = e.getPoint();
        lastPoint = e.getPoint();
        canvas.mousePressed(e);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDrawing) {
            lastPoint = e.getPoint();
            canvas.mouseDragged(e);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        isDrawing = false;
        canvas.mouseReleased(e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        canvas.mouseClicked(e);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {
        canvas.mouseMoved(e);
    }
    
    private Color getColorFromName(String name) {
        switch (name.toLowerCase()) {
            case "black": return Color.BLACK;
            case "red": return Color.RED;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "orange": return Color.ORANGE;
            case "pink": return Color.PINK;
            case "cyan": return Color.CYAN;
            case "magenta": return Color.MAGENTA;
            case "white": return Color.WHITE;
            default: return Color.BLACK;
        }
    }
    
    private String getColorName(Color color) {
        if (color.equals(Color.BLACK)) return "Black";
        if (color.equals(Color.RED)) return "Red";
        if (color.equals(Color.GREEN)) return "Green";
        if (color.equals(Color.BLUE)) return "Blue";
        if (color.equals(Color.YELLOW)) return "Yellow";
        if (color.equals(Color.ORANGE)) return "Orange";
        if (color.equals(Color.PINK)) return "Pink";
        if (color.equals(Color.CYAN)) return "Cyan";
        if (color.equals(Color.MAGENTA)) return "Magenta";
        if (color.equals(Color.WHITE)) return "White";
        return "Black";
    }
    
    private void updateStatusLabel() {
        statusLabel.setText("Tool: " + currentTool + " | Color: " + getColorName(currentColor) + " | Brush Size: " + brushSize);
    }
    
    public void setPickedColor(Color color) {
        this.currentColor = color;
        canvas.setCurrentColor(color);
        
        // Update status to show picked color
        String colorInfo = String.format("Picked Color - RGB(%d, %d, %d)", 
            color.getRed(), color.getGreen(), color.getBlue());
        statusLabel.setText(colorInfo + " | Tool: " + currentTool + " | Brush Size: " + brushSize);
        
        // Try to find matching color in dropdown
        String colorName = getColorName(color);
        for (int i = 0; i < colorChoice.getItemCount(); i++) {
            String item = colorChoice.getItem(i);
            if (item.contains(colorName)) {
                colorChoice.select(i);
                return;
            }
        }
        // If no match found, select Custom
        colorChoice.select(colorChoice.getItemCount() - 1);
    }
    
    private void openCustomColorDialog() {
        Dialog colorDialog = new Dialog(this, "Choose Custom Color", true);
        colorDialog.setLayout(new BorderLayout());
        colorDialog.setSize(450, 400);
        colorDialog.setLocationRelativeTo(this);
        
        // Create color palette panel
        ColorPalettePanel palettePanel = new ColorPalettePanel();
        colorDialog.add(palettePanel, BorderLayout.CENTER);
        
        // Create button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");
        
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = palettePanel.getSelectedColor();
                canvas.setCurrentColor(currentColor);
                updateStatusLabel();
                colorDialog.dispose();
            }
        });
        
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorDialog.dispose();
            }
        });
        
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        colorDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        colorDialog.setVisible(true);
    }
    
    private void saveDrawing() {
        FileDialog fileDialog = new FileDialog(this, "Save Drawing", FileDialog.SAVE);
        fileDialog.setFile("*.png");
        fileDialog.setVisible(true);
        
        String filename = fileDialog.getFile();
        if (filename != null) {
            String directory = fileDialog.getDirectory();
            File file = new File(directory, filename);
            canvas.saveToFile(file);
        }
    }
    
    private void loadDrawing() {
        FileDialog fileDialog = new FileDialog(this, "Load Drawing", FileDialog.LOAD);
        fileDialog.setFile("*.png");
        fileDialog.setVisible(true);
        
        String filename = fileDialog.getFile();
        if (filename != null) {
            String directory = fileDialog.getDirectory();
            File file = new File(directory, filename);
            canvas.loadFromFile(file);
        }
    }
    
    public static void main(String[] args) {
        new SimpleDrawingApp();
    }
    
    // Inner class for custom color palette
    class ColorPalettePanel extends Panel {
        private Color selectedColor = Color.BLACK;
        private Canvas paletteCanvas;
        private Canvas previewCanvas;
        private Label rgbLabel;
        
        public ColorPalettePanel() {
            setLayout(new BorderLayout());
            
            // Create gradient palette canvas
            paletteCanvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    int width = getWidth();
                    int height = getHeight();
                    
                    // Draw HSB gradient
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
            
            // Add mouse listener to palette
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
            
            // Create preview panel
            Panel previewPanel = new Panel(new BorderLayout());
            previewCanvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    g.setColor(selectedColor);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            previewCanvas.setSize(80, 80);
            
            rgbLabel = new Label("RGB: 0, 0, 0");
            
            previewPanel.add(new Label("Selected Color:"), BorderLayout.NORTH);
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
}