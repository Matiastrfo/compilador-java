package main.java.compilador.ast;
import java.util.List;

public class Declaration implements Statement {
    public final String type; // "long" o "double"
    public final List<String> ids;

    public Declaration(String type, List<String> ids){
        this.type = type;
        this.ids = ids;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitDeclaration(this);
    }
}
