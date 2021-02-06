package org.qwli.rowspot.service.file;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qwli7
 * @date 2021/2/6 8:50
 * 功能：ReadablePath
 **/
public interface ReadablePath {

    /**
     * inputStream
     * @return inputStream
     * @throws IOException ioException
     */
    InputStream getInputStream() throws IOException;

    /**
     * fileName
     * @return fileName
     */
    String fileName();

    /**
     * size
     * @return long
     */
    long size();

    /**
     * lastModifiedTime
     * @return long
     */
    long lastModified();

    /**
     * extension
     * @return String
     */
    String getExtension();
}
