package test.janus.data;

import static org.janus.fluentActions.ActionBuilder.v;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import org.janus.actions.Action;
import org.janus.actions.ActionList;
import org.janus.actions.BigDecimalType;
import org.janus.actions.HandleValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.data.DataDescriptionImpl;
import org.janus.fluentActions.ActionBuilder;
import org.janus.fluentActions.IRelation;
import org.janus.miniforth.And;
import org.janus.miniforth.Compare;
import org.janus.miniforth.Compare.Comp;
import org.janus.miniforth.Constant;
import org.janus.miniforth.MiniForthContext;
import org.janus.miniforth.Not;
import org.janus.miniforth.Or;
import org.janus.miniforth.StackPredicate;
import org.junit.Test;


public class FluentActions {
    private static final Logger LOG = Logger.getLogger(FluentActions.class);
    private static final String AUSNAHME_AUFGETRETEN = "erwartete Ausnahme aufgetreten";
    private static final String UNERWARTETE_AUSNAHME = "unerwartete Ausnahme";

	@Test
	public void testHandleValue1() {
		try {
			DataDescription description = new DataDescriptionImpl();
			DataContext dataContext = description.newContext();
			HandleValue value = new HandleValue("gehtNicht");
			value.setObject(dataContext, value);
			fail("sollte nicht gehen");
		} catch (Exception ex) {
		    LOG.error(AUSNAHME_AUFGETRETEN,ex);
		}
	}

	@Test
	public void testHandleValue2() {
		try {
			DataDescription description = new DataDescriptionImpl();
			HandleValue value = new HandleValue("geht");
			value.configure(description);

			DataContext dataContext = description.newContext();
			value.setObject(dataContext, "aber");

			assertEquals("aber", value.getObject(dataContext));

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	@Test
	public void testHandleValue3() {
		try {
			HandleValue value = new HandleValue("geht");
			assertEquals("geht", value.getName());
		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}



	@Test
	public void testHandleValue5() {
		try {
			DataDescription description = new DataDescriptionImpl();
			HandleValue value = new HandleValue("gehtNicht",BigDecimalType.decimal);
			value.configure(description);
			DataContext dataContext = description.newContext();
			value.perform(dataContext);
			assertEquals(BigDecimal.ZERO, value.getObject(dataContext));

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
		}
	}

	@Test
	public void testHandleValue6() {
		try {
			DataDescription description = new DataDescriptionImpl();
			HandleValue value = new HandleValue("gehtNicht");
			value.configure(description);
			DataContext dataContext = description.newContext();
			HandleValue value2 = new HandleValue("gehtNicht");
			value.setObject(dataContext, "Test");
			assertEquals("Test", value2.getObject(dataContext));

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
		}
	}

	@Test
	public void testActionList1() {
		try {
			DataDescription description = new DataDescriptionImpl();
			DataContext dataContext = description.newContext();

			TestAction a1 = new TestAction();
			TestAction a2 = new TestAction();

			ActionList list = new ActionList();
			list.add(a1);
			list.add(a2);

			list.configure(description);
			list.perform(dataContext);

			assertTrue(a1.configured > 0);
			assertTrue(a2.configured > 0);
			assertTrue(a1.performed > 0);
			assertTrue(a1.performed > 0);
		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	@Test
	public void testActionList1b() {
		try {
			DataDescription description = new DataDescriptionImpl();
			DataContext dataContext = description.newContext();

			TestAction a1 = new TestAction();
			TestAction a2 = new TestAction();

			ActionList list = new ActionList();
			list.add(a1);
			list.add(a2);

			list.perform(dataContext);

			assertTrue(a1.configured > 0);
			assertTrue(a2.configured > 0);
			assertTrue(a1.performed > 0);
			assertTrue(a2.performed > 0);
		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	@Test
	public void testActionList2() {
		try {
			DataDescription description = new DataDescriptionImpl();
			DataContext dataContext = description.newContext();

			TestAction a1 = new TestAction();
			TestAction a2 = new TestAction();

			ActionList list = new ActionList();
			list.addAction(a1);
			list.addAction(a2);
			list.removeAction(a1);

			list.configure(description);
			list.perform(dataContext);

			assertTrue(a1.configured == 0);
			assertTrue(a2.configured > 0);
			assertTrue(a1.performed == 0);
			assertTrue(a2.performed > 0);
		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	@Test
	public void testActionList3() {
		try {
			DataDescription description = new DataDescriptionImpl();
			DataContext dataContext = description.newContext();

			TestAction a1 = new TestAction();
			TestAction a2 = new TestAction();

			ActionList list = new ActionList();
			list.addAction(a1);
			list.addAction(a2);
			list.addAction(a1);

			list.configure(description);
			list.configure(description);
			list.perform(dataContext);

			assertTrue(a1.configured == 1);
			assertTrue(a2.configured == 1);
			assertTrue(a1.performed == 2);
			assertTrue(a2.performed == 1);
		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	@Test
	public void testAddMultNegate() {
		try {
			DataDescription description = new DataDescriptionImpl();
			MiniForthContext dataContext = new MiniForthContext(description);

			dataContext.push(new BigDecimal("2.12"));
			dataContext.push(new BigDecimal("3.12"));
			dataContext.add();
			assertEquals(new BigDecimal("5.24").setScale(2,
					BigDecimal.ROUND_HALF_UP), dataContext.popBigDecimal());

			dataContext.push(new BigDecimal("12.12"));
			dataContext.push(new BigDecimal("33.12"));
			dataContext.mult();
			assertEquals(new BigDecimal("401.41"), dataContext.popBigDecimal());

			dataContext.push(new BigDecimal("12.12"));
			dataContext.negate();
			assertEquals(new BigDecimal("-12.12"), dataContext.popBigDecimal());

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	@Test
	public void testCompare() {
		try {
			DataDescription description = new DataDescriptionImpl();
			MiniForthContext dataContext = new MiniForthContext(description);

			doCompareTest(dataContext, "1.12", "2.12", Comp.EQ, false);
			doCompareTest(dataContext, "1.12", "2.12", Comp.NEQ, true);
			doCompareTest(dataContext, "2.12", "1.12", Comp.NEQ, true);

			doCompareTest(dataContext, "1.12", "1.12", Comp.EQ, true);
			doCompareTest(dataContext, "1.12", "1.12", Comp.NEQ, false);

			doCompareTest(dataContext, "1.12", "1.12", Comp.LT, false);
			doCompareTest(dataContext, "1.12", "1.12", Comp.GT, false);

			doCompareTest(dataContext, "1.12", "1.12", Comp.LEQ, true);
			doCompareTest(dataContext, "1.12", "1.12", Comp.GEQ, true);

			doCompareTest(dataContext, "1.12", "2.12", Comp.LT, true);
			doCompareTest(dataContext, "1.12", "2.12", Comp.LEQ, true);
			doCompareTest(dataContext, "1.12", "2.12", Comp.GT, false);
			doCompareTest(dataContext, "1.12", "2.12", Comp.GEQ, false);

			doCompareTest(dataContext, "4.12", "2.12", Comp.LT, false);
			doCompareTest(dataContext, "4.12", "2.12", Comp.LEQ, false);
			doCompareTest(dataContext, "4.12", "2.12", Comp.GT, true);
			doCompareTest(dataContext, "4.12", "2.12", Comp.GEQ, true);

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
			fail("sollte gehen");
		}
	}

	private void doCompareTest(MiniForthContext dataContext, String av,
			String bv, Comp art, boolean result) {
		Constant a = new Constant(new BigDecimal(av));
		Constant b = new Constant(new BigDecimal(bv));
		Compare com = new Compare(art);

		StackPredicate pred = new StackPredicate();
		pred.addAction(a);
		pred.addAction(b);
		pred.addAction(com);
		assertEquals(result, pred.hasPredicate(dataContext));
	}

	@Test
	public void testCompare2() {
		DataDescription description = new DataDescriptionImpl();
		MiniForthContext dataContext = new MiniForthContext(description);

		Constant a = new Constant("1.2");
		Constant b = new Constant(new BigDecimal("1.2"));
		Compare com = new Compare(Comp.EQ);

		StackPredicate pred = new StackPredicate();
		pred.add(a);
		pred.add(b);
		pred.add(com);
		try {
			pred.hasPredicate(dataContext);
			fail("keine Exception");
		} catch (Exception ex) {
		    LOG.error(AUSNAHME_AUFGETRETEN,ex);
		}
	}

	@Test
	public void testStackNichtAusgeglichen() {
		DataDescription description = new DataDescriptionImpl();
		MiniForthContext dataContext = new MiniForthContext(description);

		Constant a = new Constant("1.2");
		Constant b = new Constant(new BigDecimal("1.2"));
		Constant c = new Constant(new BigDecimal("1.2"));
		Compare com = new Compare(Comp.EQ);

		StackPredicate pred = new StackPredicate();
		pred.add(a);
		pred.add(b);
		pred.add(c);
		pred.add(com);
		try {
			pred.hasPredicate(dataContext);
			fail("keine Exception");
		} catch (Exception ex) {
		    LOG.error(AUSNAHME_AUFGETRETEN,ex);
		}
	}

	@Test
	public void testAndOr() {
		DataDescription description = new DataDescriptionImpl();
		MiniForthContext dataContext = new MiniForthContext(description);

		doLogicalOperation(dataContext, true, true, new And(), true);
		doLogicalOperation(dataContext, true, false, new And(), false);
		doLogicalOperation(dataContext, false, true, new And(), false);
		doLogicalOperation(dataContext, false, false, new And(), false);

		doLogicalOperation(dataContext, true, true, new Or(), true);
		doLogicalOperation(dataContext, true, false, new Or(), true);
		doLogicalOperation(dataContext, false, true, new Or(), true);
		doLogicalOperation(dataContext, false, false, new Or(), false);

		doLogicalOperation(dataContext, false, new Not(), true);
		doLogicalOperation(dataContext, true, new Not(), false);

	}

	private void doLogicalOperation(MiniForthContext dataContext, boolean ab,
			boolean bb, Action logicalAction, boolean result) {
		Constant a = new Constant(ab);
		Constant b = new Constant(bb);
		StackPredicate pred = new StackPredicate();
		pred.addAction(a);
		pred.addAction(b);
		pred.addAction(logicalAction);
		assertEquals(result, pred.hasPredicate(dataContext));
	}

	private void doLogicalOperation(MiniForthContext dataContext, boolean ab,
			Action logicalAction, boolean result) {
		Constant a = new Constant(ab);
		StackPredicate pred = new StackPredicate();
		pred.addAction(a);
		pred.addAction(logicalAction);
		assertEquals(result, pred.hasPredicate(dataContext));
	}

	@Test
	public void testFluent1() {
		ActionBuilder builder = new ActionBuilder();
		builder.fetch("a").plus(v("b"));
		builder.store("c");

		DataDescription description = new DataDescriptionImpl();
		Action action = builder;
		action.configure(description);
		int a = description.getHandle("a");
		int b = description.getHandle("b");
		int c = description.getHandle("c");

		MiniForthContext withStack = new MiniForthContext(description);

		withStack.setObject(a, new BigDecimal("2.12"));
		withStack.setObject(b, new BigDecimal("3.12"));
		action.perform(withStack);

		assertEquals(
				new BigDecimal("5.24").setScale(2, BigDecimal.ROUND_HALF_UP),
				withStack.getObject(c));

	}

	@Test
	public void testFluent2() {
		ActionBuilder builder = new ActionBuilder();
		builder.fetch("a").plus(v("b"));
		builder.store("c").export("test", "a,b,c,d");

		DataDescription description = new DataDescriptionImpl();
		Action action = builder;
		action.configure(description);
		int a = description.getHandle("a");
		int b = description.getHandle("b");
		int c = description.getHandle("c");
		int d = description.getHandle("d");

		MiniForthContext withStack = new MiniForthContext(description);

		withStack.setObject(a, new BigDecimal("20002.12"));
		withStack.setObject(b, new BigDecimal("3.12"));
		withStack.setObject(d, new Date());
		action.perform(withStack);

		assertEquals(new BigDecimal("20005.24").setScale(2,
				BigDecimal.ROUND_HALF_UP), withStack.getObject(c));

		try {
			withStack.closeOutput();
		} catch (FileNotFoundException e) {
		    LOG.error(UNERWARTETE_AUSNAHME,e);
			LOG.error("Fehler",e);;
		}
	}

	@Test
	public void testFluent3() {
		IRelation rel = v("a").plus(v("b")).gt(v("c"));
		testRelation(rel, true, 1.0f, 2.0f, 1.5f);
		rel = v("a").plus(v("b")).lt(v("c"));
		testRelation(rel, false, 1.0f, 2.0f, 1.5f);
		rel = v("a").mult(v("b")).lt(v("c"));
		testRelation(rel, true, 3.0f, 2.0f, 9.0f);
	}

	@Test
	public void testFluent4() {
		IRelation rel1 = v("a").plus(v("b")).gt(v("c"));
		IRelation rel2 = v("a").mult(v("b")).lt(v("c"));

		IRelation rel = rel1.and(rel2);
		testRelation(rel, false, 3.0f, 2.0f, 9.0f);

		rel = rel1.or(rel2);
		testRelation(rel, true, 3.0f, 2.0f, 9.0f);
	}

	public void testRelation(IRelation rel, boolean result, float va, float vb,
			float vc) {
		DataDescription description = new DataDescriptionImpl();
		int a = description.getHandle("a");
		int b = description.getHandle("b");
		int c = description.getHandle("c");
		int r = description.getHandle("result");

		ActionBuilder builder = new ActionBuilder();
		builder.addAction(rel);
		builder.store("result");

		builder.configure(description);
		MiniForthContext withStack = new MiniForthContext(description);

		withStack.setObject(a, BigDecimal.valueOf(va));
		withStack.setObject(b, BigDecimal.valueOf(vb));
		withStack.setObject(c, BigDecimal.valueOf(vc));

		builder.perform(withStack);

		Object obj = withStack.getObject(r);

		assertEquals(Boolean.valueOf(result), obj);
	}
}
