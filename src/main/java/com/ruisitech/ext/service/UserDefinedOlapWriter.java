//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.ext.service;

import com.ruisi.ext.engine.cross.OlapWriterInterface;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.TableQueryDto;
import java.util.List;

public class UserDefinedOlapWriter implements OlapWriterInterface {
    private TableQueryDto table;

    public UserDefinedOlapWriter() {
    }

    public void wirteRowDims(ExtRequest request, ExtWriter out, CrossReportContext report) {
        this.table = (TableQueryDto)request.getAttribute("table");
        out.print("<div class='rowDimsList'>");
        out.print("<table class=\"grid5\" cellpadding=\"0\" cellspacing=\"0\">");
        out.print("<tr>");
        List<DimDto> rows = this.table.getRows();

        for(int i = 0; i < rows.size(); ++i) {
            DimDto row = (DimDto)rows.get(i);
            Integer id = row.getId();
            String name = row.getDimdesc();
            out.print("<th>");
            out.print("<span>" + name + " <a href=javascript:; onclick='setRdimInfo(this, \"" + id + "\", \"" + name + "\")' class='dimoptbtn set'> &nbsp; </a></span>");
            out.print("</th>");
        }

        out.print("</tr>");
        out.print("</table>");
        out.println("</div>");
    }

    public void writeColDims(ExtRequest request, ExtWriter out, CrossReportContext report) {
        this.table = (TableQueryDto)request.getAttribute("table");
        out.print("<div class='colDimsList'>");
        List<DimDto> cols = this.table.getCols();
        if (cols.size() <= 0) {
            out.print(" <div style=\"margin:3px;color:#999999;font-size:13px;\">列标签区域</div> ");
        } else {
            for(int i = 0; i < cols.size(); ++i) {
                DimDto col = (DimDto)cols.get(i);
                Integer id = col.getId();
                String name = col.getDimdesc();
                out.print("<span>" + name + " <a href=javascript:; onclick='setCdimInfo(this, \"" + id + "\", \"" + name + "\")' class='dimoptbtn set'> &nbsp; </a></span>");
            }
        }

        out.println("</div>");
    }
}
