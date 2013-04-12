package com.swoop.utestdat.controller;

import com.swoop.utestdat.data.TestCycleSource;
import com.swoop.utestdat.data.TestTargetSource;
import com.swoop.utestdat.data.TestHistorySource;
import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Controller
{
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
			while (result.size() < n && testTargets.size() > 0) {
				double weightedRandom = rand.nextDouble() * getTotalWeight();
				int index = select(weightedRandom);
				result.add(testTargets.get(index));
				testTargets.remove(index);
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

		private int select(double weightedRandom)
		{
			double runningWeight = 0;
			for (int i = 0; i < testTargets.size(); ++i) {
				TestTarget tt = testTargets.get(i);
				runningWeight += tt.getWeight();
				if (runningWeight > weightedRandom) {
					return i;
				}
			}
			return -1;
		}
	}
}
