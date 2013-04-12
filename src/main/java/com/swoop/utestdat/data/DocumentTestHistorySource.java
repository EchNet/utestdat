package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import net.ech.doc.Document;

/*
 * Enable adjustment of weights depending on external JSON document.
 */
public class DocumentTestHistorySource
	extends DocumentBasedDataSource
	implements TestHistorySource
{
	@Override
	public void update(TestTarget tt)
		throws IOException
	{
		Document forTestTarget = getDocument().find(tt.getId());
		if (!forTestTarget.isNull()) {
			double mult = forTestTarget.find("weight").cast(Double.class, 1.0);
			tt.setWeight(tt.getWeight() * mult); 
		}
	}
}
