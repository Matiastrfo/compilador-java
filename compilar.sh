#!/bin/bash
echo "Compilando proyecto..."

# Crear directorio target/classes si no existe
mkdir -p target/classes

# Compilar todos los archivos Java - RUTA CORREGIDA
find compilador-java/src/main/java -name "*.java" > sources.txt
javac -cp "lib/antlr-4.13.0-complete.jar" -d target/classes @sources.txt

if [ $? -eq 0 ]; then
    echo "¡Compilación exitosa!"
    echo ""
    java -cp "target/classes:lib/antlr-4.13.0-complete.jar" main.java.compilador.Main
else
    echo "Error en la compilación"
fi

# Limpiar archivo temporal
rm -f sources.txt
