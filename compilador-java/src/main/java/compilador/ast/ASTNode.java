package main.java.compilador.ast;

public interface ASTNode {
    <T> T accept(main.java.compilador.visitors.Visitor<T> visitor);
}
