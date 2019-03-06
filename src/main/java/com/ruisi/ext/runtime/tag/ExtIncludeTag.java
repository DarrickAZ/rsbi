//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.runtime.tag;

import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.InputOptionFactory;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.wrapper.ExtRequestImpl;
import com.ruisi.ext.engine.wrapper.ExtResponseImpl;
import com.ruisi.ext.engine.wrapper.ExtWriterImpl;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtIncludeTag extends TagSupport {
    private static Log a = LogFactory.getLog(ExtIncludeTag.class);
    private String b;
    private String c;

    public ExtIncludeTag() {
    }

    public String getMvid() {
        return this.b;
    }

    public void setMvid(String var1) {
        this.b = var1;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            MVContext var1 = ActionProcess.findMVContextById(this.b);
            ExtRequestImpl var2 = new ExtRequestImpl((HttpServletRequest)this.pageContext.getRequest());
            ExtResponseImpl var3 = new ExtResponseImpl((HttpServletResponse)this.pageContext.getResponse());
            Map var4 = var1.getMvParams();
            InputOption var5 = InputOptionFactory.createInputOption(var2, var3, var4);
            ExtEnvirContextImpl var6 = new ExtEnvirContextImpl(var5);
            ExtWriterImpl var7 = new ExtWriterImpl(this.pageContext.getOut());
            ServletContext var8 = this.pageContext.getServletContext();
            DaoHelper var9 = DaoUtils.getDaoHelper(var8);
            var2.setAttribute("ext.respath", this.c);
            BuilderManager.buildContext(this.b, var2, var7, var9, var6, var3, var5, var8);
        } catch (Exception var10) {
            a.error("xml调用出错.", var10);
        }

        return super.doEndTag();
    }

    public String getResPath() {
        return this.c;
    }

    public void setResPath(String var1) {
        this.c = var1;
    }
}
