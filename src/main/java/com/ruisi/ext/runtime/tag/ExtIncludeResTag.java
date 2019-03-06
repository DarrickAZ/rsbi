//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.runtime.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtIncludeResTag extends TagSupport {
    private static Log a = LogFactory.getLog(ExtIncludeResTag.class);
    private String b = "";
    private boolean c;

    public ExtIncludeResTag() {
    }

    public boolean isUseExtGrid() {
        return this.c;
    }

    public void setUseExtGrid(boolean var1) {
        this.c = var1;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter var1 = this.pageContext.getOut();
            var1.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.b + "ext-res/js/build/fonts/fonts-min.css\" />");
            var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/jquery.js\"></script>");
            var1.println("<script language=\"javascript\" src=\"" + this.b + "ext-res/js/ext-base.js\"></script>");
            var1.println("<script language=\"javascript\" src=\"" + this.b + "ext-res/js/FusionCharts.js\"></script>");
            var1.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.b + "ext-res/css/boncbase.css\" />");
            var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/highcharts/highcharts.src.js\"></script>");
            var1.println("<!--[if IE]><script type=\"text/javascript\" src=\"" + this.b + "ext-res/highcharts/excanvas.compiled.js\"></script><![endif]-->");
            var1.println("<script language=\"javascript\" src=\"" + this.b + "ext-res/js/sortabletable.js\"></script>");
            var1.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.b + "ext-res/js/extjs/resources/css/ext-all.css\"/>");
            if (this.c) {
                var1.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.b + "ext-res/css/extJsCssExtend.css\" />");
                var1.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.b + "ext-res/js/extjs/ux/css/ColumnHeaderGroup.css\" />");
                var1.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.b + "ext-res/js/extjs/ux/css/LockingGridView.css\" />");
            }

            var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/extjs/ext-base.js\"></script>");
            var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/extjs/ext-all.js\"></script>");
            var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/extjs/ext-lang-zh_CN.js\"></script>");
            if (this.c) {
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/extjs/ux/ColumnHeaderGroup.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/extjs/ux/LockingGridView.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/extjs/ux/LockingGridView.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/02-ColumnModelExtends.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/03-GridViewExtends.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/04-GridPanelExtends.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/05-FormatExtends.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/06-PagingToolbarExtends.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/07-LockingColumnHeaderGroup.js\"></script>");
                var1.println("<script type=\"text/javascript\" src=\"" + this.b + "ext-res/js/08-Boncgrid.js\"></script>");
            }
        } catch (Exception var2) {
            a.error("xml引入资源文件出错.", var2);
        }

        return super.doEndTag();
    }

    public String getPath() {
        return this.b;
    }

    public void setPath(String var1) {
        this.b = var1;
    }
}
