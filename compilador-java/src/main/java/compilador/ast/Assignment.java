package main.java.compilador.ast;

public class Assignment implements Statement {
    public final String id;
    public final compilador.ast.expr.Expr expr;

    public Assignment(String id, compilador.ast.expr.Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public <T> T accept(compilador.visitors.Visitor<T> visitor) {
        return visitor.visitAssignment(this);
    }
}
