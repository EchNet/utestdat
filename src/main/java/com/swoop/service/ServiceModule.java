package com.swoop.service;

import java.io.IOException;

public interface ServiceModule
{
	public Object execute(ServiceManager serviceManager, Context context)
		throws IOException;
}
