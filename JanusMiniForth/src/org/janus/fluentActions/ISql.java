package org.janus.fluentActions;

public interface ISql extends IActionList {

	ISql atTheStart();

	ISql atTheEnd();

	ISql inRun();

	@Override
	ISql export(String name, String fields);

	@Override
	ISql sum(String name, String initAt);
}