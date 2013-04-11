package com.swoop.utestdat.data;

import java.io.IOException;
import java.net.URL;

public class SwoopTestCycleSource
	extends UrlDataSource
	implements TestCycleSource
{
	public SwoopTestCycleSource(URL url)
	{
		super(url);
	}

	@Override
	public String get()
		throws IOException
	{
		return query().find("swoop.version.build").require(String.class);
	}
}
