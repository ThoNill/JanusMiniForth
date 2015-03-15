package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.data.DataContext;

public class Add extends WordImpl implements Action {

	public Add() {
		super(-1);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		((MiniForthContext) context).add();
	}

}
