package main.java.compilador;

import main.java.compilador.lexer.*;
import main.java.compilador.parser.*;
import main.java.compilador.errors.*;
import main.java.compilador.visitors.*;
import main.java.compilador.symtab.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== ANALIZADOR LÉXICO ===\n");

            String testCode = """
                long _x = 10;
                double _prom = 5.5;
                if (_x > 5) then write(_prom);
            """;

            LexerManual lexer = new LexerManual(testCode);
            List<Token> tokens = lexer.tokenize();

            System.out.println("TOKENS RECONOCIDOS:");
            for (Token token : tokens) {
                if (token.type != TokenType.EOF) System.out.println(token);
            }

            System.out.println("\n=== ANALIZADOR SINTÁCTICO ===\n");
            Parser parser = new Parser(tokens);
            var program = parser.parseProgram();

            System.out.println("\n=== ÁRBOL DE SINTAXIS ABSTRACTRA (AST) ===\n");
            ASTPrinter printer = new ASTPrinter();
            program.accept(printer);

            System.out.println("\n=== TABLA DE SÍMBOLOS ===\n");
            SymbolTableVisitor symbolVisitor = new SymbolTableVisitor();
            program.accept(symbolVisitor);
            
            SymbolTable symbolTable = symbolVisitor.getSymbolTable();
            System.out.println("\nSÍMBOLOS EN LA TABLA:");
            for (Symbol symbol : symbolTable.all()) {
                System.out.println("  " + symbol.name + " | " + symbol.type + " | " + 
                                 symbol.value + " | " + symbol.scope + " | " + symbol.lineDecl);
            }

            System.out.println("\n✅ ¡COMPILADOR COMPLETO FUNCIONANDO!");
            System.out.println("   - Lexer ✅   Parser ✅   AST ✅   Tabla de Símbolos ✅");

        } catch (ParserException e) {
            System.err.println("❌ Error sintáctico: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
