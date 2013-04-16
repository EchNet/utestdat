package com.swoop.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.swoop.mongo.MongoCollectionAction;
import com.swoop.mongo.MongoPool;
import java.io.IOException;
import java.util.Map;
import net.ech.doc.Document;
import net.ech.util.StrongReference;

public class MongoConnector
{
	private MongoPool mongoPool;
	private String uri;
	private String collection;

	public MongoPool getMongoPool()
	{
		return mongoPool;
	}

	public void setMongoPool(MongoPool mongoPool)
	{
		this.mongoPool = mongoPool;
	}

	public String getUri()
	{
		return uri;
	}

	public void setUri(String uri)
	{
		this.uri = uri;
	}

	public String getCollection()
	{
		return collection;
	}

	public void setCollection(String collection)
	{
		this.collection = collection;
	}

	public Document findOne(final Map<String,Object> query)
		throws IOException
	{
		return findOne(query, null);
	}

	public Document findOne(final Map<String,Object> query, final Map<String,Object> filters)
		throws IOException
	{
		final StrongReference<Object> objRef = new StrongReference<Object>();

		mongoPool.createCollection(new MongoURI(uri), collection).act(new MongoCollectionAction() {
			@Override
			public void act(DBCollection dbc)
				throws IOException, MongoException
			{
				objRef.set(dbc.findOne(mongoize(query), mongoize(filters)));
			}
		});

		return new Document(objRef.get());
	}

	public Document findMany(final Map<String,Object> query, int limit)
		throws IOException
	{
		return findMany(query, null, limit);
	}

	public Document findMany(final Map<String,Object> query, final Map<String,Object> filter, final int limit)
		throws IOException
	{
		final StrongReference<Object> objRef = new StrongReference<Object>();

		mongoPool.createCollection(new MongoURI(uri), collection).act(new MongoCollectionAction() {
			@Override
			public void act(DBCollection dbc)
				throws IOException, MongoException
			{
				DBObject[] array = dbc.find(mongoize(query), mongoize(filter)).toArray(limit).toArray(new DBObject[0]);
				for (DBObject obj : array) {
					obj.removeField("_id");
				}
				objRef.set(array);
			}
		});

		return new Document(objRef.get());
	}

	private DBObject mongoize(Map<String,Object> parameters)
	{
		DBObject q = new BasicDBObject();
		for (Map.Entry<String,Object> entry : parameters.entrySet()) {
			q.put(entry.getKey(), entry.getValue());
		}
		return q;
	}
}
