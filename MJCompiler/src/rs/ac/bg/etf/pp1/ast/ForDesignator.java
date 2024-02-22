// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ForDesignator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private OptionalDesignatorStatementList OptionalDesignatorStatementList;

    public ForDesignator (OptionalDesignatorStatementList OptionalDesignatorStatementList) {
        this.OptionalDesignatorStatementList=OptionalDesignatorStatementList;
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.setParent(this);
    }

    public OptionalDesignatorStatementList getOptionalDesignatorStatementList() {
        return OptionalDesignatorStatementList;
    }

    public void setOptionalDesignatorStatementList(OptionalDesignatorStatementList OptionalDesignatorStatementList) {
        this.OptionalDesignatorStatementList=OptionalDesignatorStatementList;
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
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDesignator(\n");

        if(OptionalDesignatorStatementList!=null)
            buffer.append(OptionalDesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForDesignator]");
        return buffer.toString();
    }
}
