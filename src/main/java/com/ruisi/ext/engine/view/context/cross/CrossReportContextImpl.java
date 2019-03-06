//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.cross.CrossReportBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CrossReportContextImpl extends AbstractContext implements CrossReportContext {
    private String a;
    private String b;
    private Map c;
    private Map d;
    private List e;
    private CrossCols f;
    private CrossRows g;
    private String h;
    private String i;
    private Boolean j;
    private PageInfo k;
    private Boolean l;
    private Boolean m;
    private String n;
    private String o;
    private CrossKpi p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v = "def";
    private boolean w = true;
    private String x;
    private List y;
    private String z;
    private Boolean A;
    private List B;
    private String C;

    public CrossReportContextImpl() {
    }

    public Map getExtDatas() {
        return this.c;
    }

    @Override
    public void setExtDatas(Map var1) {
        this.c = var1;
    }

    @Override
    public String getTotalTemplateName() {
        return this.b;
    }

    @Override
    public void setTotalTemplateName(String var1) {
        this.b = var1;
    }

    @Override
    public String getRefDsource() {
        return this.s;
    }

    @Override
    public void setRefDsource(String var1) {
        this.s = var1;
    }

    @Override
    public String getRefDataCetner() {
        return this.q;
    }

    @Override
    public void setRefDataCetner(String var1) {
        this.q = var1;
    }

    @Override
    public String getFirstSort() {
        return this.o;
    }

    @Override
    public void setFirstSort(String var1) {
        this.o = var1;
    }

    @Override
    public String getExportName() {
        return this.n;
    }

    @Override
    public void setExportName(String var1) {
        this.n = var1;
    }

    @Override
    public String getHeight() {
        return this.u;
    }

    @Override
    public void setHeight(String var1) {
        this.u = var1;
    }

    @Override
    public Boolean getPrint() {
        return this.m;
    }

    @Override
    public void setPrint(Boolean var1) {
        this.m = var1;
    }

    @Override
    public Boolean getExport() {
        return this.l;
    }

    @Override
    public void setExport(Boolean var1) {
        this.l = var1;
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.getCrossCols().getCols() != null && this.getCrossCols().getCols().size() != 0) {
            if (this.getCrossRows().getRows() != null && this.getCrossRows().getRows().size() != 0) {
                MVContext var1 = RuleUtils.findMVContext(this);
                Iterator var4;
                String var8;
                if (this.z != null && this.z.length() > 0) {
                    int var2 = 0;
                    var4 = var1.getCrossReports().values().iterator();

                    while(var4.hasNext()) {
                        CrossReportContext var3 = (CrossReportContext)var4.next();
                        String var5 = var3.getLabel();
                        if (var5 != null && var5.length() > 0 && var5.equals(this.z)) {
                            ++var2;
                        }
                    }

                    if (var2 > 1) {
                        var8 = ConstantsEngine.replace("配置的lebel $0 在文件 $1 (xml)中已经存在.", this.z, var1.getMvid());
                        throw new ExtConfigException(var8);
                    }
                }

                if (this.getCrossRows().getLink() != null) {
                    this.getCrossRows().getLink().check();
                }

                String var6 = this.getRefDataCetner();
                if (var6 != null && var6.length() > 0 && !var1.getGridDataCenters().containsKey(var6)) {
                    var8 = ConstantsEngine.replace(" crossReport 中引用的dataCenter ($0) 不存在. mv = $1", var6, var1.getMvid());
                    throw new ExtConfigException(var8);
                } else {
                    if (this.getDims() != null) {
                        var4 = this.getDims().iterator();

                        while(var4.hasNext()) {
                            RowDimContext var7 = (RowDimContext)var4.next();
                            var7.check();
                        }
                    }

                }
            } else {
                throw new ExtConfigException("缺少 row 表头.");
            }
        } else {
            throw new ExtConfigException("缺少 col 表头.");
        }
    }

    @Override
    public String getLabel() {
        return this.z;
    }

    @Override
    public void setLabel(String var1) {
        this.z = var1;
    }

    @Override
    public String getDataUrl() {
        return this.C;
    }

    @Override
    public void setDataUrl(String var1) {
        this.C = var1;
    }

    @Override
    public Object clone() {
        CrossReportContext var1 = null;
        try {
            var1 = (CrossReportContext)super.clone();
            var1.setCrossCols(this.getCrossCols().clone());
            var1.setCrossRows(this.getCrossRows().clone());
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        if (var1.getDims() != null) {
            var1.setDims((List)null);
        }

        return var1;
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new CrossReportBuilder(this);
    }

    @Override
    public String getWidth() {
        return this.t;
    }

    @Override
    public void setWidth(String var1) {
        this.t = var1;
    }

    @Override
    public String getConfCallBack() {
        return this.r;
    }

    @Override
    public void setConfCallBack(String var1) {
        this.r = var1;
    }

    @Override
    public String getTemplateName() {
        return this.a;
    }

    @Override
    public void setTemplateName(String var1) {
        this.a = var1;
    }

    @Override
    public List loadOptions() {
        return this.e;
    }

    @Override
    public void setOptions(List var1) {
        this.e = var1;
    }

    @Override
    public String getRef() {
        return this.h;
    }

    @Override
    public void setRef(String var1) {
        this.h = var1;
    }

    @Override
    public CrossCols getCrossCols() {
        return this.f;
    }

    @Override
    public void setCrossCols(CrossCols var1) {
        this.f = var1;
    }

    @Override
    public CrossRows getCrossRows() {
        return this.g;
    }

    @Override
    public void setCrossRows(CrossRows var1) {
        this.g = var1;
    }

    @Override
    public String getId() {
        return this.i;
    }

    @Override
    public void setId(String var1) {
        this.i = var1;
    }

    @Override
    public boolean isShowData() {
        return this.w;
    }

    @Override
    public void setShowData(boolean var1) {
        this.w = var1;
    }

    @Override
    public String getOut() {
        return this.x;
    }

    @Override
    public void setOut(String var1) {
        this.x = var1;
    }

    @Override
    public List getDims() {
        return this.B;
    }

    @Override
    public void setDims(List var1) {
        this.B = var1;
    }

    @Override
    public PageInfo getPageInfo() {
        return this.k;
    }

    @Override
    public void setPageInfo(PageInfo var1) {
        this.k = var1;
    }

    @Override
    public CrossKpi getBaseKpi() {
        return this.p;
    }

    @Override
    public void setBaseKpi(CrossKpi var1) {
        this.p = var1;
    }

    @Override
    public List getRowHeads() {
        return this.y;
    }

    @Override
    public void setRowHeads(List var1) {
        this.y = var1;
    }

    @Override
    public Boolean getOrderDrill() {
        return this.j;
    }

    @Override
    public void setOrderDrill(Boolean var1) {
        this.j = var1;
    }

    @Override
    public Boolean getBgAgg() {
        return this.A;
    }

    @Override
    public void setBgAgg(Boolean var1) {
        this.A = var1;
    }

    @Override
    public Map getExtSqlTemplates() {
        return this.d;
    }

    @Override
    public void setExtSqlTemplates(Map var1) {
        this.d = var1;
    }

    @Override
    public String getStyle() {
        return this.v;
    }

    @Override
    public void setStyle(String var1) {
        this.v = var1;
    }
}
