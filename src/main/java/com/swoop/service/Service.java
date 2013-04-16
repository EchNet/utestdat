package com.swoop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Service
	implements ServiceModule
{
	private Map<String,Context.Getter<?>> getters;
	private List<String> sequence;

	public void setGetters(Map<String,Context.Getter<?>> getters)
	{
		this.getters = getters;
	}

	public void setSequence(List<String> sequence)
	{
		this.sequence = sequence;
	}

	public Context createContext(String path, Map<String,String> parameters)
		throws IOException
	{
		Context context = new Context();
		context.installValue("path", path);
		for (Map.Entry<String,String> param : parameters.entrySet()) {
			context.installValue(param.getKey(), param.getValue());
		}
		for (Map.Entry<String,Context.Getter<?>> entry : getters.entrySet()) {
			context.installGetter(entry.getKey(), entry.getValue());
		}
		return context;
	}

	@Override
	public Object execute(ServiceManager serviceManager, Context context)
		throws IOException
	{
		Object last = null;
		for (String moduleName : sequence) {
			last = serviceManager.lookupServiceModule(moduleName).execute(serviceManager, context);
			context.installValue("$last", last);
		}
		return last;
	}
}
