package com.swoop.utestdat.controller;

import com.swoop.service.Context;
import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import net.ech.doc.Document;
import net.ech.doc.DocumentProducer;

public class DefaultTestTargetListSource
	implements Context.Getter<List<TestTarget>>
{
	private final static double ACTIVITY_COEFFICIENT = 0.001;

	private DocumentProducer testTargetSource;
	private DocumentProducer testHistorySource;

	private void setTestTargetSource(DocumentProducer testTargetSource)
	{
		this.testTargetSource = testTargetSource;
	}

	private void setTestHistorySource(DocumentProducer testHistorySource)
	{
		this.testHistorySource = testHistorySource;
	}

	@Override
	public List<TestTarget> get(Context context, String key)
		throws IOException
	{
		Document testTargetDoc = testTargetSource.produce();
		Document testHistoryDoc = testHistorySource.produce();

		List<TestTarget> result = new ArrayList<TestTarget>();
		for (Document ttChild : testTargetDoc.children()) {
			String testTargetId =  ttChild.getName();
			TestTarget tt = new TestTarget(testTargetId);
			int activityLevel = ttChild.find("page").find("views").cast(Integer.class, 0);
			tt.setWeight(activityLevel * ACTIVITY_COEFFICIENT);
			Document forTestTarget = testHistoryDoc.find(tt.getId());
			if (!forTestTarget.isNull()) {
				double mult = forTestTarget.find("weight").cast(Double.class, 1.0);
				tt.setWeight(tt.getWeight() * mult); 
				tt.setName(forTestTarget.find("domain").cast(String.class, "???"));
			}
		}
		return result;
	}
}
