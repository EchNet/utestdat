package com.swoop.utestdat.controller;

import com.swoop.service.Context;
import java.io.IOException;
import java.util.Random;
import net.ech.doc.Document;

public class DefaultRandomNumberGenerator 
	implements Context.Getter<Double>
{
	private Random rng;

	@Override
	public Double get(Context context, String key)
		throws IOException
	{
		if (rng == null) {
			String userString = context.get("user", String.class);

			Document versionDoc = context.get("versionDoc", Document.class);
			String buildString = versionDoc.find("swoop.version.build").require(String.class);

			rng = new Random((userString.hashCode() << 32L) | buildString.hashCode());
		}
		return rng.nextDouble();
	}
}
