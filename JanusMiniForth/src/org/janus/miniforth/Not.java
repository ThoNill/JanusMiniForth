package org.janus.miniforth;

import org.janus.data.DataContext;

public class Not extends WordImpl {

	public Not() {
		super(0);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);

		MiniForthContext withStack = (MiniForthContext) context;
		Boolean a = withStack.popBoolean();
		withStack.pushBoolean(!a);
	}

}
