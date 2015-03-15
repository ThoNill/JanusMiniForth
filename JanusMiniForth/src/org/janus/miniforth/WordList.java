package org.janus.miniforth;

import org.janus.actions.Action;
import org.janus.actions.ActionList;
import org.janus.data.DataContext;

public class WordList extends ActionList implements Word {

	public WordList() {
		super();
	}

	public void check() {
		int saldo = getStackSaldo();
		if (saldo != 0) {
			throw new RuntimeException("Stack Saldo != 0 " + saldo);
		}
	}

	@Override
	public int getStackSaldo() {
		int saldo = 0;
		for (Action a : this) {
			if (a instanceof Word) {
				saldo += ((Word) a).getStackSaldo();
			}
		}
		return saldo;
	}

	@Override
	public void perform(DataContext context) {
		check();
		super.perform(context);
	}

}
