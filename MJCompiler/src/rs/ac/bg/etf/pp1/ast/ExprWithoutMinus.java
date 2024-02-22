// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ExprWithoutMinus extends Expr {

    private Term Term;
    private AddOpList AddOpList;

    public ExprWithoutMinus (Term Term, AddOpList AddOpList) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AddOpList=AddOpList;
        if(AddOpList!=null) AddOpList.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
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
        if(Term!=null) Term.accept(visitor);
        if(AddOpList!=null) AddOpList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AddOpList!=null) AddOpList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AddOpList!=null) AddOpList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprWithoutMinus(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOpList!=null)
            buffer.append(AddOpList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprWithoutMinus]");
        return buffer.toString();
    }
}
