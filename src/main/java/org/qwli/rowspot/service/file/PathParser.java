package org.qwli.rowspot.service.file;

import org.qwli.rowspot.util.FileUtil;
import org.springframework.util.StringUtils;

/**
 * @author qwli7
 * <ul>
 *      <li>123.png=>empty</li>
 *      <li>123.png/200=>new Resize(200)</li>
 *      <li>123.png/200x300=>new Resize(200,300,true)</li>
 *      <li>123.png/200x300!=>new Resize(200,300,false)</li>
 *      <li>123.png/x300=>new Resize(null,300,true)</li>
 *      <li>123.png/200x=>new Resize(200,null,true)</li>
 * </ul>
 */
public class PathParser {

    private static final String CONCAT = "x";
    private static final String FORCE = "!";
    private static final Resize INVALID_RESIZE = new Resize(-1);


    private final String sourcePath;
    private final Resize resize;
    private final String path;


    public boolean isValid() {
        if (this.resize == null || this.resize == INVALID_RESIZE) {
            return !this.path.equals(this.sourcePath);
        }
        return this.resize.isInvalid();
    }


    public Resize getResize() {
        return resize;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getPath() {
        return path;
    }

    private String getSourcePathByResizePath(String path) {
        String extension = FileUtil.getFileExtension(path);
        if (!StringUtils.hasText(extension)) {
            return path;
        }
        String sourcePath = path;
        int index = path.lastIndexOf('/');
        if (index != -1) {
            sourcePath = path.substring(0, index);
        }

        return sourcePath;
    }

    public PathParser(String path) {
        this.path = path;
        this.resize = getResizeFromRequestPath(path);
        this.sourcePath = getSourcePathByResizePath(path);
    }


    public Resize getResizeFromRequestPath(String path) {

        String extension = FileUtil.getFileExtension(path);
        if (!StringUtils.hasText(extension)) {
            return null;
        }

        String name = FileUtil.getFileNameWithoutExtension(path);

        if (!name.contains(CONCAT)) {
            try {
                return new Resize(Integer.parseInt(name));
            } catch (NumberFormatException ex) {
                return INVALID_RESIZE;
            }
        }
        //only height metrics
        if (name.startsWith(CONCAT)) {
            try {
                return new Resize(null, Integer.parseInt(name.substring(1)),
                        true);
            } catch (NumberFormatException ex) {
                return INVALID_RESIZE;
            }
        }
        //only width metrics
        if (name.endsWith(CONCAT)) {
            try {
                return new Resize(Integer.parseInt(name.substring(0, name.length() - 1)),
                        null, true);
            } catch (NumberFormatException ex) {
                return INVALID_RESIZE;
            }
        }

        boolean keepRatio = !name.endsWith(FORCE);
        String sizeInfo = keepRatio ? name : name.substring(0, name.length() - 1);

        String[] array = sizeInfo.split(CONCAT);
        if (array.length != 2) {
            return INVALID_RESIZE;
        }
        try {
            return new Resize(Integer.parseInt(array[0]), Integer.parseInt(array[1]), keepRatio);
        } catch (NumberFormatException ex) {
            return INVALID_RESIZE;
        }
    }
}
