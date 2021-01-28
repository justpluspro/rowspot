package org.qwli.rowspot.web;

/**
 * @author qwli7
 * 基础查询参数
 */
public abstract class AbstractQueryParam {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 分页大小
     */
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
