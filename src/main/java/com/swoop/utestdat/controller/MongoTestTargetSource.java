package com.swoop.utestdat.controller;

import com.swoop.mongo.MongoConnector;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.ech.doc.Document;
import net.ech.doc.DocumentProducer;

public class MongoTestTargetSource
	extends MongoConnector
	implements DocumentProducer 
{
	@Override
	public Document produce()
		throws IOException
	{
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> filters = new HashMap<String,Object>();
		filters.put("domains", 1);

		Document testTargetData = findOne(parameters, filters);
		if (testTargetData.isNull()) {
			throw new IOException("cannot find a recent page events report"); 
		}
		Document domains = testTargetData.find("domains");
		if (domains.isNull()) {
			throw new IOException("page events report is missing domains?"); 
		}

		return domains;
	}
}
