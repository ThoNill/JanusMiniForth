package org.janus.fluentActions;

import org.janus.actions.Action;

public interface IRelation extends Action {

	IRelation and(IRelation relation);

	IRelation or(IRelation relation);

	IRelation not();
}