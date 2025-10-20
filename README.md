# 🚀 COMPILADOR JAVA - Proyecto Universitario

**Universidad del Aconcagua** - *Licenciatura en Informática y Desarrollo de Software*  
**Trabajo Práctico de Compiladores - 2025**

---

## 📋 Descripción
Compilador completo desarrollado en Java que implementa un subconjunto estructurado de un lenguaje similar a C.  
Realiza análisis léxico, sintáctico y construcción de tabla de símbolos según las especificaciones del trabajo práctico.

---

## 🏗️ Estructura del Proyecto
**COMPILADOR-JAVA/**  
│── **src/main/java/compilador/**  
│   ├── **ast/** - Nodos del Árbol de Sintaxis Abstracta  
│   │   ├── J_Assignment.java  
│   │   ├── J_ASTNode.java  
│   │   ├── J_Declaration.java  
│   │   ├── J_IfStatement.java  
│   │   ├── J_Program.java  
│   │   ├── J_Read.java  
│   │   ├── J_Statement.java  
│   │   └── J_While.java  
│   ├── **errors/** - Sistema de Manejo de Errores  
│   │   ├── J_LeverException.java  
│   │   └── J_ParserException.java  
│   ├── **lever/** - Analizador Léxico  
│   │   ├── J_LeverManual.java  
│   │   ├── J_Token.java  
│   │   └── J_TokenType.java  
│   ├── **parser/** - Analizador Sintáctico  
│   │   └── J_Parser.java  
│   ├── **symtab/** - Tabla de Símbolos  
│   │   ├── J_Symbol.java  
│   │   └── J_SymbolTable.java  
│   ├── **visitors/** - Visitantes del AST  
│   │   ├── J_ASTPrinter.java  
│   │   ├── J_SymbolTableVisitor.java  
│   │   └── J_Visitor.java  
│   └── **J_Main.java** - Punto de Entrada Principal  
│── **lib/**  
│   └── antlr4-4.13.0-complete.jar  
│── **target/**  
├── **complar.bat** - Script Ejecución Windows  
└── **complar.sh** - Script Ejecución Linux/Mac  

---

## ⚡ Características Técnicas

### 🔍 **Funcionalidades Implementadas**
- **Análisis Léxico**: Reconocimiento de tokens y manejo de errores léxicos  
- **Análisis Sintáctico**: Parsing de estructuras del lenguaje con parser recursivo descendente  
- **Tabla de Símbolos**: Gestión completa de variables con información de tipo, valor y ámbito  
- **Manejo de Errores**: Sistema detallado con reporte de línea y descripción precisa  

---

### 📝 **Lenguaje Soportado**
- **Comentarios**: Multilínea `/* */` y una línea `//`  
- **Tipos de datos**: `long`, `double`  
- **Palabras reservadas**: `if`, `then`, `else`, `while`, `break`, `read`, `write`  
- **Operadores**:  
  - Aritméticos (`+ - * /`)  
  - Relacionales (`> < >= <= == !=`)  
  - Lógicos y de asignación  
- **Estructuras de control**: `if-then-else`, `while` con bloques  
- **Entrada/Salida**: `read()`, `write()`  
- **Identificadores**: Comienzan con letra o `_`, máximo 32 caracteres  

---

## 🚀 Instalación y Ejecución

### **Compilación y Ejecución**
```bash
# Windows
complar.bat

# Linux/Mac
chmod +x complar.sh
./complar.sh
```

---

🧩 Ejemplo de Código

```markdown

> /* Programa de ejemplo */
> long _x, _y;
> double _prom;
>
> read(_x);
> read(_y);
>
> if (_x > _y) then
>     _prom = (_x + _y) / 2;
> else
>     _prom = (_y - _x) / 2;
>
> write(_prom);
> 
```
✅ Especificaciones Cumplidas

✅ Sistema completo de comentarios con detección de errores

✅ Todas las palabras reservadas implementadas

✅ Operadores aritméticos, relacionales y lógicos

✅ Estructuras de control if-then-else y while

✅ Sistema de entrada/salida read/write

✅ Tabla de símbolos con información completa

✅ Manejo detallado de errores léxicos y sintácticos

✅ Validación de identificadores según especificaciones
