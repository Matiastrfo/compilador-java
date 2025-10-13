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
            
            // Operadores compuestos
            if (current == '=' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token("EQ", "==", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '!' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token("NEQ", "!=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '>' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token("GTE", ">=", line, column));
                position += 2; column += 2; continue;
            }
            if (current == '<' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token("LTE", "<=", line, column));
                position += 2; column += 2; continue;
            }
            
            // Cadenas entre comillas
            if (current == '"') {
                tokens.add(readString()); continue;
            }
            
            // Identificadores
            if (Character.isLetter(current) || current == '_') {
                tokens.add(readIdentifier()); continue;
            }
            
            // Números
            if (Character.isDigit(current)) {
                tokens.add(readNumber()); continue;
            }
            
            // Operadores simples
            if ("+-*/=<>!;(),{}".indexOf(current) != -1) {
                tokens.add(new Token("OP", String.valueOf(current), line, column));
                position++; column++; continue;
            }
            
            // Error - carácter inesperado
            System.err.printf("Error léxico [Línea %d:%d]: carácter inesperado '%c'%n", line, column, current);
            position++; column++;
        }
        
        tokens.add(new Token("EOF", "", line, column));
        return tokens;
    }
    
    private Token readIdentifier() {
        int start = position, startColumn = column;
        while (position < input.length() && 
               (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            position++; column++;
        }
        String text = input.substring(start, position);
        
        // Palabras reservadas
        if (text.equals("long")) return new Token("LONG", text, line, startColumn);
        if (text.equals("double")) return new Token("DOUBLE", text, line, startColumn);
        if (text.equals("if")) return new Token("IF", text, line, startColumn);
        if (text.equals("then")) return new Token("THEN", text, line, startColumn);
        if (text.equals("else")) return new Token("ELSE", text, line, startColumn);
        if (text.equals("while")) return new Token("WHILE", text, line, startColumn);
        if (text.equals("read")) return new Token("READ", text, line, startColumn);
        if (text.equals("write")) return new Token("WRITE", text, line, startColumn);
        if (text.equals("true")) return new Token("TRUE", text, line, startColumn);
        if (text.equals("false")) return new Token("FALSE", text, line, startColumn);
        if (text.equals("break")) return new Token("BREAK", text, line, startColumn);
        
        return new Token("ID", text, line, startColumn);
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
            return new Token("DOUBLE_LIT", input.substring(start, position), line, startColumn);
        }
        
        return new Token("INT", input.substring(start, position), line, startColumn);
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
        
        return new Token("STRING", str.toString(), line, startColumn);
    }
}