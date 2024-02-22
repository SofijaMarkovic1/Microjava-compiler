package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MyTab extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);
	private static int currentLevel;
	public static void init() {
		Scope universe = currentScope = new Scope(null);
		
		universe.addToLocals(new Obj(Obj.Type, "int", intType));
		universe.addToLocals(new Obj(Obj.Type, "char", charType));
		universe.addToLocals(new Obj(Obj.Type, "bool", intType));
		universe.addToLocals(new Obj(Obj.Con, "eol", charType, 10, 0));
		universe.addToLocals(new Obj(Obj.Con, "null", nullType, 0, 0));
		
		universe.addToLocals(chrObj = new Obj(Obj.Meth, "chr", charType, 0, 1));
		{
			openScope();
			currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 0, 1));
			chrObj.setLocals(currentScope.getLocals());
			closeScope();
		}
		
		universe.addToLocals(ordObj = new Obj(Obj.Meth, "ord", intType, 0, 1));
		{
			openScope();
			currentScope.addToLocals(new Obj(Obj.Var, "ch", charType, 0, 1));
			ordObj.setLocals(currentScope.getLocals());
			closeScope();
		} 
		
		
		universe.addToLocals(lenObj = new Obj(Obj.Meth, "len", intType, 0, 1));
		{
			openScope();
			currentScope.addToLocals(new Obj(Obj.Var, "arr", new Struct(Struct.Array, noType), 0, 1));
			lenObj.setLocals(currentScope.getLocals());
			closeScope();
		}
		
		currentLevel = -1;
	}
}
