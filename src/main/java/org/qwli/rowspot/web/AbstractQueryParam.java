package org.qwli.rowspot.web;

public abstract class AbstractQueryParam {

    private Integer page;

    private Integer size;

    public Integer getPage() {
        return page == null || page < 1 ? 1 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size == null || size < 10 ? 10 : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
