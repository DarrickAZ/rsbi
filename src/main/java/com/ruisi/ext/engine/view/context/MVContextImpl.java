//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.MVBuilder;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.html.SubmitCheckContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.HashMap;
import java.util.Map;

public class MVContextImpl extends AbstractContext implements MVContext {
    private String a;
    private String b;
    private boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private Boolean f = false;
    private Map g;
    private Map h;
    private Map i;
    private Map j;
    private Map k;
    private SubmitCheckContext l;
    private Map m;
    private String n;
    private Map o;
    private Map p;
    private Map q;

    public MVContextImpl() {
    }

    @Override
    public Map getCubeDataCenters() {
        return this.p;
    }

    @Override
    public void setCubeDataCenters(Map var1) {
        this.p = var1;
    }

    @Override
    public String getMvid() {
        return this.a;
    }

    @Override
    public void setMvid(String var1) {
        this.a = var1;
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new MVBuilder(this);
    }

    @Override
    public String getFormId() {
        return this.b;
    }

    @Override
    public void setFormId(String var1) {
        this.b = var1;
    }

    @Override
    public boolean isShowForm() {
        return this.c;
    }

    @Override
    public void setShowForm(boolean var1) {
        this.c = var1;
    }

    @Override
    public boolean isUpload() {
        return this.d;
    }

    @Override
    public void setUpload(boolean var1) {
        this.d = var1;
    }

    @Override
    public Map getSqls() {
        return this.g;
    }

    @Override
    public void setSqls(Map var1) {
        this.g = var1;
    }

    @Override
    public Map getCharts() {
        return this.h;
    }

    @Override
    public void setCharts(Map var1) {
        this.h = var1;
    }

    @Override
    public SubmitCheckContext getSubmitCheck() {
        return this.l;
    }

    @Override
    public void setSubmitCheck(SubmitCheckContext var1) {
        this.l = var1;
    }

    @Override
    public Map getDataGrids() {
        return this.j;
    }

    @Override
    public void setDataGrids(Map var1) {
        this.j = var1;
    }

    @Override
    public boolean isFromRef() {
        return this.e;
    }

    @Override
    public void setFromRef(boolean var1) {
        this.e = var1;
    }

    @Override
    public String getScripts() {
        return this.n;
    }

    @Override
    public void setScripts(String var1) {
        this.n = var1;
    }

    @Override
    public Map getCrossReports() {
        return this.m;
    }

    @Override
    public void setCrossReports(Map var1) {
        this.m = var1;
    }

    @Override
    public Map getGridReports() {
        return this.i;
    }

    @Override
    public void setGridReports(Map var1) {
        this.i = var1;
    }

    @Override
    public Map getGridDataCenters() {
        return this.k;
    }

    @Override
    public Map getDsources() {
        return this.o;
    }

    @Override
    public void setDsources(Map var1) {
        this.o = var1;
    }

    @Override
    public void setGridDataCenters(Map var1) {
        this.k = var1;
    }

    @Override
    public Boolean getHideMV() {
        return this.f;
    }

    @Override
    public void setHideMV(Boolean var1) {
        this.f = var1;
    }

    @Override
    public Map getMvParams() {
        return this.q;
    }

    @Override
    public void setMvParam(String var1, InputField var2) throws ExtConfigException {
        Object var3 = this.q;
        if (var3 == null) {
            var3 = new HashMap();
            this.q = (Map)var3;
        }

        InputField var4 = (InputField)((Map)var3).get(var1);
        if (var4 != null) {
            String var5 = ConstantsEngine.replace("id为 $0 的输入参数框存在重复.", var1);
            throw new ExtConfigException(var5);
        } else {
            ((Map)var3).put(var1, var2);
        }
    }
}
