package test.janus.data;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.janus.actions.DataValue;
import org.janus.actions.HandleValue;
import org.janus.data.DataDescription;
import org.janus.data.DataDescriptionImpl;
import org.janus.fluentActions.ColumnSum;
import org.janus.miniforth.MiniForthContext;
import org.junit.Test;

public class StartRunStopTest {
    private static final Logger LOG = Logger.getLogger(FluentActions.class);
    private static final String UNERWARTETE_AUSNAHME = "unerwartete Ausnahme";

	public StartRunStopTest() {
		
	}

	@Test
	public void test1() {
		try {
			DataDescription description = new DataDescriptionImpl();

			TestStartRunEnd sre = new TestStartRunEnd();

			TestAction b = new TestAction();
			TestAction r = new TestAction();
			TestAction s = new TestAction();

			sre.addBeforeRun(b);
			sre.addAfterRun(s);
			sre.addAction(r);

			sre.configure(description);

			MiniForthContext dataContext = new MiniForthContext(description);

			sre.perform(dataContext);

			assertEquals(1, b.performed);
			assertEquals(3, r.performed);
			assertEquals(1, s.performed);

			assertEquals(1, b.configured);
			assertEquals(1, r.configured);
			assertEquals(1, s.configured);

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
		}
	}

	@Test
	public void test2() {
		try {
			DataDescription description = new DataDescriptionImpl();

			TestStartRunEnd sre = new TestStartRunEnd();

			ColumnSum columnSum = new ColumnSum("wert");
			HandleValue sum = columnSum.getSumValue();
			DataValue wert = new HandleValue("wert");

			sre.addBeforeRun(sum);
			sre.addAction(columnSum);

			sre.configure(description);

			MiniForthContext dataContext = new MiniForthContext(description);
			wert.setObject(dataContext, new BigDecimal("1.00"));

			sre.perform(dataContext);

			assertEquals(new BigDecimal("3.00"), sum.getObject(dataContext));

		} catch (Exception ex) {
		    LOG.error(UNERWARTETE_AUSNAHME,ex);
		}
	}

}
