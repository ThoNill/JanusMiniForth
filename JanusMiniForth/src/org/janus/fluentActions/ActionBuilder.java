package org.janus.fluentActions;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

import org.janus.actions.Action;
import org.janus.actions.ActionList;
import org.janus.actions.HandleValue;
import org.janus.actions.StartRunEnd;
import org.janus.database.SqlStatement;
import org.janus.helper.DebugAssistent;
import org.janus.miniforth.Constant;
import org.janus.miniforth.Fetch;
import org.janus.miniforth.Store;

public class ActionBuilder extends TermRelatImpl implements IActionList, ISql,
		IRelation {

	enum Place {
		BEFORE, RUN, AFTER;
	}

	Place status;

	ActionList currentActions = null;
	Deque<ActionList> lists = null;

	public ActionBuilder() {
		super();
		currentActions = this;
		lists = new ArrayDeque<>();
	}

	@Override
	public ActionBuilder pop() {
		currentActions = lists.pop();
		return this;
	}

	@Override
	public ActionBuilder fetch(String name) {
		DebugAssistent.doNullCheck(name);

		addAction(new Fetch(new HandleValue(name)));
		return this;
	}

	@Override
	public ActionBuilder store(String name) {
		DebugAssistent.doNullCheck(name);

		addAction(new Store(new HandleValue(name)));
		return this;
	}

	@Override
	public ActionBuilder actions() {
		ActionList child = new ActionList();

		pushParent(child);
		return this;
	}

	private void pushParent(ActionList child) {
		addAction(child);
		lists.push(currentActions);
		currentActions = child;
	}

	@Override
	public ISql sql(String name, String stmtText) {
		DebugAssistent.doNullCheck(name, stmtText);

		ActionList child = new SqlStatement(name, stmtText);
		pushParent(child);
		return this;
	}

	@Override
	public ISql atTheStart() {
		status = Place.BEFORE;
		return this;
	}

	@Override
	public ISql atTheEnd() {
		status = Place.AFTER;
		return this;
	}

	@Override
	public ISql inRun() {
		status = Place.RUN;
		return this;
	}

	@Override
	public ISql export(String name, String fields) {
		DebugAssistent.doNullCheck(name, fields);

		Export export = new Export(name);
		for (String field : fields.split(" *, *")) {
			export.add(new HandleValue(field));
		}
		addAction(export);
		return this;
	}

	@Override
	public ActionBuilder addAction(Action action) {
		if (action instanceof TermRelatImpl) {
			((TermRelatImpl) action).addToBuilder(this);
		} else {
			if (currentActions instanceof SqlStatement) {
				StartRunEnd<?> stmt = (StartRunEnd<?>) currentActions;
				switch (status) {
				case BEFORE:
					stmt.addBeforeRun(action);
					break;
				case AFTER:
					stmt.addAfterRun(action);
					break;
				case RUN:
				default:
					stmt.addAction(action);
					break;
				}
			} else {
				currentActions.add(action);
			}
		}
		return this;
	}

	@Override
	public ActionBuilder sum(String name, String initAt) {
		DebugAssistent.doNullCheck(name, initAt);

		ColumnSum s = new ColumnSum(name);
		addAction(s);
		for (Action a : lists) {
			if (a instanceof StartRunEnd) {
				StartRunEnd<?> sql = (StartRunEnd<?>) a;
				if (initAt.equals(sql.getName())) {
					sql.addBeforeRun(s.getSumValue());
				}
			}
		}
		return this;
	}

	public static ITerm c(Serializable value) {
		return new TermRelatImpl(new Constant(value));
	}

	public static ITerm v(String name) {
		return new TermRelatImpl(new Fetch(new HandleValue(name)));
	}

}
