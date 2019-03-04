//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeService {
    public TreeService() {
    }

    public List<Map<String, Object>> createTreeData(List<Map<String, Object>> datas, TreeInterface treeface) {
        List<Map<String, Object>> roots = this.getChildren(datas, 0);
        this.loopChildren(roots, datas, treeface);
        return roots;
    }

    public List<Map<String, Object>> createTreeDataById(List<Map<String, Object>> datas, TreeInterface treeface, int id) {
        List<Map<String, Object>> roots = this.getChildren(datas, id);
        this.loopChildren(roots, datas, treeface);
        return roots;
    }

    private void loopChildren(List<Map<String, Object>> nodes, List<Map<String, Object>> datas, TreeInterface treeface) {
        for(int i = 0; i < nodes.size(); ++i) {
            Map<String, Object> root = (Map)nodes.get(i);
            treeface.processMap(root);
            Object ret = root.get("id");
            int id;
            if (ret instanceof Integer) {
                id = (Integer)ret;
            } else if (ret instanceof BigDecimal) {
                id = ((BigDecimal)ret).intValue();
            } else {
                id = ((Long)ret).intValue();
            }

            List<Map<String, Object>> child = this.getChildren(datas, id);
            if (child.size() > 0) {
                this.loopChildren(child, datas, treeface);
            }

            if (child.size() > 0) {
                root.put("state", "closed");
            }

            treeface.processEnd(root, child.size() > 0);
            root.put("children", child);
        }

    }

    private List<Map<String, Object>> getChildren(List<Map<String, Object>> datas, int id) {
        List<Map<String, Object>> roots = new ArrayList();

        for(int i = 0; i < datas.size(); ++i) {
            Map<String, Object> m = (Map)datas.get(i);
            Object pobj = m.get("pid");
            int pid;
            if (pobj instanceof Integer) {
                pid = (Integer)pobj;
            } else if (pobj instanceof Long) {
                pid = ((Long)pobj).intValue();
            } else {
                if (!(pobj instanceof BigDecimal)) {
                    throw new RuntimeException("类型不支持。");
                }

                pid = ((BigDecimal)pobj).intValue();
            }

            if (pid == id) {
                roots.add(m);
            }
        }

        return roots;
    }

    public void addReport2Cata(List<Map<String, Object>> catas, List<Map<String, Object>> reports) {
        for(int i = 0; i < catas.size(); ++i) {
            Map<String, Object> cata = (Map)catas.get(i);
            cata.put("state", "closed");
            List<Map<String, Object>> rpt = this.findReports(cata, reports);
            List<Map<String, Object>> children = (List)cata.get("children");
            if (children != null && children.size() > 0) {
                this.addReport2Cata(children, reports);
            }

            for(int j = 0; j < rpt.size(); ++j) {
                Map<String, Object> rp = (Map)rpt.get(j);
                rp.put("iconCls", "icon-gears");
                Map<String, Object> att = new HashMap();
                att.put("rfile", rp.get("rfile"));
                rp.put("attributes", att);
                rp.remove("crtdate");
            }

            children.addAll(rpt);
        }

    }

    private List<Map<String, Object>> findReports(Map<String, Object> cata, List<Map<String, Object>> reports) {
        List<Map<String, Object>> ret = new ArrayList();
        Object pobj = cata.get("id");
        int id;
        if (pobj instanceof Integer) {
            id = (Integer)pobj;
        } else if (pobj instanceof Long) {
            id = ((Long)pobj).intValue();
        } else {
            if (!(pobj instanceof BigDecimal)) {
                throw new RuntimeException("类型不支持。");
            }

            id = ((BigDecimal)pobj).intValue();
        }

        for(int i = 0; i < reports.size(); ++i) {
            Map<String, Object> report = (Map)reports.get(i);
            Object cobj = report.get("cataId");
            int cataId;
            if (cobj instanceof Integer) {
                cataId = (Integer)cobj;
            } else if (cobj instanceof Long) {
                cataId = ((Long)cobj).intValue();
            } else {
                if (!(cobj instanceof BigDecimal)) {
                    throw new RuntimeException("类型不支持。");
                }

                cataId = ((BigDecimal)cobj).intValue();
            }

            if (id == cataId) {
                ret.add(report);
            }
        }

        return ret;
    }
}
