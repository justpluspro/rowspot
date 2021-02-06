package org.qwli.rowspot.service.file;

import org.qwli.rowspot.model.enums.FileType;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/6 8:44
 * 功能：ReadablePathFileResource
 **/
public class ReadablePathFileResource implements Resource {


    private final static MediaType IMAGE_WEBP = MediaType.valueOf("image/webp");

    private final ReadablePath readablePath;

    public ReadablePathFileResource(ReadablePath readablePath) {
        super();
        this.readablePath = readablePath;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public URL getURL() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public URI getURI() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public File getFile() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long contentLength() throws IOException {
        return readablePath.size();
    }

    @Override
    public long lastModified() throws IOException {
        return readablePath.lastModified();
    }

    @Override
    public Resource createRelative(String s) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return readablePath.fileName();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return readablePath.getInputStream();
    }

    public Optional<MediaType> getMediaType() {
        MediaType type = null;
        String ext = readablePath.getExtension();
        if (FileType.GIF.name().equals(ext) || FileType.GIF.getCode().equals(ext)) {
            type = MediaType.IMAGE_GIF;
        }
        if (FileType.JPEG.name().equals(ext) || FileType.JPEG.getCode().equals(ext)) {
            type = MediaType.IMAGE_JPEG;
        }
        if (FileType.PNG.name().equals(ext) || FileType.PNG.getCode().equals(ext)) {
            type = MediaType.IMAGE_PNG;
        }
        if (FileType.WEBP.name().equals(ext) || FileType.WEBP.getCode().equals(ext)) {
            type = IMAGE_WEBP;
        }
        return Optional.ofNullable(type);
    }

}
