package main.java.compilador.ast;

public class Assignment implements Statement {
    public final String id;
    public final main.java.compilador.ast.expr.Expr expr;

    public Assignment(String id, main.java.compilador.ast.expr.Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitAssignment(this);
    }
}
