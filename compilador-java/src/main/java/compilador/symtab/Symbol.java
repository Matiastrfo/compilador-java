package main.java.compilador.symtab;

public class Symbol {
    public final String name;
    public final String type; // "long" o "double"
    public Object value;
    public final String scope;
    public final int lineDecl;

    public Symbol(String name, String type, String scope, int lineDecl){
        this.name = name; this.type = type; this.scope = scope; this.lineDecl = lineDecl;
    }
}
