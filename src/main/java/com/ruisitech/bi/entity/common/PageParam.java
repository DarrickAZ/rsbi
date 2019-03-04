//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.common;

public class PageParam {
    private Integer total;
    private Integer page;
    private Integer rows;
    private String sort;
    private String order;
    private String search;

    public PageParam() {
    }

    public Integer getTotal() {
        if (this.total == null) {
            this.total = 0;
        }

        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        if (this.rows == null) {
            this.rows = 10;
        }

        return this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String toString() {
        return "PageParam [total=" + this.total + ", pageIndex=" + this.page + ", pageSize=" + this.rows + "]";
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
