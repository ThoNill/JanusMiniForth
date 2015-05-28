package org.janus.fluentActions;

import java.math.BigDecimal;

import org.janus.actions.Action;
import org.janus.actions.BigDecimalType;
import org.janus.actions.HandleValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.helper.DebugAssistent;

public class ColumnSum implements Action {

	int handleValue = -1;
	int handleSum = -1;
	String name;

	public ColumnSum(String name) {
		DebugAssistent.doNullCheck(name);

		this.name = name;
	}

	@Override
	public void configure(DataDescription description) {
		DebugAssistent.doNullCheck(description);

		handleValue = description.getHandle(name);
		handleSum = description.getHandle(getSumName());
	}

	@Override
	public void perform(DataContext context) {
		DebugAssistent.doNullCheck(context);

		BigDecimal value = (BigDecimal) context.getObject(handleValue);
		BigDecimal sum = (BigDecimal) context.getObject(handleSum);
		BigDecimal neueSum = sum.add(value);
		context.setObject(handleSum, neueSum);
	}

	public HandleValue getSumValue() {
		HandleValue sum = new HandleValue(getSumName());
		return sum;
	}

	private String getSumName() {
		return "sum_" + name;
	}
}
