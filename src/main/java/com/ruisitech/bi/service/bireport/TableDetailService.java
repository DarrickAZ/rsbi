//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.bireport.TableDetailDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.model.TableDimension;
import com.ruisitech.bi.service.model.TableDimensionService;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableDetailService {
    @Autowired
    private TableCacheService cahceService;
    @Autowired
    private DaoHelper daoHelper;
    @Autowired
    private TableDimensionService dimService;

    public TableDetailService() {
    }

    public List<EtlTableMetaCol> getTableHeader(Integer tid) {
        TableInfoVO tvo = this.cahceService.getTableInfo(tid);
        return tvo.getCols();
    }

    public List<Map<String, Object>> detailDatas(TableDetailDto dto) throws ExtConfigException {
        TableInfoVO tvo = this.cahceService.getTableInfo(dto.getTid());
        String sql = this.createSql(tvo, dto.getPms());
        PageInfo page = new PageInfo();
        if (dto.getPage() != null) {
            page.setCurtpage((long)(dto.getPage() - 1));
        }

        if (dto.getRows() != null) {
            page.setPagesize(dto.getRows());
        }

        List<Map<String, Object>> ls = DaoUtils.calPage(sql, page, this.daoHelper);
        dto.setTotal((int)page.getAllsize());
        return ls;
    }

    public String createSql(TableInfoVO tvo, Map<String, String> pms) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        List<EtlTableMetaCol> cols = tvo.getCols();

        String name;
        String val;
        for(int i = 0; i < cols.size(); ++i) {
            EtlTableMetaCol col = (EtlTableMetaCol)cols.get(i);
            name = col.getExpression();
            val = col.getColName();
            sb.append(name != null && name.length() != 0 ? name : val);
            sb.append(" as ");
            sb.append("\"" + col.getColName() + "\"");
            if (i != cols.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(" from ");
        sb.append(tvo.getTname());
        sb.append(" where 1=1 ");
        Iterator var11 = pms.entrySet().iterator();

        while(true) {
            while(var11.hasNext()) {
                Entry<String, String> p = (Entry)var11.next();
                name = (String)p.getKey();
                val = (String)p.getValue();
                TableDimension col = this.dimService.findDimensionByAlias(name, new Integer(tvo.getTid()));
                String exp = col.getCol();
                if ("NULLVAL".equals(val)) {
                    sb.append(" and " + (exp != null && exp.length() > 0 ? exp : name) + " is null");
                } else if (!"Double".equalsIgnoreCase(col.getVtype()) && !"Int".equalsIgnoreCase(col.getVtype())) {
                    sb.append(" and " + (exp != null && exp.length() > 0 ? exp : name) + " = '" + val + "'");
                } else {
                    sb.append(" and " + (exp != null && exp.length() > 0 ? exp : name) + " = " + val);
                }
            }

            return sb.toString();
        }
    }

    public void exportDatas(TableDetailDto dto, HttpServletResponse res) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TableInfoVO tvo = this.cahceService.getTableInfo(dto.getTid());
        String sql = this.createSql(tvo, dto.getPms());
        List<Map<String, Object>> ls = this.daoHelper.queryForList(sql);
        OutputStream os = res.getOutputStream();
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        WritableSheet sheet = workbook.createSheet("页1", 0);

        int i;
        for(i = 0; i < tvo.getCols().size(); ++i) {
            EtlTableMetaCol col = (EtlTableMetaCol)tvo.getCols().get(i);
            WritableCell cell = new Label(i, 0, col.getColName());
            sheet.addCell(cell);
        }

        for(i = 0; i < ls.size(); ++i) {
            Map<String, Object> data = (Map)ls.get(i);

            for(int j = 0; j < tvo.getCols().size(); ++j) {
                EtlTableMetaCol col = (EtlTableMetaCol)tvo.getCols().get(j);
                WritableCell cell = null;
                Object value = data.get(col.getColName());
                if (value == null) {
                    cell = new Label(j, i, "");
                } else if (value instanceof Date) {
                    Date d = (Date)value;
                    cell = new Label(j, i + 1, sdf.format(d));
                } else if (value instanceof Integer) {
                    cell = new Number(j, i + 1, (double)(Integer)value);
                } else if (value instanceof Double) {
                    cell = new Number(j, i + 1, (Double)value);
                } else if (value instanceof BigDecimal) {
                    cell = new Number(j, i + 1, ((BigDecimal)value).doubleValue());
                } else {
                    cell = new Label(j, i + 1, value.toString());
                }

                sheet.addCell((WritableCell)cell);
            }
        }

        workbook.write();
        workbook.close();
    }
}
