//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractBuilder {
    protected ExtRequest request;
    protected ContextEmitter emitter;
    protected DaoHelper daoHelper;
    protected ExtEnvirContext veloContext;
    protected ExtResponse response;
    protected InputOption option;
    protected ServletContext servletContext;

    public AbstractBuilder() {
    }

    protected abstract void processStart() throws ExtConfigException, ScriptEnginerException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException, DocumentException, ServletException, WriteException, InvalidFormatException;

    protected abstract void processEnd() throws IOException;
}
