package com.swoop.utestdat.controller;

import com.swoop.utestdat.data.*;
import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Controller
{
	private static final int MAX_TRIALS = 100;

	private TestCycleSource testCycleSource;
	private TestTargetSource testTargetSource;
	private TestHistorySource testHistorySource;

	public void setTestCycleSource(TestCycleSource testCycleSource)
	{
		this.testCycleSource = testCycleSource;
	}

	public TestCycleSource getTestCycleSource()
	{
		return testCycleSource;
	}

	public void setTestTargetSource(TestTargetSource testTargetSource)
	{
		this.testTargetSource = testTargetSource;
	}

	public TestTargetSource getTestTargetSource()
	{
		return testTargetSource;
	}

	public void setTestHistorySource(TestHistorySource testHistorySource)
	{
		this.testHistorySource = testHistorySource;
	}

	public TestHistorySource getTestHistorySource()
	{
		return testHistorySource;
	}

	public Set<TestTarget> pick(String user, int n)
		throws IOException
	{
		return new Picker(user, n).result;
	}

	private class Picker
	{
		Random rand;
		int n;
		List<TestTarget> testTargets = buildTestTargets();
		Set<TestTarget> result = new HashSet<TestTarget>();
		
		Picker(String user, int n)
			throws IOException
		{
			this.rand = new Random((user.hashCode() << 32L) | testCycleSource.get().hashCode());
			this.n = n;
			pick();
		}

		void pick()
			throws IOException
		{
			double totalWeight = getTotalWeight();
			int ntrials = 0;
			while (result.size() < n && ntrials < MAX_TRIALS) {
				double index = rand.nextDouble() * totalWeight;
				result.add(indexByWeight(index));
				++ntrials;
			}
		}

		private List<TestTarget> buildTestTargets()
			throws IOException
		{
			List<TestTarget> result = testTargetSource.getTestTargets();
			for (TestTarget tt : result) {
				getTestHistorySource().update(tt);
			}
			return result;
		}

		private double getTotalWeight()
		{
			double totalWeight = 0;
			for (TestTarget tt : testTargets) {
				totalWeight += tt.getWeight();
			}
			return totalWeight;
		}

		private TestTarget indexByWeight(double index)
		{
			double totalWeight = 0;
			TestTarget target = null;
			for (TestTarget tt : testTargets) {
				totalWeight += tt.getWeight();
				if (totalWeight > index) {
					target = tt;
					break;
				}
			}
			return target;
		}
	}
}
