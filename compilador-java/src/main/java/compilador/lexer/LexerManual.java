package main.java.compilador.lexer;

import java.util.*;

public class LexerManual {
    private final String input;
    private int position;
    private int line;
    private int column;
    
    public LexerManual(String input) {
        this.input = input;
        this.position = 0;
        this.line = 1;
        this.column = 1;
    }
    
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        
        while (position < input.length()) {
            char current = input.charAt(position);
            
            // Saltar espacios y tabs
            if (current == ' ' || current == '\t') {
                position++; column++; continue;
            }
            
            // Saltar saltos de línea
            if (current == '\n') {
                position++; line++; column = 1; continue;
            }
            
            // Comentarios de una línea
            if (current == '/' && position + 1 < input.length() && input.charAt(position + 1) == '/') {
                // Saltar hasta el final de la línea
                while (position < input.length() && input.charAt(position) != '\n') {
                    position++;
                }
                continue;
            }
            
            // Comentarios multilínea
            if (current == '/' && position + 1 < input.length() && input.charAt(position + 1) == '*') {
                position += 2; column += 2;
                boolean commentClosed = false;
                
                while (position < input.length()) {
                    if (input.charAt(position) == '*' && position + 1 < input.length() && input.charAt(position + 1) == '/') {
                        position += 2; column += 2;
                        commentClosed = true;
                        break;
                    }
                    if (input.charAt(position) == '\n') {
                        line++; column = 1; position++;
                    } else {
                        position++; column++;
                    }
                }
                
                if (!commentClosed) {
                    System.err.printf("Error léxico [Línea %d:%d]: comentario multilínea sin cerrar%n", line, column);
                }
                continue;
            }
            
            if (current == '+' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.PLUS_ASSIGN, "+=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '-' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.MINUS_ASSIGN, "-=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '*' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.MULT_ASSIGN, "*=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '/' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.DIV_ASSIGN, "/=", line, column));
                position += 2; column += 2; continue;
            }
            
            // Operadores lógicos: &&, ||
            if (current == '&' && position + 1 < input.length() && input.charAt(position + 1) == '&') {
                tokens.add(new Token(TokenType.AND, "&&", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '|' && position + 1 < input.length() && input.charAt(position + 1) == '|') {
                tokens.add(new Token(TokenType.OR, "||", line, column));
                position += 2; column += 2; continue;
            }
            
            // Operadores relacionales existentes + <> (alternativa a !=)
            if (current == '=' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.EQ, "==", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '!' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.NEQ, "!=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '>' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.GE, ">=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '<' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.LE, "<=", line, column));
                position += 2; column += 2; continue;
            }
            // NUEVO: Operador <> (alternativa a !=)
            if (current == '<' && position + 1 < input.length() && input.charAt(position + 1) == '>') {
                tokens.add(new Token(TokenType.DIFF, "<>", line, column));
                position += 2; column += 2; continue;
            }
            
            // Cadenas entre comillas
            if (current == '"') {
                tokens.add(readString()); continue;
            }
            
            // Identificadores (con límite de 32 caracteres)
            if (Character.isLetter(current) || current == '_') {
                tokens.add(readIdentifier()); continue;
            }
            
            // Números
            if (Character.isDigit(current)) {
                tokens.add(readNumber()); continue;
            }
            
            // Operadores simples
            if (current == '+') { tokens.add(new Token(TokenType.PLUS, "+", line, column)); position++; column++; continue; }
            if (current == '-') { tokens.add(new Token(TokenType.MINUS, "-", line, column)); position++; column++; continue; }
            if (current == '*') { tokens.add(new Token(TokenType.MULT, "*", line, column)); position++; column++; continue; }
            if (current == '/') { tokens.add(new Token(TokenType.DIV, "/", line, column)); position++; column++; continue; }
            if (current == '=') { tokens.add(new Token(TokenType.ASSIGN, "=", line, column)); position++; column++; continue; }
            if (current == '>') { tokens.add(new Token(TokenType.GT, ">", line, column)); position++; column++; continue; }
            if (current == '<') { tokens.add(new Token(TokenType.LT, "<", line, column)); position++; column++; continue; }
            if (current == '!') { tokens.add(new Token(TokenType.NOT, "!", line, column)); position++; column++; continue; }
            if (current == ';') { tokens.add(new Token(TokenType.SEMI, ";", line, column)); position++; column++; continue; }
            if (current == '(') { tokens.add(new Token(TokenType.LPAREN, "(", line, column)); position++; column++; continue; }
            if (current == ')') { tokens.add(new Token(TokenType.RPAREN, ")", line, column)); position++; column++; continue; }
            if (current == '{') { tokens.add(new Token(TokenType.LBRACE, "{", line, column)); position++; column++; continue; }
            if (current == '}') { tokens.add(new Token(TokenType.RBRACE, "}", line, column)); position++; column++; continue; }
            if (current == ',') { tokens.add(new Token(TokenType.COMMA, ",", line, column)); position++; column++; continue; }
            
            // Error - carácter inesperado
            System.err.printf("Error léxico [Línea %d:%d]: carácter inesperado '%c'%n", line, column, current);
            position++; column++;
        }
        
        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }
    
    private Token readIdentifier() {
        int start = position, startColumn = column;
        while (position < input.length() && 
               (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            position++; column++;
        }
        String text = input.substring(start, position);
        
        if (text.length() > 32) {
            System.err.printf("Advertencia [Línea %d:%d]: identificador '%s' excede 32 caracteres%n", line, startColumn, text);
        }
        
        // Palabras reservadas
        if (text.equals("long")) return new Token(TokenType.LONG, text, line, startColumn);
        if (text.equals("double")) return new Token(TokenType.DOUBLE, text, line, startColumn);
        if (text.equals("if")) return new Token(TokenType.IF, text, line, startColumn);
        if (text.equals("then")) return new Token(TokenType.THEN, text, line, startColumn);
        if (text.equals("else")) return new Token(TokenType.ELSE, text, line, startColumn);
        if (text.equals("while")) return new Token(TokenType.WHILE, text, line, startColumn);
        if (text.equals("read")) return new Token(TokenType.READ, text, line, startColumn);
        if (text.equals("write")) return new Token(TokenType.WRITE, text, line, startColumn);
        if (text.equals("true")) return new Token(TokenType.TRUE, text, line, startColumn);
        if (text.equals("false")) return new Token(TokenType.FALSE, text, line, startColumn);
        if (text.equals("break")) return new Token(TokenType.BREAK, text, line, startColumn);
        
        return new Token(TokenType.ID, text, line, startColumn);
    }
    
    private Token readNumber() {
        int start = position, startColumn = column;
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position++; column++;
        }
        
        // Verificar si es decimal
        if (position < input.length() && input.charAt(position) == '.') {
            position++; column++;
            while (position < input.length() && Character.isDigit(input.charAt(position))) {
                position++; column++;
            }
            return new Token(TokenType.REAL_CONST, input.substring(start, position), line, startColumn);
        }
        
        return new Token(TokenType.INT_CONST, input.substring(start, position), line, startColumn);
    }
    
    private Token readString() {
        int startColumn = column;
        position++; column++; // Saltar la comilla inicial
        
        StringBuilder str = new StringBuilder();
        boolean stringClosed = false;
        
        while (position < input.length()) {
            char current = input.charAt(position);
            
            if (current == '"') {
                stringClosed = true;
                position++; column++;
                break;
            }
            
            if (current == '\n') {
                line++; column = 1;
            }
            
            // Manejar caracteres escapados
            if (current == '\\' && position + 1 < input.length()) {
                char next = input.charAt(position + 1);
                if (next == 'n') { str.append('\n'); position += 2; column += 2; continue; }
                if (next == 't') { str.append('\t'); position += 2; column += 2; continue; }
                if (next == '"') { str.append('"'); position += 2; column += 2; continue; }
                if (next == '\\') { str.append('\\'); position += 2; column += 2; continue; }
            }
            
            str.append(current);
            position++; column++;
        }
        
        if (!stringClosed) {
            System.err.printf("Error léxico [Línea %d:%d]: cadena sin cerrar%n", line, startColumn);
        }
        
        return new Token(TokenType.STRING_CONST, str.toString(), line, startColumn);
    }
}