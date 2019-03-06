//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.dc;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.ProcContext;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtWriterPrintStreamImpl;
import com.ruisi.ispire.dc.cube.DataCenter;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import com.ruisi.ispire.dc.cube.engine.SystemFuncLoader;
import com.ruisi.ispire.dc.cube.operation.DataSetHor2Ver;
import com.ruisi.ispire.dc.cube.operation.HorizontalKpiDeal;
import com.ruisi.ispire.dc.cube.operation.Processor;
import com.ruisi.ispire.dc.cube.operation.ScriptInvoke;
import com.ruisi.ispire.dc.cube.operation.SysExtKpiComputer;
import com.ruisi.ispire.dc.cube.operation.SysKpiComputer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class CubeDataCenterBuilder {
    private DataCenterContext a;
    private Map b;
    private DataCenter c;
    private static Logger d = Logger.getLogger(CubeDataCenterBuilder.class);
    private SystemFuncLoader e;

    public CubeDataCenterBuilder(DataCenterContext var1, Map var2) throws ScriptEnginerException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.a = var1;
        this.b = var2;
        String var3 = ExtContext.getInstance().getConstant("dataCenter");
        if (var3 != null && var3.length() != 0) {
            this.c = (DataCenter)Class.forName(var3).newInstance();
            this.e = new SystemFuncLoader();
        } else {
            throw new ScriptEnginerException("未配置数据中心.");
        }
    }

    public Object buildByXML() throws ScriptEnginerException {
        this.c.setInputParams(this.b);
        List var1 = this.a.getDataSetConf().getKpis();
        if (var1 != null && var1.size() != 0) {
            DataSet var13 = this.c.getDataSet(this.a);
            DataSetHor2Ver var3 = new DataSetHor2Ver(var13, this.a);
            var3.process();
            List var4 = this.a.getDataSetConf().getHorizontalKpis();
            if (var4 != null && var4.size() > 0) {
                HorizontalKpiDeal var5 = new HorizontalKpiDeal(var13, var4);
                var5.process();
            }

            ArrayList var14 = new ArrayList();
            Iterator var7 = this.a.getProcess().iterator();

            Processor var8;
            while(var7.hasNext()) {
                ProcContext var6 = (ProcContext)var7.next();
                var8 = var6.getProccess();
                var14.add(var8);
            }

            StringBuffer var15 = new StringBuffer(this.e.load() + "\n");

            for(int var16 = 0; var16 < var14.size(); ++var16) {
                var8 = (Processor)var14.get(var16);
                if (var8 instanceof ScriptInvoke) {
                    ScriptInvoke var9 = (ScriptInvoke)var8;
                    var15.append(var9.createJSFunc());
                }
            }

            SysExtKpiComputer var17 = new SysExtKpiComputer(var13);
            var15.append(var17.createExtKpiJsFunc());
            this.c.setJsExtKpiFuncAndAlias(var17.getCreaterFuncNameAndAlias());
            SysKpiComputer var18 = new SysKpiComputer(var13, this.a.getDataSetConf().getComputeKpis());
            var15.append(var18.createJSFunc());
            this.c.setSysKpiComputeFNameAndParams(var18.getFuncParamsAndNames());
            String var19 = var15.toString();
            if (var19 != null && var19.length() > 0) {
                PageBuilder$JSObject var10 = this.a(var15.toString());

                for(int var11 = 0; var11 < var14.size(); ++var11) {
                    Processor var12 = (Processor)var14.get(var11);
                    if (var12 instanceof ScriptInvoke) {
                        ((ScriptInvoke)var12).setInvocable(var10);
                    }
                }

                var17.setInvocable(var10);
                var17.process(var19);
                var18.setInvocable(var10);
                var18.process();
            }

            for(int var20 = 0; var20 < var14.size(); ++var20) {
                Processor var21 = (Processor)var14.get(var20);
                var21.initDataSet(var13);
                var21.process();
            }

            return var13.getDatas();
        } else {
            String var2 = ConstantsEngine.replace(" 引用的dataCenter中未配置指标. id= $0 ", this.a.getId());
            throw new ExtRuntimeException(var2);
        }
    }

    private PageBuilder$JSObject a(String var1) throws ScriptEnginerException {
        try {
            Context var2 = Context.enter();
            ScriptableObject var6 = var2.initStandardObjects();
            Object var4 = Context.javaToJS(new ExtWriterPrintStreamImpl(System.out), var6);
            ScriptableObject.putProperty(var6, "out", var4);
            var2.evaluateString(var6, var1, (String)null, 1, (Object)null);
            return new PageBuilder$JSObject(var2, var6);
        } catch (Exception var5) {
            String var3 = "JS函数构建错误. \n " + var1;
            d.error(var3, var5);
            throw new ScriptEnginerException(var3, var5);
        }
    }
}
