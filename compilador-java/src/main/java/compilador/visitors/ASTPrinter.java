package main.java.compilador.visitors;

import main.java.compilador.ast.*;
import main.java.compilador.ast.expr.*;
import java.util.*;

public class ASTPrinter implements Visitor<Void> {
    private int indent = 0;
    
    private void p(String s) {
        for(int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println(s);
    }

    @Override 
    public Void visitProgram(Program p) {
        p("Program");
        indent++;
        for(Statement s : p.statements) s.accept(this);
        indent--;
        return null;
    }

    @Override 
    public Void visitDeclaration(Declaration d) {
        p("Declaration: " + d.type + " " + d.ids);
        return null;
    }

    @Override 
    public Void visitAssignment(Assignment a) {
        p("Assignment: " + a.id + " = ");
        indent++; 
        a.expr.accept(this); 
        indent--;
        return null;
    }

    @Override 
    public Void visitIf(IfStatement ifStmt) {
        p("IfStatement");
        indent++;
        p("Condition:");
        indent++; 
        ifStmt.condition.accept(this); 
        indent--;
        p("Then:");
        indent++; 
        ifStmt.thenStmt.accept(this); 
        indent--;
        if (ifStmt.elseStmt != null) {
            p("Else:");
            indent++; 
            ifStmt.elseStmt.accept(this); 
            indent--;
        }
        indent--;
        return null;
    }

    @Override 
    public Void visitWhile(While whileStmt) {
        p("WhileStatement");
        indent++;
        p("Condition:");
        indent++; 
        whileStmt.condition.accept(this); 
        indent--;
        p("Body:");
        indent++; 
        whileStmt.body.accept(this); 
        indent--;
        indent--;
        return null;
    }

    @Override 
    public Void visitRead(Read readStmt) {
        p("Read: " + readStmt.variableName);
        return null;
    }

    @Override 
    public Void visitWrite(Write writeStmt) {
        p("Write:");
        indent++;
        writeStmt.expression.accept(this);
        indent--;
        return null;
    }

    @Override 
    public Void visitBinaryExpr(BinaryExpr expr) {
        p("BinaryExpr: " + expr.op);
        indent++;
        expr.left.accept(this);
        expr.right.accept(this);
        indent--;
        return null;
    }

    @Override 
    public Void visitLiteral(Literal lit) {
        p("Literal: " + lit.value + " (" + lit.value.getClass().getSimpleName() + ")");
        return null;
    }

    @Override 
    public Void visitVariable(Variable var) {
        p("Variable: " + var.name);
        return null;
    }
}
