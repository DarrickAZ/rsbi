//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.ruisitech.bi.entity.bireport.TableDetailDto;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.service.bireport.TableDetailService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/bireport"})
public class DetailController extends BaseController {
    @Autowired
    private TableDetailService detailService;

    public DetailController() {
    }

    @RequestMapping(
        value = {"/detail.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object detail(@RequestBody TableDetailDto dto, HttpServletRequest req) {
        try {
            req.getSession().setAttribute("qdto", dto);
            List<Map<String, Object>> ls = this.detailService.detailDatas(dto);
            return new Result(RequestStatus.SUCCESS.getStatus(), "操作成功", ls, (long)dto.getTotal());
        } catch (Exception var4) {
            return super.buildError("出错啦。" + var4.getMessage());
        }
    }

    @RequestMapping({"/header.action"})
    @ResponseBody
    public Object header(Integer tid) {
        List<EtlTableMetaCol> ls = this.detailService.getTableHeader(tid);
        return ls;
    }

    @RequestMapping(
        value = {"/exportDetail.action"},
        method = {RequestMethod.GET}
    )
    @ResponseBody
    public Object export(HttpServletRequest req, HttpServletResponse res) throws Exception {
        TableDetailDto dto = (TableDetailDto)req.getSession().getAttribute("qdto");
        if (dto == null) {
            return null;
        } else {
            res.setContentType("application/x-msdownload");
            String contentDisposition = "attachment; filename=\"file.xls\"";
            res.setHeader("Content-Disposition", contentDisposition);
            this.detailService.exportDatas(dto, res);
            return null;
        }
    }
}
