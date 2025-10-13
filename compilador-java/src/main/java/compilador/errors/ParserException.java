package main.java.compilador.errors;

public class ParserException extends RuntimeException {
    private final int line;
    private final int column;

    public ParserException(String msg, int line, int column) {
        super(String.format("Error sintáctico [línea %d, col %d]: %s", line, column, msg));
        this.line = line;
        this.column = column;
    }
}
