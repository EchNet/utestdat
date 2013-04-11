package com.swoop.utestdat.data;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import net.ech.doc.Document;
import net.ech.doc.JsonDeserializer;

public class UrlDataSource
    implements DataSource
{
	private URL url;
	private InputStream inputStream;

	public UrlDataSource(URL url)
	{
		this.url = url;
	}

	public URL getUrl()
	{
		return url;
	}

	@Override
    public Document query()
        throws IOException
	{
		open();
		try {
			return new Document(new JsonDeserializer().read(new BufferedReader(new InputStreamReader(inputStream))));
		}
		finally {
			close();
		}
	}

	private void open()
		throws IOException
	{
		if (this.inputStream == null) {
			try {
				URLConnection urlConnection = url.openConnection();
				urlConnection.setConnectTimeout(5000);
				urlConnection.setReadTimeout(5000);
				if (urlConnection instanceof HttpURLConnection) {
					urlConnection.setRequestProperty("Accept-Encoding", "gzip");
					switch (((HttpURLConnection) urlConnection).getResponseCode()) {
					case 403:
					case 404:
						throw new FileNotFoundException(url.toString());
					}
				}
				this.inputStream = urlConnection.getInputStream();
				final String contentEncoding = urlConnection.getContentEncoding();
				if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
					this.inputStream = new GZIPInputStream(this.inputStream);
				}
			}
			catch (FileNotFoundException e) {
				throw e;
			}
			catch (IOException e) {
				throw new IOException(url.toString(), e);
			}
		}
	}

	private void close()
		throws IOException
	{
		try {
			inputStream.close();
		}
		finally {
			inputStream = null;
		}
	}
}
