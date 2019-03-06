//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.form.TreeContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;
import java.util.List;

public class TreeBuilder extends AbstractBuilder {
    private TreeContext a;

    public TreeBuilder(TreeContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
    }

    protected void processStart() {
        List var1 = null;
        String var2;
        if (this.a.getTemplateName() != null) {
            var2 = TemplateManager.buildTempldate(this.a.getTemplateName(), this.request, this.veloContext);
            String var3 = this.a.getRefDsource();
            if (var3 != null && var3.length() != 0) {
                DataSourceBuilder var4 = new DataSourceBuilder();
                MVContext var5 = RuleUtils.findCurMV(this.veloContext);
                DataSourceContext var6 = null;
                try {
                    var6 = var4.findDataSource(var3, var5);
                } catch (ExtConfigException e) {
                    e.printStackTrace();
                }
                var1 = (new DataSourceBuilder()).queryForList(var2, var6);
            } else {
                var1 = this.daoHelper.queryForList(var2);
            }
        }

        var2 = this.a.getValue();
        if (var2 != null && var2.length() > 0) {
            var2 = TestUtils.findValue(var2, this.request, this.veloContext);
        }

        TreeContext var7 = (TreeContext)this.a.clone();
        var7.setOptions(var1);
        var7.setOutValue(var2);
        this.emitter.startTree(var7);
    }
}
