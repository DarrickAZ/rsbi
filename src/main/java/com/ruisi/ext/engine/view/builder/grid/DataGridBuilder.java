//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.grid;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.Rebuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.ColConfigContext;
import com.ruisi.ext.engine.view.context.grid.ColsContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class DataGridBuilder extends AbstractBuilder implements Rebuilder {
    private DataGridContext a;

    @Override
    public void rebuild() {
    }

    @Override
    public void processEnd() {
    }

    public void processStart() throws ExtConfigException {
        DataGridContext var1 = (DataGridContext)this.a.clone();
        if (this.a.getDataRef() != null && this.a.getDataRef().length() > 0) {
            try {
                this.emitter.startDataGrid(var1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ExtConfigException e) {
                e.printStackTrace();
            }
        } else {
            String var2 = (String)this.request.getAttribute("ext.isExport");
            String var3 = (String)this.request.getAttribute("ext_fromAjax");
            if (!"true".equals(var2) && this.a.isAjax() && (var3 == null || "false".equals(var3))) {
                try {
                    this.emitter.startDataGrid(var1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                List var4 = null;
                String var5 = this.a.getDataId();
                String var6;
                if (var5 != null && var5.length() != 0) {
                    var4 = (List)super.request.getAttribute(var5);
                    if (var4 == null) {
                        var6 = ConstantsEngine.replace("未在request对象中找到id为 $0 的数据集.", var5);
                        throw new ExtConfigException(var6);
                    }

                    PageInfo var18 = (PageInfo)this.request.getAttribute("ext.view.pageInfo");
                    var1.setPageInfo(var18);
                } else {
                    var6 = this.a.getRef();
                    String var7;
                    String var8;
                    String var23;
                    if (var6 != null && var6.length() > 0) {
                        var8 = RuleUtils.findCurMV(this.veloContext).getMvid();
                        MVContext var9 = ExtContext.getInstance().getMVContext(var8);
                        if (var9.getSqls() == null || var9.getSqls().get(var6) == null) {
                            var23 = ConstantsEngine.replace("ref 为 $0 的sql不存在, mvId = $1 .", var6, var8);
                            throw new ExtConfigException(var23);
                        }

                        var7 = TemplateManager.buildTempldate((String)var9.getSqls().get(var6), this.request, this.veloContext);
                    } else {
                        var7 = TemplateManager.buildTempldate(this.a.getTemplateName(), this.request, this.veloContext);
                    }

                    var8 = this.request.getParameter("ext_col_order");
                    String var21 = this.request.getParameter("ext_order_state");
                    if (var8 != null && var8.length() > 0) {
                        var7 = "select * from (" + var7 + ") cc order by " + var8;
                        if ("d".equals(var21)) {
                            var7 = var7 + " desc";
                        }
                    }

                    if (!"true".equals(var2)) {
                        PageInfo var10 = this.a.getPageInfo();
                        if (var10 != null) {
                            PageInfo var11 = new PageInfo();
                            var11.setPagesize(var10.getPagesize());
                            String var12 = "currpage_";
                            if (this.a.getPageInputName() != null && this.a.getPageInputName().length() > 0) {
                                var12 = this.a.getPageInputName();
                            }

                            if ("true".equals(var3)) {
                                var12 = "currPage";
                            }

                            String var13 = this.request.getParameter(var12);
                            if (var13 != null && var13.length() != 0) {
                                var11.setCurtpage(Long.parseLong(var13));
                            } else {
                                var11.setCurtpage(0L);
                            }

                            String var14 = this.a.getRefDsource();
                            if (var14 != null && var14.length() > 0) {
                                DataSourceBuilder var15 = new DataSourceBuilder();
                                MVContext var16 = RuleUtils.findCurMV(this.veloContext);
                                DataSourceContext var17 = var15.findDataSource(var14, var16);
                                var4 = (new DataSourceBuilder()).queryForList(var7, var17, var11, this.request);
                            } else {
                                var4 = DaoUtils.calPage(var7, var11, this.daoHelper);
                            }

                            this.request.setAttribute("ext.view.pageInfo", var11);
                            var1.setPageInfo(var11);
                        } else {
                            String var26 = this.a.getRefDsource();
                            if (var26 != null && var26.length() > 0) {
                                DataSourceBuilder var29 = new DataSourceBuilder();
                                MVContext var30 = RuleUtils.findCurMV(this.veloContext);
                                DataSourceContext var34 = var29.findDataSource(var26, var30);
                                var4 = (new DataSourceBuilder()).queryForList(var7, var34);
                            } else {
                                var4 = this.daoHelper.queryForList(var7);
                            }
                        }
                    } else {
                        var23 = this.a.getRefDsource();
                        if (var23 != null && var23.length() != 0) {
                            var4 = this.daoHelper.queryForList(var7);
                        } else {
                            DataSourceBuilder var27 = new DataSourceBuilder();
                            MVContext var31 = RuleUtils.findCurMV(this.veloContext);
                            DataSourceContext var32 = var27.findDataSource(var23, var31);
                            var4 = (new DataSourceBuilder()).queryForList(var7, var32);
                        }
                    }
                }

                ColConfigContext var19 = this.a.getColConfigContext();
                if (var19.getColsContext() != null) {
                    ColsContext var24 = var19.getColsContext();
                    List var20 = (List)this.veloContext.get(var24.getDataRef());
                    if (var20 != null) {
                        String[] var22 = new String[var20.size()];
                        String[] var25 = new String[var20.size()];

                        for(int var28 = 0; var28 < var20.size(); ++var28) {
                            Map var33 = (Map)var20.get(var28);
                            var22[var28] = (String)var33.get(var24.getAllAlias());
                            var25[var28] = (String)var33.get(var24.getAllDescs());
                        }

                        var24.setAliasArray(var22);
                        var24.setDescArray(var25);
                    }
                }

                var1.setOptions(var4);
                try {
                    this.emitter.startDataGrid(var1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public DataGridBuilder(DataGridContext var1) {
        this.a = var1;
    }
}
