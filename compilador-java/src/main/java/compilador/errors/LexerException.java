package main.java.compilador.errors;

public class LexerException extends RuntimeException {
    private final int line;
    private final int column;

    public LexerException(String msg, int line, int column) {
        super(String.format("Error léxico [línea %d, col %d]: %s", line, column, msg));
        this.line = line;
        this.column = column;
    }

    public int getLine() { return line; }
    public int getColumn() { return column; }
}
