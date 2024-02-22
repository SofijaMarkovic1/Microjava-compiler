// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class CondTerm implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private AndList AndList;

    public CondTerm (AndList AndList) {
        this.AndList=AndList;
        if(AndList!=null) AndList.setParent(this);
    }

    public AndList getAndList() {
        return AndList;
    }

    public void setAndList(AndList AndList) {
        this.AndList=AndList;
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
        if(AndList!=null) AndList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AndList!=null) AndList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AndList!=null) AndList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTerm(\n");

        if(AndList!=null)
            buffer.append(AndList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTerm]");
        return buffer.toString();
    }
}
