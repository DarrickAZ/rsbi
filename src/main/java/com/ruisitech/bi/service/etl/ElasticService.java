//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ispire.dc.grid.DatasetProvider;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTransformMapper;
import com.ruisitech.bi.mapper.model.TableMetaColMapper;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.query.AggregationQueryAction;
import org.nlpcn.es4sql.query.QueryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ElasticService implements DatasetProvider {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DaoHelper daoHelper;
    @Value("${elasticsearch.url}")
    private String elasticsearch;
    @Autowired
    private EtlTransformMapper mapper;
    @Autowired
    private EtlTableMetaService tableService;
    @Autowired
    private DataSourceService dSource;
    @Autowired
    private TableCacheService cahceService;
    @Autowired
    private DataLoaderService dlService;
    @Autowired
    private TableMetaColMapper colMapper;
    @Autowired
    private EtlTableMetaColMapper etlColMapper;
    private Logger log = Logger.getLogger(ElasticService.class);
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Integer rowCount;

    public ElasticService() {
    }

    public String getVersion() {
        try {
            if (this.elasticsearch != null && this.elasticsearch.trim().length() != 0) {
                String ret = (String)this.restTemplate.getForObject(this.elasticsearch, String.class, new Object[0]);
                JSONObject obj = (JSONObject)JSON.parse(ret);
                JSONObject v = obj.getJSONObject("version");
                return v.getString("number");
            } else {
                return "";
            }
        } catch (Exception var4) {
            this.log.error("获取 elasticsearch 版本出错。", var4);
            return "获取失败";
        }
    }

    public boolean existIndex(String name) {
        name = name.toLowerCase();

        try {
            String indexExist = this.elasticsearch + "/_cat/indices/" + name + "?format=json";
            Map<String, Object> urlVariables = new HashMap();
            this.restTemplate.getForEntity(indexExist, (Class)null, urlVariables);
            return true;
        } catch (HttpClientErrorException var4) {
            if (var4.getStatusCode().value() == 404) {
                return false;
            } else {
                throw var4;
            }
        }
    }

    public JSONArray listIndexs() {
        try {
            if (this.elasticsearch != null && this.elasticsearch.length() != 0) {
                String indexExist = this.elasticsearch + "/_cat/indices/?format=json";
                Map<String, Object> urlVariables = new HashMap();
                ResponseEntity<JSONArray> indexs = this.restTemplate.getForEntity(indexExist, JSONArray.class, urlVariables);
                return (JSONArray)indexs.getBody();
            } else {
                return new JSONArray();
            }
        } catch (Exception var4) {
            this.log.error("查询elasticsearch索引出错。", var4);
            return null;
        }
    }

    public Result run(Integer id, ServletContext ctx) {
        Result res = new Result();
        String str = this.mapper.getTfConfig(this.sysUser, id);
        if (str == null) {
            res.setMsg(id + "同步ES不存在。");
            res.setResult(0);
            return res;
        } else if (this.elasticsearch != null && this.elasticsearch.length() != 0) {
            try {
                JSONObject json = JSON.parseObject(str);
                Integer tableId = json.getInteger("tableId");
                EtlTableMeta table = this.tableService.getTableOnly(tableId);
                table.setTableName(table.getTableName().toLowerCase());
                List<EtlTableMetaCol> tableCols = this.tableService.queryTableColumns(tableId, false);
                table.setMetaCols(tableCols);
                String loadtype = json.getString("loadtype");
                if ("ql".equals(loadtype)) {
                    String delUrl = this.elasticsearch + "/" + table.getTableName();

                    try {
                        Map<String, Object> urlVariables = new HashMap();
                        this.restTemplate.delete(delUrl, urlVariables);
                    } catch (HttpClientErrorException var30) {
                        if (var30.getStatusCode().value() != 404) {
                            this.log.error("出错了", var30);
                        }
                    } catch (Exception var31) {
                        this.log.error("出错了", var31);
                    }
                }

                List<Map<String, Object>> cols = this.colMapper.queryMetaCols(this.sysUser, table.getTableId());
                List<EtlTableMetaCol> dynas = this.etlColMapper.queryTableColumnsOnlyExpress(tableId, this.sysUser);
                boolean existIndex = this.existIndex(table.getTableName());
                int idx;
                if (!existIndex) {
                    String indexcrt = this.elasticsearch + "/" + table.getTableName();
                    Map<String, Object> userEntity = new HashMap();
                    Map<String, Object> mappings = new HashMap();
                    userEntity.put("mappings", mappings);
                    Map<String, Object> category = new HashMap();
                    mappings.put("data", category);
                    Map<String, Object> properties = new HashMap();
                    category.put("properties", properties);

                    EtlTableMetaCol c;
                    HashMap col;
                    String tp;
                    for(idx = 0; idx < table.getMetaCols().size(); ++idx) {
                        c = (EtlTableMetaCol)table.getMetaCols().get(idx);
                        col = new HashMap();
                        tp = null;
                        if (!c.getColType().equalsIgnoreCase("date") && !c.getColType().equalsIgnoreCase("datetime")) {
                            if (c.getColType().equalsIgnoreCase("String")) {
                                tp = "keyword";
                            } else if (c.getColType().equalsIgnoreCase("Int")) {
                                tp = "integer";
                            } else if (c.getColType().equalsIgnoreCase("Double")) {
                                tp = "double";
                            }
                        } else {
                            tp = "date";
                        }

                        col.put("type", tp);
                        properties.put(c.getColName(), col);
                    }

                    String vtp;
                    for(idx = 0; idx < cols.size(); ++idx) {
                        Map<String, Object> tcol = (Map)cols.get(idx);
                        col = new HashMap();
                        tp = null;
                        vtp = (String)tcol.get("valType");
                        if (!vtp.equalsIgnoreCase("date") && !vtp.equalsIgnoreCase("datetime")) {
                            if (vtp.equalsIgnoreCase("String")) {
                                tp = "keyword";
                            } else if (vtp.equalsIgnoreCase("Int")) {
                                tp = "integer";
                            } else if (vtp.equalsIgnoreCase("Double")) {
                                tp = "double";
                            }
                        } else {
                            tp = "date";
                        }

                        col.put("type", tp);
                        properties.put((String)tcol.get("alias"), col);
                    }

                    Iterator var47 = dynas.iterator();

                    while(var47.hasNext()) {
                        c = (EtlTableMetaCol)var47.next();
                        col = new HashMap();
                        tp = null;
                        vtp = c.getColType();
                        if (!vtp.equalsIgnoreCase("date") && !vtp.equalsIgnoreCase("datetime")) {
                            if (vtp.equalsIgnoreCase("String")) {
                                tp = "keyword";
                            } else if (vtp.equalsIgnoreCase("Int")) {
                                tp = "integer";
                            } else if (vtp.equalsIgnoreCase("Double")) {
                                tp = "double";
                            }
                        } else {
                            tp = "date";
                        }

                        col.put("type", tp);
                        properties.put(c.getColName(), col);
                    }

                    this.restTemplate.put(indexcrt, userEntity, new Object[]{JSONObject.class});
                }

                StringBuffer sql = new StringBuffer("select ");

                int i;
                EtlTableMetaCol c;
                for(i = 0; i < table.getMetaCols().size(); ++i) {
                    c = (EtlTableMetaCol)table.getMetaCols().get(i);
                    sql.append(c.getColName());
                    if (i != table.getMetaCols().size() - 1) {
                        sql.append(",");
                    }
                }

                for(i = 0; i < cols.size(); ++i) {
                    Map<String, Object> tcol = (Map)cols.get(i);
                    sql.append(",");
                    sql.append(tcol.get("colName"));
                    sql.append(" as ");
                    sql.append(tcol.get("alias"));
                }

                for(i = 0; i < dynas.size(); ++i) {
                    c = (EtlTableMetaCol)dynas.get(i);
                    sql.append(",");
                    sql.append(c.getExpression());
                    sql.append(" as ");
                    sql.append(c.getColName());
                }

                sql.append(" from " + table.getTableName());
                if ("zl".equals(loadtype)) {
                    String sjc = json.getString("sjc");
                    String impId = "es" + id;
                    Date dt = this.dlService.getDateLabel(impId, sjc);
                    if (dt != null) {
                        sql.append(" where " + sjc + " > '" + this.sdf.format(dt) + "'");
                    }

                    if (dt == null) {
                        this.dlService.insertDateLabel(impId, sjc, new Date());
                    } else {
                        this.dlService.updateDateLable(impId, sjc, dt);
                    }
                }

                Connection conn = null;
                PreparedStatement ps = null;

                try {
                    conn = this.dSource.getConnection(ctx);
                    ps = conn.prepareStatement(sql.toString(), 1003, 1007);
                    ps.setFetchSize(5000);
                    ps.setFetchDirection(1000);
                    ResultSet rs = ps.executeQuery();

                    ArrayList ls;
                    Integer var51;
                    for(ls = new ArrayList(); rs.next(); var51 = this.rowCount = this.rowCount + 1) {
                        idx = 0;
                        Map<String, Object> dt = new HashMap();

                        int i;
                        EtlTableMetaCol c;
                        for(i = 0; i < table.getMetaCols().size(); ++i) {
                            c = (EtlTableMetaCol)table.getMetaCols().get(i);
                            dt.put(c.getColName(), DataSourceBuilder.getResultSetValue(rs, i + 1));
                            ++idx;
                        }

                        for(i = 0; i < cols.size(); ++i) {
                            Map<String, Object> tcol = (Map)cols.get(i);
                            dt.put((String)tcol.get("alias"), DataSourceBuilder.getResultSetValue(rs, idx + 1));
                            ++idx;
                        }

                        for(i = 0; i < dynas.size(); ++i) {
                            c = (EtlTableMetaCol)dynas.get(i);
                            dt.put(c.getColName(), DataSourceBuilder.getResultSetValue(rs, idx + 1));
                            ++idx;
                        }

                        ls.add(dt);
                        if (ls.size() == 3000) {
                            this.batchInsert(ls, table.getTableName());
                            ls.clear();
                        }

                        Integer var54 = this.rowCount;
                    }

                    if (ls.size() > 0) {
                        this.batchInsert(ls, table.getTableName());
                    }
                } finally {
                    conn.close();
                    ps.close();
                }

                table.setEsEnable("y");
                this.tableService.updateEsEnable(table);
                this.cahceService.removeTableInfo(table.getTableId());
                res.setResult(1);
                return res;
            } catch (HttpClientErrorException var32) {
                this.log.error("es同步出错了。" + var32.getResponseBodyAsString(), var32);
                res.setMsg(var32.getResponseBodyAsString());
                res.setResult(0);
                return res;
            } catch (Exception var33) {
                this.log.error("es同步出错了。", var33);
                res.setMsg(var33.getMessage());
                res.setResult(0);
                return res;
            }
        } else {
            res.setMsg("未配置elasticsearch。");
            res.setResult(0);
            return res;
        }
    }

    private void batchInsert(List<Map<String, Object>> ls, String tableName) {
        tableName = tableName.toLowerCase();
        if (ls.size() != 0) {
            String inserturl = this.elasticsearch + "/" + tableName + "/data/_bulk?pretty";
            StringBuffer datas = new StringBuffer();

            for(int i = 0; i < ls.size(); ++i) {
                Map<String, Object> data = (Map)ls.get(i);
                datas.append("{\"index\":{}}\n");
                datas.append(JSONObject.toJSONString(data) + "\n");
            }

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
            HttpEntity<String> req = new HttpEntity(datas.toString(), requestHeaders);
            this.restTemplate.postForObject(inserturl, req, String.class, new Object[0]);
        }
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void saveOrUpdate(EtlTransform tf) throws Exception {
        if (tf.getId() == null) {
            int ret = this.mapper.tfTableExist(this.sysUser, tf.getTargetTable());
            if (ret > 0) {
                throw new Exception("表" + tf.getTargetTable() + "已经同步。");
            }

            tf.setIncome("es");
            tf.setCrtuser(RSBIUtils.getLoginUserInfo().getUserId());
            if (tf.getIdType() == 2) {
                tf.setId(this.mapper.maxtfid(this.sysUser));
            }

            this.mapper.insert(tf);
            EtlTableMeta table = new EtlTableMeta();
            table.setTableId(tf.getTableMetaId());
            table.setUseEs("y");
            this.tableService.updateUseEs(table);
        } else {
            this.mapper.update(tf);
        }

        this.cahceService.removeTableInfo(tf.getTableMetaId());
    }

    public EtlTransform getConfig(Integer id) {
        return this.mapper.selectByPrimaryKey(this.sysUser, id);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void deleteConfig(Integer id) {
        EtlTransform tf = this.mapper.selectByPrimaryKey(this.sysUser, id);
        String tname = tf.getTargetTable().toLowerCase();
        String delUrl = this.elasticsearch + "/" + tname;

        try {
            Map<String, Object> urlVariables = new HashMap();
            this.restTemplate.delete(delUrl, urlVariables);
        } catch (HttpClientErrorException var6) {
            if (var6.getStatusCode().value() != 404) {
                this.log.error("出错了", var6);
            }
        } catch (Exception var7) {
            this.log.error("出错了", var7);
        }

        this.mapper.deleteByPrimaryKey(this.sysUser, id);
        EtlTableMeta tab = new EtlTableMeta();
        tab.setTableId(tf.getTableMetaId());
        tab.setUseEs("n");
        tab.setEsEnable("n");
        this.tableService.updateUseEs(tab);
        this.tableService.updateEsEnable(tab);
        this.cahceService.removeTableInfo(tf.getTableMetaId());
    }

    public List<Map<String, Object>> querySql(String sql, String tname, ExtRequest request, boolean isAgg, PageInfo page) throws Exception {
        tname = tname.toLowerCase();
        SearchDao searchDao = new SearchDao((Client)null);

        try {
            if (page != null) {
                if (page.getCurtpage() * (long)page.getPagesize() + (long)page.getPagesize() > 10000L) {
                    page.setCurtpage((long)Math.floor((double)(10000 / page.getPagesize())) - 1L);
                }

                sql = sql + " limit " + page.getCurtpage() * (long)page.getPagesize() + "," + page.getPagesize();
            }

            this.log.info("sql = " + sql);
            String url = this.elasticsearch + "/" + tname + "/data/_search";
            QueryAction qa = searchDao.explain(sql, isAgg);
            String param = qa.explain().explain();
            this.log.info("param = " + param);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
            HttpEntity<String> req = new HttpEntity(param, requestHeaders);
            JSONObject datas = (JSONObject)this.restTemplate.postForObject(url, req, JSONObject.class, new Object[0]);
            return qa instanceof AggregationQueryAction ? this.convertAggreDatas(datas) : this.convertListDatas(datas, page);
        } catch (HttpServerErrorException var13) {
            throw new Exception(var13.getResponseBodyAsString());
        } catch (HttpClientErrorException var14) {
            if (var14.getStatusCode().value() == 404) {
                throw new Exception("Elasticsearch中找不到表：" + tname);
            } else {
                this.log.error("查询出错。" + var14.getResponseBodyAsString(), var14);
                throw new Exception(var14.getResponseBodyAsString());
            }
        } catch (Exception var15) {
            this.log.error("查询出错。", var15);
            throw var15;
        }
    }

    private List<Map<String, Object>> convertListDatas(JSONObject datas, PageInfo page) {
        List<Map<String, Object>> ret = new ArrayList();
        JSONObject hits = datas.getJSONObject("hits");
        if (page != null) {
            page.setAllsize(hits.getLongValue("total"));
        }

        JSONArray dts = hits.getJSONArray("hits");

        for(int i = 0; i < dts.size(); ++i) {
            JSONObject dt = dts.getJSONObject(i);
            JSONObject fields = dt.getJSONObject("fields");
            if (fields != null && !fields.isEmpty()) {
                Iterator<String> keys = fields.keySet().iterator();
                HashMap vls = new HashMap();

                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    JSONArray field = fields.getJSONArray(key);
                    vls.put(key, field.get(0));
                }

                ret.add(vls);
            } else {
                JSONObject source = dt.getJSONObject("_source");
                Iterator<String> keys = source.keySet().iterator();
                HashMap vls = new HashMap();

                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    Object val = source.get(key);
                    if (val instanceof Long) {
                        vls.put(key, this.sdf.format(new Date((Long)val)));
                    } else {
                        vls.put(key, val);
                    }
                }

                ret.add(vls);
                JSONObject highlight = dt.getJSONObject("highlight");
                if (highlight != null && !highlight.isEmpty()) {
                    Iterator highKeys = highlight.keySet().iterator();

                    while(highKeys.hasNext()) {
                        String key = (String)highKeys.next();
                        Object val = highlight.get(key);
                        vls.put(key, val);
                    }
                }
            }
        }

        return ret;
    }

    private List<Map<String, Object>> convertAggreDatas(JSONObject datas) {
        List<Map<String, Object>> ret = new ArrayList();
        JSONObject aggregations = datas.getJSONObject("aggregations");
        HashMap<String, Object> map = new HashMap();
        Iterator<String> keys = aggregations.keySet().iterator();
        boolean isend = false;

        String key;
        while(keys.hasNext()) {
            key = (String)keys.next();
            if ("all_matching_docs".equals(key)) {
                ret.add(this.all_matching_docs(aggregations.getJSONObject(key)));
                return ret;
            }

            Object tmpVal = null;
            JSONObject tmp = aggregations.getJSONObject(key);
            if (tmp != null && (tmpVal = tmp.get("value")) != null) {
                map.put(key, tmpVal);
                isend = true;
            }
        }

        if (!isend) {
            key = (String)aggregations.keySet().iterator().next();
            this.loopObject(aggregations, key, map, ret);
        } else {
            ret.add(map);
        }

        return ret;
    }

    private Map<String, Object> all_matching_docs(JSONObject json) {
        Map<String, Object> ret = new HashMap();
        JSONObject obj = json.getJSONObject("buckets").getJSONObject("all");
        Iterator its = obj.keySet().iterator();

        while(its.hasNext()) {
            String k = (String)its.next();
            if (!"key".equals(k) && !"doc_count".equals(k) && !"key_as_string".equals(k)) {
                JSONObject tmp = obj.getJSONObject(k);
                Object tmpVal = null;
                if (tmp != null && (tmpVal = tmp.get("value")) != null) {
                    ret.put(k, tmpVal);
                }
            }
        }

        return ret;
    }

    private void loopObject(JSONObject input, String key, HashMap<String, Object> map, List<Map<String, Object>> ret) {
        JSONObject obj = input.getJSONObject(key);
        JSONArray buckets = obj.getJSONArray("buckets");
        int i = 0;

        label56:
        while(buckets != null && i < buckets.size()) {
            JSONObject bucket = buckets.getJSONObject(i);
            Object val = bucket.get("key_as_string") != null ? bucket.get("key_as_string") : bucket.get("key");
            map.put(key, val);
            boolean isend = false;
            Iterator its = bucket.keySet().iterator();

            while(true) {
                while(true) {
                    String k;
                    do {
                        do {
                            do {
                                if (!its.hasNext()) {
                                    if (isend) {
                                        Map<String, Object> m = (Map)map.clone();
                                        ret.add(m);
                                    }

                                    ++i;
                                    continue label56;
                                }

                                k = (String)its.next();
                            } while("key".equals(k));
                        } while("doc_count".equals(k));
                    } while("key_as_string".equals(k));

                    JSONObject tmp = bucket.getJSONObject(k);
                    Object tmpVal = null;
                    if (tmp != null && (tmpVal = tmp.get("value")) != null) {
                        map.put(k, tmpVal);
                        isend = true;
                    } else {
                        this.loopObject(bucket, k, map, ret);
                    }
                }
            }
        }

    }

    public List<Map<String, Object>> queryData(String sql, boolean isAgg, String tname, ExtRequest req, PageInfo page) throws Exception {
        return this.querySql(sql, tname, req, isAgg, page);
    }

    public Integer getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
