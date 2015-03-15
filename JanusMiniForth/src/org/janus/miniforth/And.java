package org.janus.miniforth;

import org.janus.data.DataContext;

public class And extends WordImpl {

	public And() {
		super(-1);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		MiniForthContext withStack = (MiniForthContext) context;
		Boolean a = withStack.popBoolean();
		Boolean b = withStack.popBoolean();
		withStack.pushBoolean(a && b);
	}

}
