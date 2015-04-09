package org.janus.fluentActions;

import java.io.Serializable;

import org.janus.actions.Action;
import org.janus.actions.ActionList;
import org.janus.actions.HandleValue;
import org.janus.helper.DebugAssistent;
import org.janus.miniforth.Add;
import org.janus.miniforth.And;
import org.janus.miniforth.Compare;
import org.janus.miniforth.Compare.Comp;
import org.janus.miniforth.Constant;
import org.janus.miniforth.Fetch;
import org.janus.miniforth.Mult;
import org.janus.miniforth.Negate;
import org.janus.miniforth.Not;
import org.janus.miniforth.Or;
import org.janus.miniforth.WordList;

public class TermRelatImpl extends WordList implements ITerm, IRelation {

	public TermRelatImpl(Action action) {
		super();
		addAction(action);
	}

	public TermRelatImpl() {
		super();
	}

	public void addToBuilder(TermRelatImpl builder) {
		for (Action action : this) {
			builder.addAction(action);
		}
	}

	@Override
	public ActionList addAction(Action action) {
		if (action instanceof TermRelatImpl) {
			((TermRelatImpl) action).addToBuilder(this);
		} else {
			super.addAction(action);
		}
		return this;
	}

	@Override
	public TermRelatImpl fetch(String name) {
		DebugAssistent.doNullCheck(name);

		addAction(new Fetch(new HandleValue(name)));
		return this;
	}

	@Override
	public TermRelatImpl plus(ITerm term) {
		addAction(term);
		addAction(new Add());
		return this;
	}

	@Override
	public TermRelatImpl mult(ITerm term) {
		addAction(term);
		addAction(new Mult());
		return this;
	}

	@Override
	public TermRelatImpl negate() {
		addAction(new Negate());
		return this;
	}

	@Override
	public IRelation eq(ITerm term) {
		addAction(term);
		addAction(new Compare(Comp.EQ));
		return this;

	}

	@Override
	public IRelation neq(ITerm term) {
		addAction(term);
		addAction(new Compare(Comp.NEQ));
		return this;
	}

	@Override
	public IRelation gt(ITerm term) {
		addAction(term);
		addAction(new Compare(Comp.GT));
		return this;
	}

	@Override
	public IRelation lt(ITerm term) {
		addAction(term);
		addAction(new Compare(Comp.LT));
		return this;
	}

	@Override
	public IRelation geq(ITerm term) {
		addAction(term);
		addAction(new Compare(Comp.GEQ));
		return this;
	}

	@Override
	public IRelation leq(ITerm term) {
		addAction(term);
		addAction(new Compare(Comp.LEQ));
		return this;
	}

	@Override
	public TermRelatImpl and(IRelation relation) {
		addAction(relation);
		addAction(new And());
		return this;
	}

	@Override
	public TermRelatImpl or(IRelation relation) {
		addAction(relation);
		addAction(new Or());
		return this;
	}

	@Override
	public TermRelatImpl not() {
		addAction(new Not());
		return this;
	}

	@Override
	public TermRelatImpl constant(Serializable value) {
		addAction(new Constant(value));
		return this;
	}

}
