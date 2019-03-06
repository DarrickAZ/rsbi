//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.dc;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import com.ruisi.ext.engine.view.context.dc.grid.MultiDsContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriterPrintStreamImpl;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import com.ruisi.ispire.dc.cube.engine.SystemFuncLoader;
import com.ruisi.ispire.dc.cube.operation.ScriptInvoke;
import com.ruisi.ispire.dc.grid.ColumnInfo;
import com.ruisi.ispire.dc.grid.DatasetProvider;
import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridDataMetaData;
import com.ruisi.ispire.dc.grid.GridProcContext;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class GridDataCenterBuilder {
    private GridDataCenterContext a;
    private ExtRequest b;
    private ExtEnvirContext c;
    private DaoHelper d;
    private SystemFuncLoader e;
    private Map f = new HashMap();
    private static Logger g = Logger.getLogger(GridDataCenterBuilder.class);

    public Map getExtDatas() {
        return this.f;
    }

    public GridDataCenterBuilder(GridDataCenterContext var1, ExtRequest var2, ExtEnvirContext var3, DaoHelper var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = new SystemFuncLoader();
    }

    public List buildByXML(PageInfo var1) throws ScriptEnginerException, ExtConfigException {
        Object var2 = null;
        GridSetConfContext var3 = this.a.getConf();
        DatasetProvider var4 = var3.getDatasetProvider();
        int var24;
        if (var4 != null) {
            String var5 = TemplateManager.buildTempldate(var3.getTemplateName(), this.b, this.c);
            var2 = var4.queryData(var5, var3.isAgg(), var3.getMaster(), this.b, var1);
        } else if (var3.getMultiDsContext() != null && var3.getMultiDsContext().size() > 0) {
            var2 = new ArrayList();
            Thread[] var20 = new Thread[var3.getMultiDsContext().size()];

            for(int var6 = 0; var6 < var3.getMultiDsContext().size(); ++var6) {
                MultiDsContext var7 = (MultiDsContext)var3.getMultiDsContext().get(var6);
                String var8 = TemplateManager.buildTempldate(var7.getTemplateName(), this.b, this.c);
                QueryThread var9 = new QueryThread(var8, var7.getRefDsource(), (List)var2, this.d, this.c);
                Thread var10 = new Thread(var9);
                var20[var6] = var10;
                var10.start();
            }

            try {
                Thread[] var29 = var20;
                int var26 = var20.length;

                for(var24 = 0; var24 < var26; ++var24) {
                    Thread var22 = var29[var24];
                    var22.join();
                }
            } catch (Exception var19) {
                var19.printStackTrace();
            }
        } else {
            var2 = this.a(var3.getTemplateName(), var3.getRefDsource(), var1);
        }

        if (var2 != null && ((List)var2).size() != 0) {
            GridDataMetaData var21 = new GridDataMetaData();
            Map var23 = (Map)((List)var2).get(0);
            Iterator var27 = var23.entrySet().iterator();

            ColumnInfo var11;
            String var31;
            Object var32;
            while(var27.hasNext()) {
                Entry var25 = (Entry)var27.next();
                var31 = (String)var25.getKey();
                var32 = var25.getValue();
                var11 = new ColumnInfo();
                if (var32 == null) {
                    for(int var12 = 1; var12 < ((List)var2).size(); ++var12) {
                        Map var13 = (Map)((List)var2).get(var12);
                        Object var14 = var13.get(var31);
                        if (var14 != null) {
                            var32 = var14;
                            break;
                        }
                    }
                }

                if (var32 == null) {
                    var11.setType("None");
                } else if (var32 instanceof String) {
                    var11.setType("String");
                } else if (!(var32 instanceof BigDecimal) && !(var32 instanceof Integer) && !(var32 instanceof Double) && !(var32 instanceof Long)) {
                    if (!(var32 instanceof Timestamp) && !(var32 instanceof Date)) {
                        var11.setType("None");
                    } else {
                        var11.setType("Date");
                    }
                } else {
                    var11.setType("Number");
                }

                var31 = var31.toUpperCase();
                var11.setName(var31);
                var21.getQueryColumns().put(var31, var11);
            }

            label384:
            for(var24 = 0; var24 < ((List)var2).size(); ++var24) {
                var27 = var23.keySet().iterator();

                while(true) {
                    do {
                        if (!var27.hasNext()) {
                            continue label384;
                        }

                        var31 = ((String)var27.next()).toUpperCase();
                        var32 = var23.get(var31);
                        if (var32 == null) {
                            var32 = var23.get(var31.toLowerCase());
                        }

                        var11 = new ColumnInfo();
                    } while(var32 == null);

                    if (var32 instanceof String) {
                        var11.setType("String");
                    } else if (!(var32 instanceof BigDecimal) && !(var32 instanceof Integer) && !(var32 instanceof Double) && !(var32 instanceof Long)) {
                        if (!(var32 instanceof Timestamp) && !(var32 instanceof Date)) {
                            var11.setType("None");
                        } else {
                            var11.setType("Date");
                        }
                    } else {
                        var11.setType("Number");
                    }

                    var11.setName(var31);
                    var21.getQueryColumns().put(var31, var11);
                }
            }

            List var28 = this.a.getProcess();
            ArrayList var30 = new ArrayList();
            Iterator var37 = var28.iterator();

            while(var37.hasNext()) {
                GridProcContext var33 = (GridProcContext)var37.next();
                GridBaseProcessor var35 = var33.getProccess();
                var35.init((List)var2, var21, this);
                var30.add(var35);
            }

            if (var30.size() == 0) {
                return (List)var2;
            } else {
                StringBuffer var34 = new StringBuffer();
                Iterator var36 = var30.iterator();

                while(var36.hasNext()) {
                    GridBaseProcessor var39 = (GridBaseProcessor)var36.next();
                    if (var39 instanceof ScriptInvoke) {
                        ScriptInvoke var40 = (ScriptInvoke)var39;
                        var34.append(var40.createJSFunc());
                    }
                }

                String var42 = var34.toString();

                try {
                    GridBaseProcessor var43;
                    if (var42 != null && var42.length() > 0) {
                        var42 = var42 + this.e.load();
                        PageBuilder$JSObject var38 = this.a(var42);
                        Iterator var44 = var30.iterator();

                        while(var44.hasNext()) {
                            var43 = (GridBaseProcessor)var44.next();
                            if (var43 instanceof ScriptInvoke) {
                                ScriptInvoke var45 = (ScriptInvoke)var43;
                                var45.setInvocable(var38);
                            }
                        }
                    }

                    for(int var41 = 0; var41 < var30.size(); ++var41) {
                        var43 = (GridBaseProcessor)var30.get(var41);
                        var2 = var43.process();
                        if (var41 + 1 < var30.size()) {
                            ((GridBaseProcessor)var30.get(var41 + 1)).initDatas((List)var2);
                        }
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                } finally {
                    if (var42 != null && var42.length() > 0) {
                        Context.exit();
                    }

                }

                return (List)var2;
            }
        } else {
            return (List)var2;
        }
    }

    public ExtRequest getRequest() {
        return this.b;
    }

    public ExtEnvirContext getVeloContext() {
        return this.c;
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
            g.error(var3, var5);
            throw new ScriptEnginerException(var3, var5);
        }
    }

    private List a(String var1, String var2, PageInfo var3) throws ExtConfigException {
        List var4 = null;
        String var5 = TemplateManager.buildTempldate(var1, this.b, this.c);
        DataSourceBuilder var6;
        MVContext var7;
        DataSourceContext var8;
        if (var3 != null) {
            if (var2 != null && var2.length() != 0) {
                var6 = new DataSourceBuilder();
                var7 = RuleUtils.findCurMV(this.c);
                var8 = var6.findDataSource(var2, var7);
                var4 = (new DataSourceBuilder()).queryForList(var5, var8, var3, this.b);
            } else {
                var4 = DaoUtils.calPage(var5, var3, this.d);
                this.b.setAttribute("ext.view.pageInfo", var3);
            }
        } else if (var2 != null && var2.length() != 0) {
            var6 = new DataSourceBuilder();
            var7 = RuleUtils.findCurMV(this.c);
            var8 = var6.findDataSource(var2, var7);
            var4 = (new DataSourceBuilder()).queryForList(var5, var8);
        } else {
            var4 = this.d.queryForList(var5);
        }

        return var4;
    }
}
