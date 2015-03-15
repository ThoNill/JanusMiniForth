package org.janus.miniforth;

import org.janus.data.DataContext;

public class Compare extends WordImpl {

	public enum Comp {
		EQ, NEQ, LT, GT, LEQ, GEQ
	}

	Comp comp;

	public Compare(Comp comp) {
		super(-1);
		this.comp = comp;
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		MiniForthContext withStack = (MiniForthContext) context;
		Comparable a = (Comparable) withStack.pop();
		Comparable b = (Comparable) withStack.pop();
		boolean result = false;
		if (a.getClass().equals(b.getClass())) {
			result = compare(a, b);
		} else {
			throw new RuntimeException("Vergleich nicht möglich");
		}
		withStack.pushBoolean(result);
	}

	private boolean compare(Comparable a, Comparable b) {
		int c = a.compareTo(b);
		if (c == 0) {
			if (comp.equals(Comp.NEQ)) {
				return false;
			} else {
				return comp.equals(Comp.EQ) || comp.equals(Comp.LEQ)
						|| comp.equals(Comp.GEQ);
			}

		} else {
			if (c > 0) {
				return comp.equals(Comp.NEQ) || comp.equals(Comp.LEQ)
						|| comp.equals(Comp.LT);
			} else {
				return comp.equals(Comp.NEQ) || comp.equals(Comp.GEQ)
						|| comp.equals(Comp.GT);
			}
		}
	}

}
