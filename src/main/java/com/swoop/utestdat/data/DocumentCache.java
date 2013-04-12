package com.swoop.utestdat.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.ech.doc.Document;
import net.ech.doc.DocumentResolver;

/**
 * A simple document cache.
 */
public class DocumentCache
{
	private DocumentResolver documentResolver;
	private Map<String,Document> cache = new HashMap<String,Document>();

	public DocumentResolver getDocumentResolver()
	{
		return documentResolver;
	}

	public void setDocumentResolver(DocumentResolver documentResolver)
	{
		this.documentResolver = documentResolver;
	}

	public synchronized Document getDocument(String key)
		throws IOException
	{
		if (!cache.containsKey(key)) {
			cache.put(key, getDocumentResolver().resolve(key).produce());
		}
		return cache.get(key);
	}
}
