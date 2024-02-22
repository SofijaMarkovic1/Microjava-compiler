package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	int printCallCount = 0;
	int varDeclCount = 0;
	boolean returnFound = false;
	boolean errorDetected = false;
	Obj currentMethod = null;
	boolean currentMethodHasReturn = false;
	Scope currentMethodScope=null;
	Map<String, List<List<String>>> formalPars = new HashMap<>();
	String nameForCurrentMethod=null;
	int numOfActPars=0;
	boolean funcCall = false;
	boolean inForStatement = false;
	Stack<Integer> funcCalls = new Stack<>();
	Stack<Boolean> inFor = new Stack<>();
	List<String> namespacesNames = new ArrayList<>();
	String currentNamespace = null;
	static public Obj brojac1;
	static public Obj brojac2;
	
	Logger log = Logger.getLogger(getClass());
	
	public boolean passed() {
		return !errorDetected;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
    public void visit(PrintStatementWithoutNumConst print) {
		printCallCount++;
		if(print.getExpr().struct.getKind()!=Struct.Int && print.getExpr().struct.getKind()!=Struct.Char && print.getExpr().struct.getKind()!=Struct.Bool) {
    		report_error("Greska: Argument print naredbe mora biti tipa int, char ili bool", print);
		}
		else report_info("Uspesna print naredba", print);
	}
    public void visit(PrintStatementWithNumConst print) {
    	printCallCount++;
    	if(print.getExpr().struct.getKind()!=Struct.Int && print.getExpr().struct.getKind()!=Struct.Char && print.getExpr().struct.getKind()!=Struct.Bool) {
    		report_error("Greska: Argument print naredbe mora biti tipa int, char ili bool", print);
		}
    	else report_info("Uspesna print naredba", print);
	}
    public void visit(ReadStatement read) {
    	if(read.getDesignator().obj.getKind()!=Obj.Var && read.getDesignator().obj.getKind()!=Obj.Elem) {
    		report_error("Greska: Argument read naredbe mora biti promenljiva ili element niza", read);
    		return;
    	}
    	if(read.getDesignator().obj.getType().getKind()!=Struct.Int && read.getDesignator().obj.getType().getKind()!=Struct.Char && read.getDesignator().obj.getType().getKind()!=Struct.Bool) {
    		report_error("Greska: Argument read naredbe mora biti tipa int, char ili bool", read);
    		return;
    	}
    	report_info("Uspesna read naredba", read);
    }
    
    public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getProgramName(), Tab.noType);
		Tab.openScope();
		List<List<String>> list = new ArrayList<>();
		List<String> param = new ArrayList<>();
		param.add("i");
		param.add("int");
		list.add(param);
		formalPars.put("chr", list);
		List<List<String>> list1 = new ArrayList<>();
		List<String> param1 = new ArrayList<>();
		param1.add("ch");
		param1.add("char");
		list1.add(param1);
		formalPars.put("ord", list1);
		List<List<String>> list2 = new ArrayList<>();
		List<String> param2 = new ArrayList<>();
		param2.add("arr");
		param2.add("array");
		list2.add(param2);
		formalPars.put("len", list2);
		brojac1 = Tab.insert(Obj.Var, "brojac1", Tab.intType);
		brojac1.setLevel(0);
		brojac2 = Tab.insert(Obj.Var, "brojac2", Tab.intType);
		brojac2.setLevel(0);
	}
    
    public void visit(Program program) {
    	Tab.chainLocalSymbols(program.getProgramName().obj);
    	Tab.closeScope();
    	if(!formalPars.containsKey("main")) {
    		report_error("Greska: Program ne sadrzi main metodu. ", program);
    	}
    }
    
    public void visit(TypeWithoutScopeResolution type) {
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj) {
    		report_error("Greska: Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola.", type);
    		((Type)type).struct = Tab.noType;
    	}
    	else {
    		Struct s;
    		if(typeNode.getName().equals("int")) s = Tab.intType;
    		else if(typeNode.getName().equals("char")) s = Tab.charType;
    		else if(typeNode.getName().equals("bool")) s = MyTab.boolType;
    		else s = typeNode.getType();
    		if(typeNode.getKind() == Obj.Type) {
    			if(type.getParent() instanceof VariableDecl) {
    				((VariableDecl)type.getParent()).getType().struct = s;
    			}
    			else if(type.getParent() instanceof TypedMethod) {
    				((TypedMethod)type.getParent()).getType().struct = s;
    			}
    			else if(type.getParent() instanceof ConstDeclaration) {
    				((ConstDeclaration)type.getParent()).getType().struct = s;
    			}
    			else if(type.getParent() instanceof SingleFormPar) {
    				((SingleFormPar)type.getParent()).getType().struct = s;
    			}
    			else if(type.getParent() instanceof MultipleFormPars) {
    				((MultipleFormPars)type.getParent()).getType().struct = s;
    			}
    			else if(type.getParent() instanceof ConstructorArray) {
    				((ConstructorArray)type.getParent()).getType().struct = s;
    			}
    		}
    		else {
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip.", type);
    			if(type.getParent() instanceof Type) ((Type)type.getParent()).struct = Tab.noType;
    			else if (type.getParent() instanceof ConstructorArray) ((ConstructorArray)type.getParent()).struct = Tab.noType;
    			
    		}
    	}
    }
    
    public void visit(Var var){
		varDeclCount++;
		Obj object;
		if(currentMethodScope!=null) {
			object = currentMethodScope.findSymbol(var.getVarName());
			if(object!=null) {
				report_error("Greska: Ime " + var.getVarName() + " je vec deklarisano.", var);
				var.obj = Tab.noObj;
				return;
			}
		}
		else {
			object = Tab.find(var.getVarName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + var.getVarName() + " je vec deklarisano.", var);
				var.obj = Tab.noObj;
				return;
			}
		}
		
		Type type = null;
		SyntaxNode v = var;
		while(v.getParent() instanceof SignleVarDecl || v.getParent() instanceof VarDecls) {
			v = v.getParent();
		}
		if(v.getParent() instanceof VariableDecl) {
			VariableDecl variableDecl = (VariableDecl)v.getParent();
			type = variableDecl.getType();
		}
		else if(v.getParent() instanceof MultipleFormPars) {
			MultipleFormPars formPars = (MultipleFormPars)v.getParent();
			type = formPars.getType();
		}
		else if(v.getParent() instanceof SingleFormPar) {
			SingleFormPar formPars = (SingleFormPar)v.getParent();
			type = formPars.getType();
		}
		Variable variable = (Variable)var;
		String name = currentNamespace==null?var.getVarName(): currentNamespace + "::" + var.getVarName();
		variable.obj= Tab.insert(Obj.Var, name, type.struct);
		if(currentMethod==null) variable.obj.setLevel(0);
		else variable.obj.setLevel(1);
		report_info("Deklarisana promenljiva " + var.getVarName(), var);
	}
    
    public void visit(Array array){
		varDeclCount++;
		Obj object;
		if(currentMethodScope!=null) {
			object = currentMethodScope.findSymbol(array.getVarName());
			if(object!=null) {
				report_error("Greska: Ime " + array.getVarName() + " je vec deklarisano.", array);
				array.obj = Tab.noObj;
				return;
			}
		}
		else {
			object = Tab.find(array.getVarName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + array.getVarName() + " je vec deklarisano.", array);
				array.obj = Tab.noObj;
				return;
			}
		}
		Type type = null;
		SyntaxNode v = array;
		while(v.getParent() instanceof SignleVarDecl || v.getParent() instanceof VarDecls) {
			v = v.getParent();
		}
		if(v.getParent() instanceof VariableDecl) {
			VariableDecl variableDecl = (VariableDecl)v.getParent();
			type = variableDecl.getType();
		}
		else if(v.getParent() instanceof MultipleFormPars) {
			MultipleFormPars formPars = (MultipleFormPars)v.getParent();
			type = formPars.getType();
		}
		else if(v.getParent() instanceof SingleFormPar) {
			SingleFormPar formPars = (SingleFormPar)v.getParent();
			type = formPars.getType();
		}
		Variable variable = (Variable)array;
		Struct arrayStruct = new Struct(Struct.Array);
		arrayStruct.setElementType(type.struct);
		String name = currentNamespace==null? array.getVarName() : currentNamespace + "::" + array.getVarName();
		variable.obj= Tab.insert(Obj.Var, name, arrayStruct);
		if(currentMethod==null) variable.obj.setLevel(0);
		else variable.obj.setLevel(1);
		report_info("Deklarisan niz " + array.getVarName(), array);
	}
    
    public void visit(CharAssignment ca) {
    	Obj object;
		if(currentMethodScope!=null) {
			object = currentMethodScope.findSymbol(ca.getConstName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + ca.getConstName() + " je vec deklarisano.", ca);
				Assignment a = (Assignment)ca;
				a.obj = Tab.noObj;
				return;
			}
		}
		else {
			object = Tab.find(ca.getConstName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + ca.getConstName() + " je vec deklarisano.", ca);
				Assignment a = (Assignment)ca;
				a.obj = Tab.noObj;
				return;
			}
		}
    	Type type = null;
		SyntaxNode c = ca.getParent();
		while(c.getParent() instanceof NoConstAssignements || c.getParent() instanceof ConstAssignements) {
			c = c.getParent();
		}
		ConstDeclaration cd = (ConstDeclaration)c.getParent();
		type = cd.getType();
		if(type.struct.getKind() != Struct.Char) {
			report_error("Greska: Vrednost " + ca.getValue() + " ne moze se dodeliti konstanti " + ca.getConstName() , ca);
			Assignment a = (Assignment)ca;
			a.obj = Tab.noObj;
			return;
		}
		Assignment a = (Assignment)ca;
		String name = currentNamespace==null? ca.getConstName() : currentNamespace + "::" + ca.getConstName();
		a.obj = Tab.insert(Obj.Con, name, type.struct);
		a.obj.setAdr(ca.getValue());
		a.obj.setLevel(currentMethod==null? 0 : 1);
		report_info("Deklarisana konstanta " + ca.getConstName(), ca);
    }
    
    public void visit(NumAssignment na) {
    	Obj object;
		if(currentMethodScope!=null) {
			object = currentMethodScope.findSymbol(na.getConstName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + na.getConstName() + " je vec deklarisano.", na);
				Assignment a = (Assignment)na;
				a.obj = Tab.noObj;
				return;
			}
		}
		else {
			object = Tab.find(na.getConstName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + na.getConstName() + " je vec deklarisano.", na);
				Assignment a = (Assignment)na;
				a.obj = Tab.noObj;
				return;
			}
		}
    	
    	Type type = null;
		SyntaxNode n = na.getParent();
		while(n.getParent() instanceof NoConstAssignements || n.getParent() instanceof ConstAssignements) {
			n = n.getParent();
		}
		ConstDeclaration cd = (ConstDeclaration)n.getParent();
		type = cd.getType();
		if(type.struct.getKind() != Struct.Int) {
			report_error("Greska: Vrednost " + na.getValue() + " ne moze se dodeliti konstanti " + na.getConstName() , na);
			Assignment a = (Assignment)na;
			a.obj = Tab.noObj;
			return;
		}
		Assignment a = (Assignment)na;
		String name = currentNamespace==null? na.getConstName() : currentNamespace + "::" + na.getConstName();
		a.obj = Tab.insert(Obj.Con, name, type.struct);
		a.obj.setAdr(na.getValue());
		a.obj.setLevel(currentMethod==null? 0 : 1);
		report_info("Deklarisana konstanta " + na.getConstName(), na);
    }
    
    public void visit(BoolAssignment ba) {
    	Obj object;
		if(currentMethodScope!=null) {
			object = currentMethodScope.findSymbol(ba.getConstName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + ba.getConstName() + " je vec deklarisano.", ba);
				Assignment a = (Assignment)ba;
				a.obj = Tab.noObj;
				return;
			}
		}
		else {
			object = Tab.find(ba.getConstName());
			if(object!=Tab.noObj) {
				report_error("Greska: Ime " + ba.getConstName() + " je vec deklarisano.", ba);
				Assignment a = (Assignment)ba;
				a.obj = Tab.noObj;
				return;
			}
		}
    	
    	Type type = null;
		SyntaxNode n = ba.getParent();
		while(n.getParent() instanceof NoConstAssignements || n.getParent() instanceof ConstAssignements) {
			n = n.getParent();
		}
		ConstDeclaration cd = (ConstDeclaration)n.getParent();
		type = cd.getType();
		if(type.struct.getKind() != Struct.Bool) {
			report_error("Greska: Vrednost " + ba.getValue() + " ne moze se dodeliti konstanti " + ba.getConstName() , ba);
			Assignment a = (Assignment)ba;
			a.obj = Tab.noObj;
			return;
		}
		Assignment a = (Assignment)ba;
		String name = currentNamespace==null? ba.getConstName() : currentNamespace + "::" + ba.getConstName();
		a.obj = Tab.insert(Obj.Con, name, type.struct);
		a.obj.setAdr(ba.getValue());
		a.obj.setLevel(currentMethod==null? 0 : 1);
		report_info("Deklarisana konstanta " + ba.getConstName(), ba);
    }
    
    public void visit(TypedMethod typedMethod) {
    	Obj object = Tab.find(typedMethod.getMethodName());
		if(object!=Tab.noObj) {
			report_error("Greska: Ime " + typedMethod.getMethodName() + " je vec deklarisano.", typedMethod);
			MethodTypeName mtn = (MethodTypeName)typedMethod;
			mtn.obj = Tab.noObj;
			return;
		}
		String name = currentNamespace==null? typedMethod.getMethodName() : currentNamespace + "::" + typedMethod.getMethodName();
    	currentMethod = Tab.insert(Obj.Meth, name, typedMethod.getType().struct);
    	MethodTypeName mtn = (MethodTypeName)typedMethod;
    	mtn.obj = currentMethod;
    	Tab.openScope();
    	currentMethodScope = Tab.currentScope();
    	nameForCurrentMethod = currentMethod.getName();
    	formalPars.put(nameForCurrentMethod, new ArrayList<>());
    	report_info("Obradjuje se funkcija " + typedMethod.getMethodName(), typedMethod);
    }
    
    public void visit(VoidMethod voidMethod) {
    	Obj object = Tab.find(voidMethod.getMethodName());
		if(object!=Tab.noObj) {
			report_error("Greska: Ime " + voidMethod.getMethodName() + " je vec deklarisano.", voidMethod);
			MethodTypeName mtn = (MethodTypeName)voidMethod;
	    	mtn.obj = Tab.noObj;
			return;
		}
		String name = currentNamespace==null? voidMethod.getMethodName() : currentNamespace + "::" + voidMethod.getMethodName();
    	currentMethod = Tab.insert(Obj.Meth, name, Tab.noType);
    	MethodTypeName mtn = (MethodTypeName)voidMethod;
    	mtn.obj = currentMethod;
    	Tab.openScope();
    	currentMethodScope = Tab.currentScope();
    	nameForCurrentMethod = currentMethod.getName();
    	formalPars.put(nameForCurrentMethod, new ArrayList<>());
    	report_info("Obradjuje se funkcija " + voidMethod.getMethodName(), voidMethod);
    }
    
    public void visit(MethodDeclaration methodDeclaration) {
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	if(currentMethod.getType().getKind()!=Struct.None && !currentMethodHasReturn) {
    		report_error("Funkcija " + currentMethod.getName() + " mora da ima return naredbu", methodDeclaration);
			return;
    	}
    	currentMethod.setLevel(formalPars.get(currentMethod.getName()).size());
    	currentMethod = null;
    	currentMethodHasReturn = false;
    	currentMethodScope=null;
    }
    
    public void visit(MulFactor mulFactor) {
    	if(mulFactor.getFactor().struct.getKind()!=Struct.Int || mulFactor.getMulFactorList().struct.getKind()!=Struct.Int) {
    		report_error("Greska: Operatori *, / i % mogu se koristiti samo za promenljive tipa int", mulFactor);
    		mulFactor.struct = Tab.noType;
			return;
    	}
    	mulFactor.struct = mulFactor.getFactor().struct;
    }
    public void visit(NoMulFactor noMulFactor) {
    	noMulFactor.struct = noMulFactor.getFactor().struct;
    }
    public void visit(Term term) {
    	term.struct = term.getMulFactorList().struct;
    }
    public void visit(AddOpTerm addOpTerm) {
    	if(addOpTerm.getTerm().struct.getKind()!=Struct.Int) {
    		report_error("Greska: Operatori + i - mogu se koristiti samo za promenljive tipa int", addOpTerm);
    		addOpTerm.struct = Tab.noType;
			return;
    	}
    	addOpTerm.struct= addOpTerm.getTerm().struct;
    }
    
    public void visit(NoAddOpTerm noAddOpTerm) {
    	noAddOpTerm.struct = Tab.noType;
    	//noAddOpTerm.struct = new Struct(Struct.None);
    }
    public void visit(ExprWithMinus exprMinus) {
    	boolean addition = false;
    	if(exprMinus.getAddOpList().struct.getKind()==Struct.Int) addition = true;
    	if(exprMinus.getMinusTerm().getTerm().struct.getKind()!=Struct.Int) {
    		report_error("Greska: Operatori + i - mogu se koristiti samo za promenljive tipa int", exprMinus);
    		exprMinus.struct = Tab.noType;
			return;
    	}
    	exprMinus.struct = exprMinus.getMinusTerm().getTerm().struct;
    }
    
    
    public void visit(ExprWithoutMinus exprNoMinus) {
    	boolean addition = false;
    	if(exprNoMinus.getAddOpList().struct.getKind()==Struct.Int) addition = true;
    	if(exprNoMinus.getTerm().struct.getKind()!=Struct.Int && addition) {
    		report_error("Greska: Operatori + i - mogu se koristiti samo za promenljive tipa int", exprNoMinus);
    		exprNoMinus.struct = Tab.noType;
			return;
    	}
    	exprNoMinus.struct = exprNoMinus.getTerm().struct;
	}
    
    public void visit(DesignatorWithoutScopeResolution designator) {
    	Obj obj=null;
    	String name = currentNamespace == null ? designator.getName(): currentNamespace + "::" + designator.getName();
		obj = Tab.find(name);
		if(obj==Tab.noObj) {
    		report_error("Greska: Ime " + designator.getName() + " nije deklarisano", designator);
    		Designator d = (Designator)designator;
    		d.obj = Tab.noObj;
			return;
    	}
    	Designator d = (Designator)designator;
    	d.obj = obj;
    }
    
    public void visit(Function function) {
    	Obj func = function.getDesignator().obj;
    	if(func.getKind()!=Obj.Meth) {
    		report_error("Greska: Ime " + func.getName() + " ne predstavlja funkciju", function);
    		function.struct = Tab.noType;
    		return;
    	}
    	if(func.getType()==Tab.noType) {
    		report_error("Greska: Funkcija " + func.getName() + " je tipa void i ne moze se koristiti u izrazu", function);
    		function.struct = Tab.noType;
    		return;
    	}
    	List<List<String>> list = formalPars.get(func.getName());
		if(list==null) {
			report_error("Greska: Nije moguc poziv funkcije u njenoj definiciji", function);
			function.struct = Tab.noType;
    		return;
		}
    	if(list.size()!=numOfActPars) {
    		report_error("Greska: Nisu prosledjeni svi argumenti", function);
    		function.struct = Tab.noType;
    		return;
    	}
    	else {
    		if(funcCalls.isEmpty()) {
    			numOfActPars=0;
    			funcCall = false;
    		}
    		else {
    			numOfActPars = funcCalls.pop();
    			funcCall = true;
    		}
    		report_info("Poziv funkcije " + func.getName(), function);
    		function.struct = func.getType();
    	}
    }
    public void visit(FactorVariable var) {
    	var.struct = var.getDesignator().obj.getType();
    }
    public void visit(FactorNumConst factorNum) {
    	factorNum.struct = Tab.intType;
    	//factorNum.struct = new Struct(Struct.Int);
    }
    public void visit(FactorCharConst factorChar) {
    	factorChar.struct = Tab.charType;
    	//factorChar.struct = new Struct(Struct.Char);
    }
    public void visit(FactorBoolConst factorBool) {
    	factorBool.struct = MyTab.boolType;
    	//factorBool.struct = new Struct(Struct.Bool);
    }
    public void visit(ConstructorArray constructorArray) {
    	if(constructorArray.getExpr().struct.getKind()!=Struct.Int) {
    		report_error("Greska: Indeks nije tipa int", constructorArray);
    		constructorArray.struct = Tab.noType;
    		return;
    	}
    	Type t = (Type)constructorArray.getType();
    	constructorArray.struct = new Struct(Struct.Array, t.struct);
    }
    public void visit(ExprInParenthesis exprInParenthesis) {
    	exprInParenthesis.struct = exprInParenthesis.getExpr().struct;
    }
    public void visit(AssignValue assignValue) {
    	if(assignValue.getDesignator().obj.getKind()!=Obj.Var && assignValue.getDesignator().obj.getKind()!=Obj.Elem) {
    		report_error("Greska: Nije moguca dodela vrednosti konstanti", assignValue);
    		return;
    	}
    	if(assignValue.getExpr().struct.assignableTo(assignValue.getDesignator().obj.getType())) report_info("Uspesna dodela vrednosti", assignValue);
    	else report_error("Greska: Nekompatibilnost tipova pri dodeli", assignValue);
    }
    
    public void visit(FunctionCall functionCall) {
    	Obj func = functionCall.getDesignator().obj;
    	if(func.getKind()!=Obj.Meth) {
    		report_error("Greska: Ime " + func.getName() + " ne predstavlja funkciju", functionCall);
    		//functionCall.struct = Tab.noType;
    	}
    	List<List<String>> list = formalPars.get(func.getName());
    	if(list==null) {
    		report_error("Greska: Nije moguc poziv funkcije u njenoj definiciji.", functionCall);
    		return;
    	}
    	if(list.size()!=numOfActPars) {
    		report_error("Greska: Nisu prosledjeni svi argumenti", functionCall);
    		return;
    	}
    	else {
    		if(funcCalls.isEmpty()) {
    			numOfActPars=0;
    			funcCall = false;
    		}
    		else {
    			numOfActPars = funcCalls.pop();
    			funcCall = true;
    		}
    		report_info("Poziv funkcije " + func.getName(), functionCall);
    	}
    }
    public void visit(CallWithActPars call) {
    	SyntaxNode s = call;
    	while(!(s.getParent() instanceof Function || s.getParent() instanceof FunctionCall)) {
    		s = s.getParent();
    	}
    	if(s.getParent() instanceof Function) {
    		Function f = (Function)s.getParent();
        	if(numOfActPars!=formalPars.get(f.getDesignator().obj.getName()).size()) {
        		report_error("Greska: Nisu prosledjeni svi argumenti", call);
        		return;
        	}
    	}
    	else {
    		FunctionCall f = (FunctionCall)s.getParent();
    		List<List<String>> list = formalPars.get(f.getDesignator().obj.getName());
    		if(list==null) {
    			report_error("Greska: Nije moguc poziv funkcije u njenoj definiciji", call);
        		return;
    		}
    		if(numOfActPars!=list.size()) {
        		report_error("Greska: Nisu prosledjeni svi argumenti", call);
        		return;
        	}
    	}
    }
    public void visit(IncrementDesignator increment) {
    	if(increment.getDesignator().obj.getKind()==Obj.Con) {
    		report_error("Greska: Ime " + increment.getDesignator().obj.getName() + " predstavlja konstantu", increment);
    		return;
    	}
    	if(increment.getDesignator().obj.getType().getKind()!=Struct.Int) {
    		report_error("Greska: Promenljiva " + increment.getDesignator().obj.getName() + " nije tipa int", increment);
    		return;
    	}
    }
    public void visit(DecrementDesignator decrement) {
    	if(decrement.getDesignator().obj.getKind()==Obj.Con) {
    		report_error("Greska: Ime " + decrement.getDesignator().obj.getName() + " predstavlja konstantu", decrement);
    		return;
    	}
    	if(decrement.getDesignator().obj.getType().getKind()!=Struct.Int) {
    		report_error("Greska: Promenljiva " + decrement.getDesignator().obj.getName() + " nije tipa int", decrement);
    		return;
    	}
    }
    public void visit(ArrayName name) {
    	Obj obj = Tab.find(name.getName());
    	if(obj==Tab.noObj) {
    		name.obj=Tab.noObj;
    		report_error("Greska: Ime " + name.getName() + " nije deklarisano", name);
			return;
    	}
    	if(obj.getType().getKind()!=Struct.Array) {
    		name.obj=Tab.noObj;
    		report_error("Greska: Ime " + name.getName() + " nije niz", name);
			return;
    	}
    	name.obj = obj;
    }
    
    public void visit(DesignatorWithoutScopeResolutionAndIndexing indexing) {
    	Designator d = (Designator)indexing;
    	if(indexing.getExpr().struct.getKind()!=Struct.Int) {
    		report_error("Greska: U [] mora biti tio int", indexing);
    		d.obj = Tab.noObj;
			return;
    	}
    	d.obj = new Obj(Obj.Elem, indexing.getArrayName().getName(), indexing.getArrayName().obj.getType().getElemType());
    	d.obj.setLevel(indexing.getArrayName().obj.getLevel());
    	d.obj.setAdr(indexing.getArrayName().obj.getAdr());
    }
    
    public void visit(SingleFormPar formPar) {
    	String type = "";
    	if(formPar.getVariable() instanceof Array) type="array";
    	switch (formPar.getType().struct.getKind()) {
		case Struct.Int:
			type += "int";
			break;
		case Struct.Bool:
			type += "bool";
			break;
		case Struct.Char:
			type += "char";
			break;
		case Struct.Array:
			type = "array";
			switch (formPar.getType().struct.getElemType().getKind()) {
			case Struct.Int:
				type += "int";
				break;
			case Struct.Bool:
				type += "bool";
				break;
			case Struct.Char:
				type += "char";
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
    	if(formalPars.get(nameForCurrentMethod)!=null && type!="") {
    		ArrayList<String> list = new ArrayList<>();
    		String name;
    		if(formPar.getVariable() instanceof Var) {
    			name = ((Var)formPar.getVariable()).getVarName();
    		}
    		else {
    			name = ((Array)formPar.getVariable()).getVarName();
    		}
    		list.add(name);
    		list.add(type);
    		formalPars.get(nameForCurrentMethod).add(list);
    		return;
    	}
    	else {
    		report_error("Greska: U obilasku formalnih parametara", formPar);
			return;
    	}
    }
    public void visit(MultipleFormPars formPar) {
    	String type = "";
    	if(formPar.getVariable() instanceof Array) {
    		type="array";
    	}
    	switch (formPar.getType().struct.getKind()) {
		case Struct.Int:
			type += "int";
			break;
		case Struct.Bool:
			type += "bool";
			break;
		case Struct.Char:
			type += "char";
			break;
		case Struct.Array:
			type = "array";
			switch (formPar.getType().struct.getElemType().getKind()) {
			case Struct.Int:
				type += "int";
				break;
			case Struct.Bool:
				type += "bool";
				break;
			case Struct.Char:
				type += "char";
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
    	if(formalPars.get(nameForCurrentMethod)!=null && type!="") {
    		ArrayList<String> list = new ArrayList<>();
    		String name;
    		if(formPar.getVariable() instanceof Var) {
    			name = ((Var)formPar.getVariable()).getVarName();
    		}
    		else {
    			name = ((Array)formPar.getVariable()).getVarName();
    		}
    		list.add(name);
    		list.add(type);
    		formalPars.get(nameForCurrentMethod).add(list);
    		return;
    	}
    	else {
    		report_error("Greska: U obilasku formalnih parametara", formPar);
			return;
    	}
    }
    
    public void visit(ReturnStatement returnStatement) {
    	if(currentMethod==null) {
    		report_error("Greska: Return naredba ne moze postojati izvan definicije funkcije", returnStatement);
    	}
    	else if(currentMethod.getType().getKind()==Struct.None) {
    		currentMethodHasReturn=true;
    	}
    	
    }
    
    public void visit(SingleExpr expr) {
    	SyntaxNode e = expr;
    	while(!(e.getParent() instanceof Function || e.getParent() instanceof FunctionCall)) {
    		e = e.getParent();
    	}
    	String type;
    	String name;
    	if(e.getParent() instanceof Function) {
    		Function f = (Function)e.getParent();
    		name = f.getDesignator().obj.getName();
    		type = formalPars.get(f.getDesignator().obj.getName()).get(numOfActPars).get(1);
    	}
    	else {
    		FunctionCall f = (FunctionCall)e.getParent();
    		name = f.getDesignator().obj.getName();
    		List<List<String>> list = formalPars.get(f.getDesignator().obj.getName());
    		if(list==null) {
    			report_error("Greska: Nije moguc poziv funkcije u njenoj definiciji", expr);
        		return;
    		}
    		type = list.get(numOfActPars).get(1);
    	}
    	String exprType="";
    	switch (expr.getExpr().struct.getKind()) {
		case Struct.Int:
			exprType = "int";
			break;
		case Struct.Bool:
			exprType = "bool";
			break;
		case Struct.Char:
			exprType = "char";
			break;
		case Struct.Array:
			exprType = "array";
			if(name.equals("len")) break;
			switch (expr.getExpr().struct.getElemType().getKind()) {
			case Struct.Int:
				exprType += "int";
				break;
			case Struct.Bool:
				exprType += "bool";
				break;
			case Struct.Char:
				exprType += "char";
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
    	if(!type.equals(exprType)) {
    		report_error("Greska: Tipovi stvarni argumenata se ne poklapaju sa potpisom funkcije", expr);
    		return;
    	}
    	else {
    		numOfActPars++;
    	}
    }
    public void visit(MultipleExpr expr) {
    	SyntaxNode e = expr;
    	while(!(e.getParent() instanceof Function || e.getParent() instanceof FunctionCall)) {
    		e = e.getParent();
    	}
    	String type;
    	String name;
    	if(e.getParent() instanceof Function) {
    		Function f = (Function)e.getParent();
    		name = f.getDesignator().obj.getName();
    		type = formalPars.get(f.getDesignator().obj.getName()).get(numOfActPars).get(1);
    	}
    	else {
    		FunctionCall f = (FunctionCall)e.getParent();
    		name = f.getDesignator().obj.getName();
    		List<List<String>> list = formalPars.get(f.getDesignator().obj.getName());
    		if(list==null) {
    			report_error("Greska: Nije moguc poziv funkcije u njenoj definiciji", expr);
        		return;
    		}
    		type = list.get(numOfActPars).get(1);
    	}
    	String exprType="";
    	switch (expr.getExpr().struct.getKind()) {
		case Struct.Int:
			exprType = "int";
			break;
		case Struct.Bool:
			exprType = "bool";
			break;
		case Struct.Char:
			exprType = "char";
			break;
		case Struct.Array:
			exprType = "array";
			if(name.equals("len")) break;
			switch (expr.getExpr().struct.getElemType().getKind()) {
			case Struct.Int:
				exprType += "int";
				break;
			case Struct.Bool:
				exprType += "bool";
				break;
			case Struct.Char:
				exprType += "char";
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
    	if(!type.equals(exprType)) {
    		report_error("Greska: Tipovi stvarni argumenata se ne poklapaju sa potpisom funkcije", expr);
    		return;
    	}
    	else {
    		numOfActPars++;
    	}
    }

    public void visit(ReturnStatementWithExpr returnStatement) {
    	if(currentMethod==null) {
    		report_error("Greska: Return naredba ne moze postojati izvan definicije funkcije", returnStatement);
    	}
    	else if(currentMethod.getType().getKind()==Struct.None) {
    		currentMethodHasReturn=true;
    	}
    	if(!returnStatement.getExpr().struct.assignableTo(currentMethod.getType())) {
    		if(currentMethod.getType().getKind()==Struct.Int) report_error("Greska: Funkcija " + currentMethod.getName() + " vraca vrednost tipa int", returnStatement);
    		else if(currentMethod.getType().getKind()==Struct.Char) report_error("Greska: Funkcija " + currentMethod.getName() + " vraca vrednost tipa char", returnStatement);
    		else if(currentMethod.getType().getKind()==Struct.Bool) report_error("Greska: Funkcija " + currentMethod.getName() + " vraca vrednost tipa bool", returnStatement);
    	}
    	else currentMethodHasReturn = true;
    }
    
    public void visit(CondFactWithSingleExpr cond) {
    	cond.struct = MyTab.boolType;
    }
    public void visit(CondFactWithMultipleExpr cond) {
    	//boolean uslov1 = (cond.getExpr().struct.getKind() == cond.getExpr1().struct.getKind() && cond.getExpr().struct.getKind()!=Struct.Array);
    	boolean uslov2 = (cond.getExpr().struct.getKind() == cond.getExpr1().struct.getKind() && cond.getExpr().struct.getKind()==Struct.Array && cond.getExpr().struct.getElemType().getKind()==cond.getExpr1().struct.getElemType().getKind());
    	//boolean uslov3 = (cond.getExpr().struct.isRefType() && cond.getExpr1().struct==Tab.noType);
    	//boolean uslov4 = (cond.getExpr1().struct.isRefType() && cond.getExpr().struct==Tab.noType);
    	if(!cond.getExpr().struct.compatibleWith(cond.getExpr1().struct)) {
    		report_error("Greska: Tipovi izraza u uslovu nisu kompatibilni", cond);
    		return;
    	}
    	if(uslov2) {
    		if(!(cond.getRelOp() instanceof Equals) && !(cond.getRelOp() instanceof NotEquals)) {
    			report_error("Greska: Za poredjenje nizova mogu se koristiti samo operatori == i !=", cond);
        		return;
    		}
    	}
    	cond.struct = MyTab.boolType;
    }
    
    public void visit(SingleCondFact fact) {
    	fact.struct = fact.getCondFact().struct;
    }
    public void visit(AndCondFact fact) {
    	fact.struct = fact.getCondFact().struct;
    }
    public void visit(CondTerm term) {
    	term.struct = term.getAndList().struct;
    }
    public void visit(SingleCondTerm term) {
    	term.struct = term.getCondTerm().struct;
    }
    public void visit(OrCondTerm term) {
    	term.struct = term.getOrList().struct;
    }
    public void visit(ValidCondition cond) {
    	cond.struct = cond.getOrList().struct;
    	report_info("Uspesno obradjen condition", cond);
    }
    
    public void visit(CallStart start) {
    	if(funcCall==false) {
    		funcCall = true;
    		numOfActPars=0;
    	}
    	else {
    		funcCalls.push(numOfActPars);
    	}
    }
    
    public void visit(ForStart start) {
    	if(inForStatement==false) inForStatement = true;
    	else {
    		inFor.push(inForStatement);
    		inForStatement = true;
    	}
    	
    }
    
    public void visit(ForStatement forStatement) {
    	if(inFor.isEmpty())inForStatement = false;
    	else inForStatement = inFor.pop();
    }
    
    public void visit(BreakStatement breakStatement) {
    	if(!inForStatement) {
			report_error("Greska: Nije dozvoljena break naredba izvan for naredbe", breakStatement);
    	}
    }
    public void visit(ContinueStatement continueStatement) {
    	if(!inForStatement) {
			report_error("Greska: Nije dozvoljena continue naredba izvan for naredbe", continueStatement);
    	}
    }
    
    public void visit(IfStatement ifStatement) {
    	if(ifStatement.getCondition().struct.getKind()!=Struct.Bool) {
    		report_error("Greska: Neispravan uslov u if naredbi", ifStatement);
    	}
    	else {
    		report_info("Uspesno obradjena if naredba", ifStatement);
    	}
    }
    public void visit(IfElseStatement ifStatement) {
    	if(ifStatement.getCondition().struct.getKind()!=Struct.Bool) {
    		report_error("Greska: Neispravan uslov u if naredbi", ifStatement);
    	}
    	else {
    		report_info("Uspesno obradjena if-else naredba", ifStatement);
    	}
    }
    public void visit(WithDesignator des) {
    	if(des.getDesignator().obj.getKind()!=Obj.Var && des.getDesignator().obj.getKind()!=Obj.Elem) {
    		report_error("Greska: Clan nije promenljiva ili emelent niza", des);
    		return;
    	}
    	SyntaxNode s = des;
    	while(s.getParent() instanceof DesignatorList) s = s.getParent();
    	
    	if(((DesignatorList)s).struct==null) {
    		((DesignatorList)s).struct = des.getDesignator().obj.getType();
    	}
    	else if(((DesignatorList)s).struct.getKind()!=des.getDesignator().obj.getType().getKind()) {
    		report_error("Greska: Nisu svi clanovi istog tipa", des);
    		return;
    	}
    }
    
    public void visit(ArrayAssignement array) {
    	if(array.getDesignator().obj.getType().getKind()!=Struct.Array) {
    		report_error("Greska: Clan posle * nije niz", array);
    		return;
    	}
    	if(array.getDesignator1().obj.getType().getKind()!=Struct.Array) {
    		report_error("Greska: Clan posle = nije niz", array);
    		return;
    	}
    	//boolean uslov1 = array.getDesignatorList().struct.getKind()==array.getDesignator1().obj.getType().getElemType().getKind();
    	//boolean uslov2 = array.getDesignatorList().struct.isRefType() && array.getDesignator1().obj.getType().getElemType().getKind()==Tab.noType.getKind();
    	if(!array.getDesignator1().obj.getType().getElemType().assignableTo(array.getDesignatorList().struct) && array.getDesignatorList().struct!=null) {
    		report_error("Greska: Elementi clana posle * nisu istog tipa kao svi clanovi niza", array);
    		return;
    	}
    	//uslov1 = (array.getDesignator1().obj.getType().getElemType().getKind()==array.getDesignator().obj.getType().getElemType().getKind());
    	//uslov2 = (array.getDesignator().obj.getType().getElemType().isRefType() && array.getDesignator1().obj.getType().getElemType().getKind()==Tab.noType.getKind());
    	if(!array.getDesignator1().obj.getType().getElemType().assignableTo(array.getDesignator().obj.getType().getElemType())) {
    		report_error("Greska: Elementi clana posle = nisu istog tipa kao elementi clana posle *", array);
    		return;
    	}
    }
    
    public void visit(NamespaceName name) {
    	if(namespacesNames.contains(name.getName())) {
    		report_error("Greska: Vec je definisan namespace sa imenom " + name.getName(), name);
    		return;
    	}
    	namespacesNames.add(name.getName());
    	currentNamespace = name.getName();
    }
    
    public void visit(NamespaceEnd end) {
    	currentNamespace = null;
    }
    
    public void visit(DesignatorWithScopeResolution d) {
    	Obj obj=null;
    	obj = Tab.find(d.getScope() + "::" + d.getName());
		if(obj==Tab.noObj) {
    		report_error("Greska: Ime " + d.getName() + " nije deklarisano u prostoru " + d.getScope(), d);
    		Designator designator = (Designator)d;
    		designator.obj = Tab.noObj;
			return;
    	}
    	Designator designator = (Designator)d;
    	designator.obj = obj;
    }
    public void visit(ArrayNameWithScopeResolution name) {
    	Obj obj = Tab.find(name.getScope() + "::" + name.getName());
    	if(obj==Tab.noObj) {
    		name.obj=Tab.noObj;
    		report_error("Greska: Ime " + name.getName() + " nije deklarisano", name);
			return;
    	}
    	if(obj.getType().getKind()!=Struct.Array) {
    		name.obj=Tab.noObj;
    		report_error("Greska: Ime " + name.getName() + " nije niz", name);
			return;
    	}
    	name.obj = obj;
    }
    
    public void visit(DesignatorWithScopeResolutionAndIndexing d) {
    	Designator designator = (Designator)d;
    	if(d.getExpr().struct.getKind()!=Struct.Int) {
    		report_error("Greska: U [] mora biti tio int", d);
    		d.obj = Tab.noObj;
			return;
    	}
    	designator.obj = new Obj(Obj.Elem, d.getArrayNameWithScopeResolution().getName(), d.getArrayNameWithScopeResolution().obj.getType().getElemType());
    	designator.obj.setLevel(d.getArrayNameWithScopeResolution().obj.getLevel());
    	designator.obj.setAdr(d.getArrayNameWithScopeResolution().obj.getAdr());
    }
}
