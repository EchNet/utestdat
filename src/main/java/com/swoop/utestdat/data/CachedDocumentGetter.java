package com.swoop.utestdat.data;

import com.swoop.service.Context;
import java.io.IOException;
import net.ech.doc.Document;
import net.ech.doc.DocumentProducer;

public class CachedDocumentGetter
	implements Context.Getter<Document>, DocumentProducer
{
	private String key;
	private DocumentCache documentCache;

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public DocumentCache getDocumentCache()
	{
		return documentCache;
	}

	public void setDocumentCache(DocumentCache documentCache)
	{
		this.documentCache = documentCache;
	}

	@Override
	public Document produce()
		throws IOException
	{
		return getDocumentCache().getDocument(getKey());
	}

	@Override
	public Document get(Context context, String key)
		throws IOException
	{
		return produce();
	}
}
