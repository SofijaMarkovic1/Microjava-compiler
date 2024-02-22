// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclaration extends ConstDecl {

    private Type Type;
    private ConstAssignementList ConstAssignementList;

    public ConstDeclaration (Type Type, ConstAssignementList ConstAssignementList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstAssignementList=ConstAssignementList;
        if(ConstAssignementList!=null) ConstAssignementList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstAssignementList getConstAssignementList() {
        return ConstAssignementList;
    }

    public void setConstAssignementList(ConstAssignementList ConstAssignementList) {
        this.ConstAssignementList=ConstAssignementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstAssignementList!=null) ConstAssignementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstAssignementList!=null) ConstAssignementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstAssignementList!=null) ConstAssignementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssignementList!=null)
            buffer.append(ConstAssignementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclaration]");
        return buffer.toString();
    }
}
