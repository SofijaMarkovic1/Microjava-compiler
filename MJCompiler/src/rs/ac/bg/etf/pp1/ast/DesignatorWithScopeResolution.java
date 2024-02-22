// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class DesignatorWithScopeResolution extends Designator {

    private String scope;
    private String name;

    public DesignatorWithScopeResolution (String scope, String name) {
        this.scope=scope;
        this.name=name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope=scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorWithScopeResolution(\n");

        buffer.append(" "+tab+scope);
        buffer.append("\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorWithScopeResolution]");
        return buffer.toString();
    }
}
