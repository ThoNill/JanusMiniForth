package org.janus.fluentActions;

import org.janus.actions.Action;

public interface ITerm extends Action {

	ITerm fetch(String name);

	ITerm constant(Object value);

	ITerm plus(ITerm term);

	ITerm mult(ITerm term);

	ITerm negate();

	IRelation eq(ITerm term);

	IRelation neq(ITerm term);

	IRelation gt(ITerm term);

	IRelation lt(ITerm term);

	IRelation geq(ITerm term);

	IRelation leq(ITerm term);

}