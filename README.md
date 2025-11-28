# Simple Drawing Studio - AWT Version

A professional GUI-based drawing application built with Java AWT/Swing, demonstrating proper OOP design principles and package organization.

## Overview

This is a full-featured drawing application with a clean, organized codebase structured for maintainability and scalability. The project follows best practices in Java development and is ideal for educational demonstrations of OOP concepts.

## Features

### Drawing Tools
- **Freehand Brush**: Draw freely with adjustable brush size (1-10 pixels)
- **Shape Tools**: Line, Rectangle, Oval, Triangle, Diamond with real-time preview
- **Eraser**: Remove brush strokes and shapes by clicking on them
- **Color Picker**: Pick colors directly from the canvas

### Color Management
- **Preset Colors**: 10 common colors (Black, Red, Green, Blue, Yellow, Orange, Pink, Cyan, Magenta, White)
- **Custom Color Palette**: HSB gradient color picker for precise color selection
- **Color Preview**: Visual preview of current color
- **RGB Display**: Shows RGB values of picked colors

### Canvas Operations
- **Undo/Redo**: Up to 10 levels of undo/redo functionality
- **Clear Canvas**: Reset the entire drawing surface
- **Save/Load**: Save drawings as PNG images and load them back
- **Double Buffering**: Smooth, flicker-free rendering

### UI Features
- **Rounded Buttons**: Custom styled buttons with colored borders
- **Organized Toolbar**: Grouped into Properties, Shapes, and Actions sections
- **Status Bar**: Displays current tool, color, and brush size
- **Dark Theme**: Professional dark gray toolbar background

## Project Structure

```
JavaDrawingApp/
├── src/com/drawingstudio/     # Source code with proper package structure
│   ├── app/                    # Main application
│   ├── canvas/                 # Drawing surface
│   ├── shapes/                 # Shape class hierarchy
│   ├── manager/                # Business logic managers
│   ├── utils/                  # Utility classes
│   ├── ui/                     # Custom UI components
│   └── events/                 # Event handlers
├── bin/                        # Compiled classes
├── run.bat                     # Build and run script
├── README.md                   # This file
└── README_PACKAGE_STRUCTURE.md # Detailed package documentation
```

For detailed package structure and OOP concepts demonstrated, see [README_PACKAGE_STRUCTURE.md](README_PACKAGE_STRUCTURE.md).

## How to Run

### Windows
Simply double-click `run.bat` or run from command line:
```batch
run.bat
```

### Manual Compilation
```batch
# Compile
javac -d bin src/com/drawingstudio/**/*.java

# Run
java -cp bin com.drawingstudio.app.SimpleDrawingApp
```

## Usage Guide

### Basic Drawing
1. Select a tool from the dropdown (Brush, Eraser, or Color Picker)
2. Choose a color from the preset colors or create a custom color
3. Adjust brush size using the brush size dropdown
4. Click and drag on the canvas to draw

### Shape Drawing
1. Click any shape button (Line, Rectangle, Oval, Triangle, Diamond)
2. Click and drag on the canvas to define the shape
3. Release to finalize the shape

### Color Picking
1. Select "Color Picker" from the tool dropdown or click the "Color Picker" button
2. Click anywhere on the canvas to pick that color
3. The picked color becomes your current drawing color

### Using Custom Colors
1. Click "Custom Color..." button
2. Click on the gradient palette to select a color
3. Click OK to apply the selected color

### Eraser
1. Select "Eraser" from the tool dropdown
2. Click on shapes to delete them
3. Drag to erase brush strokes

## Technical Highlights

### OOP Principles Demonstrated
- **Encapsulation**: Functionality grouped into logical packages
- **Abstraction**: Abstract `ShapeBase` class defines shape interface
- **Inheritance**: All shapes inherit from `ShapeBase`
- **Polymorphism**: Shapes treated uniformly through base class
- **Factory Pattern**: `ShapeUtils` creates appropriate shape objects

### Architecture Benefits
- **Separation of Concerns**: UI, business logic, and data are separated
- **Single Responsibility**: Each class has one clear purpose
- **Maintainability**: Easy to locate and modify functionality
- **Scalability**: New features can be added without breaking existing code
- **Testability**: Components can be tested independently

## System Requirements

- Java Development Kit (JDK) 8 or higher
- Windows OS (batch file provided; can be adapted for Unix systems)

## File Format

Drawings are saved as PNG images, preserving both brush strokes and vector shapes in a single rasterized format.

## Keyboard Shortcuts

Currently, all operations are performed via mouse and buttons. Keyboard shortcuts can be added in future versions.

## Known Limitations

- No zoom functionality
- Fixed canvas size (800x600 pixels)
- PNG export only (no SVG or other vector formats)
- No layer support

## Future Enhancements

Potential improvements for future versions:
- Keyboard shortcuts
- Adjustable canvas size
- Fill tool for closed shapes
- Text tool
- Image import
- Multiple layers
- SVG export
- Grid and ruler guides

## Credits

Developed as an educational project demonstrating professional Java application development practices.
