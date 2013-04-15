package com.swoop.utestdat.data;

import com.swoop.utestdat.model.TestTarget;
import java.io.IOException;
import net.ech.doc.Document;
import net.ech.doc.DocumentResolver;
import net.ech.doc.ChildDocumentResolver;
import net.ech.util.Hash;
import org.junit.*;
import static org.junit.Assert.*;

public class DocumentTestHistorySourceTest
{
	DocumentTestHistorySource testHistorySource;

	@Before
	public void setUp()
	{
		Document config = new Document(new Hash("testHistory", new Hash("tt", new Hash("weight", 3.5))));
		DocumentResolver documentResolver = new ChildDocumentResolver(config);
		DocumentCache documentCache = new DocumentCache();
		documentCache.setDocumentResolver(documentResolver);
		testHistorySource = new DocumentTestHistorySource();
		testHistorySource.setDocumentCache(documentCache);
		testHistorySource.setKey("testHistory");
	}

	@Test
	public void testUpdate() throws Exception
	{
		TestTarget tt = new TestTarget("tt");
		tt.setName("tt");
		tt.setWeight(1);
		testHistorySource.update(tt);
		assertEquals(3.5, tt.getWeight(), 0.00000001);
	}
}
