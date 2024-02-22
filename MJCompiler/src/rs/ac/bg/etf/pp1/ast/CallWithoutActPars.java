// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class CallWithoutActPars extends Call {

    private CallStart CallStart;

    public CallWithoutActPars (CallStart CallStart) {
        this.CallStart=CallStart;
        if(CallStart!=null) CallStart.setParent(this);
    }

    public CallStart getCallStart() {
        return CallStart;
    }

    public void setCallStart(CallStart CallStart) {
        this.CallStart=CallStart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CallStart!=null) CallStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CallStart!=null) CallStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CallStart!=null) CallStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CallWithoutActPars(\n");

        if(CallStart!=null)
            buffer.append(CallStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CallWithoutActPars]");
        return buffer.toString();
    }
}
