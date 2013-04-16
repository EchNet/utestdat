package com.swoop.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager
{
	private Map<String,Object> services;
	private Map<String,Object> modules;

	public void setServices(Map<String,Object> services)
	{
		this.services = services;
	}

	public void setModules(Map<String,Object> modules)
	{
		this.modules = modules;
	}

	public Service lookupService(String serviceName)
		throws IOException
	{
		if (!services.containsKey(serviceName)) {
			throw new FileNotFoundException(serviceName);
		}

		Object obj = services.get(serviceName);
		if (!(obj instanceof Service)) {
			throw new IOException("configuration error - " + serviceName + " is not a service.");
		}
		return (Service) obj;
	}

	public ServiceModule lookupServiceModule(String moduleName)
		throws IOException
	{
		if (!modules.containsKey(moduleName)) {
			throw new IOException("configuration error - module " + moduleName + " not found");
		}

		Object obj = modules.get(moduleName);
		if (!(obj instanceof ServiceModule)) {
			throw new IOException("configuration error - " + moduleName + " is not a service module.");
		}
		return (ServiceModule) obj;
	}

	public Object service(String path, Map<String,String> parameters)
		throws IOException
	{
		Service service = lookupService(path);
		Context context = service.createContext(path, parameters);
		return service.execute(this, context);
	}
}
