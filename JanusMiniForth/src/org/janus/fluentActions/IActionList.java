package org.janus.fluentActions;

public interface IActionList extends ITerm {

	IActionList store(String name);

	IActionList pop();

	IActionList actions();

	ISql sql(String name, String stmtText);

	IActionList export(String name, String fields);

	IActionList sum(String name, String initAt);

}