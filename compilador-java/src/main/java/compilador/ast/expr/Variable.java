package main.java.compilador.ast.expr;

public class Variable implements Expr {
    public final String name;
    public Variable(String name){ this.name = name; }
    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitVariable(this);
    }
}
