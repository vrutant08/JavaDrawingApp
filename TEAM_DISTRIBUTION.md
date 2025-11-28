# Team Distribution Guide

This guide shows how the codebase is organized for your 3-person team presentation.

## Package Overview & Responsibilities

### Person 1: Core Application & Canvas (app + canvas packages)
**Packages to Study:** `app/` and `canvas/`

**Files:**
- `app/SimpleDrawingApp.java` - ~300 lines (MINIMAL - just coordination!)
  - Main entry point
  - Component initialization
  - Event coordination only
  - Delegates to other packages

- `canvas/DrawingCanvas.java` - ~320 lines
  - Drawing surface implementation
  - Double buffering for smooth rendering
  - Mouse event handling
  - Integration with managers and shapes

**Key Concepts to Explain:**
- Application lifecycle and initialization
- Event-driven architecture
- Canvas rendering with double buffering
- Coordination between UI and business logic
- How the main class stays minimal by delegating

**Demo Points:**
- Show how SimpleDrawingApp just creates and wires components
- Explain double buffering technique
- Show mouse event handling flow

---

### Person 2: UI Components & Visual Design (ui package)
**Package to Study:** `ui/`

**Files:**
- `ui/RoundedButton.java` - ~60 lines
  - Custom button with rounded corners
  - Custom painting and styling

- `ui/ButtonStyler.java` - ~75 lines
  - Centralized button styling
  - Three style themes (shape/action/color)
  - Label creation

- `ui/ColorPalettePanel.java` - ~95 lines
  - HSB gradient color picker
  - Interactive color selection
  - Real-time preview

- `ui/DialogManager.java` - ~65 lines
  - Modal dialog creation
  - Color picker dialog
  - Dialog lifecycle management

- `ui/ToolbarFactory.java` - ~140 lines
  - Factory pattern for creating UI panels
  - Properties panel (colors, tools, brush size)
  - Shapes panel (shape buttons)
  - Actions panel (undo, save, etc.)
  - Main toolbar assembly

**Key Concepts to Explain:**
- Custom component creation (RoundedButton)
- Factory pattern for UI creation
- Separation of styling logic
- Modal dialogs and user interaction
- Component reusability

**Demo Points:**
- Show custom painting in RoundedButton
- Explain how ToolbarFactory keeps main class clean
- Demonstrate color picker functionality
- Show how styling is centralized

---

### Person 3: Business Logic & Data (shapes, manager, utils, events packages)
**Packages to Study:** `shapes/`, `manager/`, `utils/`, `events/`

#### Shapes Package:
- `shapes/ShapeBase.java` - ~55 lines (Abstract base class)
- `shapes/LineShape.java` - ~25 lines
- `shapes/RectShape.java` - ~30 lines
- `shapes/OvalShape.java` - ~30 lines
- `shapes/TriangleShape.java` - ~28 lines
- `shapes/DiamondShape.java` - ~32 lines

#### Manager Package:
- `manager/HistoryManager.java` - ~120 lines
  - Undo/Redo functionality
  - State management (max 10 steps)
  - Deep copying of canvas state

- `manager/FileManager.java` - ~75 lines
  - Save drawings as PNG
  - Load drawings from PNG
  - Image compositing

- `manager/FileDialogManager.java` - ~45 lines
  - File dialog abstraction
  - Save/Load dialog creation

#### Utils Package:
- `utils/ColorUtils.java` - ~55 lines
  - Color name ↔ Color object conversion
  - RGB formatting

- `utils/PointUtils.java` - ~30 lines
  - Point calculations (distance, midpoint)
  - Bounds checking

- `utils/ShapeUtils.java` - ~35 lines
  - Factory pattern for shape creation
  - Shape type validation

#### Events Package:
- `events/CanvasMouseHandler.java` - ~35 lines
  - Mouse click, press, release events
  - Delegates to canvas

- `events/CanvasMotionHandler.java` - ~25 lines
  - Mouse drag and move events
  - Delegates to canvas

**Key Concepts to Explain:**
- OOP: Inheritance (ShapeBase → concrete shapes)
- OOP: Polymorphism (shapes treated uniformly)
- Factory pattern (ShapeUtils, ToolbarFactory)
- State management (HistoryManager)
- Event delegation pattern
- Utility classes for reusable logic

**Demo Points:**
- Show shape hierarchy and polymorphism
- Explain undo/redo mechanism
- Demonstrate factory pattern
- Show event handler separation

---

## File Size Summary

### Minimal Main Class ✅
- **SimpleDrawingApp.java**: ~300 lines (down from 548!)
  - Just initialization and coordination
  - No business logic
  - No heavy UI code
  - All delegated to packages

### Well-Distributed Code
- **UI Package**: ~435 lines across 5 files
- **Shapes Package**: ~200 lines across 6 files
- **Manager Package**: ~240 lines across 3 files
- **Utils Package**: ~120 lines across 3 files
- **Events Package**: ~60 lines across 2 files
- **Canvas Package**: ~320 lines in 1 file

**Total**: ~1,375 lines well-organized across 21 files in 7 packages

---

## OOP Principles Demonstrated

### 1. Encapsulation
- Each package encapsulates related functionality
- Private fields with public methods
- Clear interfaces between packages

### 2. Abstraction
- `ShapeBase` provides abstract interface
- Concrete implementations hidden
- Clean separation of what vs. how

### 3. Inheritance
- All shapes extend `ShapeBase`
- Common functionality in base class
- Specialized behavior in subclasses

### 4. Polymorphism
- Shapes treated uniformly through `ShapeBase`
- `List<ShapeBase>` holds different shape types
- Runtime behavior based on actual type

### 5. Design Patterns
- **Factory Pattern**: `ShapeUtils`, `ToolbarFactory`
- **Observer Pattern**: Event handlers
- **Delegation Pattern**: Main class delegates to packages
- **Single Responsibility**: Each class has one clear job

---

## For Viva Voce / Presentation

### Opening (All Together):
1. Show the clean package structure
2. Explain the motivation for organization
3. Overview of each package's responsibility

### Individual Presentations:
1. **Person 1**: Walk through app startup and canvas rendering
2. **Person 2**: Demonstrate UI components and user interaction
3. **Person 3**: Explain business logic and OOP concepts

### Closing (All Together):
1. Show how components work together
2. Demonstrate adding a new feature (easy now!)
3. Benefits of this architecture

---

## Quick Reference: What's In Each Package?

```
app/        → Application entry point and coordination
canvas/     → Drawing surface and rendering
shapes/     → Shape hierarchy (OOP inheritance/polymorphism)
manager/    → Business logic (history, file operations)
utils/      → Reusable helper functions
ui/         → Custom UI components and factories
events/     → Event handling delegation
```

## Benefits of This Organization

1. **Easy to Understand**: Each package has clear purpose
2. **Easy to Maintain**: Find and fix bugs quickly
3. **Easy to Extend**: Add features without breaking existing code
4. **Easy to Test**: Test each package independently
5. **Easy to Explain**: Perfect for team presentations!
6. **Professional**: Industry-standard organization

Each team member can now focus on their packages and explain them confidently during the viva voce!
