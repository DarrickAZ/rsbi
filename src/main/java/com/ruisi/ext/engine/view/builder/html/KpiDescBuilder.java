//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.face.UserKpiDesc;
import com.ruisi.ext.engine.view.context.html.KpiContext;
import com.ruisi.ext.engine.view.context.html.KpiDescContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KpiDescBuilder extends AbstractBuilder {
    private KpiDescContext a;

    public KpiDescBuilder(KpiDescContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() {
        KpiDescContext var1 = (KpiDescContext)this.a.clone();
        if (var1.getKpis() != null && var1.getKpis().size() != 0) {
            StringBuffer var2 = new StringBuffer("");
            Iterator var4 = var1.getKpis().iterator();

            while(var4.hasNext()) {
                KpiContext var3 = (KpiContext)var4.next();
                String var5 = var3.getKey();
                var2.append("'");
                var2.append(var5);
                var2.append("'");
                var2.append(",");
            }

            String var7 = var2.substring(0, var2.length() - 1);
            String var9 = "select t.name, unit, name_desc, t.tkey from sc_kpi_desc t where t.tkey in (" + var7 + ") order by id";
            List var11 = this.daoHelper.queryForList(var9);
            var1.setOptions(var11);
        } else {
            var1.setOptions(new ArrayList());
        }

        String var6 = var1.getImpl();
        if (var6 != null && var6.length() > 0) {
            UserKpiDesc var8 = null;
            try {
                var8 = (UserKpiDesc)Class.forName(ExtContext.getInstance().getConstant(var6)).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            var8.init(this.servletContext, this.daoHelper, this.veloContext);
            List var10 = var8.kpis();
            var1.loadOptions().addAll(var10);
        }

        this.emitter.startKpiDesc(var1);
    }
}
