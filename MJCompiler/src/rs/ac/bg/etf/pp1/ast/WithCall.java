// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class WithCall extends OptionalCall {

    private Call Call;

    public WithCall (Call Call) {
        this.Call=Call;
        if(Call!=null) Call.setParent(this);
    }

    public Call getCall() {
        return Call;
    }

    public void setCall(Call Call) {
        this.Call=Call;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Call!=null) Call.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Call!=null) Call.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Call!=null) Call.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WithCall(\n");

        if(Call!=null)
            buffer.append(Call.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WithCall]");
        return buffer.toString();
    }
}
