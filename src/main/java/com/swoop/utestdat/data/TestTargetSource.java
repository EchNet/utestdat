package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import java.util.List;

public interface TestTargetSource
{
	public List<TestTarget> getTestTargets()
		throws IOException;
}
