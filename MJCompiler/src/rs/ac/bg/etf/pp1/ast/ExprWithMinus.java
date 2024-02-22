// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ExprWithMinus extends Expr {

    private MinusTerm MinusTerm;
    private AddOpList AddOpList;

    public ExprWithMinus (MinusTerm MinusTerm, AddOpList AddOpList) {
        this.MinusTerm=MinusTerm;
        if(MinusTerm!=null) MinusTerm.setParent(this);
        this.AddOpList=AddOpList;
        if(AddOpList!=null) AddOpList.setParent(this);
    }

    public MinusTerm getMinusTerm() {
        return MinusTerm;
    }

    public void setMinusTerm(MinusTerm MinusTerm) {
        this.MinusTerm=MinusTerm;
    }

    public AddOpList getAddOpList() {
        return AddOpList;
    }

    public void setAddOpList(AddOpList AddOpList) {
        this.AddOpList=AddOpList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MinusTerm!=null) MinusTerm.accept(visitor);
        if(AddOpList!=null) AddOpList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MinusTerm!=null) MinusTerm.traverseTopDown(visitor);
        if(AddOpList!=null) AddOpList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MinusTerm!=null) MinusTerm.traverseBottomUp(visitor);
        if(AddOpList!=null) AddOpList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprWithMinus(\n");

        if(MinusTerm!=null)
            buffer.append(MinusTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOpList!=null)
            buffer.append(AddOpList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprWithMinus]");
        return buffer.toString();
    }
}
