package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.util.ArrayList;
import java.util.List;

public class DummyTestTargetSource
	implements TestTargetSource
{
	@Override
	public List<TestTarget> getTestTargets()
	{
		List<TestTarget> result = new ArrayList<TestTarget>();
		for (int i = 1; i <= 5; ++i) {
			TestTarget tt = new TestTarget("test" + i);
			tt.setName("www.test" + i + ".com");
			tt.setWeight(i);
		}
		return result;
	}
}
