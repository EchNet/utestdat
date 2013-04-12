package com.swoop.utestdat.data;

import java.io.IOException;
import net.ech.doc.Document;

/*
 * Enable adjustment of weights depending on external JSON document.
 */
abstract public class DocumentBasedDataSource
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

	protected Document getDocument()
		throws IOException
	{
		return getDocumentCache().getDocument(getKey());
	}
}
