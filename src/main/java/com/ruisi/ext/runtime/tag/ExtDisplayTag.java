//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.runtime.tag;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.wrapper.ExtWriterImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ExtDisplayTag extends TagSupport {
    public ExtDisplayTag() {
    }

    @Override
    public int doEndTag() throws JspException {
        ExtWriterImpl var1 = new ExtWriterImpl(this.pageContext.getOut());
        InputOption var2 = (InputOption)this.pageContext.getRequest().getAttribute("ext.view.option");
        ExtEnvirContextImpl var3 = new ExtEnvirContextImpl(var2);
        DaoHelper var4 = DaoUtils.getDaoHelper(this.pageContext.getServletContext());
        String var5 = (String)this.pageContext.getRequest().getAttribute("ext.view.mvid");

        try {
            BuilderManager.buildContext(var5, var2.getRequest(), var1, var4, var3, var2.getResponse(), var2, this.pageContext.getServletContext());
        } catch (AuthException var11) {
            try {
                this.pageContext.getRequest().getRequestDispatcher("/pages/control/AuthError.jsp").forward(this.pageContext.getRequest(), this.pageContext.getResponse());
            } catch (ServletException var9) {
                var9.printStackTrace();
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        } catch (Exception var12) {
            Exception var6 = var12;

            try {
                String var7 = var6.getMessage();
                this.pageContext.getRequest().setAttribute("ext.mv.err", var7);
                this.pageContext.include("/pages/control/Error.jsp");
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

        return super.doEndTag();
    }
}
