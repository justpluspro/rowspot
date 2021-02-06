package org.qwli.rowspot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author qwli7
 * @date 2021/2/6 14:00
 * 功能：FileUtil
 **/
public class FileUtil {

    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        super();
    }


    public static void forceMkdir(Path path) throws IOException {
        if(path == null) {
            return;
        }
        if(Files.exists(path) && Files.isDirectory(path)) {
            return;
        }
        synchronized (FileUtil.class){
            if(!Files.exists(path)){
                Files.createDirectories(path);
            } else {
                if(!Files.isDirectory(path)) {
                    throw new IOException("目标位置" + path + "已经存在文件，并且不是文件夹");
                }
            }
        }
    }


    public static void deleteQuietly(Path path) {
        if(!Files.exists(path)){
            return;
        }
        synchronized (FileUtil.class) {
            try {
                Files.walkFileTree(path, new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        return null;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // delete file
                        Files.deleteIfExists(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        return null;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        // delete directory
                        Files.deleteIfExists(path);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException ex) {
                logger.error("delete file occurred exception:[{}]", ex.getMessage(), ex);
            }
        }
    }

    public static void move(Path file, Path target) throws IOException {
        if(file == null || target == null) {
            return;
        }
        forceMkdir(target.getParent());
        Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * get filename without ext
     * @param fullName fullName
     * @return String
     */
    public static String getFileNameWithoutExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static String getFileExtension(String path) {
        return getFileExtension(new File(path));
    }

    public static String getFileExtension(Path path) {
        return getFileExtension(path.toFile());
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex+1);
    }
}
