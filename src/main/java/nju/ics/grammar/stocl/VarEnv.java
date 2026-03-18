package nju.ics.grammar.stocl;

import java.util.*;

public class VarEnv<T> {

    private final Deque<Map<String, T>> scopes = new ArrayDeque<>();

    public VarEnv() {

    }

    public void pushScope() {
        scopes.push(new HashMap<>());
    }

    public void popScope() {
        scopes.pop();
    }

    public void put(String name, T content) {
        scopes.peek().put(name, content);
    }

    public void remove(String name) {
        scopes.peek().remove(name);
    }

    public T resolve(String name) {
        for (Map<String, T> scope : scopes) {
            T b = scope.get(name);
            if (b != null) return b;
        }
        return null;
    }

    public List<String> getCurScopeVarNames() {
        return new ArrayList<>(scopes.peek().keySet());
    }




}
