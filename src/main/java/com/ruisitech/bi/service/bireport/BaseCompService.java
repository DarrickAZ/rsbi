//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.portal.CompParamDto;
import com.ruisitech.bi.entity.portal.DashboardParamDto;
import com.ruisitech.bi.entity.portal.PortalChartQuery;
import com.ruisitech.bi.entity.portal.PortalParamDto;
import com.ruisitech.bi.entity.portal.PortalTableQuery;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseCompService {
    protected JSONObject pageBody;

    public BaseCompService() {
    }

    public String writerUnit(Object bd) {
        if (bd == null) {
            return "";
        } else {
            int v = 0;
            if (bd instanceof BigDecimal) {
                v = ((BigDecimal)bd).intValue();
            } else if (bd instanceof Integer) {
                v = (Integer)bd;
            }

            if (v == 1) {
                return "";
            } else if (v == 100) {
                return "百";
            } else if (v == 1000) {
                return "千";
            } else if (v == 10000) {
                return "万";
            } else if (v == 1000000) {
                return "百万";
            } else {
                return v == 100000000 ? "亿" : "*" + v;
            }
        }
    }

    public int type2value(String tp) {
        int curDate = 4;
        if (tp.equals("year")) {
            curDate = 4;
        } else if (tp.equals("quarter")) {
            curDate = 3;
        } else if (tp.equals("month")) {
            curDate = 2;
        } else if (tp.equals("day")) {
            curDate = 1;
        }

        return curDate;
    }

    public String createWarning(Map<String, Object> warn, String kpiFmt, StringBuffer scripts) {
        String funcName = "warn" + IdCreater.create();
        scripts.append("function " + funcName + "(val, a, b, c, d){");
        scripts.append("if(d == 'html'){out.print('<span class=\"kpiValue\">');}");
        scripts.append("if(val == null){out.print('-')}else{out.print(val, '" + kpiFmt + "');}");
        scripts.append("if(d != 'html'){");
        scripts.append(" return;");
        scripts.append("}");
        scripts.append("if(val " + warn.get("logic1") + " " + warn.get("val1") + "){");
        scripts.append("out.print(\"<span class='" + warn.get("pic1") + "'></span>\")");
        scripts.append("}else if(val " + (warn.get("logic1").equals(">=") ? "<" : "<=") + " " + warn.get("val1") + " && val " + warn.get("logic2") + " " + warn.get("val2") + "){");
        scripts.append("out.print(\"<span class='" + warn.get("pic2") + "'></span>\")");
        scripts.append("}else{");
        scripts.append("out.print(\"<span class='" + warn.get("pic3") + "'></span>\")");
        scripts.append("}");
        scripts.append("if(d == 'html'){out.print('</span>');}");
        scripts.append("}");
        return funcName;
    }

    public void parserHiddenParam(List<PortalParamDto> params, MVContext mv, Map<String, InputField> mvParams) throws ExtConfigException {
        if (params != null) {
            for(int i = 0; i < params.size(); ++i) {
                PortalParamDto param = (PortalParamDto)params.get(i);
                TextFieldContext target = new TextFieldContextImpl();
                target.setId(param.getParamid());
                String defvalue = param.getDefvalue();
                String type = param.getType();
                String dtformat = param.getDtformat();
                if (("dateselect".equals(type) || "monthselect".equals(type) || "yearselect".equals(type)) && "now".equals(defvalue)) {
                    defvalue = (new SimpleDateFormat(dtformat)).format(new Date());
                }

                target.setValue(defvalue);
                target.setType("hidden");
                mvParams.put(target.getId(), target);
                mv.setMvParam(target.getId(), target);
                mv.getChildren().add(target);
                target.setParent(mv);
            }
        }

    }

    public String dealDashboardParams(List<DashboardParamDto> params, TableInfoVO tinfo) {
        boolean qEs = false;
        if ("y".equals(tinfo.getUseEs()) && "y".equals(tinfo.getEsEnable())) {
            qEs = true;
        }

        StringBuffer sb = new StringBuffer("");

        for(int i = 0; params != null && i < params.size(); ++i) {
            DashboardParamDto dto = (DashboardParamDto)params.get(i);
            if (dto.getTid().equals(tinfo.getTid()) && dto.getValue() != null && dto.getValue().length() != 0 && !"null".equalsIgnoreCase(dto.getValue())) {
                sb.append(" and ");
                sb.append(qEs ? (dto.getColname().equals(dto.getFromCol()) ? dto.getFromCol() : dto.getKey()) : dto.getColname());
                sb.append("=");
                if ("String".equals(dto.getValType())) {
                    sb.append("'");
                }

                sb.append(dto.getValue());
                if ("String".equals(dto.getValType())) {
                    sb.append("'");
                }
            }
        }

        return sb.toString();
    }

    public String dealCubeParams(List<CompParamDto> params, String nodetype, TableInfoVO tinfo) {
        boolean qEs = false;
        if ("y".equals(tinfo.getUseEs()) && "y".equals(tinfo.getEsEnable())) {
            qEs = true;
        }

        StringBuffer sb = new StringBuffer("");

        for(int i = 0; params != null && i < params.size(); ++i) {
            CompParamDto param = (CompParamDto)params.get(i);
            String col = qEs ? (param.getCol().equals(param.getFromCol()) ? param.getCol() : param.getAlias()) : param.getCol();
            String type = param.getType();
            String val = param.getVal();
            String val2 = param.getVal2();
            String valuetype = param.getValuetype();
            String usetype = param.getUsetype();
            String linkparam = param.getLinkparam();
            String linkparam2 = param.getLinkparam2();
            String ntype = param.getNodetype();
            if (ntype == null || ntype.equals(nodetype)) {
                if (type.equals("like")) {
                    if (val != null) {
                        val = "%" + val + "%";
                    }

                    if (val2 != null) {
                        val2 = "%" + val2 + "%";
                    }
                }

                if ("string".equals(valuetype)) {
                    if (val != null) {
                        if ("in".equals(type)) {
                            String[] vls = val.split(",");
                            val = "";

                            for(int j = 0; j < vls.length; ++j) {
                                val = val + "'" + vls[j] + "'";
                                if (j != vls.length - 1) {
                                    val = val + ",";
                                }
                            }
                        } else {
                            val = "'" + val + "'";
                        }
                    }

                    if (val2 != null) {
                        val2 = "'" + val2 + "'";
                    }
                }

                if (type.equals("between")) {
                    if (usetype.equals("gdz")) {
                        sb.append(" and " + col + " " + type + " " + val + " and " + val2);
                    } else {
                        sb.append("#if([x]" + linkparam + " && [x]" + linkparam + " != '' && [x]" + linkparam2 + " && [x]" + linkparam2 + " != '') ");
                        sb.append(" and " + col + " " + type + " " + ("string".equals(valuetype) ? "'" : "") + "[x]" + linkparam + ("string".equals(valuetype) ? "'" : "") + " and " + ("string".equals(valuetype) ? "'" : "") + "[x]" + linkparam2 + ("string".equals(valuetype) ? "'" : "") + " #end");
                    }
                } else if (type.equals("in")) {
                    if (usetype.equals("gdz")) {
                        sb.append(" and " + col + " in (" + val + ")");
                    } else {
                        sb.append("#if([x]" + linkparam + " && [x]" + linkparam + " != '') ");
                        sb.append(" and " + col + " in ($extUtils.printVals([x]" + linkparam + ", '" + valuetype + "'))");
                        sb.append("  #end");
                    }
                } else if (usetype.equals("gdz")) {
                    sb.append(" and " + col + " " + type + " " + val);
                } else {
                    sb.append("#if([x]" + linkparam + " && [x]" + linkparam + " != '') ");
                    sb.append(" and " + col + " " + type + " " + ("string".equals(valuetype) ? "'" + ("like".equals(type) ? "%" : "") + "[x]" + linkparam + "" + ("like".equals(type) ? "%" : "") + "'" : "[x]" + linkparam) + "");
                    sb.append("  #end");
                }
            }
        }

        return sb.toString().replaceAll("\\[x\\]", "\\$");
    }

    public String getTableAliasByTname(Map<TableInfoVO, String> map, String tname) {
        String ret = null;
        Iterator var4 = map.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<TableInfoVO, String> ent = (Entry)var4.next();
            if (((TableInfoVO)ent.getKey()).getTname().equals(tname)) {
                ret = (String)ent.getValue();
                break;
            }
        }

        return ret;
    }

    public String findEventParamName(String compId) {
        if (this.pageBody == null) {
            throw new RuntimeException("pageBody 未初始化...");
        } else {
            String paramName = null;
            int i = 1;

            while(true) {
                Object tmp = this.pageBody.get("tr" + i);
                if (tmp == null) {
                    return paramName;
                }

                JSONArray trs = (JSONArray)tmp;

                for(int j = 0; j < trs.size(); ++j) {
                    JSONObject td = trs.getJSONObject(j);
                    Object cldTmp = td.get("children");
                    if (cldTmp != null) {
                        JSONArray children = (JSONArray)cldTmp;

                        for(int k = 0; k < children.size(); ++k) {
                            JSONObject comp = children.getJSONObject(k);
                            String type = comp.getString("type");
                            Map link;
                            String target;
                            if ("chart".equals(type)) {
                                PortalChartQuery chart = (PortalChartQuery)JSONObject.toJavaObject(comp, PortalChartQuery.class);
                                link = chart.getChartJson().getLink();
                                if (link != null) {
                                    target = (String)link.get("target");
                                    if (target != null && target.indexOf(compId) >= 0) {
                                        paramName = (String)link.get("paramName");
                                        break;
                                    }
                                }
                            } else if ("table".equals(type)) {
                                PortalTableQuery table = (PortalTableQuery)JSONObject.toJavaObject(comp, PortalTableQuery.class);
                                link = table.getLink();
                                if (link != null) {
                                    target = (String)link.get("target");
                                    if (target != null && target.indexOf(compId) >= 0) {
                                        paramName = (String)link.get("paramName");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                ++i;
            }
        }
    }

    public void setPageBody(JSONObject pageBody) {
        this.pageBody = pageBody;
    }
}
