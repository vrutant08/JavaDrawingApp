# Simple Drawing Studio - Package Structure

## Package Organization

This project demonstrates professional Java code organization with proper OOP principles.

### Package Structure

```
src/com/drawingstudio/
├── app/              # Main application class
│   └── SimpleDrawingApp.java
├── canvas/           # Drawing surface implementation
│   └── DrawingCanvas.java
├── shapes/           # Shape class hierarchy
│   ├── ShapeBase.java
│   ├── LineShape.java
│   ├── RectShape.java
│   ├── OvalShape.java
│   ├── TriangleShape.java
│   └── DiamondShape.java
├── manager/          # Business logic managers
│   ├── HistoryManager.java
│   └── FileManager.java
├── utils/            # Utility classes
│   ├── ColorUtils.java
│   ├── PointUtils.java
│   └── ShapeUtils.java
├── ui/               # Custom UI components
│   └── RoundedButton.java
└── events/           # Event handlers
    ├── CanvasMouseHandler.java
    └── CanvasMotionHandler.java
```

### Key OOP Concepts Demonstrated

1. **Encapsulation**: Each package encapsulates related functionality
2. **Abstraction**: ShapeBase provides abstract interface for all shapes
3. **Inheritance**: All shape classes extend ShapeBase
4. **Polymorphism**: Shapes are treated uniformly through ShapeBase interface
5. **Separation of Concerns**: UI, logic, and data are separated
6. **Factory Pattern**: ShapeUtils creates appropriate shape objects
7. **Single Responsibility**: Each class has one clear purpose

### How to Compile and Run

```batch
run.bat
```

This will compile all source files and run the application.

### Features

- Freehand drawing with brush tool
- Shape tools (Line, Rectangle, Oval, Triangle, Diamond)
- Eraser tool (works on both brush strokes and shapes)
- Color picker tool
- Custom color palette with HSB gradient
- Undo/Redo functionality (up to 10 steps)
- Save/Load drawings as PNG
- Double buffering for smooth rendering
- Rounded buttons with custom styling

### Architecture Benefits

- **Maintainability**: Easy to locate and modify specific functionality
- **Scalability**: New features can be added without modifying existing code
- **Testability**: Each component can be tested independently
- **Reusability**: Utilities and components can be reused in other projects
- **Readability**: Clear structure makes code easy to understand

### For College Presentation

This structure demonstrates:
- Professional Java project organization
- Best practices in OOP design
- Clean code principles
- Separation of concerns
- Design patterns (Factory, Observer)

Each package has a specific responsibility, making it easy to explain the architecture during viva voce.
