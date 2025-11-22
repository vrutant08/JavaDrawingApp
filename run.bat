@echo off
REM Drawing Studio Application - Package Structure Build Script
REM This script compiles and runs the packaged Java application

echo ========================================
echo  Drawing Studio - Package Structure
echo ========================================
echo.

REM Navigate to project directory
cd /d "C:\Users\vruta\Desktop\VSCode\OtherProjects\JavaProjectSimple"

echo Compiling Java packages...
echo.

REM Clean previous compilation
if exist "bin" (
    echo Cleaning previous build...
    rmdir /s /q bin
)
mkdir bin

REM Compile all packages
echo Compiling source files...
javac -d bin -sourcepath src src\com\drawingstudio\app\SimpleDrawingApp.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo  ERROR: Compilation failed!
    echo ========================================
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Compilation successful!
echo ========================================
echo.
echo Running application...
echo.

REM Run the application
java -cp bin com.drawingstudio.app.SimpleDrawingApp

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo  ERROR: Application failed to run!
    echo ========================================
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Application closed successfully
echo ========================================
pause
