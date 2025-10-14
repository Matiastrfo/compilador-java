package main.java.compilador.ast.expr;

public class BinaryExpr implements Expr {
    public final String op;
    public final Expr left, right;
    public BinaryExpr(String op, Expr left, Expr right){
        this.op = op; this.left = left; this.right = right;
    }
    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitBinaryExpr(this);
    }
}
