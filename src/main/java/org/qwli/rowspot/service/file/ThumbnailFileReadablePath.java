package org.qwli.rowspot.service.file;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qwli7
 * @date 2021/2/6 17:22
 * 功能：ThumbnailFileReadablePath
 **/
public class ThumbnailFileReadablePath implements ReadablePath {

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String fileName() {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public long lastModified() {
        return 0;
    }

    @Override
    public String getExtension() {
        return null;
    }
}
