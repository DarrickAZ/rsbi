//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.tab;

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
import com.ruisi.ext.engine.view.context.tab.TabContext;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;
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

public class TabBuilder extends AbstractBuilder {
    private TabContext a;

    public TabBuilder(TabContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
        this.emitter.endTab(this.a);
    }

    @Override
    protected void processStart() throws ExtConfigException {
        String var1 = this.a.getRef();
        TabViewContext var2 = (TabViewContext)this.a.getParent();
        if (!var2.isAjax()) {
            StringBufferWriterImpl var3 = new StringBufferWriterImpl();
            Map var4 = ExtContext.getInstance().getMVContext(var1).getMvParams();
            MVContext var5 = RuleUtils.findCurMV(this.veloContext);
            Map var6 = var5.getMvParams();
            HashMap var7 = new HashMap();
            Entry var8;
            Iterator var9;
            if (var4 != null) {
                var9 = var4.entrySet().iterator();

                while(var9.hasNext()) {
                    var8 = (Entry)var9.next();
                    var7.put((String)var8.getKey(), (InputField)var8.getValue());
                }
            }

            if (var6 != null) {
                var9 = var6.entrySet().iterator();

                while(var9.hasNext()) {
                    var8 = (Entry)var9.next();
                    var7.put((String)var8.getKey(), (InputField)var8.getValue());
                }
            }

            InputOption var13 = InputOptionFactory.createInputOption(this.request, this.response, var7);
            ExtEnvirContextImpl var14 = new ExtEnvirContextImpl(var13);
            Iterator var11 = var7.entrySet().iterator();

            Entry var10;
            InputField var12;
            while(var11.hasNext()) {
                var10 = (Entry)var11.next();
                var12 = (InputField)var10.getValue();
                var14.put(var12.getId(), this.veloContext.get(var12.getId()));
            }

            try {
                BuilderManager.buildContext(ExtContext.getInstance().getMVContext(var1), this.request, var3, this.daoHelper, var14, this.response, var13, this.servletContext);
            } catch (AuthException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ServletException e) {
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
            this.a.setContent(var3.toString());
            var11 = var7.entrySet().iterator();

            while(var11.hasNext()) {
                var10 = (Entry)var11.next();
                var12 = (InputField)var10.getValue();
                this.veloContext.put(var12.getId(), var14.get(var12.getId()));
            }
        }

        this.emitter.startTab(this.a);
    }
}
