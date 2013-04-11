package com.swoop.utestdat.data;

import java.io.IOException;
import net.ech.doc.Document;

public interface DataSource
{
    public Document query()
        throws IOException;
}
