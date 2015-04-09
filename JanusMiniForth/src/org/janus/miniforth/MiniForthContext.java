package org.janus.miniforth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Stack;

import org.janus.data.DataDescription;
import org.janus.database.DataContextWithConnection;

public class MiniForthContext extends DataContextWithConnection {
	private Stack<Serializable> objectStack = new Stack<>();
	private PrintWriter output;

	public MiniForthContext(DataDescription description) {
		super(description);
	}

	public void push(BigDecimal value) {
		value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
		objectStack.push(value);
	}

	public void push(Serializable value) {
		objectStack.push(value);
	}

	public Serializable pop() {
		return objectStack.pop();
	}

	public BigDecimal popBigDecimal() {
		return (BigDecimal) objectStack.pop();
	}

	public void add() {
		BigDecimal a = popBigDecimal();
		BigDecimal b = popBigDecimal();
		BigDecimal c = a.add(b);
		push(c);
	}

	public void mult() {
		BigDecimal a = popBigDecimal();
		BigDecimal b = popBigDecimal();
		BigDecimal c = a.multiply(b);
		push(c);
	}

	public void negate() {
		BigDecimal a = popBigDecimal().negate();
		objectStack.push(a);
	}

	public PrintWriter getOutput() throws FileNotFoundException {
		if (output == null) {
			File f = new File("output.txt");
			output = new PrintWriter(f);
		}
		return output;
	}

	public void closeOutput() throws FileNotFoundException {
		if (output != null) {
			output.close();
			output = null;
		}
	}

	public void pushBoolean(boolean compare) {
		objectStack.push(compare);
	}

	public Boolean popBoolean() {
		return (Boolean) objectStack.pop();
	}

}
