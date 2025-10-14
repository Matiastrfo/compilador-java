package main.java.compilador.ast.expr;

public interface Expr {
    <T> T accept(main.java.compilador.visitors.Visitor<T> visitor);
}
