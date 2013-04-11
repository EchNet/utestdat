package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;

public interface TestHistorySource
{
	public void update(TestTarget testTarget)
		throws IOException;
}
