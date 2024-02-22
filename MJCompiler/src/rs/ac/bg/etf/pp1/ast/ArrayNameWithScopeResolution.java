// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ArrayNameWithScopeResolution implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String scope;
    private String name;

    public ArrayNameWithScopeResolution (String scope, String name) {
        this.scope=scope;
        this.name=name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope=scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayNameWithScopeResolution(\n");

        buffer.append(" "+tab+scope);
        buffer.append("\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayNameWithScopeResolution]");
        return buffer.toString();
    }
}
