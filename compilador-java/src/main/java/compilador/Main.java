package main.java.compilador;

import compilador.lexer.*;
import compilador.parser.*;
import compilador.errors.*;
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
                if (!token.type.equals("EOF")) System.out.println(token);
            }

            System.out.println("\n=== ANALIZADOR SINTÁCTICO ===\n");
            Parser parser = new Parser(tokens);
            parser.parseProgram();

            System.out.println("\n✅ ¡COMPILADOR FUNCIONANDO!");

        } catch (ParserException e) {
            System.err.println("❌ Error sintáctico: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error general: " + e.getMessage());
        }
    }
}
