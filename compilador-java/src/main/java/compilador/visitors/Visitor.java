package main.java.compilador.visitors;

import main.java.compilador.ast.*;
import main.java.compilador.ast.expr.*;

public interface Visitor<T> {
    // Program y Statements
    T visitProgram(Program p);
    T visitDeclaration(Declaration d);
    T visitAssignment(Assignment a);
    T visitIf(IfStatement ifStmt);
    T visitWhile(While whileStmt);
    T visitRead(Read readStmt);
    T visitWrite(Write writeStmt);
    
    // Expresiones
    T visitBinaryExpr(BinaryExpr expr);
    T visitLiteral(Literal lit);
    T visitVariable(Variable var);
}