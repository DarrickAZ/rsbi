//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util.tag;

import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DataWriteTag extends TagSupport {
    private static final long serialVersionUID = -8538523230625208563L;
    private Boolean is3g;
    private String state;

    public DataWriteTag() {
    }

    public int doEndTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        Map data = (Map)this.pageContext.findAttribute("data");

        try {
            out.print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"edittable\">");
            List<EtlTableMetaCol> cols = (List)this.pageContext.findAttribute("cols");

            int i;
            EtlTableMetaCol m;
            String id;
            String inputtype;
            String type;
            for(i = 0; i < cols.size(); ++i) {
                m = (EtlTableMetaCol)cols.get(i);
                out.print("<tr>");
                out.print("<td height=\"50\" width=\"" + (this.is3g ? 90 : 120) + "\">");
                out.print(m.getColNote() + "：");
                out.print("</td>");
                out.print(" <td class=\"hctx\">");
                out.print("   <input type=\"text\" id=\"" + m.getColName() + "\" name=\"" + m.getColName() + "\" style=\"width:180px; height:30px;\" ");
                id = m.getColType();
                inputtype = m.getInputType();
                String useCol;
                if ("Double".equals(id)) {
                    out.print("class=\"easyui-numberbox\"");
                    if ("Double".equals(id)) {
                        out.print(" precision=\"2\"");
                    }
                } else if ("Datetime".equals(id)) {
                    out.print("class=\"easyui-datetimebox\"");
                    out.print(" editable=\"false\"");
                } else if ("Date".equals(id)) {
                    out.print("class=\"easyui-datebox\"");
                    out.print(" editable=\"false\"");
                } else if ("String".equals(id) && inputtype.equals("input")) {
                    out.print("class=\"easyui-textbox\"");
                } else if (("String".equals(id) || "Int".equals(id)) && inputtype.equals("select")) {
                    out.print("class=\"easyui-combobox\"");
                    type = m.getValuestype();
                    if ("static".equals(type)) {
                        useCol = m.getOptions();
                        out.print(" data-options=\"valueField:'label',textField:'value',data:[");
                        String[] opts = useCol.split(",");

                        for(int j = 0; j < opts.length; ++j) {
                            out.print("{label:'" + opts[j] + "',value:'" + opts[j] + "'}");
                            if (j != opts.length - 1) {
                                out.print(",");
                            }
                        }

                        out.print("]\"");
                    } else if ("dtz".equals(type)) {
                        useCol = m.getUseCol();
                        String updatecol = m.getUpdateCol();
                        Integer tid = m.getTableId();
                        Integer colId = m.getColId();
                        out.print(" data-options=\"valueField:'id',textField:'text',url:'" + (this.is3g ? "../imp/" : "") + "comboData.action?tableId=" + tid + "&colId=" + (colId == null ? "" : colId) + "'");
                        if (updatecol != null && updatecol.length() > 0) {
                            EtlTableMetaCol col = this.findUpdatecol(updatecol, cols);
                            String tp = col.getColType();
                            String itp = col.getInputType();
                            String tpfmt = "";
                            if (!"Double".equals(tp) && !"Int".equals(tp)) {
                                if ("String".equals(tp) && itp.equals("input")) {
                                    tpfmt = "textbox";
                                }
                            } else {
                                tpfmt = "numberbox";
                            }

                            out.print(",onSelect:function(a){var v = a." + useCol + "; $('#" + ("insert".equals(this.state) ? "writectx" : "moddatactx") + " #" + updatecol + "')." + tpfmt + "('setValue', v); }");
                        }

                        out.print("\"");
                    }

                    out.print(" editable=\"false\"");
                } else if ("Int".equals(id)) {
                    out.print("class=\"easyui-numberbox\"");
                }

                type = m.getDefvalue();
                if (type == null) {
                    type = "";
                }

                if ("Datetime".equals(id)) {
                    type = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
                } else if ("Date".equals(id)) {
                    type = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
                }

                useCol = m.getColName();
                out.print(" value=\"" + (data == null ? type : (data.get(useCol) == null ? "" : data.get(useCol))) + "\" ");
                out.print(">  ");
                out.print("<font class=\"text-warning\">");
                out.print(m.getColDesc() != null && m.getColDesc().length() != 0 ? m.getColDesc() : "");
                out.print("</font>");
                out.print("</td>");
                out.print("</tr>");
            }

            out.print("</table>");
            if (this.is3g) {
                out.print("<script>$(function(){");

                for(i = 0; i < cols.size(); ++i) {
                    m = (EtlTableMetaCol)cols.get(i);
                    id = m.getColName();
                    inputtype = m.getInputType();
                    type = m.getColType();
                    if ("Double".equals(type)) {
                        out.print("$(\"#" + id + "\").numberbox({});");
                    } else if ("Datetime".equals(type)) {
                        out.print("$(\"#" + id + "\").datetimebox({});");
                    } else if ("Date".equals(type)) {
                        out.print("$(\"#" + id + "\").datebox({});");
                    } else if ("String".equals(type) && inputtype.equals("input")) {
                        out.print("$(\"#" + id + "\").textbox({});");
                    } else if (("String".equals(type) || "Int".equals(type)) && inputtype.equals("select")) {
                        out.print("$(\"#" + id + "\").combobox({});");
                    } else if ("Int".equals(type)) {
                        out.print("$(\"#" + id + "\").numberbox({});");
                    }
                }

                out.print("});</script>");
            }
        } catch (Exception var17) {
            var17.printStackTrace();
        }

        return super.doEndTag();
    }

    public EtlTableMetaCol findUpdatecol(String col, List<EtlTableMetaCol> cols) {
        EtlTableMetaCol ret = null;

        for(int i = 0; i < cols.size(); ++i) {
            EtlTableMetaCol m = (EtlTableMetaCol)cols.get(i);
            String name = m.getColName();
            if (col.equals(name)) {
                ret = m;
                break;
            }
        }

        return ret;
    }

    public Boolean getIs3g() {
        return this.is3g;
    }

    public void setIs3g(Boolean is3g) {
        this.is3g = is3g;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
