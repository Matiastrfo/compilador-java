package main.java.compilador.ast;

public class While implements Statement {
    public final main.java.compilador.ast.expr.Expr condition;
    public final Statement body;

    public While(main.java.compilador.ast.expr.Expr condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitWhile(this);
    }
}
