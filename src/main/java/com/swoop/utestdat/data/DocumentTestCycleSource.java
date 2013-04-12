package com.swoop.utestdat.data;

import java.io.IOException;
import net.ech.doc.Document;

public class DocumentTestCycleSource
	extends DocumentBasedDataSource
	implements TestCycleSource
{
	@Override
	public String get()
		throws IOException
	{
		return getDocument().find("swoop.version.build").require(String.class);
	}
}
