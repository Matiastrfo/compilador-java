package main.java.compilador.ast;

import java.util.List;

public class Program implements ASTNode {
    public final List<Statement> statements;
    public Program(List<Statement> statements){
        this.statements = statements;
    }

    @Override
    public <T> T accept(main.java.compilador.visitors.Visitor<T> visitor) {
        return visitor.visitProgram(this);
    }
}
