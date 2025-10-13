package main.java.compilador.lexer;

public enum TokenType {
    // palabras reservadas
    LONG, DOUBLE, IF, THEN, ELSE, WHILE, BREAK, READ, WRITE,
    // operadores y símbolos
    PLUS, MINUS, MULT, DIV, GT, LT, GE, LE, EQ, NEQ, DIFF,
    ASSIGN, LPAREN, RPAREN, LBRACE, RBRACE, SEMI, COMMA,
    // literales y id
    ID, INT_CONST, REAL_CONST, STRING_CONST,
    // especiales
    EOF
}
