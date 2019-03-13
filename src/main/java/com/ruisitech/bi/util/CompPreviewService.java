//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.InputOptionFactory;
import com.ruisi.ext.engine.cross.CrossFieldLoader;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.service.loginuser.LoginUserFactory;
import com.ruisi.ext.engine.service.loginuser.LoginUserInfoLoader;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.emitter.html.HTMLEmitter;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtRequestImpl;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtResponseImpl;
import com.ruisitech.ext.service.ExtLoginInfoLoader;
import java.io.IOException;
import java.util.Map;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileUploadException;

public class CompPreviewService {
    private ExtRequest request;
    private ExtResponse response;
    private ServletContext context;
    private DaoHelper dao;
    private static Object lock = new Object();
    private ExtEnvirContext envirCtx;
    Map<String, InputField> params = null;

    public DaoHelper getDao() {
        return this.dao;
    }

    public ExtEnvirContext getEnvirCtx() {
        return this.envirCtx;
    }

    public Map<String, InputField> getParams() {
        return this.params;
    }

    public void setParams(Map<String, InputField> params) {
        this.params = params;
    }

    public CompPreviewService(ExtRequest request, ExtResponse response, ServletContext context, DaoHelper dao) {
        this.request = request;
        this.response = response;
        this.context = context;
        this.dao = dao;
    }

    public CompPreviewService(HttpServletRequest req, HttpServletResponse resp, ServletContext sctx) throws IOException {
        this.request = new ExtRequestImpl(req);
        this.response = new ExtResponseImpl(resp);
        this.context = sctx;
        this.dao = DaoUtils.getDaoHelper(this.context);
    }

    public void initPreview() throws FileUploadException {
        LoginUserFactory loginUserFactory = LoginUserFactory.getInstance();
        LoginUserInfoLoader loginUserInfoLoader = new ExtLoginInfoLoader();
        loginUserFactory.createLoginUser(this.request, this.response, this.dao, loginUserInfoLoader);
        InputOption option = InputOptionFactory.createInputOption(this.request, this.response, this.params);
        this.request.setAttribute("ext.view.option", option);
        ExtEnvirContext ec = new ExtEnvirContextImpl(option);
        this.envirCtx = ec;
        CrossFieldLoader loader = null;
        String fieldLoader = ExtContext.getInstance().getConstant("fieldLoader");
        if (fieldLoader == null) {
            throw new ExtRuntimeException("未配置 fieldLoader.");
        } else {
            try {
                loader = (CrossFieldLoader)Class.forName(fieldLoader).newInstance();
                loader.setRequest(this.request);
            } catch (Exception var9) {
                String err = ConstantsEngine.replace("交叉报表中配置的指标 $0 在sql中未查询出数据.", fieldLoader);
                throw new ExtRuntimeException(err);
            }

            this.request.setAttribute("ext.view.fieldLoader", loader);
        }
    }

    public String buildMV(Element mv, ServletContext sctx) throws Exception {
        ContextEmitter emitter = new HTMLEmitter();
        return this.buildMV(mv, emitter, sctx);
    }

    public String buildMV(Element mv, ContextEmitter emitter, ServletContext sctx) throws Exception {
        if (mv instanceof MVContext) {
            MVContext mvo = (MVContext)mv;
            String formId = "f" + IdCreater.create();
            mvo.setFormId(formId);
            ExtContext.getInstance().putMVContext(mvo, false);
        }

        Map<String, InputField> params = this.params;
        InputOption option = InputOptionFactory.createInputOption(this.request, this.response, params);
        String ret = BuilderManager.buildContext2String(mv, this.request, this.envirCtx, this.response, this.dao, emitter, option, sctx);
        return ret;
    }

    public void initJSFunc(String scripts) throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        engine.put("request", this.request);
        Compilable compilable = (Compilable)engine;
        CompiledScript compiled = compilable.compile(scripts);
        compiled.eval();
        this.request.setAttribute("ext.script.engine", engine);
    }

    public ExtRequest getRequest() {
        return this.request;
    }

    public ExtResponse getResponse() {
        return this.response;
    }

    public ServletContext getContext() {
        return this.context;
    }

    public void setDao(DaoHelper dao) {
        this.dao = dao;
    }
}
