//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.model;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisitech.bi.entity.model.TableDimension;
import com.ruisitech.bi.entity.model.TableMeasure;
import com.ruisitech.bi.entity.model.TableMeta;
import com.ruisitech.bi.mapper.model.TableDimensionMapper;
import com.ruisitech.bi.mapper.model.TableMeasureMapper;
import com.ruisitech.bi.mapper.model.TableMetaColMapper;
import com.ruisitech.bi.mapper.model.TableMetaMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectManagerService {
    @Autowired
    private TableMetaMapper tableMapper;
    @Autowired
    private TableDimensionMapper dimMapper;
    @Autowired
    private TableMeasureMapper kpiMapper;
    @Autowired
    private TableMetaColMapper colMapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public SubjectManagerService() {
    }

    public List<TableMeta> listAuthSubject(TableMeta table) {
        String authCube = ExtContext.getInstance().getConstant("authCube");
        String key = table.getKey();
        if (key != null && key.length() > 0) {
            key = " and (tdesc like '%" + key + "%' or tnote like '%" + key + "%')";
            table.setKey(key);
        }

        if (authCube != null && "true".equalsIgnoreCase(authCube)) {
            table.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            return this.tableMapper.userSubject(table);
        } else {
            return this.tableMapper.listSubject(table);
        }
    }

    public List<TableMeta> listAllSubject(TableMeta table) {
        return this.tableMapper.listSubject(table);
    }

    public Integer tableExist(Integer tableId) {
        return this.tableMapper.tableExist(this.sysUser, tableId);
    }

    public TableMeta getCube(Integer tableId) {
        TableMeta table = this.tableMapper.getTable(this.sysUser, tableId);
        List<TableDimension> dims = this.dimMapper.getTableDims(this.sysUser, tableId);
        table.setDims(dims);
        List<TableMeasure> kpis = this.kpiMapper.getTableKpis(this.sysUser, tableId);
        table.setKpis(kpis);
        return table;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void delCube(Integer tid) {
        this.tableMapper.deleteTable(this.sysUser, tid);
        TableDimension dim = new TableDimension();
        dim.setTid(tid);
        this.dimMapper.deleteDim(dim);
        this.dimMapper.deleteGroupByTid(this.sysUser, tid);
        TableMeasure kpi = new TableMeasure();
        kpi.setTid(tid);
        this.kpiMapper.deleteKpi(kpi);
        this.kpiMapper.deleteKpiGroupByTid(this.sysUser, tid);
        this.colMapper.deleteByTid(this.sysUser, tid);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void saveCube(TableMeta table) {
        this.tableMapper.insertTable(table);
        this.insertDim(table);
        this.insertDimRela(table);
        this.insertKpi(table);
        this.insertKpiRela(table);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void updateCube(TableMeta table) {
        this.tableMapper.updateTable(table);
        List<Map<String, Object>> delObj = table.getDelObj();
        if (delObj != null && !delObj.isEmpty()) {
            for(int i = 0; i < delObj.size(); ++i) {
                Map<String, Object> obj = (Map)delObj.get(i);
                String tp = (String)obj.get("type");
                Object id = obj.get("id");
                if (id != null) {
                    if ("dim".equals(tp)) {
                        TableDimension dim = new TableDimension();
                        dim.setTid(table.getTid());
                        dim.setDimId((Integer)id);
                        this.dimMapper.deleteDim(dim);
                    } else if ("kpi".equals(tp)) {
                        TableMeasure kpi = new TableMeasure();
                        kpi.setTid(table.getTid());
                        kpi.setKpiId((Integer)id);
                        this.kpiMapper.deleteKpi(kpi);
                    } else if ("group".equals(tp)) {
                        this.dimMapper.deleteGroupById(this.sysUser, (String)id);
                    } else if ("kpigroup".equals(tp)) {
                        this.kpiMapper.deleteKpiGroup(this.sysUser, (String)id);
                    }
                }
            }
        }

        this.colMapper.deleteByTid(this.sysUser, table.getTid());
        this.updateDim(table);
        this.insertDimRela(table);
        this.updateKpi(table);
        this.insertKpiRela(table);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void updateKpi(TableMeta table) {
        Map<String, Object> groupkeys = new HashMap();
        List<String> ls = this.kpiMapper.listGroup(this.sysUser, table.getTid());

        for(int i = 0; i < ls.size(); ++i) {
            String str = (String)ls.get(i);
            groupkeys.put(str, "");
        }

        List<TableMeasure> kpis = table.getKpis();

        for(int i = 0; i < kpis.size(); ++i) {
            TableMeasure kpi = (TableMeasure)kpis.get(i);
            kpi.setTid(table.getTid());
            String kpiGroup = kpi.getKpiGroup();
            if (kpiGroup != null && kpiGroup.length() > 0 && !groupkeys.containsKey(kpiGroup)) {
                this.kpiMapper.insertKpiGroup(kpi);
                groupkeys.put(kpiGroup, "");
            }

            Integer targetId = kpi.getTargetId();
            if (targetId == null) {
                if (table.getIdType() == 2) {
                    targetId = this.kpiMapper.getMaxKpiId(this.sysUser);
                    kpi.setKpiId(targetId);
                }

                int calcKpi = kpi.getCalcKpi();
                Integer calc = kpi.getCalc();
                if (calcKpi == 1) {
                    kpi.setCol(kpi.getCol());
                } else if (calc != null && calc == 1) {
                    String c = kpi.getCol();
                    kpi.setCol(kpi.getAggre() + "(" + c + ")");
                } else {
                    kpi.setCol(kpi.getAggreCol());
                }

                this.kpiMapper.insertKpi(kpi);
                targetId = targetId == null ? this.kpiMapper.getMaxKpiId(this.sysUser) - 1 : targetId;
            } else {
                String isupdate = kpi.getIsupdate();
                kpi.setKpiId(targetId);
                int calcKpi = kpi.getCalcKpi();
                Integer calc = kpi.getCalc();
                if (calcKpi == 1) {
                    kpi.setCol(kpi.getCol());
                } else if (calc != null && calc == 1) {
                    String c = kpi.getCol();
                    c = c.substring(c.indexOf("("), c.length());
                    kpi.setCol(kpi.getAggre() + c);
                } else {
                    kpi.setCol(kpi.getAggreCol());
                }

                if ("y".equals(isupdate)) {
                    this.kpiMapper.updateKpi(kpi);
                }
            }

            kpi.setKpiId(targetId);
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void insertKpi(TableMeta table) {
        Map<String, Object> groupkeys = new HashMap();
        int kpiId = 0;
        if (table.getIdType() == 2) {
            kpiId = this.kpiMapper.getMaxKpiId(this.sysUser);
        }

        List<TableMeasure> kpis = table.getKpis();

        for(int i = 0; i < kpis.size(); ++i) {
            TableMeasure kpi = (TableMeasure)kpis.get(i);
            kpi.setTid(table.getTid());
            if (table.getIdType() == 2) {
                kpi.setKpiId(kpiId);
                ++kpiId;
            }

            String groupId = kpi.getKpiGroup();
            if (groupId != null && groupId.length() > 0 && !groupkeys.containsKey(groupId)) {
                this.kpiMapper.insertKpiGroup(kpi);
                groupkeys.put(groupId, "");
            }

            int calcKpi = kpi.getCalcKpi();
            Integer calc = kpi.getCalc();
            if (calcKpi == 1) {
                kpi.setCol(kpi.getCol());
            } else if (calc != null && calc == 1) {
                String c = kpi.getCol();
                kpi.setCol(kpi.getAggre() + "(" + c + ")");
            } else {
                kpi.setCol(kpi.getAggreCol());
            }

            this.kpiMapper.insertKpi(kpi);
            if (table.getIdType() == 1) {
                if (i == 0) {
                    kpiId = this.kpiMapper.getMaxKpiId(this.sysUser) - 1;
                } else {
                    ++kpiId;
                }

                kpi.setKpiId(kpiId);
            }
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void updateDim(TableMeta table) {
        Map<String, Object> groupkeys = new HashMap();
        List<String> ls = this.dimMapper.listGroup(this.sysUser, table.getTid());

        for(int i = 0; i < ls.size(); ++i) {
            String str = (String)ls.get(i);
            groupkeys.put(str, "");
        }

        List<TableDimension> dims = table.getDims();

        for(int i = 0; i < dims.size(); ++i) {
            TableDimension dim = (TableDimension)dims.get(i);
            dim.setOrd(i);
            dim.setTid(table.getTid());
            String type = dim.getType();
            if (type == null || type.length() == 0) {
                dim.setType("frd");
            }

            String groupId = dim.getGroupId();
            if (groupId != null && groupId.length() > 0 && !groupkeys.containsKey(groupId)) {
                this.dimMapper.insertGroup(dim);
                groupkeys.put(groupId, "");
            }

            Integer targetId = dim.getTargetId();
            if (targetId == null) {
                if (table.getIdType() == 2) {
                    Integer dimId = this.dimMapper.getMaxDimId(this.sysUser);
                    dim.setDimId(dimId);
                }

                this.dimMapper.insertDim(dim);
                targetId = targetId == null ? this.dimMapper.getMaxDimId(this.sysUser) - 1 : targetId;
            } else {
                dim.setDimId(targetId);
                String isupdate = dim.getIsupdate();
                if ("y".equals(isupdate)) {
                    this.dimMapper.updateDim(dim);
                }
            }

            dim.setDimId(targetId);
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void insertDim(TableMeta table) {
        Map<String, Object> groupkeys = new HashMap();
        int dimId = 0;
        List<TableDimension> dims = table.getDims();

        for(int i = 0; i < dims.size(); ++i) {
            TableDimension dim = (TableDimension)dims.get(i);
            String type = dim.getType();
            if (type == null || type.length() == 0) {
                dim.setType("frd");
            }

            dim.setTid(table.getTid());
            String groupId = dim.getGroupId();
            if (groupId != null && groupId.length() > 0 && !groupkeys.containsKey(groupId)) {
                this.dimMapper.insertGroup(dim);
                groupkeys.put(groupId, "");
            }

            if (table.getIdType() == 2) {
                if (dimId == 0) {
                    dimId = this.dimMapper.getMaxDimId(this.sysUser);
                } else {
                    ++dimId;
                }

                dim.setDimId(dimId);
                this.dimMapper.insertDim(dim);
            } else {
                this.dimMapper.insertDim(dim);
                if (i == 0) {
                    dimId = this.dimMapper.getMaxDimId(this.sysUser) - 1;
                } else {
                    ++dimId;
                }
            }

            dim.setDimId(dimId);
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    private void insertKpiRela(TableMeta table) {
        this.colMapper.deleteKpiMeta(this.sysUser, table.getTid());
        int maxid = table.getIdType() == 2 ? this.colMapper.getMaxRid(this.sysUser) : 0;
        List<TableMeasure> kpis = table.getKpis();

        for(int i = 0; i < kpis.size(); ++i) {
            TableMeasure kpi = (TableMeasure)kpis.get(i);
            kpi.setRid(maxid);
            kpi.setColType(2);
            kpi.setColId(kpi.getKpiId());
            kpi.setTid(table.getTid());
            kpi.setOrd(i);
            ++maxid;
            this.colMapper.insertMeta(kpi);
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    private void insertDimRela(TableMeta table) {
        this.colMapper.deleteDimMeta(this.sysUser, table.getTid());
        int maxid = table.getIdType() == 2 ? this.colMapper.getMaxRid(this.sysUser) : 0;

        for(int i = 0; i < table.getDims().size(); ++i) {
            TableDimension dim = (TableDimension)table.getDims().get(i);
            dim.setRid(maxid);
            ++maxid;
            dim.setTid(table.getTid());
            dim.setColType(1);
            dim.setColId(dim.getDimId());
            dim.setOrd(i);
            this.colMapper.insertMeta(dim);
        }

    }
}
