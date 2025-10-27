package main.java.compilador.parser;

import main.java.compilador.lexer.*;
import main.java.compilador.errors.ParserException;
import main.java.compilador.ast.*;
import main.java.compilador.ast.expr.*;
import java.util.*;

public class Parser {
    private final Iterator<Token> tokens;
    private Token current;
    private final List<Statement> statements = new ArrayList<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens.iterator();
        advance();
    }

    private void advance() {
        if (tokens.hasNext()) current = tokens.next();
    }

    private void eat(TokenType expected) {
        if (current.type == expected) {
            advance();
        } else {
            throw new ParserException(
                "Se esperaba " + expected + " pero se encontró " + current.type, 
                current.line, 
                current.column
            );
        }
    }


    public Program parseProgram() {
        while (current.type != TokenType.EOF) {
            statements.add(parseStatement());
        }
        System.out.println("Análisis sintáctico completado - AST construido");
        return new Program(statements);
    }


    private Statement parseStatement() {
        switch (current.type) {
            case LONG, DOUBLE -> { return parseDeclaration(); }
            case ID -> { return parseAssignment(); }
            case READ -> { return parseRead(); }
            case WRITE -> { return parseWrite(); }
            case IF -> { return parseIf(); }
            case WHILE -> { return parseWhile(); }
            default -> throw new ParserException(
                "Sentencia inesperada: " + current.text,
                current.line, 
                current.column
            );
        }
    }

  
    private Declaration parseDeclaration() {
        String type = current.text; // "long" o "double"
        eat(current.type);
        
        List<String> ids = new ArrayList<>();
        ids.add(current.text);
        eat(TokenType.ID);

        // Verificar si hay asignación (pero no la procesamos aún en AST)
        if (current.type == TokenType.ASSIGN) {
            eat(TokenType.ASSIGN);
            parseExpression(); // Solo consumimos la expresión, no la guardamos
        }
        
        // Variables adicionales con coma
        while (current.type == TokenType.COMMA) {
            eat(TokenType.COMMA);
            ids.add(current.text);
            eat(TokenType.ID);
        }

        eat(TokenType.SEMI);
        return new Declaration(type, ids);
    }

  
    private Assignment parseAssignment() {
        String id = current.text;
        eat(TokenType.ID);
        eat(TokenType.ASSIGN);
        Expr expr = parseExpression();
        eat(TokenType.SEMI);
        return new Assignment(id, expr);
    }

    private Read parseRead() {
        eat(TokenType.READ);
        eat(TokenType.LPAREN);
        String varName = current.text;
        eat(TokenType.ID);
        eat(TokenType.RPAREN);
        eat(TokenType.SEMI);
        return new Read(varName);
    }

    
    private Write parseWrite() {
        eat(TokenType.WRITE);
        eat(TokenType.LPAREN);
        Expr expr = parseExpression();
        eat(TokenType.RPAREN);
        eat(TokenType.SEMI);
        return new Write(expr);
    }

   
    private IfStatement parseIf() {
        eat(TokenType.IF);
        eat(TokenType.LPAREN);
        Expr condition = parseCondition();
        eat(TokenType.RPAREN);
        eat(TokenType.THEN);
        Statement thenStmt = parseStatement();
        
        Statement elseStmt = null;
        if (current.type == TokenType.ELSE) {
            eat(TokenType.ELSE);
            elseStmt = parseStatement();
        }

        return new IfStatement(condition, thenStmt, elseStmt);
    }


    private While parseWhile() {
        eat(TokenType.WHILE);
        eat(TokenType.LPAREN);
        Expr condition = parseCondition();
        eat(TokenType.RPAREN);
        eat(TokenType.LBRACE);
        
        List<Statement> bodyStatements = new ArrayList<>();
        while (current.type != TokenType.RBRACE) {
            bodyStatements.add(parseStatement());
        }
        
        eat(TokenType.RBRACE);
        
       
        if (bodyStatements.size() == 1) {
            return new While(condition, bodyStatements.get(0));
        } else {
            
            return new While(condition, bodyStatements.get(0));
        }
    }


    private Expr parseCondition() {
        Expr left = parseExpression();
        
        String operator = current.text;
        switch (current.type) {
            case GT, LT, GE, LE, EQ, NEQ -> advance();
            default -> throw new ParserException(
                "Operador relacional esperado en condición",
                current.line,
                current.column
            );
        }
        
        Expr right = parseExpression();
        return new BinaryExpr(operator, left, right);
    }

    private Expr parseExpression() {
        return parseAdditive();
    }

    private Expr parseAdditive() {
        Expr expr = parseMultiplicative();
        
        while (current.type == TokenType.PLUS || current.type == TokenType.MINUS) {
            String operator = current.text;
            advance();
            Expr right = parseMultiplicative();
            expr = new BinaryExpr(operator, expr, right);
        }
        
        return expr;
    }

    private Expr parseMultiplicative() {
        Expr expr = parsePrimary();
        
        while (current.type == TokenType.MULT || current.type == TokenType.DIV) {
            String operator = current.text;
            advance();
            Expr right = parsePrimary();
            expr = new BinaryExpr(operator, expr, right);
        }
        
        return expr;
    }

    private Expr parsePrimary() {
        switch (current.type) {
            case ID -> {
                String name = current.text;
                advance();
                return new Variable(name);
            }
            case INT_CONST -> {
                int value = Integer.parseInt(current.text);
                advance();
                return new Literal(value);
            }
            case REAL_CONST -> {
                double value = Double.parseDouble(current.text);
                advance();
                return new Literal(value);
            }
            case STRING_CONST -> {
                String value = current.text;
                advance();
                return new Literal(value);
            }
            case LPAREN -> {
                eat(TokenType.LPAREN);
                Expr expr = parseExpression();
                eat(TokenType.RPAREN);
                return expr;
            }
            default -> throw new ParserException(
                "Factor inesperado: " + current.text,
                current.line,
                current.column
            );
        }
    }
}