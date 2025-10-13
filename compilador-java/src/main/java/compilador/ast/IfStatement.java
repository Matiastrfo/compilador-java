package main.java.compilador.ast;

public class IfStatement implements Statement {
    public final compilador.ast.expr.Expr condition;
    public final Statement thenStmt;
    public final Statement elseStmt; // puede ser null

    public IfStatement(compilador.ast.expr.Expr condition, Statement thenStmt, Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public <T> T accept(compilador.visitors.Visitor<T> visitor) {
        return visitor.visitIf(this);
    }
}
