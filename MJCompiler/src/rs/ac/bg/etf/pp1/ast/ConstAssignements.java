// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ConstAssignements extends ConstAssignementList {

    private ConstAssignementList ConstAssignementList;
    private Assignment Assignment;

    public ConstAssignements (ConstAssignementList ConstAssignementList, Assignment Assignment) {
        this.ConstAssignementList=ConstAssignementList;
        if(ConstAssignementList!=null) ConstAssignementList.setParent(this);
        this.Assignment=Assignment;
        if(Assignment!=null) Assignment.setParent(this);
    }

    public ConstAssignementList getConstAssignementList() {
        return ConstAssignementList;
    }

    public void setConstAssignementList(ConstAssignementList ConstAssignementList) {
        this.ConstAssignementList=ConstAssignementList;
    }

    public Assignment getAssignment() {
        return Assignment;
    }

    public void setAssignment(Assignment Assignment) {
        this.Assignment=Assignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstAssignementList!=null) ConstAssignementList.accept(visitor);
        if(Assignment!=null) Assignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstAssignementList!=null) ConstAssignementList.traverseTopDown(visitor);
        if(Assignment!=null) Assignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstAssignementList!=null) ConstAssignementList.traverseBottomUp(visitor);
        if(Assignment!=null) Assignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstAssignements(\n");

        if(ConstAssignementList!=null)
            buffer.append(ConstAssignementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignment!=null)
            buffer.append(Assignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstAssignements]");
        return buffer.toString();
    }
}
