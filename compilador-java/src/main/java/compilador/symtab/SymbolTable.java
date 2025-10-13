package main.java.compilador.symtab;

import java.util.*;

public class SymbolTable {
    private final Map<String, Symbol> symbols = new HashMap<>();

    public void add(Symbol s){
        if (symbols.containsKey(s.name)) {
            throw new RuntimeException("Variable ya declarada: " + s.name);
        }
        symbols.put(s.name, s);
    }

    public Symbol get(String name){
        return symbols.get(name);
    }

    public Collection<Symbol> all() { return symbols.values(); }
}
