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
            System.out.println("EJEMPLO \n");

          
            String testCode = """              
                long _x, _y;
                double _prom;
                
                read(_x);
                read(_y);
                
                if (_x > _y) then
                    _prom = (_x + _y) / 2;
                else
                    _prom = (_y - _x) / 2;x
                    
                write(_prom);
                
                // Fin del programa
            """;

            System.out.println("CÓDIGO FUENTE:");
            System.out.println(testCode);
            System.out.println("=" .repeat(50));

            System.out.println("\n1. ANALIZADOR LÉXICO");
            LexerManual lexer = new LexerManual(testCode);
            List<Token> tokens = lexer.tokenize();

            System.out.println("TOKENS RECONOCIDOS:");
            for (Token token : tokens) {
                if (token.type != TokenType.EOF) {
                    System.out.println(token);
                }
            }

            System.out.println("\n 2. ANALIZADOR SINTÁCTICO");
            Parser parser = new Parser(tokens);
            var program = parser.parseProgram();

            System.out.println("\n 3. ÁRBOL DE SINTAXIS ABSTRACTRA (AST)");
            ASTPrinter printer = new ASTPrinter();
            program.accept(printer);

            System.out.println("\n 4. TABLA DE SÍMBOLOS");
            SymbolTableVisitor symbolVisitor = new SymbolTableVisitor();
            program.accept(symbolVisitor);
            
            SymbolTable symbolTable = symbolVisitor.getSymbolTable();
            System.out.println("SÍMBOLOS REGISTRADOS:");
            System.out.println("Nombre     | Tipo   | Valor | Ámbito | Línea");
            System.out.println("-" .repeat(50));
            for (Symbol symbol : symbolTable.all()) {
                System.out.printf("%-10s | %-6s | %-5s | %-6s | %d%n",
                    symbol.name, symbol.type, symbol.value, symbol.scope, symbol.lineDecl);
            }


        } catch (ParserException e) {
            System.err.println("Error sintáctico: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}