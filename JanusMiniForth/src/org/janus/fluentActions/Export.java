package org.janus.fluentActions;

import java.io.Writer;

import org.janus.actions.Action;
import org.janus.actions.DataValueList;
import org.janus.data.DataContext;
import org.janus.helper.DebugAssistent;
import org.janus.miniforth.MiniForthContext;

public class Export extends DataValueList implements Action {
	String name;

	public Export(String name) {
		super();
		this.name = name;
	}

	@Override
	public void perform(DataContext context) {
		DebugAssistent.doNullCheck(context);

		if (context instanceof MiniForthContext) {
			try {
				MiniForthContext dContext = (MiniForthContext) context;
				Writer writer = dContext.getOutput();
				writer.write(name);
				writer.write('|');
				write(context, writer, '|');
				writer.write("\n");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	public String getName() {
		return name;
	}

}
