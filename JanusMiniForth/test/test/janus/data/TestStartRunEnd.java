package test.janus.data;

import org.janus.actions.StartRunEnd;
import org.janus.data.DataContext;

public class TestStartRunEnd extends StartRunEnd<IntHolder> {
	int max = 3;

	@Override
	protected void stop(IntHolder data) throws Exception {
	    // Keine Aktion zum Test notwendig
	}

	@Override
	protected IntHolder start(DataContext ctx) throws Exception {
		return new IntHolder();
	}

	@Override
	protected boolean next(DataContext context, IntHolder data)
			throws Exception {
		data.value++;
		return data.value <= max;
	}

}

class IntHolder {
	public int value;

	public IntHolder() {
		value = 0;
	}
}
