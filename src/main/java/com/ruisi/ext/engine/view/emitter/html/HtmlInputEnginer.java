//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.tree.TreeService;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.ListUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.form.*;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HtmlInputEnginer {
    private ExtWriter a;
    private ExtRequest b;
    private InputOption c;
    private ExtEnvirContext d;

    public HtmlInputEnginer(HTMLEmitter var1) {
        this.a = var1.getOut();
        this.b = var1.getRequest();
        this.c = var1.getOption();
        this.d = var1.getCtx();
    }

    public void buildCheckBox(CheckBoxContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = var3;
            } else {
                var2 = "";
            }

            this.d.put(var1.getId(), var3);
        }

        String[] var14 = var1.getTarget();
        String var4 = null;
        int var7;
        if (var14 != null && var14.length > 0) {
            String var5 = "";
            String var6 = "";

            String var13;
            for(var7 = 0; var7 < var14.length; ++var7) {
                String var8 = var14[var7];
                Object var9 = null;
                MVContext var10 = RuleUtils.findCurMV(this.d);
                String var11 = "ext.sys.cross.rebuild";
                var9 = LabelUtils.findCrossBylabel(var10, var8);
                if (var9 == null) {
                    var9 = LabelUtils.findChartBylabel(var10, var8);
                    var11 = "ext.sys.chart.rebuild";
                }

                String var12;
                if (var9 == null) {
                    var12 = ConstantsEngine.replace("配置的target $0 在文件 $1 (xml)中未指向正确的组件.", var8, var10.getMvid());
                    throw new ExtRuntimeException(var12);
                }

                var12 = (String)PropertyUtils.getProperty(var9, "id");
                var13 = RuleUtils.getResPath(this.b) + "control/extControl?serviceid=" + var11 + "&" + "t_from_id" + "=" + var10.getMvid() + "&id=" + var12;
                var5 = var5 + "'" + var12 + "'";
                var6 = var6 + "'" + var13 + "'";
                if (var7 != var14.length - 1) {
                    var5 = var5 + ",";
                    var6 = var6 + ",";
                }
            }

            Map var17 = this.c.getParams();
            StringBuffer var18 = new StringBuffer("[");
            if (var17 != null) {
                int var20 = var17.size();
                int var21 = 0;
                Iterator var25 = var17.entrySet().iterator();

                while(var25.hasNext()) {
                    Entry var24 = (Entry)var25.next();
                    var13 = (String)var24.getKey();
                    var18.append("'");
                    var18.append(var13);
                    var18.append("'");
                    ++var21;
                    if (var21 != var20) {
                        var18.append(",");
                    }
                }

                var18.append("]");
            }

            var4 = " onclick=\"post2Comps([" + var5 + "],[" + var6 + "], " + var18 + ")\"";
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<div class=\"col-sm-3\">");
            this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
        }

        this.a.print(var1.getDesc() == null ? "" : var1.getDesc());
        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print("：");
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        List var15 = var1.loadOptions();
        String[] var16 = var2.split(",");

        for(var7 = 0; var7 < var15.size(); ++var7) {
            Map var19 = (Map)var15.get(var7);
            String var22 = var19.get("value") == null ? "" : var19.get("value").toString();
            String var23;
            if (var1.getShowSpan() != null && var1.getShowSpan()) {
                var23 = var1.getCheckboxStyle();
                this.a.print("<span class='radioSpan' style='" + (var23 != null && var23.length() != 0 ? var23 : "") + "'>");
            }

            this.a.print("<label><input type=\"checkbox\" id=\"" + var1.getId() + "\" name=\"" + var1.getId() + "\" value='" + var22 + "'");
            if (ListUtils.isExist(var22, var16)) {
                this.a.print(" checked ");
            }

            var23 = (String)var19.get("disabled");
            if (var23 != null && "true".equalsIgnoreCase(var23)) {
                this.a.print(" disabled ");
            }

            if (var4 != null) {
                this.a.print(var4);
            }

            this.a.print(">");
            this.a.print((String)var19.get("text"));
            this.a.println("</label> ");
            if (var1.getShowSpan() != null && var1.getShowSpan()) {
                this.a.print("</span>");
            }
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</div>");
        }

    }

    public void buildText(TextFieldContext var1) {
        String var2 = var1.getSize();
        if (var2 == null || var2.length() == 0) {
            var2 = "20";
        }

        String var3 = var1.getOutValue();
        if (var3 == null || var3.length() == 0) {
            var3 = this.c.getParamValue(var1.getId());
        }

        if (var3 == null || var3.length() == 0) {
            String var4 = var1.getDefaultValue();
            if (var4 != null && var4.length() > 0) {
                var3 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var3);
        this.c.setParamValue(var1.getId(), var3);
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<div class=\"col-sm-3\">");
            this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
        }

        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print(var1.getDesc() + "：");
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        this.a.print("<input type=\"text\" class=\"inputform2\" name=\"" + var1.getId() + "\" id='" + var1.getId() + "' value=\"" + (var3 == null ? "" : var3) + "\" size='" + var2 + "'");
        if (var1.getReadOnly() != null && var1.getReadOnly().length() > 0) {
            this.a.print(" readonly ");
        }

        this.a.print(" >");
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</div>");
        }

    }

    public void buildPassword(TextFieldContext var1) {
        String var2 = var1.getSize();
        if (var2 == null || var2.length() == 0) {
            var2 = "20";
        }

        String var3 = var1.getOutValue();
        if (var3 == null || var3.length() == 0) {
            var3 = this.c.getParamValue(var1.getId());
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<div class=\"col-sm-3\">");
            this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
        }

        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print(var1.getDesc() + "：");
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        this.a.print("<input type=\"password\" class=\"inputform2\" name=\"" + var1.getId() + "\" id='" + var1.getId() + "' value=\"" + (var3 == null ? "" : var3) + "\" size='" + var2 + "'>");
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</div>");
        }

    }

    public void buildHidden(TextFieldContext var1) {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.c.getParamValue(var1.getId());
        }

        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var2);
        this.c.setParamValue(var1.getId(), var2);
        if (var1.isShow()) {
            this.a.print("<input type=\"hidden\" name=\"" + var1.getId() + "\" value=\"" + (var2 == null ? "" : var2) + "\" id='" + var1.getId() + "'>");
        }

    }

    public void buildTextarea(TextFieldContext var1) {
        String var2 = var1.getSize();
        if (var2 == null || var2.length() == 0) {
            var2 = "20";
        }

        String var3 = var1.getOutValue();
        if (var3 == null || var3.length() == 0) {
            var3 = this.c.getParamValue(var1.getId());
        }

        this.a.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        this.a.println("<tr>");
        this.a.println("<td valign=\"top\" width=\"70\">");
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
        }

        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print(var1.getDesc() + "：");
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        this.a.println("</td>");
        this.a.println(" <td>");
        this.a.println("<textarea class=\"inputform2\" rows=\"5\" id='" + var1.getId() + "' style='width:" + Integer.parseInt(var2) * 10 + "px;' name=\"" + var1.getId() + "\">" + (var3 == null ? "" : var3) + "</textarea>");
        this.a.println("</td>");
        this.a.println("</tr>");
        this.a.print("</table>");
    }

    public void buildButton(ButtonContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
        String var2 = var1.getMethod();
        if (var2 == null) {
            var2 = "";
        }

        String var3 = var1.getSubmit();
        if (var3 == null || var3.length() == 0) {
            var3 = "POST";
        }

        String var4 = var1.getType();
        if (var4 == null || var4.length() == 0) {
            var4 = "submit";
        }

        MVContext var5 = RuleUtils.findCurMV(this.d);
        String var6 = var1.getAction();
        String var7 = "";
        if ("easyUI".equals(var4)) {
            this.a.print(" <a href=\"javascript:;\" class=\"easyui-linkbutton\" ");
            this.a.print(" onclick=\"postpage(v" + var5.getFormId() + ",'" + var6 + "','" + var2 + "','" + var1.getFrom() + "','" + var3 + "'," + var1.getCheckParam() + "," + var1.isConfirm() + ",'" + var7 + "', true)\"");
            this.a.print(" data-options=\"iconCls:'" + var1.getIconCls() + "'\">" + var1.getDesc() + "</a> ");
        } else {
            this.a.print("<button type=\"" + var4 + "\"");
            String var8;
            if (!var4.equals("button")) {
                var8 = var1.getExportDataGrid();
                if (var8 != null && var8.length() > 0) {
                    var6 = "ext.sys.export";
                    var2 = "";
                    var7 = var8;
                }

                this.a.print(" onclick=\"postpage(v" + var5.getFormId() + ",'" + var6 + "','" + var2 + "','" + var1.getFrom() + "','" + var3 + "'," + var1.getCheckParam() + "," + var1.isConfirm() + ",'" + var7 + "', false)\"");
            } else {
                var8 = var1.getTarget();
                String[] var9 = var1.getMvId();
                if (var8 != null && var8.length() > 0 || var9 != null && var9.length > 0) {
                    Object[] var10 = null;
                    if (var8 != null && var8.length() > 0) {
                        var10 = new Object[]{LabelUtils.findCrossBylabel(var5, var8)};
                        if (var10[0] == null) {
                            var10 = new Object[]{LabelUtils.findChartBylabel(var5, var8)};
                        }

                        if (var10[0] == null) {
                            String var21 = ConstantsEngine.replace("配置的target $0 在文件 $1 (xml)中未指向正确的组件.", var8, var5.getMvid());
                            throw new ExtRuntimeException(var21);
                        }
                    }

                    if (var9 != null && var9.length > 0) {
                        var10 = new Object[var9.length];

                        for(int var11 = 0; var11 < var9.length; ++var11) {
                            String var12 = var9[var11];
                            var10[var11] = ExtContext.getInstance().getMVContext(var12);
                        }
                    }

                    Map var20 = this.c.getParams();
                    Map var22 = var5.getMvParams();
                    StringBuffer var13 = new StringBuffer("[");
                    if (var20 != null) {
                        int var14 = var20.size();
                        int var15 = 0;
                        Iterator var17 = var20.entrySet().iterator();

                        while(var17.hasNext()) {
                            Entry var16 = (Entry)var17.next();
                            String var18 = (String)var16.getKey();
                            InputField var19 = (InputField)var22.get(var18);
                            var13.append("{");
                            var13.append("name:'" + var18 + "',");
                            var13.append("type:'" + var19.getInputType() + "'");
                            var13.append("}");
                            ++var15;
                            if (var15 != var14) {
                                var13.append(",");
                            }
                        }

                        var13.append("]");
                    }

                    String var23 = RuleUtils.getResPath(this.b) + "control/";
                    String var24;
                    String var26;
                    if (var8 != null && var8.length() > 0) {
                        var24 = (String)PropertyUtils.getProperty(var10[0], "id");
                        String var25 = "ext.sys.cross.rebuild";
                        if (var10[0] instanceof ChartContext) {
                            var25 = "ext.sys.chart.rebuild";
                        }

                        var26 = var23 + "extControl?serviceid=" + var25 + "&" + "t_from_id" + "=" + var5.getMvid() + "&id=" + var24;
                        this.a.print(" onclick=\"post2Comp('" + var24 + "','" + var26 + "', this.form, " + var13 + ")\"");
                    }

                    if (var9 != null && var9.length > 0) {
                        this.a.print(" onclick=\"post2MV([");
                        var24 = var23 + "extControl?serviceid=ext.sys.tab.ajax&" + "t_from_id" + "=" + var5.getMvid() + "&" + "mvid" + "=";

                        for(int var27 = 0; var27 < var10.length; ++var27) {
                            var26 = (String)PropertyUtils.getProperty(var10[var27], "mvid");
                            this.a.print("{'url':'" + var24 + var26 + "', 'target':'" + var26 + "', 'paramNames':" + var13 + ", 'fname':'" + var5.getFormId() + "'}");
                            if (var27 != var10.length - 1) {
                                this.a.print(",");
                            }
                        }

                        this.a.print("]) \"");
                    }
                } else {
                    this.a.print(" onclick=\"" + (var1.getOnClick() != null ? var1.getOnClick() : "") + "\" ");
                }
            }

            if (var1.getSrc() != null && var1.getSrc().length() > 0) {
                this.a.print(" src=\"" + var1.getSrc() + "\"");
            }

            if (var1.getId() != null && var1.getId().length() > 0) {
                this.a.print(" id=\"" + var1.getId() + "\"");
            }

            if (var1.getStyleClass() != null && var1.getStyleClass().length() > 0) {
                this.a.print(" class=\"" + var1.getStyleClass() + "\"");
            } else {
                this.a.print(" class=\"btn btn-info btn-sm\"");
            }

            this.a.print(">");
            this.a.print(var1.getDesc());
            this.a.println("</button>");
        }
    }

    public void rebuildSelect(SelectContext var1) {
        List var2 = var1.loadOptions();
        String var3 = JSONArray.fromObject(var2).toString();
        this.a.print(var3);
    }

    public void buildMultitSelect(MultiSelectContext var1, String var2) {
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<div class=\"col-sm-3\">");
            this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
        }

        this.a.println(var1.getDesc() == null ? "" : var1.getDesc() + "：");
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        this.a.print("<input id=\"" + var1.getId() + "\" name=\"" + var1.getId() + "\" ");
        this.a.print("data-options=\"");
        this.a.print("multiple:true,valueField: 'id',textField: 'text'");
        this.a.print("\"");
        this.a.println(" />");
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</div>");
        }

        String var3 = var1.getSize();
        this.a.println("<script>$(function(){");
        this.a.println("$(\"#" + var1.getId() + "\").combobox({editable:false,width:" + (var3 != null && var3.length() != 0 ? var3 : "120") + ",height:28,");
        this.a.print("data:[");
        List var4 = var1.loadOptions();

        for(int var5 = 0; var4 != null && var5 < var4.size(); ++var5) {
            Map var6 = (Map)var4.get(var5);
            String var7 = var6.get("value") == null ? "" : var6.get("value").toString();
            if (var7 == null) {
                var7 = "";
            }

            String var8 = (String)var6.get("text");
            this.a.print("{id:\"" + var7 + "\",text:\"" + var8 + "\"}");
            if (var5 != var4.size() - 1) {
                this.a.print(",");
            }
        }

        String var9 = "";
        if (var2 != null && var2.length() > 0) {
            String[] var10 = var2.split(",");

            for(int var11 = 0; var11 < var10.length; ++var11) {
                var9 = var9 + "\"" + var10[var11] + "\"";
                if (var11 != var10.length - 1) {
                    var9 = var9 + ",";
                }
            }
        }

        this.a.print("]}); \n");
        this.a.println("$(\"#" + var1.getId() + "\").combobox(\"setValues\", [" + var9 + "]); \n");
        this.a.println("})</script>");
    }

    public void buildSelect(SelectContext var1) throws UnsupportedEncodingException {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.c.getParamValue(var1.getId());
        }

        String var3;
        if (var2 == null || var2.length() == 0) {
            var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var2);
        this.c.setParamValue(var1.getId(), var2);
        if (var1 instanceof MultiSelectContext) {
            this.buildMultitSelect((MultiSelectContext)var1, var2);
        } else {
            if (var1.getOutBox() != null && var1.getOutBox()) {
                this.a.println("<div class=\"col-sm-3\">");
                this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
            }

            if (var1.getDesc() != null && var1.getDesc().length() > 0) {
                this.a.print(var1.getDesc() + "：");
            }

            if (var1.getOutBox() != null && var1.getOutBox()) {
                this.a.println("</span>");
            }

            this.a.print("<select id='" + var1.getId() + "' name=\"" + var1.getId() + "\" class=\"inputform2\"");
            var3 = var1.getSize();
            if (var3 != null && var3.length() > 0) {
                this.a.print(" style=\"width:" + var3 + "px;\"");
            }

            if (var1 instanceof MultiSelectContext) {
                this.a.print(" multiple ");
            }

            this.a.print(">");
            List var4 = var1.loadOptions();
            Map var7;
            if (var4 != null) {
                boolean var5 = false;

                for(int var6 = 0; var6 < var4.size(); ++var6) {
                    var7 = (Map)var4.get(var6);
                    String var8 = var7.get("value") == null ? "" : var7.get("value").toString();
                    if (var8 == null) {
                        var8 = "";
                    }

                    this.a.print("<option value='" + var8 + "'");
                    if (var8.equals(var2)) {
                        this.a.print(" selected=\"selected\"");
                        var5 = true;
                    }

                    this.a.print(">");
                    this.a.print(var7.get("text") == null ? "" : var7.get("text").toString());
                    this.a.print("</option>");
                }

                if (!var5 && var4.size() > 0) {
                    Map var13 = (Map)var4.get(0);
                    String var15 = var13.get("value") == null ? "" : var13.get("value").toString();
                    this.d.put(var1.getId(), var15);
                    this.c.setParamValue(var1.getId(), var15);
                }
            }

            this.a.println("</select>");
            if (var1.getCascade() != null && var1.getCascade().length() > 0) {
                String var12 = RuleUtils.findCurMV(this.d).getMvid();
                String var14 = RuleUtils.getResPath(this.b) + "control/" + "extControl" + "?" + "serviceid" + "=ext.sys.param.cascade&";
                var14 = var14 + "t_from_id=" + var12;
                this.a.println("<script>");
                this.a.print("cascadeParam('" + var14 + "','" + var1.getCascade() + "','" + var1.getId() + "','");
                var7 = this.c.getParams();
                Iterator var9 = var7.entrySet().iterator();

                while(var9.hasNext()) {
                    Entry var16 = (Entry)var9.next();
                    String var10 = (String)var16.getKey();
                    Object var11 = var16.getValue();
                    if (!var10.equals(var1.getCascade()) && var11 instanceof String) {
                        this.a.print(var10 + "=" + URLEncoder.encode((String)var11, "UTF-8") + "&");
                    }
                }

                this.a.print("");
                this.a.print(var1.getCascade());
                this.a.print("=");
                this.a.print("')");
                this.a.println("</script>");
            }

            if (var1.getOutBox() != null && var1.getOutBox()) {
                this.a.println("</div>");
            }

        }
    }

    public void buildDateSelect(DateSelectContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<div class=\"col-sm-3\">");
            this.a.println("<span class=\"formtext\" id=\"p_" + var1.getId() + "\">");
        }

        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print(var1.getDesc() + "：");
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        String var2 = var1.getSize();
        if (var2 == null || var2.length() == 0) {
            var2 = "10";
        }

        String var3 = var1.getOutValue();
        if (var3 == null || var3.length() == 0) {
            var3 = this.c.getParamValue(var1.getId());
        }

        String var4;
        if (var3 == null || var3.length() == 0) {
            var4 = var1.getDefaultValue();
            if (var4 != null && var4.length() > 0) {
                var3 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var3);
        this.c.setParamValue(var1.getId(), var3);
        String var5;
        String var6;
        String var7;
        if (var1.getShowCalendar() != null && var1.getShowCalendar()) {
            this.a.print("<input type='hidden' id='" + var1.getId() + "' name='" + var1.getId() + "' value='" + var3 + "'>");
            MVContext var17 = RuleUtils.findCurMV(this.d);
            var5 = null;
            String var8;
            if (var1.getTarget() != null && var1.getTarget().length > 0) {
                String[] var18 = var1.getTarget();
                var7 = "";
                var8 = "";
                StringBuffer var9 = new StringBuffer("[");
                Map var10 = this.c.getParams();
                int var11;
                Entry var13;
                String var15;
                if (var10 != null) {
                    var11 = var10.size();
                    int var12 = 0;
                    Iterator var14 = var10.entrySet().iterator();

                    while(var14.hasNext()) {
                        var13 = (Entry)var14.next();
                        var15 = (String)var13.getKey();
                        var9.append("'");
                        var9.append(var15);
                        var9.append("'");
                        ++var12;
                        if (var12 != var11) {
                            var9.append(",");
                        }
                    }

                    var9.append("]");
                }

                for(var11 = 0; var11 < var18.length; ++var11) {
                    String var19 = var18[var11];
                    var13 = null;
                    String var21 = "ext.sys.cross.rebuild";
                    Object var20 = LabelUtils.findCrossBylabel(var17, var19);
                    if (var20 == null) {
                        var20 = LabelUtils.findChartBylabel(var17, var19);
                        var21 = "ext.sys.chart.rebuild";
                    }

                    if (var20 == null) {
                        var15 = ConstantsEngine.replace("配置的target $0 在文件 $1 (xml)中未指向正确的组件.", var19, var17.getMvid());
                        throw new ExtRuntimeException(var15);
                    }

                    var15 = (String)PropertyUtils.getProperty(var20, "id");
                    String var16 = RuleUtils.getResPath(this.b) + "control/extControl?serviceid=" + var21 + "&" + "t_from_id" + "=" + var17.getMvid() + "&id=" + var15;
                    var7 = var7 + "'" + var15 + "'";
                    var8 = var8 + "'" + var16 + "'";
                    if (var11 != var18.length - 1) {
                        var7 = var7 + ",";
                        var8 = var8 + ",";
                    }
                }

                var5 = "[" + var7 + "],[" + var8 + "]," + var9;
            }

            var6 = "calendar" + IdCreater.create();
            this.a.print("<div id=\"" + var6 + "\"></div>");
            var7 = var1.getMaxDate() == null ? "" : var1.getMaxDate();
            var8 = var1.getMinDate() == null ? "" : var1.getMinDate();
            this.a.println("<script>$(function(){getCalendar('" + var1.getId() + "','" + var3 + "', '" + var6 + "', '" + var8 + "', '" + var7 + "', " + (var5 == null ? "''" : "\"" + var5 + "\"") + ")});</script>");
        } else {
            var4 = var1.getDateFormat();
            if (var4 == null || var4.length() == 0) {
                var4 = "yyyyMMdd";
            }

            var5 = "";
            var6 = var1.getMinDate();
            if (var6 != null && var6.length() > 0) {
                var5 = var5 + ",minDate:'" + var6 + "'";
            }

            var7 = var1.getMaxDate();
            if (var7 != null && var7.length() > 0) {
                var5 = var5 + ",maxDate:'" + var7 + "'";
            }

            this.a.print("<input type='text' class='Wdate inputform2' onClick=\"WdatePicker({dateFmt:'" + var4 + "'" + var5);
            "month".equals(var1.getDateType());
            this.a.print(" })\"");
            this.a.print(" readonly='true' name='" + var1.getId() + "' id='" + var1.getId() + "' value='" + var3 + "' size='" + var2 + "'> ");
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</div>");
        }

    }

    public void buildFileField(FileFieldContext var1) {
        String var2 = var1.getSize();
        if (var2 == null || var2.length() == 0) {
            var2 = "20";
        }

        this.a.print(var1.getDesc());
        this.a.print("：<input type=\"file\" name=\"" + var1.getId() + "\" id=\"" + var1.getId() + "\" size='" + var2 + "'>");
    }

    public void buildRadio(RadioContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.c.getParamValue(var1.getId());
            if (var2 == null || var2.length() == 0) {
                String var3 = var1.getDefaultValue();
                if (var3 != null && var3.length() > 0) {
                    var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
                }
            }
        }

        this.d.put(var1.getId(), var2);
        this.c.setParamValue(var1.getId(), var2);
        MVContext var17 = RuleUtils.findCurMV(this.d);
        String var4 = RuleUtils.getResPath(this.b);
        var4 = var4 + "control/" + "extControl" + "?" + "serviceid" + "=ext.sys.tab.ajax&";
        var4 = var4 + "t_from_id=" + var17.getMvid() + "&";
        StringBuffer var5 = new StringBuffer();
        var5.append("");
        Map var6 = this.c.getParams();
        Iterator var8 = var6.entrySet().iterator();

        String var9;
        while(var8.hasNext()) {
            Entry var7 = (Entry)var8.next();
            var9 = (String)var7.getKey();
            Object var10 = var7.getValue();
            if (!var9.equals(var1.getId()) && var10 instanceof String) {
                var5.append(var9);
                var5.append("=");
                var5.append(var10);
                var5.append("&");
            }
        }

        String var18 = null;
        String var14;
        String var15;
        String var16;
        String var21;
        int var25;
        if (var1.getTarget() != null && var1.getTarget().length > 0) {
            String[] var19 = var1.getTarget();
            var9 = "";
            var21 = "";

            for(int var11 = 0; var11 < var19.length; ++var11) {
                String var12 = var19[var11];
                Object var13 = null;
                var14 = "ext.sys.cross.rebuild";
                var13 = LabelUtils.findCrossBylabel(var17, var12);
                if (var13 == null) {
                    var13 = LabelUtils.findChartBylabel(var17, var12);
                    var14 = "ext.sys.chart.rebuild";
                }

                if (var13 == null) {
                    var15 = ConstantsEngine.replace("配置的target $0 在文件 $1 (xml)中未指向正确的组件.", var12, var17.getMvid());
                    throw new ExtRuntimeException(var15);
                }

                var15 = (String)PropertyUtils.getProperty(var13, "id");
                var16 = RuleUtils.getResPath(this.b) + "control/extControl?serviceid=" + var14 + "&" + "t_from_id" + "=" + var17.getMvid() + "&id=" + var15;
                var9 = var9 + "'" + var15 + "'";
                var21 = var21 + "'" + var16 + "'";
                if (var11 != var19.length - 1) {
                    var9 = var9 + ",";
                    var21 = var21 + ",";
                }
            }

            StringBuffer var23 = new StringBuffer("[");
            if (var6 != null) {
                var25 = var6.size();
                int var26 = 0;
                Iterator var29 = var6.entrySet().iterator();

                while(var29.hasNext()) {
                    Entry var27 = (Entry)var29.next();
                    var16 = (String)var27.getKey();
                    var23.append("'");
                    var23.append(var16);
                    var23.append("'");
                    ++var26;
                    if (var26 != var25) {
                        var23.append(",");
                    }
                }

                var23.append("]");
            }

            var18 = " onclick=\"post2Comps([" + var9 + "],[" + var21 + "], " + var23 + ")\"";
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("<span class=\"param\" id=\"p_" + var1.getId() + "\">");
        }

        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print(var1.getDesc() + "：");
        }

        String var20 = "div" + IdCreater.create();
        boolean var22 = false;
        var21 = null;
        List var24 = var1.loadOptions();
        if (var24 != null) {
            for(var25 = 0; var25 < var24.size(); ++var25) {
                Map var28 = (Map)var24.get(var25);
                var14 = var28.get("value") == null ? "" : var28.get("value").toString();
                if (var1.getShowSpan() != null && var1.getShowSpan()) {
                    var15 = var1.getRadioStyle();
                    this.a.print("<span class='radioSpan' style='" + (var15 != null && var15.length() != 0 ? var15 : "") + "'>");
                }

                this.a.print("<label>");
                this.a.print("<input type='radio' id='" + var1.getId() + "' name=\"" + var1.getId() + "\" value=\"" + var14 + "\"");
                if (var14.equals(var2)) {
                    this.a.print(" checked");
                }

                var15 = (String)var28.get("ref");
                if (var15 != null && var15.length() > 0) {
                    var22 = true;
                    var16 = var4 + var5 + "mvid" + "=" + var15;
                    this.a.print(" onclick=\"radiolink('" + var16 + "','" + var1.getId() + "', '" + var20 + "')\"");
                    if (var14.equals(var2)) {
                        var21 = var16;
                    }
                }

                if (var18 != null) {
                    this.a.print(var18);
                }

                this.a.print(">");
                this.a.print((String)var28.get("text"));
                this.a.print("</label>&nbsp;");
                if (var1.getShowSpan() != null && var1.getShowSpan()) {
                    this.a.print("</span>");
                }
            }
        }

        if (var1.getOutBox() != null && var1.getOutBox()) {
            this.a.println("</span>");
        }

        if (var22) {
            this.a.print("<div id='" + var20 + "'></div>");
            this.a.print("<script>jQuery(function(){radiolink('" + var21 + "','" + var1.getId() + "','" + var20 + "')})</script>");
        }

    }

    public void buildTree(TreeContext var1) {
        String var2 = var1.getSize();
        if (var2 == null || var2.length() == 0) {
            var2 = "20";
        }

        String var3 = var1.getOutValue();
        if (var3 == null || var3.length() == 0) {
            var3 = this.c.getParamValue(var1.getId());
        }

        if (var3 == null || var3.length() == 0) {
            String var4 = var1.getDefaultValue();
            if (var4 != null && var4.length() > 0) {
                var3 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var3);
        this.c.setParamValue(var1.getId(), var3);
        if (var1.getDesc() != null && var1.getDesc().length() > 0) {
            this.a.print(var1.getDesc() + "：");
        }

        this.a.print("<input type=\"text\" name=\"" + var1.getId() + "\" id='" + var1.getId() + "' value=\"" + (var3 == null ? "" : var3) + "\" size='" + var2 + "'");
        this.a.println(" >");
        TreeService var5 = new TreeService(var1);
        this.a.println("<script language='javascript'>");
        this.a.println("jQuery(function(){");
        this.a.println("//if(jQuery(\"#" + var1.getId() + "\").size() > 0){return}");
        this.a.println("var dt = " + JSONArray.fromObject(var5.createTreeData(var1.loadOptions())));
        this.a.println("jQuery(\"#" + var1.getId() + "\").combotree({");
        this.a.println(" id:'abcd',data:dt");
        this.a.println("})");
        this.a.println("});");
        this.a.println("</script>");
    }
}
