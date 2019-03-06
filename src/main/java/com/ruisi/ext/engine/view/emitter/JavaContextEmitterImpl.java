//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossBuilder;
import com.ruisi.ext.engine.cross.CrossWriter2JAVA;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.form.ButtonContext;
import com.ruisi.ext.engine.view.context.form.CheckBoxContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.FileFieldContext;
import com.ruisi.ext.engine.view.context.form.RadioContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.form.TreeContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.BRContext;
import com.ruisi.ext.engine.view.context.html.BoxContext;
import com.ruisi.ext.engine.view.context.html.CustomContext;
import com.ruisi.ext.engine.view.context.html.DivContext;
import com.ruisi.ext.engine.view.context.html.FieldsetContext;
import com.ruisi.ext.engine.view.context.html.ImageContext;
import com.ruisi.ext.engine.view.context.html.IncludeContext;
import com.ruisi.ext.engine.view.context.html.KpiDescContext;
import com.ruisi.ext.engine.view.context.html.LinkContext;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.table.TableContext;
import com.ruisi.ext.engine.view.context.html.table.TdContext;
import com.ruisi.ext.engine.view.context.html.table.TrContext;
import com.ruisi.ext.engine.view.context.tab.TabContext;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import javax.servlet.ServletContext;

public class JavaContextEmitterImpl implements ContextEmitter {
    private ExtWriter a;
    private ExtRequest b;
    private ExtResponse c;
    private UserJavaEmitter d;
    private InputOption e;
    private ExtEnvirContext f;

    public JavaContextEmitterImpl(UserJavaEmitter var1) {
        this.d = var1;
    }

    public void endBox(BoxContext var1) {
    }

    public void endDiv(DivContext var1) {
    }

    public void end(Element var1) {
    }

    public void start(Element var1) {
    }

    public void endFieldset(FieldsetContext var1) {
    }

    public void endMV(MVContext var1) {
    }

    public void endTab(TabContext var1) {
    }

    public void endTable(TableContext var1) {
    }

    public void endTabView(TabViewContext var1) {
    }

    public void endTd(TdContext var1) {
    }

    public void endTr(TrContext var1) {
    }

    public ExtWriter getWriter() {
        return this.a;
    }

    public void initialize(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5, ServletContext var6) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d.initialize(var1, var2, var3, var4);
        this.e = var5;
        this.f = var4;
    }

    public void rebuildSelect(SelectContext var1) {
    }

    public void startBox(BoxContext var1) {
    }

    public void startBR(BRContext var1) {
    }

    public void startButton(ButtonContext var1) {
    }

    public void startChart(ChartContext var1) {
        this.d.startChart(var1);
    }

    public void startCheckBox(CheckBoxContext var1) {
    }

    public void startCrossReport(CrossReportContext var1) {
        ExtEnvirContext var2 = this.f;
        CrossBuilder var3 = new CrossBuilder(var1, this.b, var2);
        var3.init();
        CrossWriter2JAVA var4 = new CrossWriter2JAVA(var3, this.a);
        var4.writeCols();
        var4.writeRows();
        this.d.startCrossReport(var4.getOutData());
    }

    public void startDataGrid(DataGridContext var1) {
    }

    public void startDateSelect(DateSelectContext var1) {
    }

    public void startDiv(DivContext var1) {
    }

    public void startFieldset(FieldsetContext var1) {
    }

    public void startFileField(FileFieldContext var1) {
    }

    public void startInclude(IncludeContext var1) {
    }

    public void startKpiDesc(KpiDescContext var1) {
    }

    public void startLink(LinkContext var1) {
    }

    public void startMV(MVContext var1) {
    }

    public void startRadio(RadioContext var1) {
    }

    public void startSelect(SelectContext var1) {
    }

    public void startTab(TabContext var1) {
    }

    public void startTable(TableContext var1) {
    }

    public void startTabView(TabViewContext var1) {
    }

    public void startTd(TdContext var1) {
    }

    public void startText(TextContext var1) {
    }

    public void startTextField(TextFieldContext var1) {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.e.getParamValue(var1.getId());
        }

        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.f);
            }
        }

        this.f.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
    }

    public void startTr(TrContext var1) {
    }

    public void startGridReport(GridReportContext var1) {
    }

    public void startTree(TreeContext var1) {
    }

    public void startCustom(CustomContext var1) {
    }

    public void startImage(ImageContext var1) {
    }
}
