// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class OrCondTerm extends OrList {

    private OrList OrList;
    private Or Or;
    private CondTerm CondTerm;

    public OrCondTerm (OrList OrList, Or Or, CondTerm CondTerm) {
        this.OrList=OrList;
        if(OrList!=null) OrList.setParent(this);
        this.Or=Or;
        if(Or!=null) Or.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public OrList getOrList() {
        return OrList;
    }

    public void setOrList(OrList OrList) {
        this.OrList=OrList;
    }

    public Or getOr() {
        return Or;
    }

    public void setOr(Or Or) {
        this.Or=Or;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OrList!=null) OrList.accept(visitor);
        if(Or!=null) Or.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OrList!=null) OrList.traverseTopDown(visitor);
        if(Or!=null) Or.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OrList!=null) OrList.traverseBottomUp(visitor);
        if(Or!=null) Or.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OrCondTerm(\n");

        if(OrList!=null)
            buffer.append(OrList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Or!=null)
            buffer.append(Or.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OrCondTerm]");
        return buffer.toString();
    }
}
