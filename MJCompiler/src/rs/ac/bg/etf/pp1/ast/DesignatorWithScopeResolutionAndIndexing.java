// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class DesignatorWithScopeResolutionAndIndexing extends Designator {

    private ArrayNameWithScopeResolution ArrayNameWithScopeResolution;
    private Expr Expr;

    public DesignatorWithScopeResolutionAndIndexing (ArrayNameWithScopeResolution ArrayNameWithScopeResolution, Expr Expr) {
        this.ArrayNameWithScopeResolution=ArrayNameWithScopeResolution;
        if(ArrayNameWithScopeResolution!=null) ArrayNameWithScopeResolution.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ArrayNameWithScopeResolution getArrayNameWithScopeResolution() {
        return ArrayNameWithScopeResolution;
    }

    public void setArrayNameWithScopeResolution(ArrayNameWithScopeResolution ArrayNameWithScopeResolution) {
        this.ArrayNameWithScopeResolution=ArrayNameWithScopeResolution;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayNameWithScopeResolution!=null) ArrayNameWithScopeResolution.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayNameWithScopeResolution!=null) ArrayNameWithScopeResolution.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayNameWithScopeResolution!=null) ArrayNameWithScopeResolution.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorWithScopeResolutionAndIndexing(\n");

        if(ArrayNameWithScopeResolution!=null)
            buffer.append(ArrayNameWithScopeResolution.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorWithScopeResolutionAndIndexing]");
        return buffer.toString();
    }
}
