package main.java.compilador.lexer;

public class Token {
    public final TokenType type;  // CAMBIAR: String → TokenType
    public final String text;
    public final int line;
    public final int column;
    
    public Token(TokenType type, String text, int line, int column) {  // CAMBIAR aquí también
        this.type = type;
        this.text = text;
        this.line = line;
        this.column = column;
    }
    
    @Override
    public String toString() {
        return String.format("Línea %d:%d - %-10s -> '%s'", line, column, type, text);
    }
}