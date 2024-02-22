// generated with ast extension for cup
// version 0.8
// 5/1/2024 0:48:7


package rs.ac.bg.etf.pp1.ast;

public class ForStatement extends Statement {

    private ForStart ForStart;
    private OptionalDesignatorStatementList OptionalDesignatorStatementList;
    private ForCondStart ForCondStart;
    private OptionalCondFact OptionalCondFact;
    private ForDesignatorStart ForDesignatorStart;
    private ForDesignator ForDesignator;
    private ForBodyStart ForBodyStart;
    private ForBody ForBody;

    public ForStatement (ForStart ForStart, OptionalDesignatorStatementList OptionalDesignatorStatementList, ForCondStart ForCondStart, OptionalCondFact OptionalCondFact, ForDesignatorStart ForDesignatorStart, ForDesignator ForDesignator, ForBodyStart ForBodyStart, ForBody ForBody) {
        this.ForStart=ForStart;
        if(ForStart!=null) ForStart.setParent(this);
        this.OptionalDesignatorStatementList=OptionalDesignatorStatementList;
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.setParent(this);
        this.ForCondStart=ForCondStart;
        if(ForCondStart!=null) ForCondStart.setParent(this);
        this.OptionalCondFact=OptionalCondFact;
        if(OptionalCondFact!=null) OptionalCondFact.setParent(this);
        this.ForDesignatorStart=ForDesignatorStart;
        if(ForDesignatorStart!=null) ForDesignatorStart.setParent(this);
        this.ForDesignator=ForDesignator;
        if(ForDesignator!=null) ForDesignator.setParent(this);
        this.ForBodyStart=ForBodyStart;
        if(ForBodyStart!=null) ForBodyStart.setParent(this);
        this.ForBody=ForBody;
        if(ForBody!=null) ForBody.setParent(this);
    }

    public ForStart getForStart() {
        return ForStart;
    }

    public void setForStart(ForStart ForStart) {
        this.ForStart=ForStart;
    }

    public OptionalDesignatorStatementList getOptionalDesignatorStatementList() {
        return OptionalDesignatorStatementList;
    }

    public void setOptionalDesignatorStatementList(OptionalDesignatorStatementList OptionalDesignatorStatementList) {
        this.OptionalDesignatorStatementList=OptionalDesignatorStatementList;
    }

    public ForCondStart getForCondStart() {
        return ForCondStart;
    }

    public void setForCondStart(ForCondStart ForCondStart) {
        this.ForCondStart=ForCondStart;
    }

    public OptionalCondFact getOptionalCondFact() {
        return OptionalCondFact;
    }

    public void setOptionalCondFact(OptionalCondFact OptionalCondFact) {
        this.OptionalCondFact=OptionalCondFact;
    }

    public ForDesignatorStart getForDesignatorStart() {
        return ForDesignatorStart;
    }

    public void setForDesignatorStart(ForDesignatorStart ForDesignatorStart) {
        this.ForDesignatorStart=ForDesignatorStart;
    }

    public ForDesignator getForDesignator() {
        return ForDesignator;
    }

    public void setForDesignator(ForDesignator ForDesignator) {
        this.ForDesignator=ForDesignator;
    }

    public ForBodyStart getForBodyStart() {
        return ForBodyStart;
    }

    public void setForBodyStart(ForBodyStart ForBodyStart) {
        this.ForBodyStart=ForBodyStart;
    }

    public ForBody getForBody() {
        return ForBody;
    }

    public void setForBody(ForBody ForBody) {
        this.ForBody=ForBody;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForStart!=null) ForStart.accept(visitor);
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.accept(visitor);
        if(ForCondStart!=null) ForCondStart.accept(visitor);
        if(OptionalCondFact!=null) OptionalCondFact.accept(visitor);
        if(ForDesignatorStart!=null) ForDesignatorStart.accept(visitor);
        if(ForDesignator!=null) ForDesignator.accept(visitor);
        if(ForBodyStart!=null) ForBodyStart.accept(visitor);
        if(ForBody!=null) ForBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForStart!=null) ForStart.traverseTopDown(visitor);
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.traverseTopDown(visitor);
        if(ForCondStart!=null) ForCondStart.traverseTopDown(visitor);
        if(OptionalCondFact!=null) OptionalCondFact.traverseTopDown(visitor);
        if(ForDesignatorStart!=null) ForDesignatorStart.traverseTopDown(visitor);
        if(ForDesignator!=null) ForDesignator.traverseTopDown(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseTopDown(visitor);
        if(ForBody!=null) ForBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForStart!=null) ForStart.traverseBottomUp(visitor);
        if(OptionalDesignatorStatementList!=null) OptionalDesignatorStatementList.traverseBottomUp(visitor);
        if(ForCondStart!=null) ForCondStart.traverseBottomUp(visitor);
        if(OptionalCondFact!=null) OptionalCondFact.traverseBottomUp(visitor);
        if(ForDesignatorStart!=null) ForDesignatorStart.traverseBottomUp(visitor);
        if(ForDesignator!=null) ForDesignator.traverseBottomUp(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseBottomUp(visitor);
        if(ForBody!=null) ForBody.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStatement(\n");

        if(ForStart!=null)
            buffer.append(ForStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalDesignatorStatementList!=null)
            buffer.append(OptionalDesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondStart!=null)
            buffer.append(ForCondStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalCondFact!=null)
            buffer.append(OptionalCondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignatorStart!=null)
            buffer.append(ForDesignatorStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignator!=null)
            buffer.append(ForDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBodyStart!=null)
            buffer.append(ForBodyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBody!=null)
            buffer.append(ForBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStatement]");
        return buffer.toString();
    }
}
