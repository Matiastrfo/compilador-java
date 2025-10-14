package main.java.compilador.ast;

public class Write implements Statement {
    public final main.java.compilador.ast.expr.Expr expression;

    public Write(main.java.compilador.ast.expr.Expr expression) {
        this.expression = expression;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitWrite(this);
    }
}
