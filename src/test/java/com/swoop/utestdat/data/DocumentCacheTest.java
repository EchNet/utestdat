package com.swoop.utestdat.data;

import java.io.IOException;
import net.ech.doc.Document;
import net.ech.doc.ChildDocumentResolver;
import net.ech.doc.DocumentResolver;
import net.ech.util.Hash;
import org.junit.*;
import static org.junit.Assert.*;

public class DocumentCacheTest
{
	DocumentCache documentCache;

	@Before
	public void setUp() throws Exception
	{
		Document doc = new Document(new Hash()
			.addEntry("a1", new Hash("name", "a1"))
			.addEntry("a2", new Hash("name", "a2")));
		DocumentResolver documentResolver = new ChildDocumentResolver(doc);
		documentCache = new DocumentCache();
		documentCache.setDocumentResolver(documentResolver);
	}

	@Test
	public void testGetDocument() throws Exception
	{
		assertNotNull(documentCache.getDocument("a1"));
		assertNotNull(documentCache.getDocument("a2"));
	}

	@Test
	public void testGetDocumentGetsCorrectData() throws Exception
	{
		assertEquals("a1", documentCache.getDocument("a1").find("name").get(String.class));
	}

	@Test
	public void testGetDocumentCaches() throws Exception
	{
		assertTrue(documentCache.getDocument("a1") == documentCache.getDocument("a1"));
	}

	@Test
	public void testGetDocumentNotFound() throws Exception
	{
		try {
			documentCache.getDocument("a3");
			fail("should not be reached");
		}
		catch (IOException e) {
		}
	}
}
