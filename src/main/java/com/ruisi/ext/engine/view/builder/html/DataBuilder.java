//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.dc.GridDataCenterBuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.context.html.DataContext;
import java.util.List;
import java.util.Map;

import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataBuilder extends AbstractBuilder implements com.ruisi.ext.engine.view.builder.DataBuilder {
    private Log a = LogFactory.getLog(DataBuilder.class);
    private DataContext b;

    public DataBuilder(DataContext var1) {
        this.b = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() {
        List var1 = null;
        String var2 = this.b.getRefDataCenter();
        if (var2 != null && var2.length() > 0) {
            MVContext var8 = RuleUtils.findCurMV(this.veloContext);
            GridDataCenterContext var10 = (GridDataCenterContext)var8.getGridDataCenters().get(var2);
            GridDataCenterBuilder var12 = new GridDataCenterBuilder(var10, this.request, this.veloContext, this.daoHelper);
            try {
                var1 = var12.buildByXML((PageInfo)null);
            } catch (ScriptEnginerException e) {
                e.printStackTrace();
            } catch (ExtConfigException e) {
                e.printStackTrace();
            }
        } else {
            String var3 = this.b.getRefDsource();
            String var4 = TemplateManager.buildTempldate(this.b.getTemplateName(), this.request, this.veloContext);
            if (var3 != null && var3.length() != 0) {
                DataSourceBuilder var5 = new DataSourceBuilder();
                MVContext var6 = RuleUtils.findCurMV(this.veloContext);
                DataSourceContext var7 = null;
                try {
                    var7 = var5.findDataSource(var3, var6);
                } catch (ExtConfigException e) {
                    e.printStackTrace();
                }
                var1 = (new DataSourceBuilder()).queryForList(var4, var7);
            } else {
                var1 = this.daoHelper.queryForList(var4);
            }
        }

        if (!this.b.isMulti()) {
            if (var1.size() == 0) {
                this.a.info("data 标签中的sql未查询出数据.");
            } else {
                Map var9 = (Map)var1.get(0);
                this.veloContext.put(this.b.getKey(), var9);
                this.request.setAttribute(this.b.getKey(), var9);

                for(int var11 = 0; this.b.getOutKey() != null && var11 < this.b.getOutKey().length; ++var11) {
                    this.veloContext.put(this.b.getOutKey()[var11], var9.get(this.b.getOutVal()[var11]));
                }
            }
        } else {
            this.veloContext.put(this.b.getKey(), var1);
            this.request.setAttribute(this.b.getKey(), var1);
        }

    }
}
