//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.form.CheckBoxContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CheckBoxBuilder extends AbstractBuilder {
    private CheckBoxContext a;

    public CheckBoxBuilder(CheckBoxContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws ExtConfigException {
        List var1 = null;
        String var2;
        String var3;
        if (this.a.getDataId() != null && this.a.getDataId().length() > 0) {
            var1 = (List)this.request.getAttribute(this.a.getDataId());
            if (var1 == null) {
                var2 = ConstantsEngine.replace("未在request对象中找到id为 $0 的数据集.", this.a.getDataId());
                throw new ExtConfigException(var2);
            }
        } else if (this.a.loadOptions() != null) {
            var1 = this.a.loadOptions();
        } else {
            var2 = TemplateManager.buildTempldate(this.a.getTemplateName(), this.request, this.veloContext);
            var3 = this.a.getRefDsource();
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

        CheckBoxContext var7 = (CheckBoxContext)this.a.clone();
        var7.setOptions(var1);
        var3 = this.a.getValue();
        if (var3 == null || var3.length() == 0) {
            String[] var8 = this.option.getParamValues(this.a.getId());
            StringBuffer var9 = new StringBuffer();

            for(int var10 = 0; var8 != null && var10 < var8.length; ++var10) {
                var9.append(var8[var10]);
                if (var10 != var8.length - 1) {
                    var9.append(",");
                }
            }

            var3 = var9.toString();
        }

        var7.setOutValue(var3);
        try {
            this.emitter.startCheckBox(var7);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
