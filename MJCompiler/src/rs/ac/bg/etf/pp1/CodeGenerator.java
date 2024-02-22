package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;


public class CodeGenerator extends VisitorAdaptor {
	Stack<Integer> ifStatementFixes = new Stack<>();
	Stack<Integer> ifBodyFixes = new Stack<>();
	Stack<Integer> jumpToForBodyFixes = new Stack<>();
	Stack<Integer> jumpToAfterForFixes = new Stack<>();
	Stack<Integer> forCondAdr = new Stack<>();
	Stack<Integer> forDesignatorStartAdr = new Stack<>();
	Stack<Integer> breakFixes = new Stack<>();
	Stack<Integer> orFixes = new Stack<>();
	Stack<Integer> andFixes = new Stack<>();
	Obj niz1;
	Obj niz;
	
	Map<Integer, Obj> designatori = new HashMap<>();
	Obj brojac1 = SemanticAnalyzer.brojac1;
	Obj brojac2 = SemanticAnalyzer.brojac2;
	int dodelaNiza=0;
	private int mainPC;
	
	public int getMainPC() {
		return mainPC;
	}
	
	public void visit(ReadStatement readStatement) {
		if (readStatement.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else if (readStatement.getDesignator().obj.getType() == Tab.intType) {
			Code.put(Code.read);
		} else if(readStatement.getDesignator().obj.getType() == MyTab.boolType) {
			Code.put(Code.read);
		}
		if(readStatement.getDesignator() instanceof DesignatorWithoutScopeResolution || readStatement.getDesignator() instanceof DesignatorWithScopeResolution) {
			Code.store(readStatement.getDesignator().obj);
		}
		else {
			if (readStatement.getDesignator().obj.getType() == Tab.charType) {
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		}
		
	}
	
	public void visit(PrintStatementWithoutNumConst print) {
		if(print.getExpr().struct==Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if(print.getExpr().struct==Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if(print.getExpr().struct == MyTab.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}
	
	
	public void visit(FactorNumConst numConst) {
		
		Obj con = Tab.insert(Obj.Con, "$", numConst.struct);
		con.setLevel(0);
		con.setAdr(numConst.getValue());
		Code.load(con);
	}
	
	public void visit(FactorCharConst charConst) {
		
		Obj con = Tab.insert(Obj.Con, "$", charConst.struct);
		con.setLevel(0);
		con.setAdr(charConst.getValue());
		Code.load(con);
	}
	
	public void visit(FactorBoolConst boolConst) {
		
		Obj con = Tab.insert(Obj.Con, "$", boolConst.struct);
		con.setLevel(0);
		con.setAdr(boolConst.getValue());
		Code.load(con);
	}
	
	public void visit(TypedMethod methodTypeName) {
		if("main".equalsIgnoreCase(methodTypeName.getMethodName())){
			mainPC = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		// Generate the entry
		
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
		
	}
	public void visit(VoidMethod methodTypeName) {
		if("main".equalsIgnoreCase(methodTypeName.getMethodName())){
			mainPC = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
		
	}
	
	public void visit(MethodDeclaration method) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AssignValue assignement) {
		 if(assignement.getDesignator().obj.getName().equals("niz")) {
		 }
		 Code.store(assignement.getDesignator().obj);
	}
	public void visit(IncrementDesignator designatorIncrement) {
		Obj obj = designatorIncrement.getDesignator().obj;
		if(designatorIncrement.getDesignator() instanceof DesignatorWithoutScopeResolution || designatorIncrement.getDesignator() instanceof DesignatorWithScopeResolution) {
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(obj);
		}
		else {
			if(designatorIncrement.getDesignator() instanceof DesignatorWithScopeResolutionAndIndexing) {
				DesignatorWithScopeResolutionAndIndexing d = (DesignatorWithScopeResolutionAndIndexing)designatorIncrement.getDesignator();
				Code.put(Code.dup2);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.baload);
				else Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.add);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.bastore);
				else Code.put(Code.astore);
			}
			else {
				DesignatorWithoutScopeResolutionAndIndexing d = (DesignatorWithoutScopeResolutionAndIndexing)designatorIncrement.getDesignator();
				Code.put(Code.dup2);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.baload);
				else Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.add);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.bastore);
				else Code.put(Code.astore);
			}
			
		}
	}

	public void visit(DecrementDesignator designatorDecrement) {
		Obj obj = designatorDecrement.getDesignator().obj;
		if(designatorDecrement.getDesignator() instanceof DesignatorWithoutScopeResolution || designatorDecrement.getDesignator() instanceof DesignatorWithScopeResolution) {
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.store(obj);
		}
		else {
			if(designatorDecrement.getDesignator() instanceof DesignatorWithScopeResolutionAndIndexing) {
				DesignatorWithScopeResolutionAndIndexing d = (DesignatorWithScopeResolutionAndIndexing)designatorDecrement.getDesignator();
				Code.put(Code.dup2);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.baload);
				else Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.sub);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.bastore);
				else Code.put(Code.astore);
			}
			else {
				DesignatorWithoutScopeResolutionAndIndexing d = (DesignatorWithoutScopeResolutionAndIndexing)designatorDecrement.getDesignator();
				Code.put(Code.dup2);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.baload);
				else Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.sub);
				if(d.obj.getType().getElemType()==Tab.charType) Code.put(Code.bastore);
				else Code.put(Code.astore);
			}
			
		}
	}
	
	public void visit(MinusTerm expr) {
		Code.put(Code.neg);
	}
	
	public void visit(ArrayName name) {
		if(name.obj.getName().equals("niz")) {
		}
		Code.load(name.obj);
		
	}
	
	public void visit(ArrayNameWithScopeResolution name) {
		Code.load(name.obj);
	}
	
	public void visit(DesignatorWithoutScopeResolution designator) {
		SyntaxNode parent = designator.getParent();
		if(!(parent instanceof AssignValue) && !(parent instanceof Function) && !(parent instanceof WithDesignator) && !(parent instanceof ArrayAssignement) && !(parent instanceof ReadStatement)) {
			Code.load(designator.obj);
		}
		if(parent instanceof WithDesignator) {
			if(dodelaNiza==0) designatori.clear();
			designatori.put(dodelaNiza, designator.obj);
			dodelaNiza++;
		}
		if(parent instanceof ArrayAssignement) {
			if(niz==null) niz = designator.obj;
			else niz1 = designator.obj;
		}
	}
	
	public void visit(DesignatorWithScopeResolution designator) {
		SyntaxNode parent = designator.getParent();
		if(!(parent instanceof AssignValue) && !(parent instanceof Function) && !(parent instanceof WithDesignator) && !(parent instanceof ArrayAssignement) && !(parent instanceof ReadStatement)) {
			Code.load(designator.obj);
		}
		if(parent instanceof WithDesignator) {
			if(dodelaNiza==0) designatori.clear();
			designatori.put(dodelaNiza, designator.obj);
			dodelaNiza++;
		}
		if(parent instanceof ArrayAssignement) {
			if(niz==null) niz = designator.obj;
			else niz1 = designator.obj;
		}
	}
	public void visit(WithoutDesignator designator) {
		if(dodelaNiza==0) designatori.clear();
		designatori.put(dodelaNiza, Tab.noObj);
		dodelaNiza++;
	}
	
	public void visit(ArrayAssignement arr) {
		if(dodelaNiza==0) designatori.clear();
		dodelaNiza=0;
		Obj arr1 = niz;
		Obj arr2 = niz1;
		niz = null;
		niz1 = null;
		for(int i=0; i<designatori.size(); i++) System.out.println(i + "-" + designatori.get(i).getName());
		System.out.println("niz-" + arr1.getName());
		System.out.println("niz1-" + arr2.getName());
		int i;
		for(i=designatori.size()-1; i>=0; i--) {
			if(designatori.get(i)==Tab.noObj) continue;
			Obj o = designatori.get(i);
			if(o.getType().getKind()==Struct.Array) {
				Code.load(arr2);
				Code.loadConst(i);
				if(arr2.getType().getElemType()==Tab.charType)Code.put(Code.baload);
				else Code.put(Code.aload);
				if(o.getType().getElemType()==Tab.charType) Code.put(Code.bastore);
				else Code.put(Code.astore);
			}
			else {
				Code.load(arr2);
				Code.loadConst(i);
				if(arr2.getType().getElemType()==Tab.charType)Code.put(Code.baload);
				else Code.put(Code.aload);
				Code.store(designatori.get(i));
			}
		}
		int adr1;
		int adr2;
		
		Code.loadConst(0);
		Code.store(brojac1);
		Code.loadConst(designatori.size());
		Code.store(brojac2);
		//provera da nismo iskocili iz range-a za prvi niz
		int adrPocetka = Code.pc;
		Code.load(brojac1);
		Code.load(arr1);
		Code.put(Code.arraylength);
		adr1= Code.pc+1;
		Code.putFalseJump(Code.ne, 0);
		//provera da nismo iskocili iz range-a za drugi niz
		Code.load(brojac2);
		Code.load(arr2);
		Code.put(Code.arraylength);
		adr2= Code.pc+1;
		Code.putFalseJump(Code.ne, 0);
		//nismo iskocili
		Code.load(arr1);
		Code.load(brojac1);
		Code.load(arr2);
		Code.load(brojac2);
		if(arr2.getType().getElemType()==Tab.charType) Code.put(Code.baload);
		else Code.put(Code.aload);
		if(arr1.getType().getElemType()==Tab.charType) Code.put(Code.bastore);
		else Code.put(Code.astore);
		Code.load(brojac1);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(brojac1);
		Code.load(brojac2);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(brojac2);
		Code.putJump(adrPocetka);
		Code.fixup(adr1);
		Code.fixup(adr2);
		
	}
	
	
	public void visit(DesignatorWithoutScopeResolutionAndIndexing designator) {
		SyntaxNode parent = designator.getParent();
		if(!(parent instanceof AssignValue) && !(parent instanceof Function) && !(parent instanceof IncrementDesignator) && !(parent instanceof DecrementDesignator) && !(parent instanceof ReadStatement) && !(parent instanceof WithDesignator)) {
			if(designator.obj.getType()==Tab.charType) Code.put(Code.baload);
			else Code.put(Code.aload);
		}
		if(parent instanceof WithDesignator) {
			if(dodelaNiza==0) designatori.clear();
			designatori.put(dodelaNiza, designator.getArrayName().obj);
			dodelaNiza++;
		}
	}
	public void visit(DesignatorWithScopeResolutionAndIndexing designator) {
		SyntaxNode parent = designator.getParent();
		if(!(parent instanceof AssignValue) && !(parent instanceof Function) && !(parent instanceof IncrementDesignator) && !(parent instanceof DecrementDesignator) && !(parent instanceof ReadStatement) && !(parent instanceof WithDesignator)) {
			if(designator.obj.getType()==Tab.charType) Code.put(Code.baload);
			else Code.put(Code.aload);
		}
		if(parent instanceof WithDesignator) {
			if(dodelaNiza==0) designatori.clear();
			designatori.put(dodelaNiza, designator.getArrayNameWithScopeResolution().obj);
			dodelaNiza++;
		}
		
	}
	
	public void visit(Function func) {
		if(func.getDesignator().obj.getName().equals("ord") || func.getDesignator().obj.getName().equals("chr")) return;
		else if(func.getDesignator().obj.getName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		Obj funcObj = func.getDesignator().obj;
		int offset = funcObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(FunctionCall func) {
		if(func.getDesignator().obj.getName().equals("ord") || func.getDesignator().obj.getName().equals("chr")) return;
		else if(func.getDesignator().obj.getName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		Obj funcObj = func.getDesignator().obj;
		int offset = funcObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(func.getDesignator().obj.getType()!=Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	public void visit(FactorVariable var) {
		//Code.load(var.getDesignator().obj);
	}
	
	public void visit(ConstructorArray arr) {
		Code.put(Code.newarray);
		if (arr.getType().struct == Tab.intType || arr.getType().struct == MyTab.boolType) {
			Code.put(1);
		} else {
			Code.put(0);
		}
	}
	
	public void visit(ReturnStatementWithExpr returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ReturnStatement returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(MulFactor mul) {
		if(mul.getMulOp() instanceof Mul) {
			Code.put(Code.mul);
		} else if(mul.getMulOp() instanceof Div) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}
	
	public void visit(AddOpTerm add) {
		if(add.getAddOp() instanceof Plus) Code.put(Code.add);
		else Code.put(Code.sub);
	}
	
	public void visit(ValidCondition cond) {
		Code.put(Code.const_1);
		int adr = Code.pc+1;
		ifStatementFixes.push(adr);
		Code.putFalseJump(Code.eq, 0);
		while(!orFixes.empty()) {
			Code.fixup(orFixes.pop());
		}
		
	}
	public void visit(IfStatement ifstmt) {
		Code.fixup(ifStatementFixes.pop());
		while(!andFixes.empty()) Code.fixup(andFixes.pop());
	}
	
	public void visit(IfBody ifBody) {
		if(ifBody.getParent() instanceof IfElseStatement) {
			int adr = Code.pc+1;
			ifBodyFixes.push(adr);
			Code.putJump(0);
		}
	}
	public void visit(Else elseBrench) {
		if(!ifStatementFixes.empty()) Code.fixup(ifStatementFixes.pop());
		while(!andFixes.empty()) Code.fixup(andFixes.pop());
	}
	
	public void visit(IfElseStatement ifElse) {
		if(!ifBodyFixes.empty()) Code.fixup(ifBodyFixes.pop());
	}
	
	public void visit(OrCondTerm or) {
		Code.put(Code.add);
		Code.loadConst(1);
		int adr1=Code.pc+1;
		Code.putFalseJump(Code.eq, 0);
		//skaci na telo ifa
		int adr2 = Code.pc+1;
		orFixes.push(adr2);
		Code.putJump(0);
		Code.fixup(adr1);
		Code.loadConst(0);
	}
	public void visit(SingleCondTerm term) {
		Code.loadConst(0);
		Code.put(Code.add);
		Code.loadConst(1);
		int adr1=Code.pc+1;
		Code.putFalseJump(Code.eq, 0);
		//skaci na telo ifa
		int adr2 = Code.pc+1;
		orFixes.push(adr2);
		Code.putJump(0);
		Code.fixup(adr1);
		Code.loadConst(0);
	}
	public void visit(Or or) {
		while(!andFixes.empty()) {
			Code.fixup(andFixes.pop());
		}
	}
	
	public void visit(SingleCondFact and) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.const_1);
		int adr = Code.pc + 1;
		Code.putFalseJump(Code.eq, 0);
		Code.loadConst(0);
		int adr1 = Code.pc+1;
		andFixes.push(adr1);
		Code.putJump(0);
		Code.fixup(adr);
		Code.loadConst(1);
	}
	
	public void visit(AndCondFact and) {
		Code.put(Code.add);
		Code.put(Code.const_1);
		int adr = Code.pc + 1;
		Code.putFalseJump(Code.eq, 0);
		Code.loadConst(0);
		int adr1 = Code.pc+1;
		andFixes.push(adr1);
		Code.putJump(0);
		Code.fixup(adr);
		Code.loadConst(1);
	}
	
	public void visit(CondFactWithSingleExpr cond) {
		Code.loadConst(0);
		int adr = Code.pc + 1;
		Code.putFalseJump(Code.eq, 0);
		Code.loadConst(0);
		int adr1 = Code.pc+1;
		Code.putJump(0);
		Code.fixup(adr);
		Code.loadConst(1);		
		Code.fixup(adr1);
	}
	
	public void visit(CondFactWithMultipleExpr cond) {
		if(cond.getRelOp() instanceof Equals) {
			int adr = Code.pc + 1;
			Code.putFalseJump(Code.eq, 0);
			Code.put(Code.const_1);
			int adr1 = Code.pc+1;
			Code.putJump(0);
			Code.fixup(adr);
			Code.loadConst(0);		
			Code.fixup(adr1);
		} else if(cond.getRelOp() instanceof NotEquals) {
			int adr = Code.pc + 1;
			Code.putFalseJump(Code.ne, 0);
			Code.put(Code.const_1);
			int adr1 = Code.pc+1;
			Code.putJump(0);
			Code.fixup(adr);
			Code.loadConst(0);		
			Code.fixup(adr1);
		} else if(cond.getRelOp() instanceof Greater) {
			int adr = Code.pc + 1;
			Code.putFalseJump(Code.gt, 0);
			Code.put(Code.const_1);
			int adr1 = Code.pc+1;
			Code.putJump(0);
			Code.fixup(adr);
			Code.loadConst(0);		
			Code.fixup(adr1);
		} else if(cond.getRelOp() instanceof GreaterEqual) {
			int adr = Code.pc + 1;
			Code.putFalseJump(Code.ge, 0);
			Code.put(Code.const_1);
			int adr1 = Code.pc+1;
			Code.putJump(0);
			Code.fixup(adr);
			Code.loadConst(0);		
			Code.fixup(adr1);
		} else if(cond.getRelOp() instanceof Less) {
			int adr = Code.pc + 1;
			Code.putFalseJump(Code.lt, 0);
			Code.put(Code.const_1);
			int adr1 = Code.pc+1;
			Code.putJump(0);
			Code.fixup(adr);
			Code.loadConst(0);		
			Code.fixup(adr1);
		} else if(cond.getRelOp() instanceof LessEqual) {
			int adr = Code.pc + 1;
			Code.putFalseJump(Code.le, 0);
			Code.put(Code.const_1);
			int adr1 = Code.pc+1;
			Code.putJump(0);
			Code.fixup(adr);
			Code.loadConst(0);		
			Code.fixup(adr1);
		}
	}
	
	public void visit(ForCondStart forCondStart) {
		//cuvam adresu uslova
		int condStart = Code.pc;
		forCondAdr.push(condStart);
	}
	
	public void visit(WithoutCondFact forCond) {
		//skacem pravo na telo petlje
		int adr = Code.pc+1;
		jumpToForBodyFixes.push(adr);
		Code.putJump(0);
	}
	
	public void visit(WithCondFact forCond) {
		//ispitivanje uslova
		Code.put(Code.const_1);
		int adr = Code.pc+1;
		Code.putFalseJump(Code.eq, 0);
		// ako je cond==true
		int adr1 = Code.pc+1;
		jumpToForBodyFixes.push(adr1); // skace se bezuslovno na telo
		Code.putJump(0);
		//ako je cond==false
		Code.fixup(adr);
		int adr2 = Code.pc + 1;
		jumpToAfterForFixes.push(adr2); //skace se bezuslovno na posle for-a
		Code.putJump(0);
	}
	
	public void visit(ForBodyStart forBodyStart) {
		if(!jumpToForBodyFixes.empty()) {
			Code.fixup(jumpToForBodyFixes.pop());
		}
	}
	
	public void visit(ForBody forBodyEnd) {
		// skacem na for designatorStart
		if(!forDesignatorStartAdr.empty()) {
			Code.putJump(forDesignatorStartAdr.pop());
		}
	}
	
	public void visit(ForDesignatorStart forDesignatorStart) {
		forDesignatorStartAdr.push(Code.pc);
	}
	
	public void visit(ForDesignator forDesignator) {
		if(!forCondAdr.empty()) {
			Code.putJump(forCondAdr.pop());
		}
	}
	public void visit(ForStatement forEnd) {
		if(!breakFixes.empty()) {
			Code.fixup(breakFixes.pop());
		}
		if(!jumpToAfterForFixes.empty()) {
			Code.fixup(jumpToAfterForFixes.pop());
		}
		
	}
	
	public void visit(BreakStatement breakStmt) {
		int adr = Code.pc + 1;
		breakFixes.push(adr);
		Code.putJump(0);
	}
	
	public void visit(ContinueStatement continueStmt) {
		if(!forDesignatorStartAdr.empty()) Code.putJump(forDesignatorStartAdr.peek());
	}
	
	
	
	
	
}
