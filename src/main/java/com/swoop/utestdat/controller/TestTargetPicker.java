package com.swoop.utestdat.controller;

import com.swoop.service.Context;
import com.swoop.service.ServiceManager;
import com.swoop.service.ServiceModule;
import com.swoop.utestdat.data.TestTargetSource;
import com.swoop.utestdat.data.TestHistorySource;
import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import net.ech.doc.Document;
import net.ech.doc.DocumentProducer;

public class TestTargetPicker
	implements ServiceModule
{
	@Override
	public Object execute(ServiceManager serviceManager, Context context)
		throws IOException
	{
		return new Picker(context).pick();
	}

	private class Picker
	{
		private Context context;
		private List<TestTarget> testTargetList;

		public Picker(Context context)
			throws IOException
		{
			this.context = context;
			this.testTargetList = context.get("testTargetList", List.class);
		}

		public Set<TestTarget> pick()
			throws IOException
		{
			Set<TestTarget> result = new HashSet<TestTarget>();
			int limit = context.get("limit", Integer.class);
			double totalWeight = getTotalWeight();
			while (result.size() < limit && testTargetList.size() > 0) {
				double weightedRandom = context.get("random", Double.class) * totalWeight;
				int index = selectTestTarget(weightedRandom);
				TestTarget tt = testTargetList.get(index);
				result.add(tt);
				totalWeight -= tt.getWeight();
				testTargetList.remove(index);
			}
			return result;
		}

		private double getTotalWeight()
		{
			double totalWeight = 0;
			for (TestTarget tt : testTargetList) {
				totalWeight += tt.getWeight();
			}
			return totalWeight;
		}

		private int selectTestTarget(double weightedRandom)
		{
			double runningWeight = 0;
			for (int i = 0; i < testTargetList.size(); ++i) {
				TestTarget tt = testTargetList.get(i);
				runningWeight += tt.getWeight();
				if (runningWeight > weightedRandom) {
					return i;
				}
			}
			return -1;
		}
	}
}
