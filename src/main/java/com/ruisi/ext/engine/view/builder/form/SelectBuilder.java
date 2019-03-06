//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.Rebuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectBuilder extends AbstractBuilder implements Rebuilder {
    private SelectContext a;
    private Boolean b;

    public SelectBuilder(SelectContext var1, Boolean var2) {
        this.a = var1;
        this.b = var2;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws ExtConfigException, DocumentException, UnsupportedEncodingException {
        List var1 = null;
        String var2;
        if (this.a.getDataId() != null && this.a.getDataId().length() > 0) {
            var1 = (List)this.request.getAttribute(this.a.getDataId());
            if (var1 == null) {
                var2 = ConstantsEngine.replace("未在request对象中找到id为 $0 的数据集.", this.a.getDataId());
                throw new ExtConfigException(var2);
            }
        } else if (this.a.loadOptions() != null) {
            var1 = (List)((ArrayList)this.a.loadOptions()).clone();
        } else if (this.a.getTemplateName() != null) {
            var2 = TemplateManager.buildTempldate(this.a.getTemplateName(), this.request, this.veloContext);
            String var3 = this.a.getRefDsource();
            if (var3 != null && var3.length() != 0) {
                DataSourceBuilder var4 = new DataSourceBuilder();
                MVContext var5 = RuleUtils.findCurMV(this.veloContext);
                DataSourceContext var6 = var4.findDataSource(var3, var5);
                var1 = (new DataSourceBuilder()).queryForList(var2, var6);
            } else {
                var1 = this.daoHelper.queryForList(var2);
            }
        }

        var2 = this.a.getValue();
        if (var2 != null && var2.length() > 0) {
            var2 = TestUtils.findValue(var2, this.request, this.veloContext);
        }

        SelectContext var7 = (SelectContext)this.a.clone();
        var7.setOptions(var1);
        var7.setOutValue(var2);
        if (this.a.getAddEmptyValue() != null && this.a.getAddEmptyValue()) {
            HashMap var8 = new HashMap();
            var8.put("value", "");
            var8.put("text", "");
            var1.add(0, var8);
        }

        this.emitter.startSelect(var7);
    }

    @Override
    public void rebuild() {
        String var1 = TemplateManager.buildTempldate(this.a.getTemplateName(), this.request, this.veloContext);
        List var2 = this.daoHelper.queryForList(var1);
        this.a.setOptions(var2);
        this.emitter.rebuildSelect(this.a);
    }
}
