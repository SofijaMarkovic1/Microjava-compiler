// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ValidCondition extends Condition {

    private OrList OrList;

    public ValidCondition (OrList OrList) {
        this.OrList=OrList;
        if(OrList!=null) OrList.setParent(this);
    }

    public OrList getOrList() {
        return OrList;
    }

    public void setOrList(OrList OrList) {
        this.OrList=OrList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OrList!=null) OrList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OrList!=null) OrList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OrList!=null) OrList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ValidCondition(\n");

        if(OrList!=null)
            buffer.append(OrList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ValidCondition]");
        return buffer.toString();
    }
}
