// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class OptionalVarDecls extends OptionalVarDecl {

    private OptionalVarDecl OptionalVarDecl;
    private VarDecl VarDecl;

    public OptionalVarDecls (OptionalVarDecl OptionalVarDecl, VarDecl VarDecl) {
        this.OptionalVarDecl=OptionalVarDecl;
        if(OptionalVarDecl!=null) OptionalVarDecl.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public OptionalVarDecl getOptionalVarDecl() {
        return OptionalVarDecl;
    }

    public void setOptionalVarDecl(OptionalVarDecl OptionalVarDecl) {
        this.OptionalVarDecl=OptionalVarDecl;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalVarDecl!=null) OptionalVarDecl.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalVarDecl!=null) OptionalVarDecl.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalVarDecl!=null) OptionalVarDecl.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptionalVarDecls(\n");

        if(OptionalVarDecl!=null)
            buffer.append(OptionalVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptionalVarDecls]");
        return buffer.toString();
    }
}
