package main.java.compilador.ast.expr;

public class Literal implements Expr {
    public final Object value; // Integer, Double, String
    public Literal(Object value){ this.value = value; }
    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitLiteral(this);
    }
}
