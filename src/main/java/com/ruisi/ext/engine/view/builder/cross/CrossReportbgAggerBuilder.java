//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.cross;

import com.ruisi.ext.engine.cross.CrossBuilder;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.dc.cube.AggregationContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.ProcContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ispire.dc.cube.DataCenter;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import com.ruisi.ispire.dc.cube.operation.Processor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CrossReportbgAggerBuilder {
    private DataCenter a;
    private CrossReportContext b;
    private ExtRequest c;

    public CrossReportbgAggerBuilder(DataCenter var1, CrossReportContext var2, ExtRequest var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    public List buildBgAgger() throws ScriptEnginerException {
        DataSet var1 = this.a.getDataSet((DataCenterContext)null);
        List var2 = this.createProcess();
        Iterator var4 = var2.iterator();

        while(var4.hasNext()) {
            ProcContext var3 = (ProcContext)var4.next();
            Processor var5 = var3.getProccess();
            var5.initDataSet(var1);
            var5.process();
        }

        return var1.getDatas();
    }

    public List createProcess() {
        ArrayList var1 = new ArrayList();
        List var2 = this.a();
        AggregationContext var3 = new AggregationContext();
        var3.setDim((String)var2.get(0));
        var1.add(var3);
        return var1;
    }

    private List a() {
        ArrayList var1 = new ArrayList();
        this.a(this.b.getCrossCols().getCols(), var1);
        if (CrossBuilder.isExistDrill(this.b)) {
            String var2 = CrossBuilder.getDrillDim(this.c);
            RowDimContext var3 = CrossBuilder.getUseDim(this.b, var2);
            var1.add(var3.getCode());
        } else {
            this.a(this.b.getCrossCols().getCols(), var1);
        }

        return var1;
    }

    private void a(List var1, List var2) {
        if (var1 != null && var1.size() != 0) {
            CrossField var3;
            for(Iterator var4 = var1.iterator(); var4.hasNext(); this.a(var3.getSubs(), var2)) {
                var3 = (CrossField)var4.next();
                String var5 = var3.getType();
                if (!"kpi".equalsIgnoreCase(var5) && !"kpiOther".equalsIgnoreCase(var5)) {
                    String var6 = var3.getAlias();
                    var2.add(var6);
                }
            }

        }
    }
}
