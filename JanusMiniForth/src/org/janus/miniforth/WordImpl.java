package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.helper.DebugAssistent;

public class WordImpl implements Action, Word {
	int stackSaldo = 0;

	public WordImpl(int stackSaldo) {
		super();
		this.stackSaldo = stackSaldo;
	}

	@Override
	public void configure(DataDescription description) {
		DebugAssistent.doNullCheck(description);

	}

	@Override
	public void perform(DataContext context) {
		DebugAssistent.doNullCheckAndOfType(MiniForthContext.class, context);

	}

	@Override
	public int getStackSaldo() {
		return stackSaldo;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
