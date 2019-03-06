//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.List;
import java.util.Map;

public class GridWriter2Text implements GridWriter {
    private GridReportContext a;
    private ExtWriter b;
    private ExtEnvirContext c;
    private InputOption d;
    private WriterService e;

    public GridWriter2Text(GridReportContext var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = new WriterService(var1);
    }

    public void begin() {
    }

    public void writeDetail() {
        GridCell[][] var1 = this.a.getDetails();
        List var2 = this.a.loadOptions();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            GridCell[] var4 = var1[var3];

            for(int var5 = 0; var5 < var2.size(); ++var5) {
                for(int var6 = 0; var6 < var4.length; ++var6) {
                    GridCell var7 = var4[var6];
                    Map var8 = (Map)var2.get(var5);
                    Object var9 = var8.get(var7.getAlias());
                    this.b.print("\"");
                    if (var9 != null) {
                        if (var7.getFormatPattern() != null && var7.getFormatPattern().length() > 0) {
                            this.b.print(this.e.format(var9, var7.getFormatPattern()));
                        } else {
                            this.b.print(var9.toString());
                        }
                    }

                    this.b.print("\"");
                    if (var6 != var4.length - 1) {
                        this.b.print(",");
                    }
                }

                this.b.println("");
            }
        }

    }

    public void writeFooter() {
        GridCell[][] var1 = this.a.getFooters();
        if (var1 != null && var1.length != 0) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                GridCell[] var3 = var1[var2];

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    GridCell var5 = var3[var4];
                    this.b.print("\"");
                    this.b.print(var5.getDesc());
                    if (var5.getDynamicText() != null && var5.getDynamicText()) {
                        Object var6 = this.e.findDynamicTextValue(var5.getAlias());
                        this.b.print(this.e.format(var6, var5.getFormatPattern()));
                    }

                    this.b.print("\"");
                    if (var4 != var3.length - 1) {
                        this.b.print(",");
                    }
                }

                this.b.println("");
            }

        }
    }

    public void writeHeader() {
        GridCell[][] var1 = this.a.getHeaders();

        for(int var2 = 0; var2 < var1.length; ++var2) {
            GridCell[] var3 = var1[var2];

            for(int var4 = 0; var4 < var3.length; ++var4) {
                GridCell var5 = var3[var4];
                String var6 = var5.getDesc();
                if (var5.getDynamicText() != null && var5.getDynamicText()) {
                    Object var7 = this.e.findDynamicTextValue(var5.getAlias());
                    var6 = var6 + this.e.format(var7, var5.getFormatPattern());
                }

                this.b.print("\"");
                this.b.print(var6);
                this.b.print("\"");
                if (var4 != var3.length - 1) {
                    this.b.print(",");
                }
            }

            this.b.println("");
        }

    }

    public void end() {
    }
}
