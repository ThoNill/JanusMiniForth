package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.actions.DataValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;

public class Store extends WordImpl implements Action {
	private DataValue value;

	public Store(DataValue value) {
		super(-1);
		this.value = value;
	}

	@Override
	public void configure(DataDescription description) {
		super.configure(description);
		value.configure(description);
	}

	@Override
	public void perform(DataContext context) {
		super.perform(context);
		value.setObject(context, ((MiniForthContext) context).pop());
	}

}
