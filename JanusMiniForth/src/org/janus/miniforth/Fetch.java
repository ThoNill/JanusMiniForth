package org.janus.miniforth;

import java.math.BigDecimal;

import org.janus.actions.Action;
import org.janus.actions.DataValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;

public class Fetch extends WordImpl implements Action {
	private DataValue value;

	public Fetch(DataValue value) {
		super(1);
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
		((MiniForthContext) context)
				.push((BigDecimal) value.getObject(context));
	}

	@Override
	public String toString() {
		return "Fetch " + value.toString();
	}

}
