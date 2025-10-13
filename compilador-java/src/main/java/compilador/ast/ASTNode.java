package main.java.compilador.ast;

public interface ASTNode {
    <T> T accept(compilador.visitors.Visitor<T> visitor);
}
