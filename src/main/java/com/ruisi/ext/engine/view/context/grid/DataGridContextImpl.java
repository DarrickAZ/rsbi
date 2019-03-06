//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.grid;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.grid.DataGridBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.Iterator;
import java.util.List;

public class DataGridContextImpl extends AbstractContext implements DataGridContext {
    private List a;
    private ColConfigContext b;
    private String c;
    private String d;
    private String e;
    private String f;
    private boolean g;
    public String pageInputName;
    private String h;
    private String i;
    private String j;
    private boolean k = false;
    private String l;
    private Boolean m;
    private String n;
    private String o;
    private String p;
    private PageInfo q;

    public DataGridContextImpl() {
    }

    public String getOut() {
        return this.n;
    }

    public void setOut(String var1) {
        this.n = var1;
    }

    public Boolean getExport() {
        return this.m;
    }

    public void setExport(Boolean var1) {
        this.m = var1;
    }

    public void check() throws ExtConfigException {
        MVContext var1 = RuleUtils.findMVContext(this);
        if (this.l != null && this.l.length() > 0) {
            int var2 = 0;
            Iterator var4 = var1.getDataGrids().values().iterator();

            while(var4.hasNext()) {
                DataGridContext var3 = (DataGridContext)var4.next();
                String var5 = var3.getLabel();
                if (var5 != null && var5.length() > 0 && var5.equals(this.l)) {
                    ++var2;
                }
            }

            if (var2 > 1) {
                String var6 = ConstantsEngine.replace("配置的lebel $0 在文件 $1 (xml)中已经存在.", this.l, var1.getMvid());
                throw new ExtConfigException(var6);
            }
        }

    }

    @Override
    public AbstractBuilder createBuilder() {
        return new DataGridBuilder(this);
    }

    @Override
    public List loadOptions() {
        return this.a;
    }

    @Override
    public void setOptions(List var1) {
        this.a = var1;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return clone;
    }

    @Override
    public PageInfo getPageInfo() {
        return this.q;
    }

    @Override
    public String getTemplateName() {
        return this.c;
    }

    @Override
    public void setTemplateName(String var1) {
        this.c = var1;
    }

    @Override
    public String getDataId() {
        return this.d;
    }

    @Override
    public void setDataId(String var1) {
        this.d = var1;
    }

    @Override
    public void setPageInfo(PageInfo var1) {
        this.q = var1;
    }

    public String getPageInfoId() {
        return this.e;
    }

    public void setPageInfoId(String var1) {
        this.e = var1;
    }

    @Override
    public String getRef() {
        return this.f;
    }

    @Override
    public void setRef(String var1) {
        this.f = var1;
    }

    @Override
    public boolean isAjax() {
        return this.g;
    }

    @Override
    public void setAjax(boolean var1) {
        this.g = var1;
    }

    @Override
    public String getId() {
        return this.h;
    }

    @Override
    public String getPageInputName() {
        return this.pageInputName;
    }

    @Override
    public void setPageInputName(String var1) {
        this.pageInputName = var1;
    }

    @Override
    public void setId(String var1) {
        this.h = var1;
    }

    @Override
    public String getDataRef() {
        return this.j;
    }

    @Override
    public void setDataRef(String var1) {
        this.j = var1;
    }

    @Override
    public ColConfigContext getColConfigContext() {
        return this.b;
    }

    @Override
    public void setColConfigContext(ColConfigContext var1) {
        this.b = var1;
    }

    @Override
    public boolean isInit() {
        return this.k;
    }

    @Override
    public void setInit(boolean var1) {
        this.k = var1;
    }

    @Override
    public String getLabel() {
        return this.l;
    }

    @Override
    public void setLabel(String var1) {
        this.l = var1;
    }

    @Override
    public String getRightMenuFunc() {
        return this.o;
    }

    @Override
    public void setRightMenuFunc(String var1) {
        this.o = var1;
    }

    @Override
    public String getTargetDiv() {
        return this.p;
    }

    @Override
    public void setTargetDiv(String var1) {
        this.p = var1;
    }

    @Override
    public String getRefDsource() {
        return this.i;
    }

    @Override
    public void setRefDsource(String var1) {
        this.i = var1;
    }
}
