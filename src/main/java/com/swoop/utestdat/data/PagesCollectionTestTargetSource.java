package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import net.ech.doc.Document;

public class PagesCollectionTestTargetSource
	extends MongoConnector
	implements TestTargetSource
{
	private final static double ACTIVITY_COEFFICIENT = 0.001;

	@Override
	public List<TestTarget> getTestTargets()
		throws IOException
	{
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> filters = new HashMap<String,Object>();
		filters.put("domains", 1);

		Document testTargetData = findOne(parameters, filters);
		if (testTargetData.isNull()) {
			throw new IOException("cannot find a recent page events report"); 
		}
		Document domains = testTargetData.find("domains");
		if (domains.isNull()) {
			throw new IOException("page events report is missing domains?"); 
		}

		List<TestTarget> result = new ArrayList<TestTarget>();
		for (Document ttChild : domains.children()) {
			String testTargetId =  ttChild.getName();
			TestTarget tt = new TestTarget(testTargetId);
			int activityLevel = ttChild.find("page").find("views").cast(Integer.class, 0);
			tt.setWeight(activityLevel * ACTIVITY_COEFFICIENT);
		}
		return result;
	}
}
