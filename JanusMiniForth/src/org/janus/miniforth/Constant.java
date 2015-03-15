package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.data.DataContext;

public class Constant extends WordImpl implements Action {
	private Object value;

	public Constant(Object value) {
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
