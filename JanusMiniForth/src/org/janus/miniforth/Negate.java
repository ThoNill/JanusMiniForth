package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.data.DataContext;

public class Negate extends WordImpl implements Action {

	public Negate() {
		super(0);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		((MiniForthContext) context).negate();
	}

}
