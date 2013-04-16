package com.swoop.service;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Context
{
	private Map<String,Getter<?>> lookup = new HashMap<String,Getter<?>>();

	public static interface Getter<T>
	{
		public T get(Context context, String key)
			throws IOException;
	}

	public void installGetter(String key, Getter<?> getter)
	{
		lookup.put(key, getter);
	}

	public <T> void installValue(String key, final T value)
	{
		installGetter(key, new Getter<T>() {
			@Override
			public T get(Context context, String key) {
				return value;
			}
		});
	}

	public <T> T get(String key, Class<T> requiredClass)
		throws IOException
	{
		if (!lookup.containsKey(key)) {
			throw new IllegalArgumentException(key + ": context variable required");
		}
		Getter<?> getter = lookup.get(key);
		Object obj = getter.get(this, key);
		return requiredClass.cast(obj);
	}
}
