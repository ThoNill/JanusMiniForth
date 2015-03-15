package test.janus.data;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;

class TestAction implements Action {
	public int configured = 0;
	public int performed = 0;

	@Override
	public void configure(DataDescription description) {
		configured++;
	}

	@Override
	public void perform(DataContext context) {
		performed++;
	}

}
