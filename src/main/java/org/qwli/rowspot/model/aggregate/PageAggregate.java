package org.qwli.rowspot.model.aggregate;

import java.io.Serializable;
import java.util.List;

/**
 * @author qwli7
 * 分页对象
 */
public class PageAggregate<T> implements Serializable {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 当前页大小
     */
    private Integer size;

    /**
     * 数据集合
     */
    private List<T> data;

    /**
     * 总页码
     */
    private Integer totalPage;

    /**
     * 是否是最后一页
     */
    private boolean lastPage;

    /**
     * 是否是第一页
     */
    private boolean firstPage;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有上一页
     */
    private boolean hasPrePage;


    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public PageAggregate(List<T> data, int number, int totalPage) {
        this.data = data;
        this.page = number;
        this.totalPage = totalPage;
        this.firstPage = getPage() == 1;
        this.lastPage = getPage().equals(getTotalPage());
        this.hasPrePage = !isFirstPage();
        this.hasNextPage = !isLastPage();
    }

    public PageAggregate(List<T> data, int number, int size, int totalPage) {
        this.data = data;
        this.size = size;
        this.page = number;
        this.totalPage = totalPage;
        this.firstPage = getPage() == 1;
        this.lastPage = getPage().equals(getTotalPage());
        this.hasPrePage = !isFirstPage();
        this.hasNextPage = !isLastPage();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
