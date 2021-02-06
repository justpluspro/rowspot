package org.qwli.rowspot.service.file;

import java.io.Serializable;

/**
 * @author qwli7
 * 缩放属性
 */
public class Resize implements Serializable {

    private Integer size;

    private Integer height;

    private Integer width;

    private boolean keepRatio;

    private Integer quality;

    public Resize(Integer size) {
        super();
        this.size = size;
    }

    public Resize(Integer width, Integer height) {
        super();
        this.height = height;
        this.width = width;
    }

    public Resize(Integer width, Integer height, boolean keepRatio) {
        super();
        this.width = width;
        this.height = height;
        this.keepRatio = keepRatio;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isKeepRatio() {
        return keepRatio;
    }

    public void setKeepRatio(boolean keepRatio) {
        this.keepRatio = keepRatio;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isInvalid() {
        if(size != null) {
            return size <= 0;
        } else {
            if (width == null && height == null) {
                return true;
            }
            if (width != null && width <= 0) {
                return true;
            }
            return height != null && height <= 0;
        }
    }
}
