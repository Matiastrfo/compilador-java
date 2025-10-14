package main.java.compilador.ast;

public class Read implements Statement {
    public final String variableName;

    public Read(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitRead(this);
    }
}
