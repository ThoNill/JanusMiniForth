package org.janus.miniforth;

import org.janus.data.DataContext;

public class Or extends WordImpl {

	public Or() {
		super(-1);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		MiniForthContext withStack = (MiniForthContext) context;
		Boolean a = withStack.popBoolean();
		Boolean b = withStack.popBoolean();
		withStack.pushBoolean(a || b);
	}

}
