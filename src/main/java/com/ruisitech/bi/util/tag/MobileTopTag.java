//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util.tag;

import com.ruisitech.bi.util.IsModel;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MobileTopTag extends TagSupport {
    private static final long serialVersionUID = -8127692969629886677L;
    private String use = "jsp";
    private static String[] mvs = new String[]{"frame.Company", "frame.User", "frame.Menu", "frame.Role-list", "frame.Dateout"};

    public MobileTopTag() {
    }

    public int doEndTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        HttpServletRequest req = (HttpServletRequest)this.pageContext.getRequest();
        if ("jsp".equals(this.use) && IsModel.check(req)) {
            this.printsData(out, req);
        } else if ("mv".equals(this.use) && IsModel.check(req)) {
            String mv = (String)this.pageContext.getRequest().getAttribute("ext.view.mvid");
            if (mv != null && this.mvExist(mv)) {
                this.printsData(out, req);
            }
        }

        return super.doEndTag();
    }

    private boolean mvExist(String mv) {
        boolean exist = false;
        String[] var3 = mvs;
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String m = var3[var5];
            if (mv.equals(m)) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    private void printsData(JspWriter out, HttpServletRequest req) {
        try {
            out.println("<div class=\"gray-bg dashbard-1\">");
            out.println("<div class=\"row border-bottom\">");
            out.println(" <nav class=\"navbar navbar-static-top\" role=\"navigation\" style=\"margin-bottom: 0px;\">");
            out.println(" <div class=\"navbar-header\">");
            out.println("<div style=\"width:250px;\">");
            out.println("<img src=\"../resource/img/frame3/log2.png\">");
            out.println("<a class=\"btn btn-xs btn-info\" href=\"../frame/Frame.action\">");
            out.println("<i class=\"fa fa-chevron-left\"></i>");
            out.println("</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("<ul class=\"nav navbar-top-links navbar-right\">");
            out.println("<li class=\"dropdown\">");
            out.println("<a class=\"dropdown-toggle count-info\" data-toggle=\"dropdown\" href=\"#\">");
            out.println("<i class=\"fa fa-user\"></i>");
            out.println("</a>");
            out.println("<ul class=\"dropdown-menu dropdown-alerts\">");
            out.println("<li><a href=\"http://www.ruisitech.com/suggest.html\" target=\"_blank\"><div>问题反馈</div></a></li>");
            out.println("<li><a href=\"http://shatter.gitbooks.io/rsbi/content/\" target=\"_blank\"><div>使用手册</div></a></li>");
            out.println(" <li><a href=\"Logout.action\"><div> 退出登录</div></a></li>");
            out.println("</ul>");
            out.println("</li>");
            out.println(" </ul>");
            out.println("</nav>");
            out.println("</div>");
            out.println("</div>");
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public String getUse() {
        return this.use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}
