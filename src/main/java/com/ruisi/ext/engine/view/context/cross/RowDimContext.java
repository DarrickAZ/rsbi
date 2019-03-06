//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestAdapter;
import java.util.List;

public class RowDimContext implements RefChecker {
    private String b;
    private String c;
    private String d;
    private boolean e;
    private String f;
    private String g;
    List a;
    private String h;
    private String i;
    private String j;
    private TestAdapter k;
    private String l;
    private String m;
    private int n;
    private CrossReportContext o;

    public RowDimContext() {
    }

    public CrossReportContext getCrossReport() {
        return this.o;
    }

    public void setCrossReport(CrossReportContext var1) {
        this.o = var1;
    }

    public String getRefDataCenter() {
        return this.l;
    }

    @Override
    public void check() throws ExtConfigException {
        String var1 = this.getRefDataCenter();
        MVContext var2 = RuleUtils.findMVContext(this.o);
        if (var1 != null && var1.length() > 0 && !var2.getGridDataCenters().containsKey(var1)) {
            String var3 = ConstantsEngine.replace(" crossReport 中引用的dataCenter ($0) 不存在. mv = $1", var1, var2.getMvid());
            throw new ExtConfigException(var3);
        }
    }

    public void setRefDataCenter(String var1) {
        this.l = var1;
    }

    public String getTestFunc() {
        return this.j;
    }

    public void setTestFunc(String var1) {
        this.j = var1;
    }

    public String getValue() {
        return this.i;
    }

    public void setValue(String var1) {
        this.i = var1;
    }

    public String getRef() {
        return this.h;
    }

    public void setRef(String var1) {
        this.h = var1;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String var1) {
        this.b = var1;
    }

    public String getCode() {
        return this.c;
    }

    public void setCode(String var1) {
        this.c = var1;
    }

    public String getType() {
        return this.d;
    }

    public void setType(String var1) {
        this.d = var1;
    }

    public List getOthers() {
        return this.a;
    }

    public void setOthers(List var1) {
        this.a = var1;
    }

    public boolean isUseLink() {
        return this.e;
    }

    public void setUseLink(boolean var1) {
        this.e = var1;
    }

    public String getCascade() {
        return this.f;
    }

    public void setCascade(String var1) {
        this.f = var1;
    }

    public String getCodeDesc() {
        return this.g;
    }

    public void setCodeDesc(String var1) {
        this.g = var1;
    }

    public TestAdapter getDrillTest() {
        return this.k;
    }

    public void setDrillTest(TestAdapter var1) {
        this.k = var1;
    }

    public String getStart() {
        return this.m;
    }

    public void setStart(String var1) {
        this.m = var1;
    }

    public int getSize() {
        return this.n;
    }

    public void setSize(int var1) {
        this.n = var1;
    }
}
