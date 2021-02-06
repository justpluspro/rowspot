package org.qwli.rowspot.service.file;

import org.qwli.rowspot.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * @author qwli7
 * NormalFileReadablePath
 */
public class NormalFileReadablePath implements ReadablePath {

    private final Path path;

    public NormalFileReadablePath(Path path) {
        this.path = path;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public String fileName() {
        return path.getFileName().toString();
    }

    @Override
    public long size() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long lastModified() {
        try {
            return Files.getLastModifiedTime(path).to(TimeUnit.SECONDS);
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public String getExtension() {
        return FileUtil.getFileExtension(path.getFileName());
    }
}
