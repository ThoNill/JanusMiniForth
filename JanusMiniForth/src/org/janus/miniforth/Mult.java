package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.data.DataContext;

public class Mult extends WordImpl implements Action {

	public Mult() {
		super(-1);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);

		((MiniForthContext) context).mult();
	}

}
