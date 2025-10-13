package main.java.compilador.parser;

import compilador.lexer.*;
import compilador.errors.ParserException;
import java.util.*;

public class Parser {
    private final Iterator<Token> tokens;
    private Token current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens.iterator();
        advance();
    }

    private void advance() {
        if (tokens.hasNext()) current = tokens.next();
    }

    private void eat(TokenType expected) {
        if (current.getType() == expected) {
            advance();
        } else {
            throw new ParserException("Se esperaba " + expected + " pero se encontró " + current.getType());
        }
    }

    // ===============================
    // PROGRAMA PRINCIPAL
    // ===============================
    public void parseProgram() {
        while (current.getType() != TokenType.EOF) {
            parseStatement();
        }
        System.out.println("✅ Análisis sintáctico completado correctamente.");
    }

    // ===============================
    // SENTENCIAS
    // ===============================
    private void parseStatement() {
        switch (current.getType()) {
            case LONG, DOUBLE -> parseDeclaration();
            case ID -> parseAssignment();
            case READ, WRITE -> parseIO();
            case IF -> parseIf();
            case WHILE -> parseWhile();
            default -> throw new ParserException("Sentencia inesperada: " + current.getLexeme());
        }
    }

    // ===============================
    // DECLARACIÓN
    // ===============================
    private void parseDeclaration() {
        eat(current.getType()); // LONG o DOUBLE
        eat(TokenType.ID);

        while (current.getType() == TokenType.COMMA) {
            eat(TokenType.COMMA);
            eat(TokenType.ID);
        }

        eat(TokenType.SEMI);
        System.out.println("→ Declaración válida");
    }

    // ===============================
    // ASIGNACIÓN
    // ===============================
    private void parseAssignment() {
        eat(TokenType.ID);
        eat(TokenType.ASSIGN);
        parseExpression();
        eat(TokenType.SEMI);
        System.out.println("→ Asignación válida");
    }

    // ===============================
    // READ / WRITE
    // ===============================
    private void parseIO() {
        if (current.getType() == TokenType.READ) {
            eat(TokenType.READ);
        } else {
            eat(TokenType.WRITE);
        }

        eat(TokenType.LPAREN);
        if (current.getType() == TokenType.ID || current.getType() == TokenType.REAL_CONST
            || current.getType() == TokenType.INT_CONST || current.getType() == TokenType.STRING_CONST) {
            parseExpression();
        } else {
            throw new ParserException("Se esperaba un identificador o valor dentro de read/write()");
        }
        eat(TokenType.RPAREN);
        eat(TokenType.SEMI);
        System.out.println("→ Operación de entrada/salida válida");
    }

    // ===============================
    // IF / THEN / ELSE
    // ===============================
    private void parseIf() {
        eat(TokenType.IF);
        eat(TokenType.LPAREN);
        parseCondition();
        eat(TokenType.RPAREN);
        eat(TokenType.THEN);
        parseStatement();

        if (current.getType() == TokenType.ELSE) {
            eat(TokenType.ELSE);
            parseStatement();
        }

        System.out.println("→ Estructura if válida");
    }

    // ===============================
    // WHILE
    // ===============================
    private void parseWhile() {
        eat(TokenType.WHILE);
        eat(TokenType.LPAREN);
        parseCondition();
        eat(TokenType.RPAREN);
        eat(TokenType.LBRACE);

        while (current.getType() != TokenType.RBRACE) {
            parseStatement();
        }

        eat(TokenType.RBRACE);
        System.out.println("→ Estructura while válida");
    }

    // ===============================
    // CONDICIONES
    // ===============================
    private void parseCondition() {
        parseExpression();

        switch (current.getType()) {
            case GT, LT, GE, LE, EQ, NEQ, DIFF -> advance();
            default -> throw new ParserException("Operador relacional esperado en condición");
        }

        parseExpression();
    }

    // ===============================
    // EXPRESIONES
    // ===============================
    private void parseExpression() {
        parseTerm();
        while (current.getType() == TokenType.PLUS || current.getType() == TokenType.MINUS) {
            advance();
            parseTerm();
        }
    }

    private void parseTerm() {
        parseFactor();
        while (current.getType() == TokenType.MULT || current.getType() == TokenType.DIV) {
            advance();
            parseFactor();
        }
    }

    private void parseFactor() {
        switch (current.getType()) {
            case ID, INT_CONST, REAL_CONST, STRING_CONST -> advance();
            case LPAREN -> {
                eat(TokenType.LPAREN);
                parseExpression();
                eat(TokenType.RPAREN);
            }
            default -> throw new ParserException("Factor inesperado: " + current.getLexeme());
        }
    }
}