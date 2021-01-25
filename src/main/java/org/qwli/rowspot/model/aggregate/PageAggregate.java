package org.qwli.rowspot.model.aggregate;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public class PageAggregate<T> implements Serializable {

    private Integer page;

    private Integer size;

    private List<T> data;

    private Integer totalPage;

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
    }

    public PageAggregate(List<T> data, int number, int size, int totalPage) {
        this.data = data;
        this.size = size;
        this.page = number;
        this.totalPage = totalPage;
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
