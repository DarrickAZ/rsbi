//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.InputOptionFactory;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.html.IncludeContext;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.StringBufferWriterImpl;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class IncludeBuilder extends AbstractBuilder {
    private IncludeContext a;

    public IncludeBuilder(IncludeContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws ExtConfigException, ServletException, IOException {
        if (this.a.getMvid() != null && this.a.getMvid().length() > 0) {
            StringBufferWriterImpl var1 = new StringBufferWriterImpl();
            Map var2 = ExtContext.getInstance().getMVContext(this.a.getMvid()).getMvParams();
            MVContext var3 = RuleUtils.findCurMV(this.veloContext);
            Map var4 = var3.getMvParams();
            HashMap var5 = new HashMap();
            Entry var6;
            Iterator var7;
            if (var2 != null) {
                var7 = var2.entrySet().iterator();

                while(var7.hasNext()) {
                    var6 = (Entry)var7.next();
                    var5.put((String)var6.getKey(), (InputField)var6.getValue());
                }
            }

            if (var4 != null) {
                var7 = var4.entrySet().iterator();

                while(var7.hasNext()) {
                    var6 = (Entry)var7.next();
                    var5.put((String)var6.getKey(), (InputField)var6.getValue());
                }
            }

            InputOption var11 = InputOptionFactory.createInputOption(this.request, this.response, var5);
            ExtEnvirContextImpl var12 = new ExtEnvirContextImpl(var11);
            Iterator var9 = var5.entrySet().iterator();

            Entry var8;
            InputField var10;
            while(var9.hasNext()) {
                var8 = (Entry)var9.next();
                var10 = (InputField)var8.getValue();
                var12.put(var10.getId(), this.veloContext.get(var10.getId()));
            }

            try {
                BuilderManager.buildContext(ExtContext.getInstance().getMVContext(this.a.getMvid()), this.request, var1, this.daoHelper, var12, this.response, var11, this.servletContext);
            } catch (AuthException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (ScriptEnginerException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.a.setContent(var1.toString());
            var9 = var5.entrySet().iterator();

            while(var9.hasNext()) {
                var8 = (Entry)var9.next();
                var10 = (InputField)var8.getValue();
                this.veloContext.put(var10.getId(), var12.get(var10.getId()));
            }
        }

        this.emitter.startInclude(this.a);
    }
}
