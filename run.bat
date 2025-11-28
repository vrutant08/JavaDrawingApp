@echo off
echo Compiling Drawing Studio...
echo.

REM Clean bin directory
if exist bin\com rmdir /s /q bin\com
if not exist bin mkdir bin

REM Compile all Java files
javac -d bin ^
    src\com\drawingstudio\shapes\*.java ^
    src\com\drawingstudio\manager\*.java ^
    src\com\drawingstudio\utils\*.java ^
    src\com\drawingstudio\ui\*.java ^
    src\com\drawingstudio\events\*.java ^
    src\com\drawingstudio\canvas\*.java ^
    src\com\drawingstudio\app\*.java

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo.
echo Compilation successful!
echo Running Drawing Studio...
echo.

REM Run the application
java -cp bin com.drawingstudio.app.SimpleDrawingApp

pause
