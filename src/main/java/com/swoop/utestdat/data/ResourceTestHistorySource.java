package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.ech.doc.Document;

/*
 * Enable adjustment of weights depending on local JSON file.
 */
public class ResourceTestHistorySource
	extends ResourceDataSource
	implements TestHistorySource
{
	public ResourceTestHistorySource(String path)
	{
		super(path);
	}

	@Override
	public void update(TestTarget tt)
		throws IOException
	{
		Document doc = query();
		String id = tt.getId();
		Document forTestTarget = doc.find(tt.getId());
		if (!forTestTarget.isNull()) {
			double mult = forTestTarget.find("weight").cast(Double.class, 1.0);
			tt.setWeight(tt.getWeight() * mult); 
		}
	}
}
