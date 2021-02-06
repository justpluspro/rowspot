package org.qwli.rowspot.service.file;

import java.io.Serializable;

/**
 * @author qwli7
 */
public class Lookup implements Serializable {

    /**
     * 资源 path
     */
    private final String path;

    /**
     * 是否必须存在
     */
    private boolean mustExists;

    /**
     * 是否必须是规则文件
     */
    private boolean mustRegularFile;

    /**
     * 是否必须是目录
     */
    private boolean mustDir;

    /**
     * 是否忽略根目录
     */
    private boolean ignoreRoot;

    public Lookup(String path) {
        super();
        this.path = path;
    }

    public static Lookup newLookup(String path) {
        return new Lookup(path);
    }

    public Lookup setMustExists(boolean mustExists) {
        this.mustExists = mustExists;
        return this;
    }

    public Lookup setMustDir(boolean mustDir) {
        this.mustDir = mustDir;
        return this;
    }

    public Lookup setIgnoreRoot(boolean ignoreRoot) {
        this.ignoreRoot = ignoreRoot;
        return this;
    }

    public Lookup setMustRegularFile(boolean mustRegularFile) {
        this.mustRegularFile = mustRegularFile;
        return this;
    }

    public String getPath() {
        return path;
    }

    public boolean isMustExists() {
        return mustExists;
    }

    public boolean isMustRegularFile() {
        return mustRegularFile;
    }

    public boolean isMustDir() {
        return mustDir;
    }

    public boolean isIgnoreRoot() {
        return ignoreRoot;
    }
}
