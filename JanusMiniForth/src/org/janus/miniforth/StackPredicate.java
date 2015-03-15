package org.janus.miniforth;

import org.janus.actions.Predicate;
import org.janus.data.DataContext;

public class StackPredicate extends WordList implements Predicate {

	public StackPredicate() {
		super();
	}

	@Override
	public boolean hasPredicate(DataContext context) {
		perform(context);
		MiniForthContext withStack = (MiniForthContext) context;
		Boolean b = withStack.popBoolean();
		return b.booleanValue();
	}

	@Override
	public int getStackSaldo() {
		return super.getStackSaldo() - 1;
	}

}
