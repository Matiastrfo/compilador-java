package main.java.compilador.visitors;

import main.java.compilador.ast.*;
import main.java.compilador.ast.expr.*;
import main.java.compilador.symtab.*;
import java.util.*;

public class SymbolTableVisitor implements Visitor<Void> {
    private final SymbolTable symbolTable = new SymbolTable();
    private String currentScope = "global";

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public Void visitProgram(Program p) {
        for (Statement stmt : p.statements) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visitDeclaration(Declaration d) {
        for (String id : d.ids) {
            Symbol symbol = new Symbol(id, d.type, currentScope, 0); // Línea 0 por simplicidad
            try {
                symbolTable.add(symbol);
                System.out.println("✅ Símbolo agregado: " + id + " (" + d.type + ")");
            } catch (RuntimeException e) {
                System.err.println("❌ " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Void visitAssignment(Assignment a) {
        // Verificar que la variable esté declarada
        Symbol symbol = symbolTable.get(a.id);
        if (symbol == null) {
            System.err.println("❌ Variable no declarada: " + a.id);
        } else {
            System.out.println("✅ Asignación a variable declarada: " + a.id);
        }
        a.expr.accept(this);
        return null;
    }

    @Override
    public Void visitIf(IfStatement ifStmt) {
        ifStmt.condition.accept(this);
        ifStmt.thenStmt.accept(this);
        if (ifStmt.elseStmt != null) {
            ifStmt.elseStmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visitWhile(While whileStmt) {
        whileStmt.condition.accept(this);
        whileStmt.body.accept(this);
        return null;
    }

    @Override
    public Void visitRead(Read readStmt) {
        Symbol symbol = symbolTable.get(readStmt.variableName);
        if (symbol == null) {
            System.err.println("❌ Variable no declarada en read: " + readStmt.variableName);
        } else {
            System.out.println("✅ Read de variable declarada: " + readStmt.variableName);
        }
        return null;
    }

    @Override
    public Void visitWrite(Write writeStmt) {
        writeStmt.expression.accept(this);
        return null;
    }

    @Override
    public Void visitBinaryExpr(BinaryExpr expr) {
        expr.left.accept(this);
        expr.right.accept(this);
        return null;
    }

    @Override
    public Void visitLiteral(Literal lit) {
        return null; // Los literales no se agregan a la tabla de símbolos
    }

    @Override
    public Void visitVariable(Variable var) {
        Symbol symbol = symbolTable.get(var.name);
        if (symbol == null) {
            System.err.println("❌ Variable no declarada: " + var.name);
        }
        return null;
    }
}
