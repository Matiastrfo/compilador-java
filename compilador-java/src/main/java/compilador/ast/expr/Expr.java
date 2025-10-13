package main.java.compilador.ast.expr;
import compilador.visitors.Visitor;

public interface Expr {
    <T> T accept(compilador.visitors.Visitor<T> visitor);
}
