package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class CounterVisitor extends VisitorAdaptor {
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	
	public static class FormParamCounter extends CounterVisitor {
		public void visit(SingleFormPar SingleFormPar) {
			count++;
		}
		public void visit(MultipleFormPars MultipleFormPars) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor {
		public void visit(VarDecls VarDecls) {
			count++;
		}
		public void visit(SignleVarDecl SignleVarDecl) {
			count++;
		}
	}
}


