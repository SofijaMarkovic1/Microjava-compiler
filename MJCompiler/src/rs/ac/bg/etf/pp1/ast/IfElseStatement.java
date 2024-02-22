// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class IfElseStatement extends Statement {

    private Condition Condition;
    private IfBody IfBody;
    private Else Else;
    private Statement Statement;

    public IfElseStatement (Condition Condition, IfBody IfBody, Else Else, Statement Statement) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.IfBody=IfBody;
        if(IfBody!=null) IfBody.setParent(this);
        this.Else=Else;
        if(Else!=null) Else.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public IfBody getIfBody() {
        return IfBody;
    }

    public void setIfBody(IfBody IfBody) {
        this.IfBody=IfBody;
    }

    public Else getElse() {
        return Else;
    }

    public void setElse(Else Else) {
        this.Else=Else;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(IfBody!=null) IfBody.accept(visitor);
        if(Else!=null) Else.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(IfBody!=null) IfBody.traverseTopDown(visitor);
        if(Else!=null) Else.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(IfBody!=null) IfBody.traverseBottomUp(visitor);
        if(Else!=null) Else.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStatement(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfBody!=null)
            buffer.append(IfBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Else!=null)
            buffer.append(Else.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStatement]");
        return buffer.toString();
    }
}
