package main.java.compilador.visitors;

import compilador.ast.*;
import compilador.ast.expr.*;
import java.util.*;

public class ASTPrinter implements Visitor<Void> {
    private int indent = 0;
    private void p(String s){
        for(int i=0;i<indent;i++) System.out.print("  ");
        System.out.println(s);
    }

    @Override public Void visitProgram(compilador.ast.Program p){
        p("Program");
        indent++;
        for(Statement s : p.statements) s.accept(this);
        indent--;
        return null;
    }

    // implementá los demás visitXXX (declaration, assignment, exprs...)
    @Override public Void visitDeclaration(compilador.ast.Declaration d) {
        p("Declaration: " + d.type + " " + d.ids);
        return null;
    }

    @Override public Void visitAssignment(compilador.ast.Assignment a) {
        p("Assignment: " + a.id);
        indent++; a.expr.accept(this); indent--;
        return null;
    }

    // y los métodos para expr...
}
