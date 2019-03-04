//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.model;

import com.ruisitech.bi.entity.model.TableMeta;
import com.ruisitech.bi.mapper.model.TableMeasureMapper;
import com.ruisitech.bi.mapper.model.TableMetaMapper;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.bi.util.TreeInterface;
import com.ruisitech.bi.util.TreeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableMetaServcice {
    @Autowired
    private TableMetaMapper tableMapper;
    @Autowired
    private TableMeasureMapper kpiMapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public TableMetaServcice() {
    }

    public Integer getDefTid() {
        String authCube = RSBIUtils.getConstant("authCube");
        if ("true".equals(authCube)) {
            TableMeta table = new TableMeta();
            table.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            List<TableMeta> metas = this.tableMapper.userSubject(table);
            return metas.size() == 0 ? null : ((TableMeta)metas.get(0)).getTid();
        } else {
            return this.tableMapper.getDefTid(this.sysUser);
        }
    }

    public Object getCubeTree(String selectDsIds) {
        Map<String, Object> curGroup = null;
        Map<String, Object> curKpigroup = null;
        if (selectDsIds != null && selectDsIds.length() != 0) {
            List<Map<String, Object>> ls = this.tableMapper.listDs(this.sysUser, selectDsIds);

            for(int i = 0; i < ls.size(); ++i) {
                Map<String, Object> m = (Map)ls.get(i);
                m.put("iconCls", "icon-cube");
                String tid = m.get("id").toString();
                List<Object> nodeChildren = new ArrayList();
                m.put("children", nodeChildren);
                Map<String, Object> wdnode = new HashMap();
                wdnode.put("id", "wd");
                wdnode.put("text", "维度");
                wdnode.put("state", "open");
                wdnode.put("iconCls", "icon-dim2");
                List<Object> wdChindren = new ArrayList();
                wdnode.put("children", wdChindren);
                nodeChildren.add(wdnode);
                Map<String, Object> zbnode = new HashMap();
                zbnode.put("id", "zb");
                zbnode.put("text", "度量");
                zbnode.put("state", "open");
                zbnode.put("iconCls", "icon-kpigroup");
                List<Object> zbChindren = new ArrayList();
                zbnode.put("children", zbChindren);
                nodeChildren.add(zbnode);
                List<Map<String, Object>> children = this.tableMapper.listDsMeta(this.sysUser, new Integer(tid));

                for(int j = 0; j < children.size(); ++j) {
                    Map<String, Object> child = (Map)children.get(j);
                    int colType = new Integer(child.get("col_type").toString());
                    String grouptype = (String)child.get("grouptype");
                    if (grouptype == null || grouptype.length() == 0) {
                        grouptype = null;
                    }

                    String groupname = (String)child.get("groupname");
                    HashMap attr;
                    if (grouptype != null && grouptype.length() > 0 && colType == 1) {
                        if (curGroup == null || !curGroup.get("id").equals(grouptype)) {
                            attr = new HashMap();
                            attr.put("id", grouptype);
                            attr.put("text", groupname);
                            attr.put("state", "open");
                            attr.put("iconCls", "icon-dim");
                            attr.put("children", new ArrayList());
                            wdChindren.add(attr);
                            curGroup = attr;
                        }
                    } else {
                        curGroup = null;
                    }

                    if (grouptype != null && grouptype.length() > 0 && colType == 2) {
                        if (curKpigroup == null || !curKpigroup.get("id").equals(grouptype)) {
                            attr = new HashMap();
                            attr.put("id", grouptype);
                            attr.put("text", groupname);
                            attr.put("state", "open");
                            attr.put("iconCls", "icon-open");
                            attr.put("children", new ArrayList());
                            zbChindren.add(attr);
                            curKpigroup = attr;
                        }
                    } else {
                        curKpigroup = null;
                    }

                    attr = new HashMap();
                    child.put("attributes", attr);
                    attr.put("col_type", colType);
                    attr.put("col_id", child.get("col_id"));
                    attr.put("col_name", child.get("col_name"));
                    attr.put("tid", child.get("tid"));
                    attr.put("tname", m.get("tname"));
                    attr.put("alias", child.get("alias"));
                    attr.put("fmt", child.get("fmt") == null ? "" : child.get("fmt"));
                    attr.put("aggre", child.get("aggre"));
                    attr.put("dim_type", child.get("dim_type"));
                    attr.put("tableName", child.get("tableName") == null ? "" : child.get("tableName"));
                    attr.put("tableColKey", child.get("tableColKey") == null ? "" : child.get("tableColKey"));
                    attr.put("tableColName", child.get("tableColName") == null ? "" : child.get("tableColName"));
                    attr.put("dateformat", child.get("dateformat") == null ? "" : child.get("dateformat"));
                    if (curGroup == null) {
                        attr.put("iscas", "");
                    } else {
                        attr.put("iscas", "y");
                    }

                    attr.put("fromCol", child.get("fromCol") == null ? "" : child.get("fromCol"));
                    attr.put("dimord", child.get("dimord") == null ? "" : child.get("dimord"));
                    attr.put("rate", child.get("rate"));
                    attr.put("unit", child.get("unit") == null ? "" : child.get("unit"));
                    attr.put("grouptype", grouptype);
                    attr.put("calc_kpi", child.get("calc_kpi"));
                    attr.put("valType", child.get("valType"));
                    attr.put("ordcol", child.get("ordcol") == null ? "" : child.get("ordcol"));
                    if (colType == 1) {
                        if (grouptype != null && grouptype.length() != 0) {
                            child.put("iconCls", "icon-dimlevel");
                            attr.put("endlvl", String.valueOf(this.islvlEnd(children, j, grouptype)));
                        } else {
                            child.put("iconCls", "icon-dim");
                            attr.put("endlvl", "true");
                        }
                    } else {
                        child.put("iconCls", "icon-kpi");
                    }

                    if (colType == 1) {
                        if (curGroup == null) {
                            wdChindren.add(child);
                        } else {
                            ((List)curGroup.get("children")).add(child);
                        }
                    } else if (colType == 2) {
                        if (curKpigroup == null) {
                            zbChindren.add(child);
                        } else {
                            ((List)curKpigroup.get("children")).add(child);
                        }
                    }
                }
            }

            return ls;
        } else {
            Map<String, Object> root = new HashMap();
            root.put("id", "root");
            root.put("text", "您还未选择数据模型！");
            root.put("state", "open");
            root.put("iconCls", "icon-no");
            root.put("children", new ArrayList());
            return root;
        }
    }

    public boolean islvlEnd(List<Map<String, Object>> children, int index, String grouptype) {
        boolean end = true;
        if (children.size() - 1 > index) {
            Map<String, Object> next = (Map)children.get(index + 1);
            String nextgroup = (String)next.get("grouptype");
            if (nextgroup != null && nextgroup.length() > 0 && nextgroup.equals(grouptype)) {
                end = false;
            } else {
                end = true;
            }
        }

        return end;
    }

    public List<Map<String, Object>> listKpiDesc(String selectDsIds) {
        return (List)(selectDsIds != null && selectDsIds.trim().length() != 0 ? this.kpiMapper.listKpiDesc(this.sysUser, selectDsIds) : new ArrayList());
    }

    public List<Map<String, Object>> applistSubject() {
        List<Map<String, Object>> ls = this.tableMapper.applistSubject(this.sysUser);
        TreeService ser = new TreeService();
        List<Map<String, Object>> ret = ser.createTreeData(ls, new TreeInterface() {
            public void processMap(Map<String, Object> m) {
            }

            public void processEnd(Map<String, Object> m, boolean hasChild) {
                String tp = (String)m.get("tp");
                if ("ds".equals(tp)) {
                    int id = Integer.parseInt(m.get("id").toString());
                    m.put("id", id - 1000);
                }

            }
        });
        return ret;
    }
}
