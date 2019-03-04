//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.service.etl.DataSourceService;
import com.ruisitech.bi.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class DataSourceController extends BaseController {
    @Autowired
    private DataSourceService dsService;

    public DataSourceController() {
    }

    @RequestMapping({"/listDataSource.action"})
    @ResponseBody
    public Object list() {
        return this.dsService.listDataSource();
    }

    @RequestMapping({"/deleteDataSource.action"})
    @ResponseBody
    public Object delete(Integer id) {
        DataSource ds = new DataSource();
        ds.setId(id);
        this.dsService.deleteDataSource(ds);
        return this.buildSucces();
    }

    @RequestMapping(
        value = {"/testDataSource.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object test(DataSource ds) {
        try {
            return this.dsService.testDataSource(ds);
        } catch (Exception var3) {
            var3.printStackTrace();
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping(
        value = {"/saveDataSource.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(DataSource ds) {
        this.dsService.insertDataSource(ds);
        return this.buildSucces();
    }
}
