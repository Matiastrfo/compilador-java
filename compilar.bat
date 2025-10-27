@echo off
echo Compilando proyecto...

cd compilador-java

:: Limpiar
if exist "target" rmdir /s /q target
mkdir target\classes

:: Compilar
echo Compilando archivos...
dir /s /b *.java > archivos.txt
javac -cp "lib\antlr-4.13.0-complete.jar" -d target\classes @archivos.txt

if %errorlevel% equ 0 (
    echo ¡Compilación exitosa!
    echo.
    echo Ejecutando programa...
    java -cp "target\classes;lib\antlr-4.13.0-complete.jar" main.java.compilador.Main
) else (
    echo Error en la compilación
)

if exist archivos.txt del archivos.txt
pause