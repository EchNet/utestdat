package com.swoop.utestdat.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import net.ech.doc.Document;
import net.ech.doc.JsonDeserializer;

public class ResourceDataSource
    implements DataSource
{
	private String path;
	private Document cached;

	public ResourceDataSource(String path)
	{
		this.path = path;
	}

	public String getPath()
	{
		return path;
	}

	@Override
    public Document query()
        throws IOException
	{
		if (cached == null) {
			InputStream inputStream = ResourceDataSource.class.getClassLoader().getResourceAsStream(path);
			if (inputStream == null) {
				throw new FileNotFoundException("resource:" + path);
			}

			Reader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				cached = new Document(new JsonDeserializer().read(reader));
			}
			finally {
				reader.close();
			}
		}
		return cached;
	}
}
