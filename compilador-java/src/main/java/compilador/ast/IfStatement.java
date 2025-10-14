package main.java.compilador.ast;

public class IfStatement implements Statement {
    public final main.java.compilador.ast.expr.Expr condition;
    public final Statement thenStmt;
    public final Statement elseStmt; // puede ser null

    public IfStatement(main.java.compilador.ast.expr.Expr condition, Statement thenStmt, Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitIf(this);
    }
}
