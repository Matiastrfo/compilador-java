@echo off
echo Compilando proyecto...
javac -cp "lib\antlr-4.13.0-complete.jar" -d target\classes src\main\java\compilador\**\*.java
if %errorlevel% equ 0 (
    echo ¡Compilación exitosa!
    java -cp "target\classes;lib\antlr-4.13.0-complete.jar" compilador.Main
) else (
    echo Error en la compilación
)