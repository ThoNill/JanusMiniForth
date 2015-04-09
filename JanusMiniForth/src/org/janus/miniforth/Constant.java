package org.janus.miniforth;

import java.io.Serializable;

import org.janus.actions.Action;
import org.janus.data.DataContext;

public class Constant extends WordImpl implements Action {
	private Serializable value;

	public Constant(Serializable value) {
		super(1);
		this.value = value;
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		((MiniForthContext) context).push(value);
	}

	@Override
	public String toString() {
		return "Constant " + value;
	}
}
