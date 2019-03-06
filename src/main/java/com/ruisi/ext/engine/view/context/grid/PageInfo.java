//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.grid;

public class PageInfo {
    protected long curtpage;
    protected int pagesize = 10;
    protected long allsize;

    public PageInfo() {
    }

    public PageInfo(int var1) {
        this.pagesize = var1;
    }

    public long getAllpage() {
        long var1 = 0L;
        if (this.allsize % (long)this.pagesize == 0L) {
            var1 = this.allsize / (long)this.pagesize;
        } else {
            var1 = this.allsize / (long)this.pagesize + 1L;
        }

        return var1;
    }

    public long getAllsize() {
        return this.allsize;
    }

    public void setAllsize(long var1) {
        this.allsize = var1;
    }

    public long getCurtpage() {
        return this.curtpage;
    }

    public void setCurtpage(long var1) {
        this.curtpage = var1;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int var1) {
        this.pagesize = var1;
    }
}
