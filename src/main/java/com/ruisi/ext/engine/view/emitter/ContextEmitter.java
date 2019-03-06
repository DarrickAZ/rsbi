//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
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
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import jxl.write.WriteException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public interface ContextEmitter {
    void initialize(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5, ServletContext var6) throws IOException, DocumentException;

    ExtWriter getWriter();

    void start(Element var1) throws DocumentException, IOException;

    void end(Element var1) throws IOException;

    void startMV(MVContext var1);

    void endMV(MVContext var1);

    void startText(TextContext var1) throws DocumentException;

    void startDataGrid(DataGridContext var1) throws UnsupportedEncodingException, ExtConfigException;

    void startBox(BoxContext var1);

    void endBox(BoxContext var1);

    void startTextField(TextFieldContext var1) throws DocumentException;

    void startTree(TreeContext var1);

    void startButton(ButtonContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException;

    void startBR(BRContext var1);

    void startSelect(SelectContext var1) throws DocumentException, UnsupportedEncodingException;

    void rebuildSelect(SelectContext var1);

    void startLink(LinkContext var1) throws UnsupportedEncodingException;

    void startInclude(IncludeContext var1) throws ServletException, IOException;

    void startCheckBox(CheckBoxContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void startDiv(DivContext var1);

    void endDiv(DivContext var1);

    void startTable(TableContext var1);

    void endTable(TableContext var1);

    void startTr(TrContext var1);

    void endTr(TrContext var1);

    void startTd(TdContext var1);

    void endTd(TdContext var1);

    void startTabView(TabViewContext var1);

    void endTabView(TabViewContext var1);

    void startTab(TabContext var1);

    void endTab(TabContext var1);

    void startDateSelect(DateSelectContext var1) throws DocumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void startFieldset(FieldsetContext var1);

    void endFieldset(FieldsetContext var1);

    void startFileField(FileFieldContext var1);

    void startChart(ChartContext var1) throws IOException, DocumentException, InvalidFormatException;

    void startRadio(RadioContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void startKpiDesc(KpiDescContext var1);

    void startCrossReport(CrossReportContext var1) throws DocumentException, InvocationTargetException, NoSuchMethodException, ExtConfigException, IllegalAccessException;

    void startGridReport(GridReportContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException, DocumentException, ExtConfigException, WriteException;

    void startCustom(CustomContext var1);

    void startImage(ImageContext var1) throws IOException, DocumentException, InvalidFormatException;
}
