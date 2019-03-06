//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.cross;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.cross.CrossBuilder;
import com.ruisi.ext.engine.cross.CrossFieldLoader;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.service.loginuser.LoginUser;
import com.ruisi.ext.engine.service.loginuser.LoginUserFactory;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.dc.GridDataCenterBuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.DimOtherContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestAdapter;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

public class CrossReportBuilder extends AbstractBuilder {
    private CrossReportContext a;

    public CrossReportBuilder(CrossReportContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws UnsupportedEncodingException, ExtConfigException, ScriptEnginerException, DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CrossFieldLoader var1 = null;
        String var2 = ExtContext.getInstance().getConstant("fieldLoader");
        if (var2 == null) {
            throw new ExtRuntimeException("未配置 fieldLoader.");
        } else {
            try {
                var1 = (CrossFieldLoader)Class.forName(var2).newInstance();
                var1.setRequest(this.request);
            } catch (Exception var14) {
                String var4 = ConstantsEngine.replace(" crossField 中获取维度信息的类 $0 加载失败. ", var2);
                throw new ExtRuntimeException(var4);
            }

            this.request.setAttribute("ext.view.fieldLoader", var1);
            MVContext var3 = RuleUtils.findCurMV(this.veloContext);
            LoginUser var15 = LoginUserFactory.getInstance().getLoginUser(this.request);
            if (var15 != null) {
                String var5 = var15.getUserId();
                List var6 = var1.loadUserCustomize(var5, var3.getMvid());
                if (var6 != null && var6.size() > 0) {
                    this.veloContext.put("userKpiLs", var6);
                }
            }

            CrossReportContext var16 = (CrossReportContext)this.a.clone();
            Iterator var8;
            if (CrossBuilder.isExistDrill(this.a)) {
                ArrayList var17 = new ArrayList();
                var8 = this.a.getDims().iterator();

                label76:
                while(true) {
                    RowDimContext var7;
                    TestAdapter var9;
                    HashMap var10;
                    do {
                        if (!var8.hasNext()) {
                            var16.setDims(var17);
                            CrossBuilder.createDeftDim(var16);
                            break label76;
                        }

                        var7 = (RowDimContext)var8.next();
                        var9 = var7.getDrillTest();
                        var10 = new HashMap();
                        var10.put("name", var7.getName());
                        var10.put("code", var7.getCode());
                        var10.put("codeDesc", var7.getCodeDesc());
                        var10.put("type", var7.getType());
                    } while(var9 != null && !var9.test(var10, this.veloContext, this.request));

                    var17.add(var7);
                }
            }

            this.a(var16, var3);
            String var18 = (String)this.request.getAttribute("ext.isExport");
            if ("true".equalsIgnoreCase(var18)) {
                String var19 = var16.getExportName();
                if (var19 != null && var19.length() > 0) {
                    var19 = TestUtils.findValue(var19, this.request, this.veloContext);
                }

                if (var19 != null) {
                    var19 = new String(var19.getBytes("gb2312"), "iso8859-1");
                } else {
                    var19 = "export";
                }

                this.response.setContentType("application/x-msdownload");
                String var20 = "attachment; filename=\"" + var19 + ".xls\"";
                this.response.setHeader("Content-Disposition", var20);
            }

            List var11;
            String var22;
            if (var16.getExtSqlTemplates() != null) {
                for(var8 = var16.getExtSqlTemplates().entrySet().iterator(); var8.hasNext(); var16.getExtDatas().put(var22, var11)) {
                    Entry var21 = (Entry)var8.next();
                    var22 = (String)var21.getKey();
                    String var24 = TemplateManager.buildTempldate((String)var21.getValue(), this.request, this.veloContext);
                    if (var16.getExtDatas() == null) {
                        var16.setExtDatas(new HashMap());
                    }

                    var11 = null;
                    if (var16.getRefDsource() != null && var16.getRefDsource().length() > 0) {
                        DataSourceBuilder var12 = new DataSourceBuilder();
                        DataSourceContext var13 = var12.findDataSource(var16.getRefDsource(), var3);
                        var11 = (new DataSourceBuilder()).queryForList(var24, var13);
                    } else {
                        var11 = this.daoHelper.queryForList(var24);
                    }
                }
            }

            this.emitter.startCrossReport(var16);
            List var23 = var16.getDims();
        }
    }

    private void a(CrossReportContext var1, MVContext var2) throws ExtConfigException, ScriptEnginerException {
        String var4;
        String var5;
        String var6;
        String var22;
        if (var1.getRefDataCetner() != null && var1.getRefDataCetner().length() > 0) {
            var4 = CrossBuilder.getDrillDim(this.request);
            GridDataCenterContext var15;
            if (var4 != null) {
                RowDimContext var16 = CrossBuilder.getUseDim(var1, var4);
                var6 = var16.getRefDataCenter();
                var15 = (GridDataCenterContext)var2.getGridDataCenters().get(var6);
                Iterator var20 = var1.getDims().iterator();

                while(var20.hasNext()) {
                    RowDimContext var18 = (RowDimContext)var20.next();
                    var22 = this.request.getParameter(var18.getCode());
                    if (var22 != null && var22.length() > 0) {
                        this.veloContext.put(var18.getCode(), var22);
                    }
                }
            } else {
                var5 = var1.getRefDataCetner();
                var15 = (GridDataCenterContext)var2.getGridDataCenters().get(var5);
            }

            GridDataCenterBuilder var17 = new GridDataCenterBuilder(var15, this.request, this.veloContext, this.daoHelper);
            List var21 = var17.buildByXML((PageInfo)null);
            var1.setOptions(var21);
        } else {
            String var3 = var1.getRef();
            if (var3 != null && var3.length() > 0) {
                if (var2.getSqls() == null || var2.getSqls().get(var3) == null) {
                    var5 = ConstantsEngine.replace("ref 为 $0 的sql不存在, mvId = $1 .", var3);
                    throw new ExtConfigException(var5);
                }

                var4 = TemplateManager.buildTempldate((String)var2.getSqls().get(var3), this.request, this.veloContext);
            } else {
                var4 = TemplateManager.buildTempldate(var1.getTemplateName(), this.request, this.veloContext);
            }

            var5 = var1.getTotalTemplateName();
            var6 = null;
            if (var5 != null && var5.length() > 0) {
                var6 = TemplateManager.buildTempldate(var5, this.request, this.veloContext);
            }

            String var7 = CrossBuilder.getDrillDim(this.request);
            String var11;
            String var25;
            if (var7 != null) {
                RowDimContext var8 = CrossBuilder.getUseDim(var1, var7);
                if (var8.getRef() != null && var8.getRef().length() > 0) {
                    var22 = (String)var2.getSqls().get(var8.getRef());
                    if (var22 == null || var22.length() == 0) {
                        var25 = ConstantsEngine.replace("ref 为 $0 的sql不存在, mvId = $1 .", var8.getRef());
                        throw new ExtConfigException(var25);
                    }

                    Iterator var26 = var1.getDims().iterator();

                    while(var26.hasNext()) {
                        RowDimContext var24 = (RowDimContext)var26.next();
                        String var30 = this.request.getParameter(var24.getCode());
                        if (var30 != null && var30.length() > 0) {
                            this.veloContext.put(var24.getCode(), var30);
                        }
                    }

                    var4 = TemplateManager.buildTempldate(var22, this.request, this.veloContext);
                } else {
                    var4 = "select * from (" + var4 + ") where 1=1 ";
                    if (var6 != null && var6.length() > 0) {
                        var6 = "select * from (" + var6 + ") where 1=1 ";
                    }

                    Iterator var10 = var1.getDims().iterator();

                    label164:
                    while(true) {
                        RowDimContext var9;
                        do {
                            do {
                                if (!var10.hasNext()) {
                                    break label164;
                                }

                                var9 = (RowDimContext)var10.next();
                                var11 = this.request.getParameter(var9.getCode());
                                if (var11 != null && var11.length() > 0) {
                                    var4 = var4 + " and " + var9.getCode() + " = '" + var11 + "' ";
                                    if (var6 != null && var6.length() > 0) {
                                        var6 = var6 + " and " + var9.getCode() + " = '" + var11 + "' ";
                                    }
                                }
                            } while(var9.getOthers() == null);
                        } while(var9.getOthers().size() <= 0);

                        Iterator var13 = var9.getOthers().iterator();

                        while(var13.hasNext()) {
                            DimOtherContext var12 = (DimOtherContext)var13.next();
                            String var14 = this.request.getParameter(var12.getCode());
                            if (var14 != null && var14.length() > 0) {
                                var4 = var4 + " and " + var12.getCode() + " = '" + var14 + "' ";
                                if (var6 != null && var6.length() > 0) {
                                    var6 = var6 + " and " + var12.getCode() + " = '" + var14 + "' ";
                                }
                            }
                        }
                    }
                }
            }

            String var19 = (String)this.request.getAttribute("ext.isExport");
            PageInfo var23 = var1.getPageInfo();
            var25 = this.request.getParameter("currPage");
            List var27;
            List var32;
            DataSourceBuilder var34;
            DataSourceContext var35;
            List var37;
            if (var23 != null && !"true".equals(var19) && var7 == null) {
                PageInfo var29 = new PageInfo();
                var29.setPagesize(var23.getPagesize());
                if (var25 != null && var25.length() != 0) {
                    var29.setCurtpage(Long.parseLong(var25));
                } else {
                    var29.setCurtpage(0L);
                }

                if (this.a.getRefDsource() != null && this.a.getRefDsource().length() > 0) {
                    var34 = new DataSourceBuilder();
                    var35 = var34.findDataSource(this.a.getRefDsource(), var2);
                    var37 = (new DataSourceBuilder()).queryForList(var4, var35, var29, this.request);
                    var1.setOptions(var37);
                } else {
                    var32 = DaoUtils.calPage(var4, var29, this.daoHelper);
                    this.request.setAttribute("ext.view.pageInfo", var29);
                    var1.setOptions(var32);
                }

                var1.setPageInfo(var29);
            } else if (this.a.getRefDsource() != null && this.a.getRefDsource().length() > 0) {
                DataSourceBuilder var28 = new DataSourceBuilder();
                DataSourceContext var31 = var28.findDataSource(this.a.getRefDsource(), var2);
                List var33 = (new DataSourceBuilder()).queryForList(var4, var31);
                var1.setOptions(var33);
            } else {
                var27 = this.daoHelper.queryForList(var4);
                var1.setOptions(var27);
            }

            if (var6 != null) {
                var11 = null;
                if (this.a.getRefDsource() != null && this.a.getRefDsource().length() > 0) {
                    var34 = new DataSourceBuilder();
                    var35 = var34.findDataSource(this.a.getRefDsource(), var2);
                    var27 = (new DataSourceBuilder()).queryForList(var4, var35);
                } else {
                    var27 = this.daoHelper.queryForList(var6);
                }

                for(int var36 = 0; var36 < var27.size(); ++var36) {
                    Map var38 = (Map)var27.get(var36);
                    var38.put("isTotalDs", "true");
                }

                var32 = var1.loadOptions();
                var27.addAll(var32);
                var1.setOptions(var27);
            }

            if (var1.getBgAgg() != null && var1.getBgAgg()) {
                var27 = var1.loadOptions();
                BackGroundAggDataCenter var40 = new BackGroundAggDataCenter(var1, this.request);
                CrossReportbgAggerBuilder var39 = new CrossReportbgAggerBuilder(var40, this.a, this.request);
                var37 = var39.buildBgAgger();
                var1.setOptions(var37);
            }

        }
    }
}
